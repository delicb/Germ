package germ.actions;

import germ.gui.windows.RequirementProperties;
import germ.i18n.Messages;
import germ.model.nodes.Requirement;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Requirement Zahteva}
 */
@SuppressWarnings("serial")
public class RequirementPropertiesAction extends AbstractGERMAction {

	public RequirementPropertiesAction() {
		//putValue(SMALL_ICON, loadIcon("images/requirement.png"));
		putValue(NAME, Messages.getString("RequirementPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("RequirementPropertiesAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent arg0) {
		RequirementProperties rq = new RequirementProperties();
		rq.setVisible(true);
	}
}
