package germ.actions;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.NewPodWindow;
import germ.i18n.Messages;
import germ.model.workspace.Project;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;

/**
 *Klasa reprezentuje akciju za kreiranje novog projekta: {@link Project Project}
 */
@SuppressWarnings("serial")
public class CreateNewProjectAction extends AbstractGERMAction {

	/**
	 * Promenljiva se koristi za proveru da li je izabrano ime moguce.
	 * Ukoliko postoji projekat sa istim imenom vrednost je true.
	 */
	private boolean nameAllreadyExists = false;

	public CreateNewProjectAction() {
		putValue(NAME, Messages.getString("CreateNewProjectAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("CreateNewProjectAction.1")); //$NON-NLS-1$
	}

	/** 
	 *  Metoda prikazuje prozor za unos naziva novog projekta
	 *  sve dok se ne odustane od akcije ili dok se ne unese validan naziv.
	 */
	public void actionPerformed(ActionEvent e) {

		while (!nameAllreadyExists) {
			NewPodWindow np = new NewPodWindow(NewPodWindow.NEW_PROJECT);
			np.setVisible(true);
			if (np.isDialogResult()) {
				File newProject = new File(ConfigurationManager.getInstance()
						.getString("workspace") //$NON-NLS-1$
						+ File.separator + np.getEnteredName());
				if (!newProject.exists()) {
					NewPodWindow nd = new NewPodWindow(NewPodWindow.NEW_DIAGRAM);
					nd.setVisible(true);
					if (nd.isDialogResult()) {
						Application.getInstance().addProject(np.getEnteredName(), nd.getEnteredName());
						Application.getInstance().getModel().updatePerformed();
						nameAllreadyExists = true;
					} else
						nameAllreadyExists = false;
				} else {
					JOptionPane.showMessageDialog(
									Application.getInstance().getView(),
									Messages.getString("CreateNewProjectAction.3"), //$NON-NLS-1$
									"INFORMATION", //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
					nameAllreadyExists = false;
				}
			} else
				nameAllreadyExists = true;
		}
	}

}
