package germ.actions;


import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju promene (ukljucivanja / iskljucivanja) prikaza mreze na kanvasu.
 */
@SuppressWarnings("serial")
public class SwitchShowGridAction extends AbstractGERMAction {
    
    SwitchShowGridAction(){
        putValue(NAME, Messages.getString("SwitchShowGridAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SwitchShowGridAction.1")); //$NON-NLS-1$
    }
    
    public void actionPerformed(ActionEvent e) {
        boolean show = ConfigurationManager.getInstance().getBoolean("showGrid"); //$NON-NLS-1$
        ConfigurationManager.getInstance().setBoolean("showGrid",!show); //$NON-NLS-1$
        Application.getInstance().getView().updatePerformed(null);
    }
}

