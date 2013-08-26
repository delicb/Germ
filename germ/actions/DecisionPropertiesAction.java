package germ.actions;
import germ.gui.windows.DecisionProperties;
import germ.i18n.Messages;
import germ.model.nodes.Decision;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Decision Odluke}
 */
@SuppressWarnings("serial")
public class DecisionPropertiesAction extends AbstractGERMAction {

	DecisionPropertiesAction(){
		//putValue(SMALL_ICON, loadIcon("images/decision.png"));
		putValue(NAME, Messages.getString("DecisionPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("DecisionPropertiesAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		DecisionProperties dp = new DecisionProperties();
		dp.setVisible(true);
	}
}
