package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Assumption;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akcija za dodavanje nove {@link Assumption Pretpostavke }
 */
@SuppressWarnings("serial")
public class AssumptionAction extends AbstractGERMAction {

	public AssumptionAction() {
		// TODO Auto-generated constructor stub
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_2,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
		putValue(SMALL_ICON, loadIcon("assumption.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("AssumptionAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("AssumptionAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		Application.getInstance().getStateMachine().insertAssumption();

	}

}
