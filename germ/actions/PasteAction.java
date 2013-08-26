package germ.actions;

import germ.app.Application;
import germ.command.PasteCommand;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju za zalepljivanje elementa na kanvas.
 */
@SuppressWarnings("serial")
public class PasteAction extends AbstractGERMAction {

    public PasteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("editpaste.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("PasteAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("PasteAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        Application.getInstance().getCommandManager().doCommand(new PasteCommand());
    }

}
