package germ.state;

import germ.actions.OpenSubDiagramAction;
import germ.app.Application;
import germ.command.MoveCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.PropertyWindow;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Requirement;
import germ.util.Cursors;
import germ.view.GERMView;
import germ.view.Handle;
import germ.view.NodePainter;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;

import javax.swing.JPopupMenu;

public class DefaultState extends State {

	/**
	 * Sadrzi nod iznad koga se nalazi mis. Pamtimo ga da ne iscrtavamo ceo
	 * canvas na svaki pomeraj misa.
	 */
	private Node mouseOverNode = null;

	/**
	 * Pamti poslednji klik misa (levi, desni) kako bi ga mogli ocitati u
	 * MouseDragu
	 */
	int mouseButton = 0;

	/**
	 * Reaguje na otpuštanje miša u podrazumevanom stanju. Ako je pritisnut levi
	 * taster misa i nije pritisnut taster Ctrl i na toj poziciji se nalazi
	 * kursor bice selektovan nod koji je na toj poziciji. <br/>
	 * Ako je pritisnut desni taster prikazuje se konteksni meni sa omogućenim
	 * opcijama u zavisnosti od stanja u kome se ceo program nalazi.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		Point lastPosition = (Point) Application.getInstance().getView().lastPosition;
		int nodeId = m.getNodeAtPosition(lastPosition);
		// Vrsi deselekciju svih nodova i selekciju noda iznad koga je mis
		// ukoliko su svi potrebni uslovi
		// zadovoljeni
		if (e.getButton() == MouseEvent.BUTTON1 && nodeId != -1
				&& !e.isControlDown()) {
			Node node = m.getNodeAt(nodeId);
			m.deselectAllNodes();
			m.selectNode(node);
		}

		// pokretanje konteksnog menija
		if (e.getButton() == MouseEvent.BUTTON3) {
			/*
			 * Pokretanje menija na desni klik. Potrebno je proveriti da li je
			 * selektovan bar jedan element, jer ako nije nema smisla pokretati
			 * meni uopste. Ako nije selektovan tacno jedan element nije moguce
			 * pokrenuti property prozor.
			 */

			Node node = null;
			JPopupMenu popup = Application.getInstance().getMainWindow()
					.getPopupMenu();
			if (nodeId != -1) {
				node = m.getNodeAt(nodeId);
				if (!m.isNodeIsSelected(node)) {
					m.deselectAllNodes();
					m.selectNode(node);
				}
				m.nodeToFront(nodeId);
			} else {
				m.deselectAllNodes();
			}

