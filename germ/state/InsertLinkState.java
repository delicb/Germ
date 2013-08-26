package germ.state;

import germ.app.Application;
import germ.command.AddLinkCommand;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;
import germ.model.links.Author;
import germ.model.links.Dependency;
import germ.model.links.Interest;
import germ.model.links.LinkType;
import germ.model.links.Refine;
import germ.model.links.Support;
import germ.model.nodes.Argument;
import germ.model.nodes.Assumption;
import germ.model.nodes.Decision;
import germ.model.nodes.Position;
import germ.model.nodes.Requirement;
import germ.model.nodes.Stakeholder;
import germ.model.nodes.Topic;
import germ.util.Cursors;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class InsertLinkState extends State {
	/*
	 * polje koje postavlja akcija dodavanja linka kako bi se znao tip linka
	 * koji se dodaje
	 */
	private LinkType linkToInsert = null;

	public void setLinkToInsert(LinkType linkType) {
		this.linkToInsert = linkType;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			GERMModel m = Application.getInstance().getModel();
			int node = m
					.getNodeAtPosition(Application.getInstance().getView().lastPosition);
			if (node == -1) {
				JOptionPane
						.showMessageDialog(
								Application.getInstance().getView(),
								Messages.getString("InsertLinkState.1"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.0"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			} else {
				/*
				 * Potrebne provere za pocetak linka u zavisnosti od vrste
				 */
				Link l = null;
				Node n = m.getNodeAt(node);
				if (linkToInsert == LinkType.AUTHOR_LINK) {
					if (n instanceof Stakeholder) {
						l = Author.createDefault(Application.getInstance()
								.getView().lastPosition, m.getLinkCount() + 1,
								m.getNodeAt(node));
					} else {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages
								.getString("InsertLinkState.4"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.0"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				if (linkToInsert == LinkType.INTEREST_LINK) {
					if (n instanceof Stakeholder) {
						l = Interest.createDefault(Application.getInstance()
								.getView().lastPosition, m.getLinkCount() + 1,
								m.getNodeAt(node));
					} else {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages
								.getString("InsertLinkState.7"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.0"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

				}
				if (linkToInsert == LinkType.REFINE_LINK) {
					if (n instanceof Requirement) {
						l = Refine.createDefault(Application.getInstance()
								.getView().lastPosition, m.getLinkCount() + 1,
								m.getNodeAt(node));
					} else {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages
								.getString("InsertLinkState.10"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.0"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				if (linkToInsert == LinkType.SUPPORT_LINK) {
					if (n instanceof Stakeholder) {
						l = Support.createDefault(Application.getInstance()
								.getView().lastPosition, m.getLinkCount() + 1,
								m.getNodeAt(node));
					} else {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages
								.getString("InsertLinkState.13"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.0"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				if (linkToInsert == LinkType.DEPENDENCY_LINK) {
					if (n instanceof Assumption || n instanceof Argument
							|| n instanceof Position || n instanceof Topic
							|| n instanceof Decision) {
						l = Dependency.createDefault(Application.getInstance()
								.getView().lastPosition, m.getLinkCount() + 1,
								m.getNodeAt(node));
					} else {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages
								.getString("InsertLinkState.16"), //$NON-NLS-1$
								Messages.getString("InsertLinkState.17"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

				}
				State.insertLinkBreakpointsState.setLinkFinished(false);
				Application.getInstance().getStateMachine().setState(
						State.insertLinkBreakpointsState);
				AddLinkCommand command = new AddLinkCommand(l);
				Application.getInstance().getCommandManager()
						.doCommand(command);
				GERMView v = Application.getInstance().getView();
				m.setSelectedLink(m.getLastLink());
				m.getSelectedLink().addBreakPoint((Point) v.lastPosition);
				Application.getInstance().getActionManager().getUndoAction().setEnabled(false);
				Application.getInstance().getActionManager().getRedoAction().setEnabled(false);
				m.updatePerformed();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			Application.getInstance().getStateMachine().setState(
					State.defaultState);
		}
	}

	@Override
	public void enter() {
		Application.getInstance().getView().setCursor(
				Cursors.getCursor("inverse"));
		Application.getInstance().getActionManager().getUndoAction()
				.setEnabled(false);
		Application.getInstance().getActionManager().getRedoAction()
				.setEnabled(false);
	}

	@Override
	public void exit() {
		Application.getInstance().getCommandManager().refreshUndoRedoButtons(
				Application.getInstance());
	}
}
