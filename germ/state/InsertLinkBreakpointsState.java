package germ.state;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;
import germ.model.links.Author;
import germ.model.links.Dependency;
import germ.model.links.Interest;
import germ.model.links.Refine;
import germ.model.links.Support;
import germ.model.nodes.Argument;
import germ.model.nodes.Assumption;
import germ.model.nodes.Decision;
import germ.model.nodes.Position;
import germ.model.nodes.Requirement;
import germ.model.nodes.Stakeholder;
import germ.model.nodes.Topic;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class InsertLinkBreakpointsState extends State {
	boolean linkFinished = false;
	GERMModel m = null;

	public void mousePressed(MouseEvent e) {
		m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		if (e.getButton() == MouseEvent.BUTTON1) {

			int node = m.getNodeAtPosition(v.lastPosition);
			if (node == -1) {
				m.getSelectedLink().addBreakPoint((Point) v.lastPosition);
				m.updatePerformed();
			} else {
				if (m.getSelectedLink().getSource() != m.getNodeAt(node)
						&& correctDestination(m.getSelectedLink(), m
								.getNodeAt(node))) {
					linkFinished = true;
					m.getSelectedLink().deleteBreakpointTail();
					m.getSelectedLink().setDestination(m.getNodeAt(node));
					m.updatePerformed();
					m.setSelectedLink(null);
					Application.getInstance().getStateMachine().setState(
							State.defaultState);
				} else {
					JOptionPane.showMessageDialog(Application.getInstance()
							.getView(), Messages
							.getString("InsertLinkBreakpointsState.1"), //$NON-NLS-1$
							Messages.getString("InsertLinkBreakpointsState.2"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			Application.getInstance().getStateMachine().setState(
					State.insertLinkState);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		GERMView v = Application.getInstance().getView();
		GERMModel m = Application.getInstance().getModel();
		int moveStep = 10;
		int moveX = 0;
		int moveY = 0;
		double width = v.getWidth();
		double height = v.getHeight();
		double tolerance = 20;

		if (m.getSelectedLink() != null) {
			m.getSelectedLink().deleteBreakpointTail();
			m.getSelectedLink().addBreakPoint((Point) v.lastPosition);
			if (e.isShiftDown()) {
				m.setSelectedBreakpoint((Point) v.lastPosition);
				m.updateSelectedBreakPointPosition90();
			}
		}

		// transliranje canvasa
		if (e.getX() >= width - tolerance) {
			moveX -= moveStep;
		}

		if (e.getX() <= tolerance && e.getX() >= 0) {
			moveX += moveStep;
		}

		if (e.getY() >= height - tolerance) {
			moveY -= moveStep;
		}

		if (e.getY() <= tolerance) {
			moveY += moveStep;
		}

		v.moveWorkspace(moveX, moveY);

		m.updatePerformed();
	}

	@Override
	public void exit() {
		Application.getInstance().getCommandManager().refreshUndoRedoButtons(
				Application.getInstance());

		if (!linkFinished) {
			if (m.getSelectedLink() != null) {
				m.removeLink(m.getSelectedLink());
				m.setSelectedLink(null);
				m.getCommandManager().removeLastCommand();
			}
		}
		m = null;
	}

	/**
	 * Metoda vraca true ako je pogodjeni node validan zavrsetak linka, false
	 * inace.
	 * 
	 * @param link
	 *            link koji se kreira
	 * @param node
	 *            pogodjen node
	 * @return
	 */
	private boolean correctDestination(Link link, Node node) {
		if (link instanceof Author) {
			if (!(node instanceof Stakeholder))
				return true;
		}
		if (link instanceof Interest) {
			if (node instanceof Requirement)
				return true;
		}
		if (link instanceof Support) {
			if (node instanceof Argument)
				return true;
		}
		if (link instanceof Refine) {
			if (node instanceof Requirement)
				return true;
		}
		if (link instanceof Dependency) {
			if (link.getSource() instanceof Assumption) {
				if (node instanceof Argument)
					return true;
			}
			if (link.getSource() instanceof Argument) {
				if (node instanceof Position)
					return true;
			}
			if (link.getSource() instanceof Position) {
				if (node instanceof Topic)
					return true;
			}
			if (link.getSource() instanceof Topic) {
				if (node instanceof Decision)
					return true;
			}
			if (link.getSource() instanceof Decision) {
				if (node instanceof Decision)
					return true;
			}
		}
		return false;
	}

	void setLinkFinished(boolean f) {
		this.linkFinished = f;
	}

}