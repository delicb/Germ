package germ.actions;

import germ.app.Application;
import germ.command.DeleteCommand;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
/**
 * Klasa predstavlja akciju brsanja elementa.
 */
public class DeleteAction extends AbstractGERMAction {

	public DeleteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		putValue(MNEMONIC_KEY, KeyEvent.VK_DELETE);
		putValue(SMALL_ICON, loadIcon("delete.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("DeleteAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("DeleteAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		Application.getInstance().getCommandManager().doCommand(new DeleteCommand());
	}

}
