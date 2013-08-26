package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju za zatvaranje aplikacije
 */
@SuppressWarnings("serial")
public class QuitAction extends AbstractGERMAction {

	QuitAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_K);
		putValue(SMALL_ICON, loadIcon("quit.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("QuitAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("QuitAction.2")); //$NON-NLS-1$
	}
	public void actionPerformed(ActionEvent e) {
		Application.getInstance().shutdown();
	}

}
