package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Assumption;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih pretpostavki na aktuelnom dijagramu.
 */
public class SelectAllAssumptionsAction extends AbstractGERMAction {

    private static final long serialVersionUID = 2666628669503171201L;

    SelectAllAssumptionsAction(){
        putValue(NAME, Messages.getString("SelectAllAssumptionsAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllAssumptionsAction.1")); //$NON-NLS-1$
    }
    
    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        m.deselectAllNodes();
        Iterator<Node> it = m.getNodeIterator();
        while(it.hasNext()) {
            Node node = it.next();
            if(node instanceof Assumption)
                m.selectNode(node);
        }
    }

}
