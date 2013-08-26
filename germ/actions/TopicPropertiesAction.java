package germ.actions;

import germ.gui.windows.TopicProperties;
import germ.i18n.Messages;
import germ.model.nodes.Topic;

import java.awt.event.ActionEvent;

/**
 * Akcija za prikaz prozora sa osobinama {@link Topic Teme}
 */
@SuppressWarnings("serial")
public class TopicPropertiesAction extends AbstractGERMAction {
	
	public TopicPropertiesAction() {;
		//putValue(SMALL_ICON, loadIcon("images/topic.png"));
		putValue(NAME, Messages.getString("TopicPropertiesAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("TopicPropertiesAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		TopicProperties tp = new TopicProperties();
		tp.setVisible(true);
	}

}
