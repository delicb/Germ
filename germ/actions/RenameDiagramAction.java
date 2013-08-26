package germ.actions;

import germ.app.Application;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.NewPodWindow;
import germ.i18n.Messages;
import germ.model.GERMModel;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Klasa predstavlja akciju preimenovanja dijagrama
 */
@SuppressWarnings("serial")
public class RenameDiagramAction extends AbstractGERMAction {
	
	public RenameDiagramAction() {
		putValue(NAME, Messages.getString("RenameDiagramAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("RenameDiagramAction.1")); //$NON-NLS-1$
	}

	/**
	 * Pronalazi se dijagram koji treba da se preimenuje. Na osnovu unesenog novog imena
	 * brise se dijagram (sa starim nazivom) iz fajl-sistema i pravi se novi dijagram sa novim imenom
	 * koji se odmah zatim i snima da bi ostao sacuvan u fajl-sistemu pod novim imenom.
	 */
	public void actionPerformed(ActionEvent e) {
		NewPodWindow rd = new NewPodWindow(NewPodWindow.EDIT_DIAGRAM);
		GERMModel current = Application.getInstance().getModel();
		rd.setEnteredName(current.toString());
		rd.setVisible(true);
		if (rd.isDialogResult()) {
			try {
				File oldDiagram = new File(current.getDiagramPath());
				File newDiagram = new File(current.getProject().getProjectDir()
						.getCanonicalPath()
						+ File.separator
						+ rd.getEnteredName()
						+ "." //$NON-NLS-1$
						+ InternalConfiguration.DIAGRAM_EXTENSION);
				current.setName(rd.getEnteredName());
				oldDiagram.renameTo(newDiagram);
				current.save();
			} catch (IOException e1) {
				JOptionPane
						.showMessageDialog(
								Application.getInstance().getView(),
								Messages.getString("RenameDiagramAction.3"), //$NON-NLS-1$
								"ERROR", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
			Application.getInstance().getMainWindow().renameDiagram(current);
			current.updatePerformed();
		}
	}
	


}
