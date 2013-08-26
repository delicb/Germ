package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Argument;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Klasa predstavlja akciju selektovanja svih agrumenata na aktuelnom dijagramu.
 */
public class SelectAllArgumentsAction extends AbstractGERMAction {

    private static final long serialVersionUID = -3589791299279057195L;

        SelectAllArgumentsAction(){
            putValue(NAME, Messages.getString("SelectAllArgumentsAction.0")); //$NON-NLS-1$
            putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllArgumentsAction.1")); //$NON-NLS-1$
        }
        
        public void actionPerformed(ActionEvent e) {
            GERMModel m = Application.getInstance().getModel();
            m.deselectAllNodes();
            Iterator<Node> it = m.getNodeIterator();
            while(it.hasNext()) {
                Node node = it.next();
                if(node instanceof Argument)
                    m.selectNode(node);
            }
        }

}
