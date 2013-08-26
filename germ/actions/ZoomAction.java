package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju zumiranja prikaza dijagrama.
 */
public class ZoomAction extends AbstractGERMAction {
	private static final long serialVersionUID = 5362240054245750777L;

	ZoomAction() {
		putValue(SMALL_ICON, loadIcon("zoomin.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("ZoomAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ZoomAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().zoomClicked();
	}
}
