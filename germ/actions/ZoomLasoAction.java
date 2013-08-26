package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju za pokretanje laso-zuma.
 */
@SuppressWarnings("serial")
public class ZoomLasoAction extends AbstractGERMAction {

	ZoomLasoAction(){
		putValue(SMALL_ICON, loadIcon("lasozoom.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("ZoomLasoAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ZoomLasoAction.2")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getStateMachine().lasoZoom();
	}
}
