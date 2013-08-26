package germ.actions;

import germ.gui.windows.AssumptionProperties;
import germ.i18n.Messages;
import germ.model.nodes.Assumption;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Assumption Pretpostavke}
 */
@SuppressWarnings("serial")
public class AssumptionPropertiesAction extends AbstractGERMAction {

	public AssumptionPropertiesAction() {
		//putValue(SMALL_ICON, loadIcon("images/assumption.png"));
		putValue(NAME, Messages.getString("AssumptionPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("AssumptionPropertiesAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		AssumptionProperties ap = new AssumptionProperties();
		ap.setVisible(true);

	}

}
