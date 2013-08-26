package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;


/**
 * Klasa predstavlja akciju brisanja dijagrama iz projekta.
 */
@SuppressWarnings("serial")
public class DeleteDiagramAction extends AbstractGERMAction {

	/**
	 * Dijagram za brisanje.
	 */
	private GERMModel diagram = null;
	
	public DeleteDiagramAction(){
		putValue(NAME, Messages.getString("DeleteDiagramAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("DeleteDiagramAction.1")); //$NON-NLS-1$
	}
	/**
	 * Brisanje dijagrama ide u sledecim koracima:
	 * 1. Ukoliko je selektovani dijagram otvoren zatvara se njegov tab.
	 * 2. Dijagram se brise iz liste dijagrama datog projekta.
	 * 3. Dijagram se brise iz fajl-sistema
	 * 4. Osvezava se prikaz JTree komponente.
	 */
	public void actionPerformed(ActionEvent arg0) {
			
		if(diagram != null){
			
			//Ako je dijagram vec otvoren sklanjamo ga sa taba
			int tabIndex = Application.getInstance().getMainWindow().getTab(diagram.getView());
			if(tabIndex != -1){
				Application.getInstance().getMainWindow().getTabs().removeTabAt(tabIndex);
			}
			
			//Brisemo ga iz liste u Projektu
			diagram.getProject().deleteDiagram(diagram);
			
			//Brisemo ga iz FS
			File diagramDir = diagram.getDiagramDir();
			if(diagramDir != null){
				if(!diagramDir.delete()){
					JOptionPane.showMessageDialog(
							Application.getInstance().getView(),
							Messages.getString("DeleteDiagramAction.2"), //$NON-NLS-1$
							Messages.getString("DeleteDiagramAction.3"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		
		//Update-ujemo JTree
		Application.getInstance().getWorkspace().updateUI();
	}

	public void setDiagram(GERMModel diagram){
		this.diagram = diagram;
	}
}
