package germ.actions;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.ImportExportFileChooserWindow;
import germ.i18n.Messages;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Klasa predstavlja akciju importovanja projekta u radni direktorijum
 */
@SuppressWarnings("serial")
public class ImportAction extends AbstractGERMAction {

	public ImportAction() {

		putValue(NAME, Messages.getString("ImportAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ImportAction.1")); //$NON-NLS-1$
	}

	/**
	 * Vrsi se ucitavanje prethodno arhiviranog projekta i njegovo raspakivanje u 
	 * radni direktorijum.
	 */
	public void actionPerformed(ActionEvent e) {
		String loadPath = getLoadPath();
		if (loadPath != null) {
			File archive = new File(loadPath);
			if (archive.exists()) {
				try {
					String project = archive.getName().split("\\.")[0];
					String workspace = ConfigurationManager.getInstance().getString("workspace");
					File projectDir = new File(workspace + File.separator + project);
					if (projectDir.exists()) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(), Messages.getString("ImportAction.2"), //$NON-NLS-1$
								Messages.getString("ImportAction.3"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					projectDir.mkdir();
					byte data[] = new byte[1024];
					ZipInputStream in = new ZipInputStream(
							new BufferedInputStream(new FileInputStream(archive
									.getCanonicalPath())));
					BufferedOutputStream out = null;
					ZipEntry entry;
					while ((entry = in.getNextEntry()) != null) {
						int len;
						out = new BufferedOutputStream(new FileOutputStream(
								projectDir + File.separator + entry.getName()), 1024);
						while ((len = in.read(data, 0, 1024)) != -1) {
							out.write(data, 0, len);
						}
						out.flush();
						out.close();
					}
					Application.getInstance().addProject(projectDir);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(Application.getInstance()
						.getView(), Messages.getString("ImportAction.4"), //$NON-NLS-1$
						Messages.getString("ImportAction.5"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Funkcija otvara prozor sa kojeg korisnik definise koju arhivu zeli da importuje.
	 * @return putanja do selektovane arhive
	 */
	private String getLoadPath() {
		ImportExportFileChooserWindow chooser = new ImportExportFileChooserWindow();
		if (chooser.showOpenDialog(Application.getInstance().getMainWindow()) == JFileChooser.APPROVE_OPTION) {
			String file = chooser.getSelectedFile().getAbsolutePath();
			return file;
		}
		return null;
	}

}
