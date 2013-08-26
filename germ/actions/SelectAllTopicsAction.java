package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Topic;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih tema na aktuelnom dijagramu.
 */
public class SelectAllTopicsAction extends AbstractGERMAction {

    private static final long serialVersionUID = 7240892077500826446L;

    SelectAllTopicsAction() {
        putValue(NAME, Messages.getString("SelectAllTopicsAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllTopicsAction.1")); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Topic)
                m.selectNode(node);
        }
    }
}
