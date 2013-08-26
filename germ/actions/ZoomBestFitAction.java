package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

/**
 * Klasa predstavlja akciju zumiranja tako da se svi postojeci nodovi na dijagramu vide.
 * Tzv Zumiranje na najbolji prikaz.
 */
@SuppressWarnings("serial")
public class ZoomBestFitAction extends AbstractGERMAction {

	ZoomBestFitAction() {
		putValue(SMALL_ICON, loadIcon("bestfit.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("ZoomBestFitAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ZoomBestFitAction.2")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		Application.getInstance().getView()
				.zoomTo(
						Application.getInstance().getModel()
								.getBestFitRectangle(false));
	}
}
