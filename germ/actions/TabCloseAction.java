package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

/**
 * Klasa predstavlja akciju gasenja selektovanog taba.
 */
@SuppressWarnings("serial")
public class TabCloseAction extends AbstractGERMAction {

	public TabCloseAction() {
        putValue(NAME, Messages.getString("TabCloseAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("TabCloseAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		JTabbedPane tabs = Application.getInstance().getMainWindow().getTabs();
		if(tabs.getComponentCount()>1)
		tabs.remove(tabs.getSelectedIndex());
	}

}
