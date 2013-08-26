package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Stakeholder;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih stakeholdera na aktuelnom dijagramu.
 */
public class SelectAllStakeholdersAction extends AbstractGERMAction {

    private static final long serialVersionUID = 8861004384647798087L;

    SelectAllStakeholdersAction() {
        putValue(NAME, Messages.getString("SelectAllStakeholdersAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllStakeholdersAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Stakeholder)
                m.selectNode(node);
        }
    }
}
