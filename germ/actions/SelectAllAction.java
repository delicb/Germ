package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Klasa predstavlja akciju selektovanja svih elemenata na aktuelnom dijagramu.
 */
public class SelectAllAction extends AbstractGERMAction {
	private static final long serialVersionUID = 5362240054245750777L;

	SelectAllAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		putValue(NAME, Messages.getString("SelectAllAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("SelectAllAction.1")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getModel().selectAllNodes();
	}
}
