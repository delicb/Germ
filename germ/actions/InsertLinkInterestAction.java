package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.links.LinkType;
import germ.state.State;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju unosa nove interesne veze
 */
@SuppressWarnings("serial")
public class InsertLinkInterestAction extends AbstractGERMAction {
	public InsertLinkInterestAction() {
        putValue(NAME, Messages.getString("InsertLinkInterestAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("InsertLinkInterestAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
    	State.insertLinkState.setLinkToInsert(LinkType.INTEREST_LINK); //$NON-NLS-1$
    	Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("InsertLinkInterestAction.3"), 0);	//$NON-NLS-1$
        Application.getInstance().getStateMachine().insertLink();
    }
}
