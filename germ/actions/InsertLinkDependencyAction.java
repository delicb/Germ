package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.links.LinkType;
import germ.state.State;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za unos nove veze zavisnosti
 */
@SuppressWarnings("serial")
public class InsertLinkDependencyAction extends AbstractGERMAction {
	public InsertLinkDependencyAction() {
        putValue(NAME, Messages.getString("InsertLinkDependencyAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("InsertLinkDependencyAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
    	State.insertLinkState.setLinkToInsert(LinkType.DEPENDENCY_LINK); //$NON-NLS-1$
    	Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("InsertLinkDependencyAction.3"), 0); //$NON-NLS-1$
        Application.getInstance().getStateMachine().insertLink();
    }
}
