package germ.actions;

import germ.gui.windows.AboutWindow;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za pokretanje about prozora.
 *
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractGERMAction {

	public AboutAction(){
		putValue(NAME, Messages.getString("AboutAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("AboutAction.1")); //$NON-NLS-1$
	}
	/* Metoda prikazuje {@link germ.gui.windows.AboutWindow}
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		AboutWindow aw = new AboutWindow();
		aw.setVisible(true);
	}

}
