package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.links.LinkType;
import germ.state.State;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju unosa nove veze rafinacije
 */
@SuppressWarnings("serial")
public class InsertLinkRefineAction extends AbstractGERMAction {
	public InsertLinkRefineAction() {
        putValue(NAME, Messages.getString("InsertLinkRefineAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("InsertLinkRefineAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
    	State.insertLinkState.setLinkToInsert(LinkType.REFINE_LINK); //$NON-NLS-1$
    	Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("InsertLinkRefineAction.3"), 0);
        Application.getInstance().getStateMachine().insertLink();
    }
}
