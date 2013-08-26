package germ.actions;

import germ.gui.windows.PositionProperties;
import germ.i18n.Messages;
import germ.model.nodes.Position;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Position Pozicije}
 */
@SuppressWarnings("serial")
public class PositionPropertiesAction extends AbstractGERMAction {

	public PositionPropertiesAction() {
		//putValue(SMALL_ICON, loadIcon("images/position.png"));
		putValue(NAME, Messages.getString("PositionPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("PositionPropertiesAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		PositionProperties ap = new PositionProperties();
		ap.setVisible(true);

	}

}
