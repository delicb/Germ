package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

/**
 * Klasa predstavlja akciju gasenja svih tabova na kanvasu osim selektovanog taba. 
 */
@SuppressWarnings("serial")
public class TabCloseOthersAction extends AbstractGERMAction {

	public TabCloseOthersAction() {
        putValue(NAME, Messages.getString("TabCloseOthersAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("TabCloseOthersAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		JTabbedPane tabs = Application.getInstance().getMainWindow().getTabs();
		if(tabs.getComponentCount()>1){
			Component selected = tabs.getSelectedComponent();
			for(Component c:tabs.getComponents())
				if(c!=selected)
				tabs.remove(c);
		}
	}
}
