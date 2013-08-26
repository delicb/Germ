package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju zumiranje na najbolji moguc prikaz trenutno selektovanih elemenata.
 */
@SuppressWarnings("serial")
public class ZoomBestFitSelectionAction extends AbstractGERMAction {

	ZoomBestFitSelectionAction(){
		putValue(SMALL_ICON, loadIcon("bestfitselected.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("ZoomBestFitSelectionAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ZoomBestFitSelectionAction.2")); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getView().zoomTo(Application.getInstance().getModel().getBestFitRectangle(true));
	}
}
