package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju za inverziju trenutne selekcije elemenata.
 */
@SuppressWarnings("serial")
public class InverseSelectionAction extends AbstractGERMAction {
	InverseSelectionAction(){

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		putValue(NAME, Messages.getString("InverseSelectionAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("InverseSelectionAction.1")); //$NON-NLS-1$	
	}
	
	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getModel().inverseSelection();
	}
}
