package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.links.LinkType;
import germ.state.State;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju unosa nove veze podrzavanja
 */
@SuppressWarnings("serial")
public class InsertLinkSupportAction extends AbstractGERMAction {
	public InsertLinkSupportAction() {
        putValue(NAME, Messages.getString("InsertLinkSupportAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("InsertLinkSupportAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
    	State.insertLinkState.setLinkToInsert(LinkType.SUPPORT_LINK); //$NON-NLS-1$
    	Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("InsertLinkSupportAction.3"), 0);	//$NON-NLS-1$
        Application.getInstance().getStateMachine().insertLink();
    }
}
