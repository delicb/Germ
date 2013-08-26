package germ.gui.windows;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;
import germ.model.workspace.Workspace;
import germ.util.Cursors;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 * Klasa predstavlja prozor za izbor workspace-a u kome ce se raditi po pokretanju aplikacije.
 */
@SuppressWarnings("serial")
public class WorkspaceChooserWindow extends JDialog {

	private JLabel lblWorkspace = new JLabel(Messages.getString("WorkspaceChooserWindow.0")); //$NON-NLS-1$
	private JTextField tfWorkspace = new JTextField();
	private JButton btnBrowse = new JButton(Messages.getString("WorkspaceChooserWindow.1")); //$NON-NLS-1$
	private JCheckBox chbUseAsDefault = new JCheckBox(
			Messages.getString("WorkspaceChooserWindow.2")); //$NON-NLS-1$
	private JButton btnOK = new JButton(Messages.getString("WorkspaceChooserWindow.3")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages.getString("WorkspaceChooserWindow.4")); //$NON-NLS-1$
	private Box okCancelBox = Box.createHorizontalBox();
	private Box workspaceBox = Box.createHorizontalBox();
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	private boolean dialogResult = false;

	public WorkspaceChooserWindow() {

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle(Messages.getString("WorkspaceChooserWindow.5")); //$NON-NLS-1$
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();

		String currentWorkspace = ConfigurationManager.getInstance().getString(
				"workspace"); //$NON-NLS-1$
		if (Workspace.isValidWorkspace(currentWorkspace)) {
			setWorkspace(currentWorkspace);
		} else {
			currentWorkspace = System.getProperty("user.home") + File.separator + "GERM"; //$NON-NLS-1$ //$NON-NLS-2$
			setWorkspace(currentWorkspace);
		}

		Container container = getContentPane();

		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = 0;
		c0.gridwidth = 2;
		c0.fill = GridBagConstraints.HORIZONTAL;
		c0.anchor = GridBagConstraints.WEST;
		c0.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.WEST;
		c01.insets = new Insets(20, 20, 0, 0);

		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 1;
		c11.gridy = 1;
		c11.anchor = GridBagConstraints.EAST;
		c11.insets = new Insets(20, 20, 10, 20);

		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(20));
		okCancelBox.add(btnCancel);
		
		workspaceBox.add(lblWorkspace);
		workspaceBox.add(Box.createHorizontalStrut(10));
		workspaceBox.add(tfWorkspace);
		workspaceBox.add(Box.createHorizontalStrut(15));
		workspaceBox.add(btnBrowse);
		
		container.add(workspaceBox, c0);
		container.add(chbUseAsDefault, c01);
		container.add(okCancelBox, c11);

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setWorkspace(Workspace
						.workspaceChooser(WorkspaceChooserWindow.this));
			}
		});

		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterPressed();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escapePressed();
			}
		});

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					escapePressed();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enterPressed();
				}
				return false;
			}
		});
		
		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);

	}

	public String getWorkspace() {
		return tfWorkspace.getText();
	}

	public void setWorkspace(String workspace) {
		this.tfWorkspace.setText(workspace);
	}

	public boolean isUseAsDefault() {
		return chbUseAsDefault.isSelected();
	}

	public boolean isDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(boolean dialogResult) {
		this.dialogResult = dialogResult;
	}

	/**
	 * Funkcija definise reakciju programa na pritisak entera ili "ok" dugmeta na prozoru.
	 * Pre zatvaranja prozora ce se ukoliko unesena putanja za workspace ne postoji napraviti novi folder sa adekvatnim nazivom
	 * i uneti nova podesavanja u kofiguracioni menadzer.
	 * Rezultat dijaloga postaje potvrdan i dijalog se sakriva.
	 */
	public void enterPressed() {
		setDialogResult(true);
		File newProjectDir = new File(getWorkspace());
		if(!newProjectDir.exists()){
			if (!newProjectDir.mkdirs()){ 
				setAlwaysOnTop(false);
				JOptionPane.showMessageDialog(Application.getInstance().getMainWindow(), Messages.getString("WorkspaceChooserWindow.10")); //$NON-NLS-1$
			}
		}
		ConfigurationManager.getInstance().setString("workspace", getWorkspace()); //$NON-NLS-1$
		if(isUseAsDefault()) ConfigurationManager.getInstance().setBoolean("defaultWorkspace",true); //$NON-NLS-1$
		setVisible(false);
	}

	/**
	 * Funkcija definise reakciju programa na pritisak escape-a ili "Cancel" dugmeta na prozoru.
	 * Rezultat dijaloga postaje odrican i dijalog se sakriva.
	 */
	public void escapePressed() {
		setDialogResult(false);
		setVisible(false);
	}

}
