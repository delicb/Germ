package germ.actions;

import germ.app.Application;
import germ.gui.windows.PropertyWindow;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Klasa predstavlja akciju prikazivanja svojstava selektovanog noda.
 */
@SuppressWarnings("serial")
public class ShowPropertyAction extends AbstractGERMAction {
    public ShowPropertyAction() {
        putValue(MNEMONIC_KEY, KeyEvent.VK_P);
        putValue(SMALL_ICON, loadIcon("property.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("ShowPropertyAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("ShowPropertyAction.2")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
            Node selectedNode = m.getSelectedNodes().get(0);
            PropertyWindow property = selectedNode.getPropertyWindow();
            property.setVisible(true);
            selectedNode.setProperties(property.isDialogResult());
            m.updatePerformed();
    }
}
