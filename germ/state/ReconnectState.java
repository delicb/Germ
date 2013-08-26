package germ.state;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;
import germ.model.links.*;
import germ.model.nodes.*;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class ReconnectState extends State {

	/**
	 * true - source false - destination
	 */
	private boolean endPoint;
	private Node beginingNode;

	public void mouseDragged(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		double width = v.getWidth();
		double height = v.getHeight();
		double tolerance = 20;
		int moveStep = 6;
		int moveX = 0;
		int moveY = 0;

		if (endPoint) {
			m.getSelectedLink().deleteBreakpointHead();
			m.getSelectedLink().addBreakPointBegining((Point) v.lastPosition);
		} else {
			m.getSelectedLink().deleteBreakpointTail();
			m.getSelectedLink().addBreakPoint((Point) v.lastPosition);
		}

		// transliranje canvasa
		if (e.getX() >= width - tolerance) {
			moveX -= moveStep;
		}

		if (e.getX() <= tolerance) {
			moveX += moveStep;
		}

		if (e.getY() >= height - tolerance) {
			moveY -= moveStep;
		}

		if (e.getY() <= tolerance) {
			moveY += moveStep;
		}

		v.moveWorkspace(moveX, moveY);

	}

	public void mouseReleased(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		int node = m.getNodeAtPosition((Point) v.lastPosition);
		if (node != -1) {
			Node hitNode = m.getNodeAt(node);

			beginingNode.removeLink(m.getSelectedLink());

			// Kliknut je source - menja se source
			if (endPoint) {
				m.getSelectedLink().deleteBreakpointHead();

				// Pokusaj korisnika da preveze na destination isti link kao i
				// na source
				if (m.getSelectedLink().getDestination() == hitNode) {
					m.getSelectedLink().setSource(beginingNode);
					JOptionPane
							.showMessageDialog(
									Application.getInstance().getView(),
									Messages.getString("ReconnectState.0"), //$NON-NLS-1$
									Messages.getString("ReconnectState.1"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
				} else {
					// Provere da li je adekvatan pocetak.
					// hitNode - pogodjeni node
					// link - link koji se edituje
					Link link = m.getSelectedLink();

					// provere

					if (isValidSource(link, hitNode))
						link.setSource(hitNode);
					else {
						m.getSelectedLink().setSource(beginingNode);
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("ReconnectState.5"),	//$NON-NLS-1$
								Messages.getString("ReconnectState.1"),	//$NON-NLS-1$
								JOptionPane.WARNING_MESSAGE);
					}
				}
				// Kliknut je destination - destination se menja
			} else {
				m.getSelectedLink().deleteBreakpointTail();

				// Pokusaj korisnika da preveze na source isti link kao i na
				// destination
				if (m.getSelectedLink().getSource() == hitNode) {
					m.getSelectedLink().setDestination(beginingNode);
					JOptionPane
							.showMessageDialog(
									Application.getInstance().getView(),
									Messages.getString("ReconnectState.2"), //$NON-NLS-1$
									Messages.getString("ReconnectState.1"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$
				} else {
					// Provere da li je adekvatan pocetak.
					// hitNode - pogodjeni node
					// link - link koji se edituje
					Link link = m.getSelectedLink();

					if (isValidDestination(link, hitNode))
						link.setDestination(hitNode);
					else {
						m.getSelectedLink().setDestination(beginingNode);
						JOptionPane
								.showMessageDialog(
										Application.getInstance().getView(),
										Messages.getString("ReconnectState.3"),
										Messages.getString("ReconnectState.4"),
										JOptionPane.WARNING_MESSAGE);
					}
				}
			}

			// Korisnik je uradio mouseReleased van Noda
		} else {

			// Pustio je source
			if (endPoint) {
				m.getSelectedLink().deleteBreakpointHead();
				m.getSelectedLink().setSource(beginingNode);

				// Pustio je destination
			} else {
				m.getSelectedLink().deleteBreakpointTail();
				m.getSelectedLink().setDestination(beginingNode);
			}
		}
		m.updatePerformed();
		Application.getInstance().getStateMachine()
				.setState(State.defaultState);
	}

	private boolean isValidDestination(Link link, Node node) {
		if (link instanceof Author)
			if (!(node instanceof Stakeholder))
				return true;
		if (link instanceof Refine)
			if (node instanceof Requirement)
				return true;
		if (link instanceof Support)
			if (node instanceof Argument)
				return true;
		if (link instanceof Interest)
			if (node instanceof Requirement)
				return true;
		if (link instanceof Dependency) {
			// pretpostavka/argument, argument/pozicija, pozicija/tema,
			// tema/odluka, odluka/odluka
			if (link.getSource() instanceof Assumption)
				if (node instanceof Argument)
					return true;
			if (link.getSource() instanceof Argument)
				if (node instanceof Position)
					return true;
			if (link.getSource() instanceof Position)
				if (node instanceof Topic)
					return true;
			if (link.getSource() instanceof Topic)
				if (node instanceof Decision)
					return true;
			if (link.getSource() instanceof Decision)
				if (node instanceof Decision)
					return true;
		}
		return false;
	}

	private boolean isValidSource(Link link, Node node) {
		if (link instanceof Author)
			if (node instanceof Stakeholder)
				return true;
		if (link instanceof Refine)
			if (node instanceof Requirement)
				return true;
		if (link instanceof Support)
			if (node instanceof Stakeholder)
				return true;
		if (link instanceof Interest)
			if (node instanceof Stakeholder)
				return true;
		if (link instanceof Dependency) {

			if (link.getDestination() instanceof Argument)
				if (node instanceof Assumption)
					return true;
			if (link.getDestination() instanceof Position)
				if (node instanceof Argument)
					return true;
			if (link.getDestination() instanceof Topic)
				if (node instanceof Position)
					return true;
			if (link.getDestination() instanceof Decision)
				if (node instanceof Topic || node instanceof Decision)
					return true;
		}
		return false;
	}

	public void initializeMove(boolean endPoint, Node node) {
		this.endPoint = endPoint;
		this.beginingNode = node;
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();

		if (endPoint) {
			m.getSelectedLink().setSource(null);
			m.getSelectedLink().addBreakPointBegining((Point) v.lastPosition);
		} else {
			m.getSelectedLink().setDestination(null);
			m.getSelectedLink().addBreakPoint((Point) v.lastPosition);
		}
	}
}
