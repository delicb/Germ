package germ.actions;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.SettingsWindow;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * Klasa predstavlja akciju prikazivanja prozora sa opstim opcijama aplikacije.
 */
@SuppressWarnings("serial")
public class SettingsAction extends AbstractGERMAction {

	SettingsAction() {
		putValue(NAME, Messages.getString("SettingsAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("SettingsAction.1")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent arg0) {
		SettingsWindow sw = new SettingsWindow();
		ConfigurationManager cm = ConfigurationManager.getInstance();
		String oldTheme = cm.getString("theme"); //$NON-NLS-1$
		String oldLanguage = cm.getString("language");
		sw.getSettings();
		sw.setVisible(true);
		if (sw.isDialogResult())
			sw.setSettings();

		String newTheme = cm.getString("theme"); //$NON-NLS-1$
		String newLanguage = cm.getString("language");
		if (!oldTheme.equals(newTheme)) {
			// ne treba ovako, ali ovako cemo za sada uraditi
			Application.getInstance().themeChanged();
		}

		if (!oldLanguage.equals(newLanguage)) {
			JOptionPane
					.showMessageDialog(
							Application.getInstance().getView(),
							Messages.getString("SettingsAction.2"), //$NON-NLS-1$
							Messages.getString("SettingsAction.3"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
