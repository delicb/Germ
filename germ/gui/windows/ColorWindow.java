package germ.gui.windows;

import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Color;
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
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Klasa predstavlja prozor za izbor boje nekog elementa
 */
@SuppressWarnings("serial")
public class ColorWindow extends JDialog implements ChangeListener {

	private JColorChooser colorChooser = new JColorChooser();
	private JButton btnOK = new JButton(Messages.getString("ColorWindow.0")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages.getString("ColorWindow.1")); //$NON-NLS-1$
	private Box okCancelBox = Box.createHorizontalBox();
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	private boolean dialogResult = false;
	/**
	 * Izabrana boja
	 */
	private Color chosenColor = null;

	public ColorWindow() {

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setResizable(false);
		setTitle(Messages.getString("ColorWindow.2")); //$NON-NLS-1$

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

		colorChooser.getSelectionModel().addChangeListener(this);

		setLayout(new GridBagLayout());
		Container container = getContentPane();

		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = 0;
		c0.weightx = 1;
		c0.fill = GridBagConstraints.HORIZONTAL;
		c0.anchor = GridBagConstraints.CENTER;
		c0.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 1;
		c1.weightx = 1;
		c1.anchor = GridBagConstraints.CENTER;
		c1.insets = new Insets(20, 20, 10, 20);

		container.add(colorChooser, c0);

		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(40));
		okCancelBox.add(btnCancel);

		container.add(okCancelBox, c1);

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

	public void stateChanged(ChangeEvent e) {
		chosenColor = colorChooser.getColor();
	}

	public Color getColor() {
		return chosenColor;
	}

	public void setColor(Color choosenColor) {
		this.chosenColor = choosenColor;
		this.colorChooser.setColor(choosenColor);
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
}
