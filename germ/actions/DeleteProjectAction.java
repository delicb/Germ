package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.workspace.Project;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;


/**
 * Klasa predstavlja akciju brisanja projekta.
 */
@SuppressWarnings("serial")
public class DeleteProjectAction extends AbstractGERMAction {

	/**
	 * Slektovani projekat
	 */
	private Project project = null;
	
	public DeleteProjectAction(){
		putValue(NAME, Messages.getString("DeleteProjectAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("DeleteProjectAction.1")); //$NON-NLS-1$
	}
	
	/**
	 * Brisanje projekta iz radnog direktorijuma ide u sledecim koracima:
	 * 1. Vrsi se brisanje svih pripadajucih dijagrama
	 * 2. Brise se sam projekat iz liste projekata u aplikaciji
	 * 3. Brise se projekat iz fajl-sistema
	 * 4. Osvezava se prikaz JTree  komponente.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(project != null){
			
			//Prvo brisemo sve dijagrame: sa Taba, iz Projekta, iz FS
			for(int i = project.getDiagramCount() - 1; i != -1 ; i--){
				
				GERMModel diagram = project.getDiagram(i);
				int tabIndex = Application.getInstance().getMainWindow().getTab(diagram.getView());
				if(tabIndex != -1){
					Application.getInstance().getMainWindow().getTabs().removeTabAt(tabIndex);
				}
				project.deleteDiagramIndex(i);
				File diagramDir = diagram.getDiagramDir();
				if(diagramDir != null){
					if(!diagramDir.delete()){
						JOptionPane.showMessageDialog(
								Application.getInstance().getView(),
								Messages.getString("DeleteProjectAction.2") + diagram.getName() + Messages.getString("DeleteProjectAction.3"), //$NON-NLS-1$ //$NON-NLS-2$
								Messages.getString("DeleteProjectAction.4"), //$NON-NLS-1$
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
			//Brisemo projekat iz Aplikacije
			Application.getInstance().removeProject(project);

			//Brisemo projekat iz FS
			if(!project.getProjectDir().delete()){
					JOptionPane.showMessageDialog(
						Application.getInstance().getView(),
						Messages.getString("DeleteProjectAction.5"), //$NON-NLS-1$
						Messages.getString("DeleteProjectAction.6"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
			
			//Update-ujemo JTree
			Application.getInstance().getWorkspace().updateUI();
		}
	}
	
	public void setProject(Project project){
		this.project = project;
	}

}
