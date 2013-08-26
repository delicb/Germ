package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.nodes.Topic;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Akzija za dodavanje nove {@link Topic Teme}
 */
@SuppressWarnings("serial")
public class TopicAction extends AbstractGERMAction {

	public TopicAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_7,
				ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(SMALL_ICON, loadIcon("topic.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("TopicAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("TopicAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().insertTopic();
	}

}
