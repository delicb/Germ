package germ.actions;

import germ.app.Application;
import germ.gui.windows.NewPodWindow;
import germ.i18n.Messages;
import germ.model.workspace.Project;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za preimenovanje projekta
 */
@SuppressWarnings("serial")
public class RenameProjectAction extends AbstractGERMAction {

	/**
	 * Selektovani projekat
	 */
	private Project currentProject = null;
	
	public RenameProjectAction() {
		putValue(NAME, Messages.getString("RenameProjectAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("RenameProjectAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {

		NewPodWindow rp = new NewPodWindow(NewPodWindow.EDIT_PROJECT);
		rp.setEnteredName(currentProject.getName());
		rp.setVisible(true);
		if(rp.isDialogResult()){
			currentProject.setProjectDir(rp.getEnteredName());
			Application.getInstance().getModel().updatePerformed();
		}

	}
	
	public void setProject(Project project){
		this.currentProject = project;
	}

}
