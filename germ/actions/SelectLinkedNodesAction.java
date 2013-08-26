package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju selektovanja povezanih nodova vec selektovane veze.
 */
@SuppressWarnings("serial")
public class SelectLinkedNodesAction extends AbstractGERMAction {
    
    SelectLinkedNodesAction() {
        putValue(NAME, Messages.getString("SelectLinkedNodesAction.0")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("SelectLinkedNodesAction.1")); //$NON-NLS-1$
    }
    
    public void actionPerformed(ActionEvent e) {
        GERMModel m = Application.getInstance().getModel();
        Node node = m.getSelectedNodes().get(0);
        for(Link l : m.getLinks()) {
            if(l.getSource() == node)
                m.selectNode(l.getDestination());
            if( l.getDestination() == node)
                m.selectNode(l.getSource());
        }
    }   
}
