package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Position;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih pozicija na aktuelnom dijagramu.
 */
public class SelectAllPositionsAction extends AbstractGERMAction {

    private static final long serialVersionUID = 8369069031252786304L;

    SelectAllPositionsAction(){
        putValue(NAME, Messages.getString("SelectAllPositionsAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllPositionsAction.1")); //$NON-NLS-1$
    }
    
    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while(it.hasNext()) {
            Node node = it.next();
            if(node instanceof Position)
                m.selectNode(node);
        }
    }
}
