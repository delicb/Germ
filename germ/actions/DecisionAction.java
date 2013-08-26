package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Decision;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akcija za dodavanje nove {@link Decision Odluke}
 */
@SuppressWarnings("serial")
public class DecisionAction extends AbstractGERMAction {

	public DecisionAction() {
		// TODO Auto-generated constructor stub
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_3,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(SMALL_ICON, loadIcon("decision.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("DecisionAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("DecisionAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertDecision();

	}

}
