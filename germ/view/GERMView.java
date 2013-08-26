package germ.view;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.MainWindow;
import germ.gui.windows.TimerThread;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;
import germ.model.event.UpdateEvent;
import germ.model.event.UpdateListener;
import germ.state.StateMachine;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GERMView extends JPanel implements UpdateListener {

	/**
	 * Model ovog view-a
	 */
	protected GERMModel model;

	/**
	 * Predstavlja tansformaciju u kojoj se cuva translacija i zoom factor (jer
	 * nista drugo ne koristimo).
	 */
	private AffineTransform transform = new AffineTransform();

	/**
	 * Pozicija na kojoj je izvrsena bilo koja promena na canvasu (bilo koji
	 * klik, move, drag).
	 */
	public Point2D lastPosition;

	/**
	 * Pravougaonik za crtanje lasa.
	 */
	protected Rectangle2D lasoRectangle = new Rectangle2D.Double();

	/**
	 * Polje koje sadrži informaciju da li je laso uključen da bi repaint znao
	 * da li ga crta.
	 */
	public boolean lasoEnabled = false;

	/**
	 * Nod koji se resize-uje.
	 */
	private Node nodeChangingShape = null;

	/**
	 * Handle na koji je kliknuto za resize node {@link #nodeChangingShape}
	 */
	private Handle handleMoving = null;

	/**
	 * Promenljiva koja pamti da li je kursor na canvasu ili nije. Updateuje se
	 * u kontroleru na mouseEntered i mouseExited metodama.
	 */
	private boolean cursorOnCanvas = false;

	/**
	 * Da li je kliknut desni taster za aktiviranje popup menija
	 */
	private boolean popupMenu = false;

	/**
	 * Klasa koja reaguje na svaku akciju miša na kanvasu.
	 */
	private class GERMController extends MouseAdapter {

		/**
		 * Reaguje na pritisak misa na canvasu. Update-uje
		 * {@link GERMView#hanleMoving} i javlja state mašini da se desio mouse
		 * pressed.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			lastPosition = e.getPoint();
			if (e.getButton() == MouseEvent.BUTTON3)
				GERMView.this.popupMenu = true;
			try {
				transform.inverseTransform(lastPosition, lastPosition);
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			handleMoving = getHandleForPoint(lastPosition);
			Application.getInstance().getStateMachine().mousePressed(e);
		}

		/**
		 * Javlja state mašni da se desio mouse klik ili dupli klik u zavisnosti
		 * od broja klikova.
		 */
		public void mouseClicked(MouseEvent e) {
			StateMachine sm = Application.getInstance().getStateMachine();
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				sm.mouseDoubleClicked(e);
			}
			sm.mouseClicked(e);
		}

		/**
		 * Javlja state mašini da se deslo otpuštanje miša i updateuje
		 * {@link GERMView#hanleMoving} i {@link GERMView#lastPosition}
		 */
		public void mouseReleased(MouseEvent e) {
			lastPosition = e.getPoint();
			try {
				transform.inverseTransform(lastPosition, lastPosition);
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			handleMoving = null;
			Application.getInstance().getStateMachine().mouseReleased(e);
		}

		/**
		 * Javlja state mašini da se desio događaj povlačenja miša i updateuje
		 * {@link GERMView#hanleMoving} i {@link GERMView#lastPosition}
		 */
		public void mouseDragged(MouseEvent e) {
			lastPosition = e.getPoint();
			try {
				transform.inverseTransform(lastPosition, lastPosition);
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			Application.getInstance().getStateMachine().mouseDragged(e);
			Application.getInstance().getMainWindow()
					.setStatusBarMessage(
							"<" + lastPosition.getX() + ","
									+ lastPosition.getY() + ">", 1);
		}

		/**
		 * Pomera canvas gore - dole. Ako je pritisnut shift taster pomera
		 * canvas levo - desno. Ako je pritisnut taster ctrl zoomira canvas.
		 */
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.isShiftDown()) {
				transform.translate(e.getWheelRotation()
						* InternalConfiguration.TRANSLATION_FACTOR_X
						/ transform.getScaleY(), 0);
			} else if (!e.isControlDown() && !e.isShiftDown()) {
				transform.translate(0, -e.getWheelRotation()
						* InternalConfiguration.TRANSLATION_FACTOR_Y
						/ transform.getScaleX());
			} else if (e.isControlDown()) {
				zoom(e);
			}
			repaint();
		}

		/**
		 * Javlja state mašini da se desio događaj pomeranja miša i aurira
		 * {@link GERMView#lastPosition}
		 */
		public void mouseMoved(MouseEvent e) {
			lastPosition = e.getPoint();
			try {
				transform.inverseTransform(lastPosition, lastPosition);
			} catch (NoninvertibleTransformException e1) {
				e1.printStackTrace();
			}
			Application.getInstance().getStateMachine().mouseMove(e);
			Application.getInstance().getMainWindow()
					.setStatusBarMessage(
							"<" + lastPosition.getX() + ","
									+ lastPosition.getY() + ">", 1);

		}

		/**
		 * Ažurira {@link GERMView#cursorOnCanvas}
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			GERMView.this.popupMenu = false;
			GERMView.this.cursorOnCanvas = true;
			GERMView.this.requestFocus();
		}

		/**
		 * Ažurira {@link GERMView#cursorOnCanvas}
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			GERMView.this.cursorOnCanvas = false;
		}
	}

	/**
	 * Listener klasa za reagovanje na pritisnute tastere (pomeranje elemenata
	 * strelicama)
	 */
	private class KeyController extends KeyAdapter {
		/**
		 * Javlja state mašini da se desio događaj pritiska tastera.
		 */
		public void keyPressed(KeyEvent e) {
			Application.getInstance().getStateMachine().keyPressed(e);
		}
	}

	/**
	 * Metoda sluzi za zumiranje iz stanja zumiranja ili na MouseWheel.<br/>
	 * <br/>
	 * 
	 * Princip rada:.
	 * <ol>
	 * <li>Izracuna se novi zoom koji treba da se podesi.</li>
	 * <li>Zapamti se tacka iznad koje je trenutno mis sa uvazenim DO SADA
	 * izvedenim transformacijama..</li>
	 * <li>Izvrsi se podesavanje transforma na novi zoom iz 1. koraka.</li>
	 * <li>Zapamti se tacka iznad koje je trenutno mis ALI sa uracunatom i
	 * poslednjom zoom transformacijom.</li>
	 * <li>Koordinatni sistem se translira za razliku tacaka izracunatih u
	 * koracima 2 i 4.</li>
	 * <li>Pozove se repaint kanvasa.</li>
	 * </ol>
	 * 
	 * @param ee
	 *            - MouseEvent koji se dobija iz state masine (stanje ZoomState)
	 *            ili MouseWheelEvent koji se dobija od mouseWheelMoved metode
	 *            View-a.
	 * @param args
	 *            - Postoji samo ukoliko state masina poziva ovu metodu i radi
	 *            zoomIn ako je true ili zoomOut ako je false.
	 */
	public void zoom(MouseEvent ee, Object... args) {
		float zoomLimit = InternalConfiguration.ZOOM_LIMIT;
		float zoomFactor = InternalConfiguration.ZOOM_FACTOR;
		Point2D oldPosition = ee.getPoint();
		Point2D newPosition = ee.getPoint();
		boolean animation = ConfigurationManager.getInstance().getBoolean(
				"animationEnabled");
		TimerThread tt = Application.getInstance().getMainWindow()
				.getTimerThread();
		double zoom;
		if (args.length == 0) {
			animation = false;
			MouseWheelEvent e = (MouseWheelEvent) ee;
			if (e.getWheelRotation() < 0)
				zoom = transform.getScaleX() * zoomFactor;
			else
				zoom = transform.getScaleX() / zoomFactor;
		} else {
			if ((Boolean) args[0])
				zoom = transform.getScaleX() * zoomFactor;
			else
				zoom = transform.getScaleX() / zoomFactor;
		}
		try {
			if (zoom > zoomLimit)
				zoom = zoomLimit;
			else if (zoom < 1 / zoomLimit)
				zoom = 1 / zoomLimit;
			transform.inverseTransform(oldPosition, oldPosition);
			if (!animation)
				transform.setToScale(zoom, zoom);
			transform.inverseTransform(newPosition, newPosition);
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
		if (animation) {
			tt.setFastZoomAndScale(true);
			tt.setIgnoreMinStepLimit(true);
			tt.setNewScale(zoom);
			tt.setRelocate(new Point2D.Double(newPosition.getX(), newPosition
					.getY()));
		} else {
			transform.translate(newPosition.getX() - oldPosition.getX(),
					newPosition.getY() - oldPosition.getY());
			repaint();
		}
	}

	/**
	 * Vrsi zumiranje kanvasa u centar ekrana za prosledjeni faktor.
	 * 
	 * @param factor
	 *            za koji je potrebno zumirati kanvas
	 */
	public void zoom(double factor) {
		Point center = new Point(getWidth() / 2, getHeight() / 2);
		Point2D oldPosition = center;
		Point2D newPosition = (Point2D) center.clone();
		double zoom = transform.getScaleX() + factor;
		float zoomLimit = InternalConfiguration.ZOOM_LIMIT;
		if (zoom > zoomLimit)
			zoom = zoomLimit;
		else if (zoom < 1 / zoomLimit)
			zoom = 1 / zoomLimit;
		try {
			transform.inverseTransform(oldPosition, oldPosition);
			transform.setToScale(zoom, zoom);
			transform.inverseTransform(newPosition, newPosition);
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
		transform.translate(newPosition.getX() - oldPosition.getX(),
				newPosition.getY() - oldPosition.getY());
		repaint();
	}

	/**
	 * Metoda vrsi zumiranje tako da pravougaonik koji je prosledjen sigurno
	 * bude vidljiv
	 * 
	 * @param rect
	 *            koji mora biti prikazan
	 */
	public void zoomTo(Rectangle2D rect) {
		float zoomLimit = InternalConfiguration.ZOOM_LIMIT;
		if (rect == null)
			return;

		Point rectCenter = new Point((int) rect.getCenterX(), (int) rect
				.getCenterY());

		double scale = getWidth() / rect.getWidth();
		if (scale > getHeight() / rect.getHeight())
			scale = getHeight() / rect.getHeight();

		if (scale > zoomLimit)
			scale = zoomLimit;

		if (ConfigurationManager.getInstance().getBoolean("animationEnabled")) {
			MainWindow mw = Application.getInstance().getMainWindow();

			mw.backupStatusBar();
			mw.setStatusBarMessage("Zooming...", 0);
			mw.getTimerThread().setRelocate(
					new Point2D.Double(rectCenter.x, rectCenter.y));

			mw.getTimerThread().setNewScale(scale);
		} else {
			transform.setToScale(scale, scale);
			Point screenCenter = new Point(getWidth() / 2, getHeight() / 2);
			try {
				transform.inverseTransform(screenCenter, screenCenter);
			} catch (NoninvertibleTransformException e) {
				e.printStackTrace();
			}
			transform.translate(screenCenter.getX() - rectCenter.x,
					screenCenter.getY() - rectCenter.y);
			repaint();
		}
	}

	/**
	 * Metoda vrsi translaciju po x i y osi.
	 * 
	 * @param x
	 *            translacija po x osi
	 * @param y
	 *            translacija po y osi
	 */
	public void moveWorkspace(double x, double y) {
		transform.translate(x, y);
		repaint();
	}

	public Rectangle2D getLasoRectangle() {
		return lasoRectangle;
	}

	public GERMModel getModel() {
		return model;
	}

	public GERMView(GERMModel _model) {
		super();

		setFocusable(true); // potrebno kako bi panel reagovao na dogadjaje
		// tastature
		model = _model;
		model.addUpdateListener(this);
		GERMController controller = new GERMController();
		addMouseListener(controller);
		addMouseMotionListener(controller);
		addMouseWheelListener(controller);
		addKeyListener(new KeyController());

		setBackground(ConfigurationManager.getInstance().getColor("background"));
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics) Metoda
	 *      prosiruje gore spomenutu metodu sa iscrtavanjem u zadatom redosledu:
	 *      <ol>
	 *      <li>Mrezu - ako je selektovano njeno iscrtavanje
	 *      <li>Laso
	 *      <li>Shadow elementi - ako su ukljuceni
	 *      <li>Veze - Link
	 *      <li>Elemente - Node
	 *      <li>Isprekidani pravougaonik i
	 *      <li>Handle-ove oko selektovanih elemenata
	 *      <li>Prelomne tacke selektovane veze - ako je selektovana veza
	 *      </ol>
	 */
	protected void paintComponent(Graphics g) {
		
		setBackground(ConfigurationManager.getInstance().getColor("background"));
		// iterator za node-ove
		Iterator<Node> it;

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Uvazavanje transformacija
		g2.transform(transform);

		// crtanje grida
		if (ConfigurationManager.getInstance().getBoolean("showGrid"))
			drawGrid(g2);
		// cranje lasa
		drawLaso(g2);

		// Ukljucujemo omeksavanje ivica (antialiasing)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Postavljanje transparencije
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.3f));

		if (ConfigurationManager.getInstance().getBoolean("nodeShadow")) {
			// senke node-ova
			it = model.getNodeIterator();
			while (it.hasNext()) {
				Node d = (Node) it.next();
				((NodePainter) d.getPainter()).drawShadow(g2);
			}
		}

		// crtanje ghostNodova
		it = model.getGhostNodesIterator();
		while (it.hasNext()) {
			Node d = (Node) it.next();
			d.getPainter().paint(g2);
		}

		// Postavljanje transparencije
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));

		// crtanje link-ova
		Iterator<Link> lit = model.getLinkIterator();
		while (lit.hasNext()) {
			Link l = (Link) lit.next();
			l.getPainter().paint(g2);
		}

		// crtanje node-ova
		it = model.getNodeIterator();
		while (it.hasNext()) {
			Node d = (Node) it.next();
			d.getPainter().paint(g2);
		}

		// crtanje okvira oko selektovanih nodova
		it = model.getSelectedNodesIterator();
		while (it.hasNext()) {
			Node n = (Node) it.next();
			drawNodeSelectionRectangles(g2, n);
		}

		// crtanje breakpointova selektovanog linka
		Link selectedLink = model.getSelectedLink();
		if (selectedLink != null)
			drawLinkBreakPoints(g2);
		drawLaso(g2);

		// crtanje indikatora da je mis na nodu (pre noda, da bude ispod)
		drawMouseOverNodeIndicator(g2);
	}

	/**
	 * Metoda za iscrtavanje selekcionog pravougaonika oko Node n pomocu
	 * Graphics2D objekta g2
	 * 
	 * @param g2
	 *            Graphics2D objekat za crtanje
	 * @param n
	 *            Node objekat oko koga se iscrtava pravougaonik
	 */
	private void drawNodeSelectionRectangles(Graphics2D g2, Node n) {
		// transparencija
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.1f));
		// linija
		g2.setStroke(new BasicStroke());
		// velicina selekcionih kvadrata
		double gap = 4 / transform.getScaleX();
		// boja
		g2.setPaint(getThemeColor());

		// pravougaonici - jedan veliki za okvir i 4 mala u uglovima
		RoundRectangle2D rr = new RoundRectangle2D.Double(n.getPosition()
				.getX()
				- gap, n.getPosition().getY() - gap, n.getSize().getWidth()
				+ gap * 2, n.getSize().getHeight() + gap * 2, 15, 15);
		g2.fill(rr);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));
		g2.draw(rr);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));
		for (Handle h : Handle.values()) {
			g2.fill(getHandleRect(h, n));
		}

	}

	/**
	 * Metoda koja crta pravougaonik oko elementa na kome se nalazi kursor
	 */
	private void drawMouseOverNodeIndicator(Graphics2D g2) {
		Node n = model.getMouseOverNode();
		if (n != null) {
			g2.setStroke(new BasicStroke());
			g2.setPaint(getThemeColor());
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.1f));
			RoundRectangle2D r = getNodeRoundRectangle(n);
			g2.fill(r);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.5f));
			g2.draw(r);
		}
	}

	/**
	 * Metoda vraca round rectangle oko zadatog elementa
	 * 
	 * @param n
	 *            node
	 * @return rectangle sracunat takod a bude oko node-a
	 */
	private RoundRectangle2D getNodeRoundRectangle(Node n) {
		double gap = 6;// transform.getScaleX();
		RoundRectangle2D r = new RoundRectangle2D.Double(n.getPosition().getX()
				- gap, n.getPosition().getY() - gap, n.getSize().getWidth() + 2
				* gap, n.getSize().getHeight() + 2 * gap, 15, 15);
		return r;
	}

	/**
	 * Metoda racuna pravougaonik koji predstavlja trazeni handle nad zadatim
	 * nodom.
	 * 
	 * @param handle
	 *            za koji se trazi Rectangle
	 * @param n
	 *            node oko koga se nalazi handle
	 * @return Rectangle2D koji predstavlja trazeni handle oko zadatog node-a
	 */
	private Shape getHandleRect(Handle handle, Node n) {
		double gap = 4;// / transform.getScaleX();
		double size = InternalConfiguration.SELECTION_RECKTANGLE_SIZE
				/ transform.getScaleX();
		double round = 10;
		switch (handle) {
		case EAST: {
			return new RoundRectangle2D.Double(n.getPosition().getX()
					+ n.getSize().getWidth(), n.getPosition().getY()
					+ n.getSize().getHeight() / 2 - gap, size, size, round,
					round);
		}

		case WEST: {
			return new RoundRectangle2D.Double(n.getPosition().getX() - gap
					- size / 2, n.getPosition().getY()
					+ n.getSize().getHeight() / 2 - gap, size, size, round,
					round);
		}

		case NORTH: {
			return new RoundRectangle2D.Double(n.getPosition().getX()
					+ n.getSize().getWidth() / 2 - size / 2, n.getPosition()
					.getY()
					- gap - size / 2, size, size, round, round);
		}

		case SOUTH: {
			return new RoundRectangle2D.Double(n.getPosition().getX()
					+ n.getSize().getWidth() / 2 - size / 2, n.getPosition()
					.getY()
					+ n.getSize().getHeight() - gap / 2, size, size, round,
					round);
		}

		case SOUTHEAST: {
			return new RoundRectangle2D.Double(n.getPosition().getX()
					+ n.getSize().getWidth(), n.getPosition().getY()
					+ n.getSize().getHeight() - gap / 2, size, size, round,
					round);
		}

		case SOUTHWEST: {
			return new RoundRectangle2D.Double(n.getPosition().getX() - gap
					- size / 2, n.getPosition().getY()
					+ n.getSize().getHeight() - gap / 2, size, size, round,
					round);
		}

		case NORTHEAST: {
			return new RoundRectangle2D.Double(n.getPosition().getX()
					+ n.getSize().getWidth(), n.getPosition().getY() - gap
					- size / 2, size, size, round, round);
		}

		case NORTHWEST: {
			return new RoundRectangle2D.Double(n.getPosition().getX() - gap
					- size / 2, n.getPosition().getY() - gap - size / 2, size,
					size, round, round);
		}
		}
		return null;
	}

	/**
	 * Metoda za iscrtavanje tacaka na selektovanom linku
	 */
	private void drawLinkBreakPoints(Graphics2D g2) {
		Link link = model.getSelectedLink();
		ArrayList<Point> breakpoints = link.getBreakPoints();
		float size = InternalConfiguration.BREAKPOINT_SIZE;
		double round = 2;
		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		for (Point point : breakpoints) {
			g2.fill(new RoundRectangle2D.Double(point.getX() - size / 2, point
					.getY()
					- size / 2, size, size, round, round));
		}
		Point point;
		if (link.getSource() != null) {
			point = link.getSourceCenterPosition();
			g2.fill(new RoundRectangle2D.Double(point.getX() - size / 2, point
					.getY()
					- size / 2, size, size, round, round));
		}
		if (link.getDestination() != null) {
			point = link.getDestinationCenterPosition();
			g2.fill(new RoundRectangle2D.Double(point.getX() - size / 2, point
					.getY()
					- size / 2, size, size, round, round));
		}
	}

	/**
	 * Metoda za iscrtavanje lasa na kanvasu. Pravougaonik koji predstavlja laso
	 * se iscrtava razlicito u zavisnosti od smera povecavanja(kvadranta).
	 * 
	 * @param g2
	 *            Graphics2D objekat za crtanje
	 */
	private void drawLaso(Graphics2D g2) {
		if (lasoEnabled) {
			// postavljanje fill boje
			g2.setPaint(getThemeColor());
			// postavljanje transparencije na 0.1 da bi se videlo sta se
			// selektuje
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.1f));
			// pravougaonik koji se crta
			double sX = model.getLasoBegin().getX();
			double sY = model.getLasoBegin().getY();

			double eX = model.getLasoEnd().getX();
			double eY = model.getLasoEnd().getY();

			// pravougaonik u zavisnosti od kvadranta

			if (sX > eX && sY > eY)
				lasoRectangle.setRect(eX, eY, Math.abs(model.getLasoEnd()
						.getX()
						- model.getLasoBegin().getX()), Math.abs(model
						.getLasoEnd().getY()
						- model.getLasoBegin().getY()));
			else if (sX > eX && sY < eY)
				lasoRectangle.setRect(eX, sY, Math.abs(model.getLasoEnd()
						.getX()
						- model.getLasoBegin().getX()), Math.abs(model
						.getLasoEnd().getY()
						- model.getLasoBegin().getY()));
			else if (sX < eX && sY > eY)
				lasoRectangle.setRect(sX, eY, Math.abs(model.getLasoEnd()
						.getX()
						- model.getLasoBegin().getX()), Math.abs(model
						.getLasoEnd().getY()
						- model.getLasoBegin().getY()));
			else
				lasoRectangle.setRect(sX, sY, Math.abs(model.getLasoEnd()
						.getX()
						- model.getLasoBegin().getX()), Math.abs(model
						.getLasoEnd().getY()
						- model.getLasoBegin().getY()));
			// Iscrtavanje samog pravougaonika lasa
			g2.fill(lasoRectangle);
			g2.setStroke(new BasicStroke(0));
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1));
			g2.draw(lasoRectangle);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.1f));
			/*
			 * iscrtavanje okvira oko svih nodeova koji su pokriveni lasom
			 */
			if (ConfigurationManager.getInstance().getBoolean(
					"lasoOverNodeShow")) {
				// boja i alpha
				g2.setStroke(new BasicStroke(0));
				g2.setPaint(getThemeColor());
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.1f));
				for (Node n : model.getNodes()) {

					if (lasoRectangle.intersects(new Rectangle2D.Double(n
							.getPosition().getX(), n.getPosition().getY(), n
							.getSize().width, n.getSize().height))) {
						g2.fill(getNodeRoundRectangle(n));
					}
				}
			}
		}
	}

	/**
	 * Na osnovu hendla iznad koga se nalazi postavlja kursor
	 */
	public void setMouseCursor(Point2D point) {
		Handle handle = getHandleForPoint(point);

		if (handle != null) {
			Cursor cursor = null;

			switch (handle) {
			case NORTH:
				cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
				break;
			case SOUTH:
				cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
				break;
			case EAST:
				cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
				break;
			case WEST:
				cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
				break;
			case SOUTHEAST:
				cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
				break;
			case NORTHWEST:
				cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
				break;
			case SOUTHWEST:
				cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
				break;
			case NORTHEAST:
				cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
				break;
			}
			setCursor(cursor);
		}
	}

	/**
	 * Vraća handle trenutno selektovanog elementa, koji se nalazi na zadatoj
	 * lokaciji.
	 * 
	 * @param point
	 *            Ulazni parametar koji određuje lokaciju
	 * @param device
	 *            Izlazni parametar preko koga se vraća element.
	 * @return Hendl za zadatu poziciju. Ukoliko je null tada je node
	 *         nedefinisan.
	 */
	public Handle getHandleForPoint(Point2D point) {
		if (getModel().getSelectedNodes().size() > 0) {
			Iterator<Node> iter = getModel().getSelectedNodesIterator();
			while (iter.hasNext()) {
				nodeChangingShape = iter.next();
				Handle handle = getHandleForPoint(nodeChangingShape, point);
				if (handle != null)
					return handle;
			}
		}
		nodeChangingShape = null;
		return null;
	}

	/**
	 * Za zadatu tačku i element vraća hendl.
	 * 
	 * @param node
	 * @param point
	 * @return Hendl ukoliko je "pogođen", u suprotnom vraća null
	 */
	private Handle getHandleForPoint(Node node, Point2D point) {
		for (Handle h : Handle.values()) {
			if (isPointInHandle(node, point, h)) {
				return h;
			}
		}
		return null;
	}

	/**
	 * Za zadati element, tačku i hendl određuje da li je tačka unutar hendla
	 * 
	 * @param node
	 * @param point
	 * @param handle
	 * @return
	 */

	private boolean isPointInHandle(Node node, Point2D point, Handle handle) {
		return getHandleRect(handle, node).contains(point);
	}

	/**
	 * Funkcija centrira pronadjeni nod na trenutno otvorenom dijagramu
	 * 
	 * @param node
	 *            - pronadjeni nod koji odgovara upitu pretrage
	 */
	public void centerNode(Node node) {
		if (ConfigurationManager.getInstance().getBoolean("animationEnabled")) {
			Application.getInstance().getMainWindow().getTimerThread()
					.setRelocate(
							new Point2D.Double(node.getSize().getWidth() / 2
									+ node.getPosition().getX(), node.getSize()
									.getHeight()
									/ 2 + node.getPosition().getY()));
		} else {
			Rectangle2D rect = new Rectangle2D.Double();
			rect.setRect(node.getPosition().getX() - getWidth() / 2
					+ node.getSize().getWidth() / 2, node.getPosition().getY()
					- getHeight() / 2 + node.getSize().getHeight() / 2,
					getWidth(), getHeight());
			zoomTo(rect);
		}
	}

	/**
	 * Metoda koja iscrtava grid na platnu. Metoda vodi racuna o stepenu zoom-a
	 * i u zavisnosti od toga iscrtava razlicitu gustinumreze
	 * 
	 * @param g2
	 */
	private void drawGrid(Graphics2D g2) {
		GeneralPath lines = new GeneralPath();
		GeneralPath detailLines = new GeneralPath();
		// tanak grid, ne zavisi od zoom faktora debljina njegove linije
		g2.setStroke(new BasicStroke(0));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.3f));
		g2.setPaint(ConfigurationManager.getInstance().getColor("gridColor"));
		// pocetak i kraj canvasa
		Point2D startPoint = new Point2D.Double(0, 0);
		Point2D endPoint = new Point2D.Double(getWidth(), getHeight());

		try {
			// konverzija u trenutne koordinate
			transform.inverseTransform(startPoint, startPoint);
			transform.inverseTransform(endPoint, endPoint);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		// detaljne linije se iscrtavaju samo kad je zoom faktor veci on nekog
		// :)
		if (transform.getScaleX() > InternalConfiguration.SCALE_STEP_DETAIL_GRID) {

			// vertikalne linije
			for (int start = (int) startPoint.getX(); start < (int) endPoint
					.getX(); start++) {
				if (start % InternalConfiguration.GRID_ZOOMEDIN_DENSITY == 0) {
					detailLines.moveTo(start, startPoint.getY());
					detailLines.lineTo(start, startPoint.getY() + getHeight()
							/ transform.getScaleY());
				}
			}

			// horizontalne linije
			for (int start = (int) startPoint.getY(); start < (int) endPoint
					.getY(); start++) {
				if (start % InternalConfiguration.GRID_ZOOMEDIN_DENSITY == 0) {
					detailLines.moveTo(startPoint.getX(), start);
					detailLines.lineTo(startPoint.getX() + getWidth()
							/ transform.getScaleX(), start);
				}
			}
			g2.draw(detailLines);
		}

		// u zavisnosti od zoom levela iscrtaj gusci ili redji grid
		int distance = InternalConfiguration.GRID_DEFAULT_DENSITY;
		if (transform.getScaleX() < InternalConfiguration.GRID_ZOOM_LEVEL_1)
			distance = InternalConfiguration.GRID_ZOOMEDOUT_DENSITY;
		if (transform.getScaleX() <= InternalConfiguration.GRID_ZOOM_LEVEL_2) {
			distance = InternalConfiguration.GRID_VERY_ZOOMEDOUT_DENSITY;
		}

		// boja grida
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		g2.setPaint(ConfigurationManager.getInstance().getColor("gridColor"));
		// vertikalne linije
		for (int start = (int) startPoint.getX(); start < (int) endPoint.getX(); start++) {
			if (start % distance == 0) {
				lines.moveTo(start, startPoint.getY());
				lines.lineTo(start, startPoint.getY() + getHeight()
						/ transform.getScaleY());
			}
		}

		// horizontalne linije
		for (int start = (int) startPoint.getY(); start < (int) endPoint.getY(); start++) {
			if (start % distance == 0) {
				lines.moveTo(startPoint.getX(), start);
				lines.lineTo(startPoint.getX() + getWidth()
						/ transform.getScaleX(), start);
			}
		}
		g2.draw(lines);
	}

	/**
	 * Odredjuje boju koje treba da bude laso u zavisnosti od teme koja se
	 * koristi.
	 * 
	 * @return Boja koje treba da bude laso.
	 */
	private Paint getThemeColor() {
		String theme = (String) ConfigurationManager.getInstance()
				.getConfigParameter("theme");
		Paint p = new Color(0, 0, 0);
		if (theme.equals("orange/"))
			p = new Color(254, 116, 24);
		if (theme.equals("blue/"))
			p = new Color(0, 106, 197);
		if (theme.equals("green/"))
			p = new Color(30, 200, 30);

		return p;
	}

	public Node getNodeChangingShape() {
		return nodeChangingShape;
	}

	public void setNodeChangingShape(Node nodeChangingShape) {
		this.nodeChangingShape = nodeChangingShape;
	}

	public Handle getHandleMoving() {
		return handleMoving;
	}

	public void setHandleMoving(Handle handleMoving) {
		this.handleMoving = handleMoving;
	}

	/**
	 * Računa centar canvasa
	 * 
	 * @return Vraća centar canvasa uračunavajući i pripadajuće transformacije
	 */
	public Point getCanvasCenter() {
		Point screenCenter = new Point(getWidth() / 2, getHeight() / 2);
		try {
			return (Point) transform.inverseTransform(screenCenter,
					screenCenter);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metoda zumira canvas na dimenzije lasoRectangle pravougaonika. Koristi se
	 * kod LasoZoom-a. Napravljena kako bi pojednostavila pozivanje iz
	 * LasoZoomState-a.
	 */
	public void zoomToLasoRectangle() {
		zoomTo(lasoRectangle);
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	public void setModel(GERMModel model) {
		this.model = model;
		repaint();
	}

	public boolean isCursorOnCanvas() {
		if (popupMenu)
			return true;
		return cursorOnCanvas;
	}

	@Override
	public String toString() {
		return getModel().toString();
	}

	/**
	 * Poziva se kada dodje do promene modela i potrebno je osvezavanje prikaza
	 */
	public void updatePerformed(UpdateEvent e) {
		repaint();
	}
}
