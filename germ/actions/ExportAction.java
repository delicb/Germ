package germ.actions;

import germ.app.Application;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.ImportExportFileChooserWindow;
import germ.i18n.Messages;
import germ.model.workspace.Project;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;


/**
 * Klasa predtavlja akciju Expotrovanja projekta kao arhive.
 */
@SuppressWarnings("serial")
public class ExportAction extends AbstractGERMAction {

	private Project projectToExport;

	public ExportAction() {
		putValue(NAME, Messages.getString("ExportAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ExportAction.1")); //$NON-NLS-1$
	}

	/**
	 * Vrsi se prikupljanje dijagrama selektovanog projekta koje se
	 * zatim pakuju u arhivu koja se snima na korisnicki definisanu destinaciju.
	 */
	public void actionPerformed(ActionEvent arg0) {

		try {
			String projectPath = projectToExport.getProjectDir()
					.getCanonicalPath();

			File[] diagrams = new File[projectToExport.getDiagramCount()];
			for (int i = 0; i != projectToExport.getDiagramCount(); i++) {
				diagrams[i] = new File(projectPath + File.separator
						+ projectToExport.getDiagram(i).getFileName());
			}
			// Create a buffer for reading the files
			byte[] buf = new byte[1024];

			try {
				String savePath = getSavePath();
				if (savePath != null) {
					String outFilename;
					if (savePath
							.endsWith(InternalConfiguration.ARCHIVE_EXTENSION)) {
						outFilename = savePath;
					} else {
						outFilename = savePath + "."
								+ InternalConfiguration.ARCHIVE_EXTENSION;
					}
					ZipOutputStream out = new ZipOutputStream(
							new FileOutputStream(outFilename));
					out.setLevel(Deflater.DEFAULT_COMPRESSION);

					for (int i = 0; i < diagrams.length; i++) {
						FileInputStream in = new FileInputStream(diagrams[i]);

						out.putNextEntry(new ZipEntry(diagrams[i].getName()));

						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}

						out.closeEntry();
						in.close();
					}
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void setProjectToExport(Project project) {
		this.projectToExport = project;
	}

	/**
	 * Funkcija otvara prozor za izbor destinacije snimanja arhive koja se exportuje
	 * 
	 * @return putanja na koju treba da se snimi arhiva.
	 */
	private String getSavePath() {
		ImportExportFileChooserWindow chooser = new ImportExportFileChooserWindow();
		if (chooser.showOpenDialog(Application.getInstance().getMainWindow()) == JFileChooser.APPROVE_OPTION) {
			String file = chooser.getSelectedFile().getAbsolutePath();
			return file;
		}
		return null;
	}

}
