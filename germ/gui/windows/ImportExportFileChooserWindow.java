package germ.gui.windows;

import germ.i18n.Messages;
import germ.util.Cursors;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Klasa predstavlja prozor za izbor putanje za import / export projekta u / iz programa.
 */
@SuppressWarnings("serial")
public class ImportExportFileChooserWindow extends JFileChooser {

	public ImportExportFileChooserWindow() {
		
		setCurrentDirectory(new File(System.getProperty("user.home"))); //$NON-NLS-1$
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				Messages.getString("ImportExportFileChooserWindow.1"), "gar"); //$NON-NLS-1$ //$NON-NLS-2$
		this.setFileFilter(filter);
		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
	}
}
