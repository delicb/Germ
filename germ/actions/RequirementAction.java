package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Requirement;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akcija za dodavanje novog {@link Requirement Zahteva}
 */
@SuppressWarnings("serial")
public class RequirementAction extends AbstractGERMAction {

	public RequirementAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(SMALL_ICON, loadIcon("requirement.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("RequirementAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("RequirementAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertRequirement();
	}
}