			// da li je node requirement i da li ima za sebe vezan subDijagram
			if (m.selectedNodesCount() <= 0) {
				popup.getComponent(5).setEnabled(false);
				popup.getComponent(6).setEnabled(false);
				popup.getComponent(7).setEnabled(false);
				popup.getComponent(8).setEnabled(false);
				popup.getComponent(9).setEnabled(false);
				popup.getComponent(10).setEnabled(false);
			} else {
				popup.getComponent(6).setEnabled(true);
				if (m.selectedNodesCount() != 1) {
					popup.getComponent(5).setEnabled(false);
					popup.getComponent(9).setEnabled(false);
					popup.getComponent(10).setEnabled(true);
				} else {
					if (m.getSelectedNodes().get(0) instanceof Requirement) {
						Requirement r = (Requirement) m.getSelectedNodes().get(
								0);
						if (r.isComplex()) {
							OpenSubDiagramAction osd = Application
									.getInstance().getActionManager()
									.getOpenSubDiagramAction();
							osd.setRequirement((Requirement) node);
							popup.getComponent(5).setEnabled(true);
						} else {
							popup.getComponent(5).setEnabled(false);
						}
					} else
						popup.getComponent(5).setEnabled(false);
					popup.getComponent(6).setEnabled(true);
					popup.getComponent(7).setEnabled(true);
					popup.getComponent(9).setEnabled(true);
					popup.getComponent(10).setEnabled(false);
				}
			}
			popup.show(Application.getInstance().getView(), e.getX(), e.getY());
		}
	}

	/**
	 * <br/>
	 * Ako je pritisnut levi taster:
	 * <ul>
	 * <li>Ako nije pritisnut ni jedan taster na tastaturi i ako se ispod nalazi
	 * nod svi drugi selektovani nodovi ce biti deselektovani i bice selektovan
	 * nod koji je ispod kursora</li>
	 * <li>Ako je pritisnut taster Ctrl i ispod se nalazi nod bice selektovan
	 * zajedno sa svim drugim selektovanim novodima (ako ih ima)</li>
	 * <li>Ako se na poziciji kursora ne nalazi ni jedan nod svi selektovani
	 * nodovi i linkovi ce biti deselekovani (ako ih ima)</li>
	 * <li>Ako se ispod kursora nalazi link bice selektovan</li>
	 * <li>Ako je pogodjen handle nekog od selektovanih nodova prelazi se u
	 * resize State</li>
	 * <li>Ako je pogodjena početna ili krajnja tačka selektovanog linka prelazi
	 * se u reconnect state</li>
	 * </ul>
	 */
	public void mousePressed(MouseEvent e) {
		// zaustavljamo beskonacan scroll
		Application.getInstance().getMainWindow().stopScroll();

		mouseButton = e.getButton();
		GERMModel m = Application.getInstance().getModel();

		StateMachine sm = Application.getInstance().getStateMachine();
		Point lastPosition = (Point) Application.getInstance().getView().lastPosition;
		GERMView v = Application.getInstance().getView();
		int nodeId = m.getNodeAtPosition(lastPosition);
		int link = -1;
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (v.getHandleMoving() != null && v.getNodeChangingShape() != null) {
				State.resizeState.initializeResize(lastPosition);
				sm.setState(State.resizeState);
			} else {
				if (nodeId != -1) { // pogodjen node
					Node hitNode = m.getNodeAt(nodeId);
					if (((NodePainter) hitNode.getPainter())
							.isCenterHit(lastPosition)
							&& m.getSelectedLink() != null) {
						if (hitNode == m.getSelectedLink().getSource())
							State.reconnectState.initializeMove(true, hitNode); // hitovan
						// source
						else
							State.reconnectState.initializeMove(false, hitNode); // hitovan
						// dest
						sm.setState(State.reconnectState);

					} else {
						if (!e.isControlDown()) {
							if (!m.isNodeIsSelected(hitNode)) {
								m.deselectAllNodes();
								m.selectNode(hitNode);
							}
						} else {
							m.toggleNodeSelect(hitNode, true);
						}
						m.nodeToFront(nodeId);
						m.setSelectedLink(null);
					}

				} else { // mozda je pogodjen link
					link = m.getLinkAtPosition(lastPosition);
					if (link != -1) {
						m.deselectAllNodes();
						m.setSelectedLink(m.getLinkAt(link));
						m.setSelectedBreakpoint(lastPosition);
					}
				}
				if (nodeId == -1 && link == -1) { // nije pogodjen ni link ni
					// node
					m.setSelectedLink(null);
					if (!e.isControlDown())
						m.deselectAllNodes();
					sm.setState(State.lasoState);
					m.setLasoBegin(lastPosition);
				}
			}

		} else if (e.getButton() == MouseEvent.BUTTON2) {
			sm.setState(State.handMoveState);
		}
	}

	/**
	 * Ako se na poziciji kursora nalazi kursor prelazi se u move state. Ako se
	 * na poziciji kursora nalazi preloman tačka linka prelazi se u
	 * moveBreakPoint state.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		StateMachine sm = Application.getInstance().getStateMachine();
		Point lastPosition = (Point) v.lastPosition;
		int node = m.getNodeAtPosition(lastPosition);

		if (mouseButton == MouseEvent.BUTTON1)
			if (node != -1) {
				sm.setState(State.moveState);
				State.moveState.initializeMove(m.getNodeAt(node));
			} else if (m.getSelectedBreakpoint() != null) {
				sm.setState(State.moveBreakpointState);
				State.moveBreakpointState.initializeMove();
			}
	}

	/**
	 * Ako se na poziciji misa nalazi nod pokrece se njegom prozor za
	 * podesavanja. Ako je selektovan link i na poziciji misa se nalazi link
	 * (ali ne i prelomna tacka) dodaje se nova prelomna tacka. Ako se na
	 * poziciji misa nalazi prelomna tacka ona se uklanja.
	 */
	public void mouseDoubleClicked(MouseEvent e) {
		Point lastPosition = (Point) Application.getInstance().getView().lastPosition;
		GERMModel m = Application.getInstance().getModel();
		int node = m.getNodeAtPosition(lastPosition);
		if (node != -1) {
			Node selectedNode = m.getNodeAt(node);
			PropertyWindow property = selectedNode.getPropertyWindow();
			property.setVisible(true);
			selectedNode.setProperties(property.isDialogResult());
			m.updatePerformed();
		} else {
			int link = m.getLinkAtPosition(lastPosition);
			if (link != -1) {
				m.setSelectedLink(m.getLinkAt(link));
				m.setSelectedBreakpoint(lastPosition);
				if (m.getSelectedBreakpoint() != null) {
					m.getSelectedLink().deleteBreakpoint(
							m.getSelectedBreakpoint());
				} else {
					m.getSelectedLink().insertNewBreakpoint(lastPosition);
				}
				m.setSelectedBreakpoint(null);
				m.updatePerformed();
			}
		}
	}

	/**
	 * Pomera selektovani nod za offset koji je definisan u konfiguraciji na
	 * stranu koja zavisi od strelice koja je pritisnuta.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		GERMModel m = Application.getInstance().getModel();
		ConfigurationManager cm = ConfigurationManager.getInstance();

		int offsetX = 0;
		int offsetY = 0;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			offsetX = 0;
			offsetY = cm.getInt("arrowMoveStep");
			break;
		case KeyEvent.VK_DOWN:
			offsetX = 0;
			offsetY = -cm.getInt("arrowMoveStep");
			break;
		case KeyEvent.VK_LEFT:
			offsetX = cm.getInt("arrowMoveStep");
			offsetY = 0;
			break;
		case KeyEvent.VK_RIGHT:
			offsetX = -cm.getInt("arrowMoveStep");
			offsetY = 0;
			break;
		}

		MoveCommand moveCommand = null;
		if (e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveCommand = new MoveCommand();
			m.updateSelectedNodesPosition(offsetX, offsetY);
			moveCommand.moveEnded();
			Application.getInstance().getCommandManager()
					.doCommand(moveCommand);
		}
	}

	/**
	 * Menja kursor ako se nadje iznad handla nekoh selektovanog noda. Kad se
	 * kursor pomeri sa handla kursor se vraca na prethodni.
	 */
	@Override
	public void mouseMove(MouseEvent e) {
		GERMView v = Application.getInstance().getView();
		GERMModel m = Application.getInstance().getModel();
		Point pos = new Point();
		try {
			v.getTransform().inverseTransform(e.getPoint(), pos);
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
		Handle h = v.getHandleForPoint(pos);
		if (h != null) {
			v.setMouseCursor(pos);
		} else
			v.setCursor(Cursors.getCurrentCursor());

		int nodeId = m.getNodeAtPosition(pos);
		if (nodeId != -1) {
			Node n = m.getNodeAt(nodeId);
			// radicemo repaint samo ako je mis na novom nodu
			if (mouseOverNode == null) {
				mouseOverNode = n;
				m.setMouseOverNode(n);
				m.updatePerformed();
			}
			// ili ako je nod promenjen
			if (n != mouseOverNode) {
				m.setMouseOverNode(n);
				m.updatePerformed();
			}
		} else {
			mouseOverNode = null;
			m.setMouseOverNode(null);
			m.updatePerformed();
		}
	}

	@Override
	public void enter() {
		Application.getInstance().getView().setCursor(
				Cursors.getCursor("default"));
	}

}