package germ.gui.windows;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.configuration.InternalConfiguration;
import germ.model.workspace.Workspace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

/**
 * Klasa koja prikazuje prozorče sa imenom programa, stilski napisanim i učitava
 * neke stvari kojima treba vremena pre nego što prepusti kontrolu glavnom
 * programu
 */
@SuppressWarnings("serial")
public class GERMSplashScreen extends JWindow {
	/**
	 * progres bar
	 */
	private JProgressBar progressBar;

	/**
	 * Posto je program jako brz :), ova konstanta sluzi da odredi vreme izmedju
	 * promene stanja progress bara
	 */
	private int sleepTime = InternalConfiguration.PROGRESS_BAR_SLEEP_TIME;

	public GERMSplashScreen() {
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(false);
		progressBar.setMaximum(6);
	}

	/**
	 * Prikazuje splash screen i dok je prikazan učitava kursore, postavlja
	 * ikonice za JTree, učitava podešavanja u xml fajlu i učitava spisak
	 * mogućih tema.
	 */
	public void showSplash() {

		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.white);

		int width = 520;
		int height = 230;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		JLabel label = new JLabel(new ImageIcon(
				"germ/gui/windows/images/novi.png"));
		content.add(label, BorderLayout.CENTER);
		content.add(progressBar, BorderLayout.SOUTH);

		toFront();
		setVisible(true);
		try {
			progressBar.setValue(0);
			Thread.sleep(sleepTime);
			ConfigurationManager cm = ConfigurationManager.getInstance();
			if (!InternalConfiguration.DEVELOPMENT_IN_PROGRES) {

				// ucitavanje tema na osnovu imena direktorijuma
				String themesFolderPath = "germ/actions/images";
				ArrayList<String> themes = new ArrayList<String>();
				File themesFolder = new File(themesFolderPath);
				for (String theme : themesFolder.list()) {
					File f = new File(themesFolder + "/" + theme);
					if (f.isDirectory() && !theme.startsWith(".")) {
						themes.add(theme);
					}

				}
				progressBar.setValue(1);
				Thread.sleep(sleepTime);

				cm.setStringArray("allThemes", themes);
				progressBar.setValue(2);
				Thread.sleep(sleepTime);

				// ucutavano sve kursore
				Class.forName("germ.util.Cursors");
				progressBar.setValue(3);
				Thread.sleep(sleepTime);

				// ucitavamo ikonice za Jtree
				Class.forName("germ.gui.workspace.CustomIconRenderer");
				progressBar.setValue(4);
				Thread.sleep(sleepTime);

				// ucitavamo konfiguraciju iz xml fajla
				Class.forName("germ.configuration.ConfigurationManager");
				progressBar.setValue(5);
				Thread.sleep(sleepTime);

				String possibleWorkspace = ConfigurationManager.getInstance()
						.getString("workspace"); //$NON-NLS-1$
				if (!ConfigurationManager.getInstance().getBoolean(
						"defaultWorkspace") //$NON-NLS-1$
						|| !Workspace.isValidWorkspace(possibleWorkspace)) {
					WorkspaceChooserWindow wcw = new WorkspaceChooserWindow();
					this.setAlwaysOnTop(false);
					this.toBack();
					wcw.setAlwaysOnTop(true);
					wcw.setVisible(true);
					wcw.toFront();
					if (wcw.isDialogResult()) {
						
						MainWindow mw = Application.getInstance()
								.getMainWindow();
						mw
								.setDefaultCloseOperation(MainWindow.DO_NOTHING_ON_CLOSE);
						mw.setAlwaysOnTop(true);
						mw.setSize(1024, 768);
						mw.setVisible(true);
						mw.setAlwaysOnTop(false);
						mw.setLocationRelativeTo(null);
					} else
						System.exit(0);
				} else {
					MainWindow mw = Application.getInstance().getMainWindow();
					mw.setDefaultCloseOperation(MainWindow.DO_NOTHING_ON_CLOSE);
					mw.setAlwaysOnTop(true);
					mw.setSize(1024, 768);
					mw.setVisible(true);
					mw.setAlwaysOnTop(false);
					mw.setLocationRelativeTo(null);
				}

				// prvi put instanciramo aplikaciju da se izvrse sve
				// incijalizacije
				Application.getInstance();
				progressBar.setValue(6);

			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);

	}
}
