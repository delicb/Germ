package germ.actions;

import germ.gui.windows.StakeholderProperties;
import germ.i18n.Messages;
import germ.model.nodes.Stakeholder;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Stakeholder Stakeholdera}
 */
@SuppressWarnings("serial")
public class StakeholderPropertiesAction extends AbstractGERMAction {

	public StakeholderPropertiesAction() {
		//putValue(SMALL_ICON, loadIcon("images/stakeholder.png"));
		putValue(NAME, Messages.getString("StakeholderPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("StakeholderPropertiesAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		StakeholderProperties sp = new StakeholderProperties();
		sp.setVisible(true);
	}
}
