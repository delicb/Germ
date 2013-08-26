package germ.actions;

import germ.app.Application;
import germ.gui.windows.MainWindow;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;


/**
 * Klasa predstavlja akciju snimanja dijagrama
 */
@SuppressWarnings("serial")
public class FileSaveAction extends AbstractGERMAction{

	FileSaveAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(SMALL_ICON, loadIcon("filesave.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("FileSaveAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("FileSaveAction.2")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		Application app = Application.getInstance();
		MainWindow mw = app.getMainWindow();
		mw.setStatusBarMessage(Messages.getString("FileSaveAction.3"), 0);	//$NON-NLS-1$
		app.getModel().save();
		mw.setStatusBarMessage(Messages.getString("FileSaveAction.4"), 0);	//$NON-NLS-1$
    }
}
