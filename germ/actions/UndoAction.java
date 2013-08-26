package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju ponistavanja poslednje izvrsene akcije.
 */
@SuppressWarnings("serial")
public class UndoAction extends AbstractGERMAction {
    
    public UndoAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("editundo.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("UndoAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("UndoAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
        Application.getInstance().getCommandManager().undoCommand();
    }
}
