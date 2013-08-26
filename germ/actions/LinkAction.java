package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju za prikazivanje pop-up menija za dodavanje novog linka. 
 */
@SuppressWarnings("serial")
public class LinkAction extends AbstractGERMAction {

    public LinkAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_8,
                ActionEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        putValue(SMALL_ICON, loadIcon("Link.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("LinkAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("LinkAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent arg0) {
    	JPopupMenu links = new JPopupMenu();
    	ActionManager actionManager = Application.getInstance().getActionManager();
    	JToolBar tb = Application.getInstance().getMainWindow().getToolbarElements();
		links.add(actionManager.getLinkAuthorAction());
		links.add(actionManager.getLinkSupportAction());
		links.add(actionManager.getLinkInterestAction());
		links.add(actionManager.getLinkRefineAction());
		links.add(actionManager.getLinkDependencyAction());
		
		links.show(tb.getComponent(8), 0, ((Icon)getValue(SMALL_ICON)).getIconHeight());
    }
}
