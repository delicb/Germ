package germ.actions;

import germ.app.Application;
import germ.command.CutCommand;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
/**
 * Klasa predstavlja akciju isecanja elementa.
 */
public class CutAction extends AbstractGERMAction {

    public CutAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		//putValue(MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(SMALL_ICON, loadIcon("editcut.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("CutAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("CutAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        Application.getInstance().getCommandManager().doCommand(new CutCommand());
    }
}
