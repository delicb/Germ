package germ.actions;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;
import germ.model.workspace.Workspace;

import java.awt.event.ActionEvent;

/**
 * Klasa akcije za promenu tekuceg radnog okruzenja :{@link Workspace Workspace}
 */
@SuppressWarnings("serial")
public class ChangeWorkspaceAction extends AbstractGERMAction {

	public ChangeWorkspaceAction() {
		putValue(NAME, Messages.getString("ChangeWorkspaceAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages
				.getString("ChangeWorkspaceAction.1")); //$NON-NLS-1$
	}

	/**
	 * Metoda prikazuje prozor za izbor radnog okruzenja i postavlja ga kao
	 * trenutno okruzenje.
	 */
	public void actionPerformed(ActionEvent e) {
		Application app = Application.getInstance();
		ConfigurationManager cm = ConfigurationManager.getInstance();
		String newWorkspace = Workspace.workspaceChooser(app.getMainWindow());
		while (!(newWorkspace != null && Workspace.isValidWorkspace(newWorkspace))) {
			newWorkspace = Workspace.workspaceChooser(app.getMainWindow());
		}
		cm.setString("workspace", newWorkspace); //$NON-NLS-1$
		app.workspaceChanged();
		// da bi se updateovao JTree
		if (app.getModel() != null) {
			app.getModel().updatePerformed();
		}
	}

}
