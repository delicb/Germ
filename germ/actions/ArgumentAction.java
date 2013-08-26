package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Argument;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
/**
 * Akcija za dodavanje novog {@link Argument Argumenta}
 */
@SuppressWarnings("serial")
public class ArgumentAction extends AbstractGERMAction {

	public ArgumentAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(SMALL_ICON, loadIcon("argument.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("ArgumentAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ArgumentAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertArgument();
	}
}
