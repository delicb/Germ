package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Decision;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih odluka na aktuelnom dijagramu.
 */
public class SelectAllDecisionsAction extends AbstractGERMAction {

    private static final long serialVersionUID = -1064529362322151169L;

    SelectAllDecisionsAction(){
        putValue(NAME, Messages.getString("SelectAllDecisionsAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllDecisionsAction.1")); //$NON-NLS-1$
    }
    
    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while(it.hasNext()) {
            Node node = it.next();
            if(node instanceof Decision)
                m.selectNode(node);
        }
    }
}
