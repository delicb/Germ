package germ.actions;

import germ.gui.windows.FindReplaceWindow;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;


/**
 * Klasa predstavlja akciju otvaranja {@link FindReplaceWindow Find/Replace prozor}
 */
@SuppressWarnings("serial")
public class FindReplaceAction extends AbstractGERMAction {

	public FindReplaceAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK));
		putValue(NAME, Messages.getString("FindReplaceAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("FindReplaceAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent arg0) {
		FindReplaceWindow frw = new FindReplaceWindow();
		frw.setVisible(true);
	}

}
