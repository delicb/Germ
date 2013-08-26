package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Requirement;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za otvaranje poddijagrama zahteva.
 */
@SuppressWarnings("serial")
public class OpenSubDiagramAction extends AbstractGERMAction {
	private Requirement requirement;

	public OpenSubDiagramAction() {
		putValue(NAME, Messages.getString("OpenSubDiagramAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("OpenSubDiagramAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		Application.getInstance().setModel(requirement.getSubDiagram());
	}
	
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
}
