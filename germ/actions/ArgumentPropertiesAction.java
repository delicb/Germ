package germ.actions;

import germ.gui.windows.ArgumentProperties;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Argument Argumenta}
 */
@SuppressWarnings("serial")
public class ArgumentPropertiesAction extends AbstractGERMAction {
	public ArgumentPropertiesAction() {
		//putValue(SMALL_ICON, loadIcon("images/argument.png"));
		putValue(NAME, Messages.getString("ArgumentPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ArgumentPropertiesAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		ArgumentProperties ap = new ArgumentProperties();
		ap.setVisible(true);
		
	}
}
