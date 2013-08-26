package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akcija za dodavanje novog {@link Stakeholder Stakeholdera}
 */
@SuppressWarnings("serial")
public class StakeholderAction extends AbstractGERMAction {

	public StakeholderAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(SMALL_ICON, loadIcon("stakeholder.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("StakeholderAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("StakeholderAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertStakeholder();

	}
}
