package germ.gui.windows;

import germ.i18n.Messages;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Klasa predstavlja prozor za kreiranje novog ili promenu imena postojeceg dijagrama / projekta. 
 */
@SuppressWarnings("serial")
public class NewPodWindow extends JDialog {

	public static int NEW_PROJECT = 0;
	public static int NEW_DIAGRAM = 1;
	public static int EDIT_PROJECT = 2;
	public static int EDIT_DIAGRAM = 3;

	protected JLabel lblNew = new JLabel();
	protected JTextField tfNew = new JTextField(20);
	protected JButton btnOK = new JButton(Messages.getString("NewPodWindow.0")); //$NON-NLS-1$
	protected JButton btnCancel = new JButton(Messages.getString("NewPodWindow.1")); //$NON-NLS-1$
	protected Box okCancelBox = Box.createHorizontalBox();
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	protected boolean dialogResult = false;

	/**
	 * Konstruktor koji u zavisnosti od prosledjene konstante kreira prozor sa adekvatnom labelom i naslovom
	 * @param pod
	 */
	public NewPodWindow(int pod) {
		switch (pod) {
		case 0:
			lblNew.setText(Messages.getString("NewPodWindow.2")); //$NON-NLS-1$
			setTitle(Messages.getString("NewPodWindow.3")); //$NON-NLS-1$
			break;
		case 1:
			lblNew.setText(Messages.getString("NewPodWindow.4")); //$NON-NLS-1$
			setTitle(Messages.getString("NewPodWindow.5")); //$NON-NLS-1$
			break;
		case 2:
			lblNew.setText(Messages.getString("NewPodWindow.6")); //$NON-NLS-1$
			setTitle(Messages.getString("NewPodWindow.7")); //$NON-NLS-1$
			break;
		case 3:
			lblNew.setText(Messages.getString("NewPodWindow.8")); //$NON-NLS-1$
			setTitle(Messages.getString("NewPodWindow.9")); //$NON-NLS-1$
			break;
		}

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();

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
		
		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(40));
		okCancelBox.add(btnCancel);

		setLayout(new GridBagLayout());
		Container container = getContentPane();

		GridBagConstraints c00 = new GridBagConstraints();
		c00.gridx = 0;
		c00.gridy = 0;
		c00.anchor = GridBagConstraints.NORTHEAST;
		c00.insets = new Insets(20, 20, 0, 0);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 1;
		c10.gridy = 0;
		c10.weightx = 1;
		c10.fill = GridBagConstraints.HORIZONTAL;
		c10.anchor = GridBagConstraints.NORTHWEST;
		c10.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 2;
		c2.gridwidth = 2;
		c2.anchor = GridBagConstraints.CENTER;
		c2.insets = new Insets(25, 0, 10, 0);

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

		container.add(lblNew, c00);
		container.add(tfNew, c10);
		container.add(okCancelBox, c2);
		
		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);

	}

	public boolean isDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(boolean dialogResult) {
		this.dialogResult = dialogResult;
	}

	/**
	 * Funkcija definise reakciju programa na pritisak entera ili "ok" dugmeta na prozoru.
	 * Rezultat dijaloga postaje potvrdan i dijalog se sakriva.
	 */
	public void enterPressed() {
		setDialogResult(true);
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

	public String getEnteredName() {
		return this.tfNew.getText();
	}
	
	public void setEnteredName(String oldName){
		this.tfNew.setText(oldName);
	}
}
