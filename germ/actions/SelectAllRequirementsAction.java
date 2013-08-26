package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Requirement;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih zahteva na aktuelnom dijagramu.
 */
public class SelectAllRequirementsAction extends AbstractGERMAction {

    private static final long serialVersionUID = -6808438252649556476L;

    SelectAllRequirementsAction() {
        putValue(NAME, Messages.getString("SelectAllRequirementsAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllRequirementsAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Requirement)
                m.selectNode(node);
        }
    }
}
