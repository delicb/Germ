package germ.model;

import germ.actions.ActionManager;
import germ.app.Application;
import germ.command.CommandManager;
import germ.configuration.ConfigurationManager;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.MainWindow;
import germ.i18n.Messages;
import germ.model.event.UpdateEvent;
import germ.model.event.UpdateListener;
import germ.model.workspace.Project;
import germ.util.Counter;
import germ.util.Cursors;
import germ.view.GERMView;
import germ.view.LinkPainter;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GERMModel {

	protected ArrayList<Node> nodes = new ArrayList<Node>();
	// promenjiva kojoj pristupa JTree i na osnovu koje se iscrtava...
	protected ArrayList<Node> nodesImmutableIndexes = new ArrayList<Node>();

	// Lista ghost nodova (nodova na starim pozicijama, pre pomeranja)
	protected ArrayList<Node> ghostNodes = new ArrayList<Node>();
	protected transient ArrayList<Node> selectedNodes = new ArrayList<Node>();
	protected transient Node mouseOverNode;
	protected ArrayList<Link> links = new ArrayList<Link>();
	protected transient Link selectedLink = null;
	protected transient Point selectedBreakpoint = null;
	/**
	 * alteredLinks je lista svih linkova cija lokacija je azurirana u tekucem
	 * koraku. Sluzi kao zastita da se ne bi neki linkovi vise puta pomerili u
	 * tekucem koraku.
	 */
	protected transient ArrayList<Link> alteredLinks = new ArrayList<Link>();
	protected transient Point lasoBegin = new Point();
	protected transient Point lasoEnd = new Point();
	protected transient EventListenerList listenerList = new EventListenerList();
	protected transient UpdateEvent updateEvent = null;
	protected transient Project project;
	private String name;
	private AffineTransform transform;

	protected transient GERMView view;
	protected transient CommandManager commandManager;

	protected transient boolean changed = false;
	private transient int commandsCountOnSave = 0;

	protected Hashtable<String, Counter> counters = new Hashtable<String, Counter>();

	public GERMModel(String name, Project project) {
		this.name = name;
		this.project = project;
		this.view = new GERMView(this);
		this.transform = view.getTransform();
		this.commandManager = new CommandManager(this);
		this.addUpdateListener(view);
		initaliseCounters();
		fireUpdatePerformed();
	}

	private void initaliseCounters() {
		counters.put("stakeholder", new Counter()); //$NON-NLS-1$
		counters.put("assumption", new Counter()); //$NON-NLS-1$
		counters.put("argument", new Counter()); //$NON-NLS-1$
		counters.put("position", new Counter()); //$NON-NLS-1$
		counters.put("decision", new Counter()); //$NON-NLS-1$
		counters.put("topic", new Counter()); //$NON-NLS-1$
		counters.put("requirement", new Counter()); //$NON-NLS-1$
	}

	public long getCounter(String type) {
		return counters.get(type).next();
	}

	public ArrayList<Node> getSelectedNodes() {
		return selectedNodes;
	}

	public ArrayList<Node> getGhostNodes() {
		return ghostNodes;
	}

	public void addLink(Link link) {
		links.add(link);
		for (Node node : nodes) {
			if (link.source == node || link.destination == node)
				node.addLink(link);
		}
		fireUpdatePerformed();
	}

	public Link getLastLink() {
		if (links.isEmpty())
			return null;
		return links.get(getLinkCount() - 1);
	}

	public void removeLink(Link link) {
		links.remove(link);
		selectedLink = null;
		for (Node node : nodes) {
			if (link.source == node || link.destination == node)
				node.removeLink(link);
		}
		fireUpdatePerformed();
	}

	public void updatePerformed() {
		fireUpdatePerformed();
	}

	public int getLinkCount() {
		return links.size();
	}

	public Link getLinkAt(int i) {
		return (Link) links.get(i);
	}

	public Iterator<Link> getLinkIterator() {
		return links.iterator();
	}

	// Iterator ghost nodova
	public Iterator<Node> getGhostNodesIterator() {
		return ghostNodes.iterator();
	}

	// dodavanje ghost noda
	public void addGhostNode(Node node) {
		ghostNodes.add((Node) (node.clone()));
	}

	public void selectNode(Node node) {
		selectedNodes.add(node);
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	public void deselectAllNodes() {
		selectedNodes.clear();
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	public void deselectNode(Node node) {
		selectedNodes.remove(node);
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	public void selectNode(Shape s) {
		Iterator<Node> it = getNodeIterator();
		Rectangle2D rect = new Rectangle2D.Double();
		while (it.hasNext()) {
			Node n = it.next();
			rect.setFrame(n.getPosition().getX(), n.getPosition().getY(), n
					.getSize().width, n.getSize().height);
			if (s.intersects(rect))
				toggleNodeSelect(n, false);
		}
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	public Iterator<Node> getSelectedNodesIterator() {
		return selectedNodes.iterator();
	}

	public int selectedNodesCount() {
		return selectedNodes.size();
	}

	public Boolean isNodeIsSelected(Node node) {
		if (selectedNodes.contains(node))
			return true;
		else
			return false;
	}

	/**
	 * Vrši inverziju selekcije nodova. Svi selektovani se deselektuju, a svi
	 * deselektovani postaju selektovani.
	 */
	public void inverseSelection() {
		for (Node node : nodes)
			toggleNodeSelect(node, false);
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	@SuppressWarnings("unchecked")
	public void selectAllNodes() {
		selectedNodes.clear();
		selectedNodes = (ArrayList<Node>) nodes.clone();
		updateSelectionStatusBar();
		fireUpdatePerformed();
	}

	/**
	 * Vrši izmenu stanja selekcije noda, ako je selektovan, postaje
	 * deselektovan, ako je deselektovan postaje selektovan.
	 * 
	 * @param node
	 *            kome se menja stanje selekcije
	 * @param updateNeeded
	 *            parametar kojim odredjujemo da li poziv ove metode treba da
	 *            pozove metodu fireUpdatePerformed (radi izbegavanja
	 *            nepotrebnih poziva).
	 */
	public void toggleNodeSelect(Node node, boolean updateNeeded) {
		if (selectedNodes.contains(node))
			selectedNodes.remove(node);
		else
			selectedNodes.add(node);
		updateSelectionStatusBar();
		if (updateNeeded)
			fireUpdatePerformed();
	}

	/**
	 * Glavna metoda za korekciju položaja nodova. Vrši korekciju položaja svih
	 * nodova koji su joj prosleđeni, kao i svih linkova koji povezuju elemente
	 * koji su prosleđeni.
	 * 
	 * @param offsetX
	 *            pomeraj X
	 * @param offsetY
	 *            pomeraj Y
	 */
	public void updateSelectedNodesPosition(int offsetX, int offsetY) {
		alteredLinks.clear();
		Iterator<Node> it = selectedNodes.iterator();
		while (it.hasNext()) {
			Node current = it.next();
			current.position.setLocation(
					current.getPosition().getX() - offsetX, current
							.getPosition().getY()
							- offsetY);
			updateLinkBreakpointPosition(current, offsetX, offsetY);
		}
		fireUpdatePerformed();
	}

	/**
	 * Metoda koja vrši snap prilikom pomeranja noda ukoliko se nalazi po 90
	 * stepeni u odnosu na drugi nod (ako je link bez breakpointova) ili u
	 * odnosu na prvi breakpoint linka.
	 * 
	 * @param hitNode
	 *            za koji proveravamo da li treba da se snapuje
	 * @param offsetX
	 *            pomeraj misa po X osi
	 * @param offsetY
	 *            pomeraj misa po Y osi
	 */
	public void updateSelectedNodesPosition90(Node hitNode, int offsetX,
			int offsetY) {
		Point centerNodePoint;
		Point comparePoint;
		boolean movedX = false;
		boolean movedY = false;
		int newOffsetX = 0;
		int newOffsetY = 0;
		// prolazi kroz sve linkove koji su povezani na node
		for (Link link : hitNode.getLinks()) {
			// prekidamo prolazak ukoliko su pomeraji vec napravljeni
			if (movedX && movedY)
				break;

			if (link.getSource() == hitNode) { // ako je node source linka
				if (link.getBreakPointCount() > 0)
					comparePoint = link.getBreakPointAt(0);
				else
					comparePoint = link.getDestinationCenterPosition();
				centerNodePoint = link.getSourceCenterPosition();
			} else { // ako je node dest. linka
				if (link.getBreakPointCount() > 0)
					comparePoint = link.getBreakPointAt(link
							.getBreakPointCount() - 1);
				else
					comparePoint = link.getSourceCenterPosition();
				centerNodePoint = link.getDestinationCenterPosition();
			}
			// ako nije došlo već do pomeraja X i ako postoje uslovi za snapX
			if (!movedX && snapByX(centerNodePoint, comparePoint, true)) {
				newOffsetX = centerNodePoint.x - comparePoint.x;
				movedX = true;
			}
			// ako nije došlo već do pomeraja Y i ako postoje uslovi za snapY
			if (!movedY && snapByY(centerNodePoint, comparePoint, true)) {
				newOffsetY = centerNodePoint.y - comparePoint.y;
				movedY = true;
			}
		}
		if (!movedX)
			newOffsetX = offsetX;
		if (!movedY)
			newOffsetY = offsetY;

		updateSelectedNodesPosition(newOffsetX, newOffsetY);
	}

	/**
	 * Vrši korekciju položaja selektovanog breakpointa. Prethodno vrši proveru
	 * da li postoji selektovan breakpoint
	 */
	public void updateSelectedBreakPointPosition() {
		GERMView v = getView();
		if (selectedBreakpoint != null) {
			selectedBreakpoint.setLocation(v.lastPosition);
			fireUpdatePerformed();
		}
	}

	/**
	 * Vrši korekciju položaja selektovanog breakpointa. Prethodno vrši proveru
	 * da li postoji selektovan breakpoint. Pri tome, vrši snapovanje pozicije
	 * koji zadovoljavaju odredjenu toleranciju na poziciju prethodnog ili
	 * sledećeg breakpointa po 90 stepeni.
	 */
	public void updateSelectedBreakPointPosition90() {
		if (selectedBreakpoint != null) {
			int selectedNo = selectedLink.getBreakPoints().lastIndexOf(
					selectedBreakpoint);
			Point prevBreakpoint;
			Point nextBreakpoint;

			// ako je selektovani breakpoint poslednji na listi
			if (selectedNo == selectedLink.getBreakPoints().size() - 1)
				nextBreakpoint = selectedLink.getDestinationCenterPosition();
			else
				nextBreakpoint = selectedLink.getBreakPointAt(selectedNo + 1);

			// ako je selektovani breakpoint prvi u listi
			if (selectedNo == 0)
				prevBreakpoint = selectedLink.getSourceCenterPosition();
			else
				prevBreakpoint = selectedLink.getBreakPointAt(selectedNo - 1);

			GERMView v = getView();

			// X
			if (prevBreakpoint != null
					&& snapByX(prevBreakpoint, selectedBreakpoint, false))
				selectedBreakpoint.setLocation(prevBreakpoint.x,
						selectedBreakpoint.getLocation().getY());

			else if (nextBreakpoint != null
					&& snapByX(nextBreakpoint, selectedBreakpoint, false))
				selectedBreakpoint.setLocation(nextBreakpoint.x,
						selectedBreakpoint.getLocation().getY());
			else
				selectedBreakpoint.setLocation(v.lastPosition.getX(),
						selectedBreakpoint.getY());

			// Y
			if (prevBreakpoint != null
					&& snapByY(prevBreakpoint, selectedBreakpoint, false))
				selectedBreakpoint.setLocation(selectedBreakpoint.getLocation()
						.getX(), prevBreakpoint.y);
			else if (nextBreakpoint != null
					&& snapByY(nextBreakpoint, selectedBreakpoint, false))
				selectedBreakpoint.setLocation(selectedBreakpoint.getLocation()
						.getX(), nextBreakpoint.y);
			else
				selectedBreakpoint.setLocation(selectedBreakpoint.getX(),
						v.lastPosition.getY());

			fireUpdatePerformed();
		}
	}

	/**
	 * Metoda vraća true ako otherBreakpoint zadovoljava toleranciju da se po X
	 * osi izjednači sa prosledjenim breakpointom.
	 * 
	 * @param breakpoint
	 *            čiju uporedjujemo X poziciju u odnosu na selektovani
	 * @param otherBreakpoint
	 *            sa kojim uporedjujemo X poziciju u odnosu na selektovani
	 * @param nodeInc
	 *            da li je potrebno inkrementirati toleranciju (ako se radi o
	 *            nodu, a ne o breakpointu)
	 * @return da li su zadovoljeni uslovi za snap po X
	 */
	boolean snapByX(Point breakpoint, Point otherBreakpoint, boolean nodeInc) {
		GERMView v = getView();
		double minimumScale = v.getTransform().getScaleX();
		if (minimumScale < 0.9)
			minimumScale = 0.9;
		double tol = InternalConfiguration.SNAP_TOLERANCE / minimumScale;
		if (nodeInc)
			tol += InternalConfiguration.SNAP_NODE_INC;

		return (breakpoint.x - tol < otherBreakpoint.x
				&& breakpoint.x + tol > otherBreakpoint.x
				&& v.lastPosition.getX() < otherBreakpoint.x + 2 * tol && v.lastPosition
				.getX() > otherBreakpoint.x - 2 * tol);
	}

	/**
	 * Metoda vraća true ako otherBreakpoint zadovoljava toleranciju da se po Y
	 * osi izjednači sa prosledjenim breakpointom.
	 * 
	 * @param breakpoint
	 *            čiju uporedjujemo Y poziciju u odnosu na selektovani
	 * @param otherBreakpoint
	 *            sa kojim uporedjujemo Y poziciju u odnosu na selektovani
	 * @param nodeInc
	 *            da li je potrebno inkrementirati toleranciju (ako se radi o
	 *            nodu, a ne o breakpointu)
	 * @return da li su zadovoljeni uslovi za snap po Y
	 */
	boolean snapByY(Point breakpoint, Point otherBreakpoint, boolean nodeInc) {
		GERMView v = getView();
		double minimumScale = v.getTransform().getScaleX();
		if (minimumScale < 0.9)
			minimumScale = 0.9;
		double tol = InternalConfiguration.SNAP_TOLERANCE / minimumScale;
		if (nodeInc)
			tol += InternalConfiguration.SNAP_NODE_INC;
		return (breakpoint.y - tol < otherBreakpoint.y
				&& breakpoint.y + tol > otherBreakpoint.y
				&& v.lastPosition.getY() < otherBreakpoint.y + 2 * tol && v.lastPosition
				.getY() > otherBreakpoint.y - 2 * tol);
	}

	/**
	 * Vrsi korekciju breakpointa linkova koji su povezani za nod koji se
	 * pomera. Korekcija pozicije se vrsi samo za linkove čija se oba noda
	 * pomeraju. Ukoliko je u tekućem koraku već izvršena korekcija pozicije
	 * breaktpointova nekog linka, on će biti preskočen.
	 * 
	 * @param node
	 *            koji je pomeren
	 * @param offsetX
	 *            pomeraj X
	 * @param offsetY
	 *            pomeraj Y
	 * 
	 */
	public void updateLinkBreakpointPosition(Node node, int offsetX, int offsetY) {
		for (Link link : node.links) {
			if (alteredLinks.contains(link))
				continue;
			if (!otherNodeHasLink(link, node))
				continue;
			for (Point current : link.breakPoints) {
				current.setLocation(current.getX() - offsetX, current.getY()
						- offsetY);
			}
			alteredLinks.add(link);
		}
	}

	/**
	 * Metoda puni ArrayList<Link> alteredLinks sa svim linkovima koji će biti
	 * pomereni kako bi se mogla napraviti Move komanda koja ce lokacije tih
	 * linkova zapamtiti U sustini je to kombinacija updateNodesPosition(...) i
	 * updateLinkBreakpointPosition(...) samo sto ne menja pozicije.
	 */
	public ArrayList<Link> loadAlteredLinks() {
		alteredLinks.clear();
		Iterator<Node> it = nodes.iterator();
		while (it.hasNext()) {
			Node current = it.next();
			for (Link link : current.links) {
				if (alteredLinks.contains(link))
					continue;
				if (!otherNodeHasLink(link, current))
					continue;
				alteredLinks.add(link);
			}
		}
		return alteredLinks;
	}

	/**
	 * Metoda vraca true ukoliko bilo koji Node sem prosledjenog ima link koji
	 * je prosledjen.
	 * 
	 * @param link
	 * @param node
	 * @return rezultat
	 */
	private boolean otherNodeHasLink(Link link, Node node) {
		for (Node current : selectedNodes) {
			if (current == node)
				continue;
			if (current == link.source || current == link.destination) {
				return true;
			}
		}
		return false;
	}

	public void addNode(Node node) {
		nodes.add(node);
		nodesImmutableIndexes.add(node);
		fireUpdatePerformed();
	}

	public void removeNode(Node node) {
		if (isNodeIsSelected(node))
			deselectNode(node);
		nodes.remove(node);
		nodesImmutableIndexes.remove(node);
		for (Link l : node.getLinks())
			removeLink(l);
		setMouseOverNode(null);
		fireUpdatePerformed();
	}

	public int getNodeCount() {
		return nodes.size();
	}

	public Node getNodeAt(int i) {
		return (Node) nodes.get(i);
	}

	public Iterator<Node> getNodeIterator() {
		return nodes.iterator();
	}

	/**
	 * Pronalazi indeks elementa koji se nalazi na zadatim logickim koordinatama
	 * 
	 * @param point
	 * @return
	 */
	public int getNodeAtPosition(Point2D point) {
		for (int i = getNodeCount() - 1; i >= 0; i--) {
			Node node = getNodeAt(i);
			if (node.getPainter().isElementAt(point)) {
				return i;
			}
		}
		return -1;
	}

	public int getLinkAtPosition(Point2D point) {
		for (int i = getLinkCount() - 1; i >= 0; i--) {
			Link link = getLinkAt(i);
			if (link.getPainter().isElementAt(point)) {

				return i;
			}
		}
		return -1;
	}

	public void setSelectedBreakpoint(Point lastPosition) {
		Point bp = null;
		if (selectedLink != null) {
			bp = ((LinkPainter) selectedLink.getPainter())
					.getBreakpointAt(lastPosition);
		}
		selectedBreakpoint = bp;
	}

	public Point getSelectedBreakpoint() {
		return selectedBreakpoint;
	}

	/**
	 * Metoda vraca pravougaonik koji obuhvata sve Nodove koje je potrebno da
	 * obuhvati. U slucaju da postoji barem 1 selektovan element, zoom ce biti
	 * prilagodjen tako da selektovani elementi budu sigurno prikazani.
	 */
	public Rectangle getBestFitRectangle(boolean selectionOnly) {
		double x1, x2, y1, y2;
		Point2D current;
		Dimension2D dim;
		Node node;
		Iterator<Node> it;
		if (selectionOnly) {
			if (selectedNodesCount() > 0) {
				it = getSelectedNodesIterator();
				node = selectedNodes.get(0);
			} else
				return null;
		} else {
			if (getNodeCount() > 0) {
				it = getNodeIterator();
				node = nodes.get(0);
			} else
				return null;
		}
		current = node.getPosition();
		x1 = current.getX();
		x2 = current.getX();
		y1 = current.getY();
		y2 = current.getY();

		while (it.hasNext()) {
			node = it.next();
			current = node.getPosition();
			dim = node.getSize();
			if (current.getX() < x1)
				x1 = current.getX();
			if (current.getX() + dim.getWidth() > x2)
				x2 = current.getX() + dim.getWidth();
			if (current.getY() < y1)
				y1 = current.getY();
			if (current.getY() + dim.getHeight() > y2)
				y2 = current.getY() + dim.getHeight();
		}
		Rectangle result = new Rectangle();

		result.setFrameFromDiagonal(x1 - 10, y1 - 10, x2 + 10, y2 + 30);
		return result;
	}

	/**
	 * Pomera element na kraj liste
	 * 
	 * @param nodeInMotion
	 */
	public void nodeToFront(int nodeInMotion) {
		nodes.add(getNodeCount(), nodes.get(nodeInMotion));
		nodes.remove(nodeInMotion);

	}

	public void addUpdateListener(UpdateListener l) {
		listenerList.add(UpdateListener.class, l);
	}

	public void removeUpdateListener(UpdateListener l) {
		listenerList.remove(UpdateListener.class, l);
	}

	public Point getLasoBegin() {
		return lasoBegin;
	}

	public void setLasoBegin(Point lasoBegin) {
		this.lasoBegin = lasoBegin;
	}

	public Point getLasoEnd() {
		return lasoEnd;
	}

	public void setLasoEnd(Point lasoEnd) {
		this.lasoEnd = lasoEnd;
		fireUpdatePerformed();
	}

	public Link getSelectedLink() {
		return selectedLink;
	}

	public Node getMouseOverNode() {
		return this.mouseOverNode;
	}

	public void setMouseOverNode(Node n) {
		this.mouseOverNode = n;
	}

	public void setSelectedLink(Link selectedLink) {
		this.selectedLink = selectedLink;
		Application app = Application.getInstance();
		if (selectedLink == null) {
			app.getView().setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
			selectedBreakpoint = null;
		} else {
			app.getView().setCursor(Cursors.getCursor("inverse")); //$NON-NLS-1$
		}
		updateActionButtons(app);
		fireUpdatePerformed();
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	/**
	 * Podrška za JTree komponentu, kod koje je bitno da elementi ne menjaju
	 * poziciju prilikom promene selekcije.
	 * 
	 * @param i
	 *            Indeks elementa
	 * @return Element na zadatoj poziciji
	 */
	public Node getNodeAtImmutableIndex(int i) {
		return nodesImmutableIndexes.get(i);
	}

	public int getNodeIndexImmutable(Node gate) {
		return nodesImmutableIndexes.indexOf(gate);
	}

	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	public String getFileName() {
		return this.getName() + "." + InternalConfiguration.DIAGRAM_EXTENSION; //$NON-NLS-1$
	}

	/**
	 * Javljamo svim listenerima da se događaj desio
	 */
	protected void fireUpdatePerformed() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UpdateListener.class) {
				// Lazily create the event:
				if (updateEvent == null)
					updateEvent = new UpdateEvent(this);
				((UpdateListener) listeners[i + 1])
						.updatePerformed(updateEvent);
			}
		}
	}

	public GERMView getView() {
		return view;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public boolean isChanged() {
		return this.changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
		Application.getInstance().getMainWindow().renameDiagram(this);
	}

	/**
	 * Javalja modelu da je izvrsena komanda da bi model znao da updateuje
	 * <code>changed</code> polje u zavisnosti od broja komandi koje su
	 * izvrsene.
	 */
	public void commandExecuted() {
		if (commandManager.getCommandsCount() == commandsCountOnSave)
			setChanged(false);
		else
			setChanged(true);
	}

	/**
	 * Upisuje sve relevantne podatke dijagrama u file.
	 */
	public void save() {
		try {
			String diagramFile = project.getProjectDir().getCanonicalPath()
					+ File.separator + getFileName();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(diagramFile), "UTF8")); //$NON-NLS-1$

			XStream xstream = new XStream(new DomDriver());
			xstream.toXML(this, out);

			// nakon sto smo sacuvali dijagram vise nije menjan
			setChanged(false);
			this.commandsCountOnSave = commandManager.getCommandsCount();
		} catch (XStreamException e) {
			System.err
					.println(Messages.getString("GERMModel.11") + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ucitava sve savucane podatke dijagrama iz fajla
	 */
	public void load() {
		try {
			String diagramFile = project.getProjectDir().getCanonicalPath()
					+ File.separator + name + "." //$NON-NLS-1$
					+ InternalConfiguration.DIAGRAM_EXTENSION;
			XStream xstream = new XStream(new DomDriver());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(diagramFile), "UTF8"));
			xstream.fromXML(in, this);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (StreamException e) {
			System.err.println(Messages.getString("GERMModel.13")); //$NON-NLS-1$
		}
	}

	public Project getProject() {
		return project;
	}

	/**
	 * Update-uje status bar sa podacima o aktuelnoj selekciji
	 */
	public void updateSelectionStatusBar() {
		MainWindow mw = Application.getInstance().getMainWindow();
		 updateActionButtons(Application.getInstance());
		// ako je selektovan barem jedan node
		if (selectedNodes.size() > 0) {
			// ako je selektovan tacno jedan node
			if (selectedNodes.size() == 1) {
				mw.setStatusBarMessage(Messages.getString("GERMModel.10") //$NON-NLS-1$
						+ selectedNodes.get(0).name, 0);
				// ako je selektovano najmanje 2 noda, provera da li su
				// selektovani svi
			} else {
				if (selectedNodes.size() == nodes.size())
					mw.setStatusBarMessage(Messages.getString("GERMModel.9") //$NON-NLS-1$
							+ nodes.size() + ")", 0);
				// ako je selektovano najmanje 2 noda, ali nisu svi
				else {
					String kobaja = new String();
					String selectionPrefix = Messages.getString("GERMModel.8"); //$NON-NLS-1$
					Node current;
					for (int i = 0; i < selectedNodes.size(); i++) {
						current = selectedNodes.get(i);
						int max = ConfigurationManager.getInstance().getInt(
								"maxStatusLabel0Length");
						if (selectionPrefix.length() + kobaja.length()
								+ current.name.length() < max) {
							kobaja += current.name;
							if (i + 1 < selectedNodes.size())
								kobaja += ", ";
						} else {
							kobaja += "...";
							break;
						}
					}
					mw.setStatusBarMessage(selectionPrefix + " ("
							+ selectedNodes.size() + "): " + kobaja, 0);
				}
			}
		} else
			// nije selektovan ni jedan
			mw.setStatusBarMessage(Messages.getString("GERMModel.7"), 0); //$NON-NLS-1$
	}

	public void updateActionButtons(Application app) {
		ActionManager am = app.getActionManager();
		if (selectedNodes.size() == 0) {
			am.getCutAction().setEnabled(false);
			am.getCopyAction().setEnabled(false);
			am.getDeleteAction().setEnabled(false);
			am.getDistributeHorizontalAction().setEnabled(false);
			am.getDistributeVerticalAction().setEnabled(false);
			am.getAlignBottomAction().setEnabled(false);
			am.getAlignLeftAction().setEnabled(false);
			am.getAlignRightAction().setEnabled(false);
			am.getAlignTopAction().setEnabled(false);
		}
		if (selectedNodes.size() == 1) {
			am.getCutAction().setEnabled(true);
			am.getCopyAction().setEnabled(true);
			am.getDeleteAction().setEnabled(true);
			am.getDistributeHorizontalAction().setEnabled(false);
			am.getDistributeVerticalAction().setEnabled(false);
			am.getAlignBottomAction().setEnabled(false);
			am.getAlignLeftAction().setEnabled(false);
			am.getAlignRightAction().setEnabled(false);
			am.getAlignTopAction().setEnabled(false);

		}
		if (selectedNodes.size() > 1) {
			am.getCutAction().setEnabled(true);
			am.getCopyAction().setEnabled(true);
			am.getDeleteAction().setEnabled(true);
			am.getDistributeHorizontalAction().setEnabled(true);
			am.getDistributeVerticalAction().setEnabled(true);
			am.getAlignBottomAction().setEnabled(true);
			am.getAlignLeftAction().setEnabled(true);
			am.getAlignRightAction().setEnabled(true);
			am.getAlignTopAction().setEnabled(true);
		}
		if(selectedLink != null && selectedLink.source != null && selectedLink.destination!=null)
			am.getDeleteAction().setEnabled(true);
	}

	/**
	 * Metoda koju XStream poziva kad zavrsi vracanje podataka iz fajla. Ovde
	 * treba staviti inicijalizaciju svega sto se ne cuva u fajlu, a treba da
	 * postoji u modelu.
	 * 
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private GERMModel readResolve() {
		this.nodesImmutableIndexes = (ArrayList<Node>) nodes.clone();
		if (this.transform != null) {
			this.view.setTransform(this.transform);
		}
		return this;
	}

	public String getDiagramPath() throws IOException {
		String diagramPath = getProject().getProjectDir().getCanonicalPath()
				+ File.separator + getFileName();
		return diagramPath;
	}

	public File getDiagramDir() {
		try {
			File diagramDir = new File(getDiagramPath());
			return diagramDir;
		} catch (IOException e) {
			return null;
		}
	}

	public ArrayList<Link> getAlteredLinks() {
		return alteredLinks;
	}

}
