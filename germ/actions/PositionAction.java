package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Position;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akcija za dodavanje nove {@link Position Pozicije}
 */
@SuppressWarnings("serial")
public class PositionAction extends AbstractGERMAction {

	public PositionAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_4,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(SMALL_ICON, loadIcon("position.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("PositionAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("PositionAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertPosition();

	}

}
