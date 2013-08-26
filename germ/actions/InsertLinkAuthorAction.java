package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.links.LinkType;
import germ.state.State;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za unos nove autorske veze.
 */
@SuppressWarnings("serial")
public class InsertLinkAuthorAction extends AbstractGERMAction {
	public InsertLinkAuthorAction() {
	        putValue(NAME, Messages.getString("InsertLinkAuthorAction.0")); //$NON-NLS-1$
	        putValue(SHORT_DESCRIPTION, Messages.getString("InsertLinkAuthorAction.1")); //$NON-NLS-1$
	    }

	    public void actionPerformed(ActionEvent arg0) {
	    	State.insertLinkState.setLinkToInsert(LinkType.AUTHOR_LINK); //$NON-NLS-1$
	    	Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("InsertLinkAuthorAction.3"), 0);	//$NON-NLS-1$
	        Application.getInstance().getStateMachine().insertLink();
	    }
}
