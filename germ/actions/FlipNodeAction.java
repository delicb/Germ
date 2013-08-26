package germ.actions;


import germ.app.Application;
import germ.command.FlipCommand;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za obrtanje elementa kao u ogledalu.
 */
@SuppressWarnings("serial")
public class FlipNodeAction extends AbstractGERMAction {
    public FlipNodeAction() {
        putValue(NAME, Messages.getString("FlipNodeAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("FlipNodeAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        Application.getInstance().getCommandManager().doCommand(new FlipCommand());
        Application.getInstance().getView().repaint();
    }

}
