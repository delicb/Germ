package germ.actions;

import germ.app.Application;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.CreateNewDiagramWindow;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.workspace.Project;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;

/**
 * Klasa koja predstavlja akciju dodavanja novog dijagrama u projekat.
 */
@SuppressWarnings("serial")
public class CreateNewDiagramAction extends AbstractGERMAction {

	/**
	 * Polje prezentuje projekat u koji se dodaje novi diajgram.
	 */
	private Project currentProject = null;
	
	/**
	 * Promenljiva se koristi za proveru da li je izabrano ime moguce.
	 * Ukoliko postoji dijagram sa istim imenom vrednost je true.
	 */
	private boolean nameAllreadyExists = false;

	public CreateNewDiagramAction() {
		putValue(NAME, Messages.getString("CreateNewDiagramAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("CreateNewDiagramAction.1")); //$NON-NLS-1$
	}

	/** 
	 *  Metoda prikazuje prozor za unos naziva novog diajgrama
	 *  sve dok se ne odustane od akcije ili dok se ne unese validan naziv.
	 */
	public void actionPerformed(ActionEvent e) {
		nameAllreadyExists = false;
		while (!nameAllreadyExists) {
			CreateNewDiagramWindow nd = new CreateNewDiagramWindow();
			if (currentProject != null)
				nd.setCbProjects(currentProject.getName());
			nd.setVisible(true);
			if (nd.isDialogResult()) {
				Project currentProject = Application.getInstance().getProject(
						nd.getProject());
				if (currentProject != null) {
					File check = new File(currentProject.getProjectDir()
							+ File.separator + nd.getEnteredName() + "." //$NON-NLS-1$
							+ InternalConfiguration.DIAGRAM_EXTENSION);
					if (!check.exists()) {
						nameAllreadyExists = true;
						GERMModel newDiagram = new GERMModel(nd
								.getEnteredName(), currentProject);
						currentProject.addDiagram(newDiagram);
						newDiagram.save();
						Application.getInstance().getMainWindow().addDiagram(newDiagram.getView());
						Application.getInstance().getModel().updatePerformed();
						Application.getInstance().getWorkspace().updatePerformed(null);
					} else {
						JOptionPane.showMessageDialog(
										Application.getInstance().getView(),
										Messages.getString("CreateNewDiagramAction.3"), //$NON-NLS-1$
										"INFORMATION", //$NON-NLS-1$
										JOptionPane.INFORMATION_MESSAGE);
						nameAllreadyExists = false;
					}
				} else {
					JOptionPane.showMessageDialog(
									Application.getInstance().getView(),
									Messages.getString("CreateNewDiagramAction.5"), //$NON-NLS-1$
									"ERROR", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}

			} else
				nameAllreadyExists = true;
		}
	}

	public void setProject(Project project) {
		this.currentProject = project;
	}

}
