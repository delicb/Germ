package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju ponavljanja prethodno zabeleze akcije u aplikaciji.
 */
@SuppressWarnings("serial")
public class RedoAction extends AbstractGERMAction {

    public RedoAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("editredo.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("RedoAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("RedoAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        Application.getInstance().getCommandManager().redoCommand();
    }

}
