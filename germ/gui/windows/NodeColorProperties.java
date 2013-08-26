package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Klasa predstavlja prozor za promenu boja grupe selektovanih nodova.
 */
@SuppressWarnings("serial")
public class NodeColorProperties extends JDialog {

	/**
	 * Predlozene vrednosti pri izboru debljine noda.
	 */
	private Double items[] = new Double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };

	private JLabel lblStroke = new JLabel(Messages.getString("NodeColorProperties.0")); //$NON-NLS-1$
	private JLabel lblFill = new JLabel(Messages.getString("NodeColorProperties.1")); //$NON-NLS-1$
	private JLabel lblThickness = new JLabel(Messages.getString("NodeColorProperties.2")); //$NON-NLS-1$
	private JButton btnStrokeColor = new JButton(Messages.getString("NodeColorProperties.3")); //$NON-NLS-1$
	private JCheckBox chbGradient = new JCheckBox(Messages.getString("NodeColorProperties.4")); //$NON-NLS-1$
	private JButton btnFillPrimColor = new JButton(Messages.getString("NodeColorProperties.5")); //$NON-NLS-1$
	private JButton btnFillSecColor = new JButton(Messages.getString("NodeColorProperties.6")); //$NON-NLS-1$
	private Box fill = Box.createHorizontalBox();
	private JComboBox cbStrokeThickness = new JComboBox(items);
	private Box stroke = Box.createHorizontalBox();
	private JButton btnOK = new JButton(Messages.getString("NodeColorProperties.7")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages.getString("NodeColorProperties.8")); //$NON-NLS-1$
	private Box okCancelBox = Box.createHorizontalBox();
	
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	protected boolean dialogResult = false;
	/**
	 * Izabrana boja okvira noda.
	 */
	protected Color strokeColor = null;
	/**
	 * Izabrana primarna boja za gradijent noda.
	 */
	protected Color fillPrimColor = null;
	/**
	 * Izabrana sekundarna boja za gradijent noda.
	 */
	protected Color fillSecColor = null;
	/**
	 * Izabrana debljina okvira noda.
	 */
	protected float strokeThickness = 0.0f;

	public NodeColorProperties() {
		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);

		cbStrokeThickness.setEditable(true);
		cbStrokeThickness.setPreferredSize(new Dimension(50, 20));
		cbStrokeThickness.setMaximumSize(new Dimension(50, 20));
		chbGradient.setSelected(false);
		btnFillSecColor.setEnabled(false);

		setResizable(false);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setLocationRelativeTo(null);
		setTitle(Messages.getString("NodeColorProperties.9")); //$NON-NLS-1$

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

		btnStrokeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setVisible(true);
				if (cw.isDialogResult())
					setStrokeColor(cw.getColor());
			}
		});

		btnFillPrimColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setVisible(true);
				if (cw.isDialogResult())
					setFillPrimColor(cw.getColor());

			}
		});

		btnFillSecColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setVisible(true);
				if (cw.isDialogResult())
					setFillSecColor(cw.getColor());

			}
		});

		cbStrokeThickness.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					strokeThickness = Float.parseFloat(cbStrokeThickness
							.getSelectedItem().toString());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(Application.getInstance()
							.getView(),
							Messages.getString("NodeColorProperties.10"), //$NON-NLS-1$
							Messages.getString("NodeColorProperties.11"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
					cbStrokeThickness.setSelectedItem(strokeThickness);
				}
			}
		});

		chbGradient.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				if (chbGradient.isSelected())
					btnFillSecColor.setEnabled(true);
				else
					btnFillSecColor.setEnabled(false);

			}
		});

		setLayout(new GridBagLayout());
		Container container = getContentPane();

		GridBagConstraints c00 = new GridBagConstraints();
		c00.gridx = 0;
		c00.gridy = 0;
		c00.anchor = GridBagConstraints.EAST;
		c00.insets = new Insets(20, 20, 0, 0);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 1;
		c10.gridy = 0;
		c10.weightx = 1;
		c10.fill = GridBagConstraints.HORIZONTAL;
		c10.anchor = GridBagConstraints.WEST;
		c10.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.EAST;
		c01.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 1;
		c11.gridy = 1;
		c11.weightx = 1;
		c11.anchor = GridBagConstraints.WEST;
		c11.insets = new Insets(10, 15, 0, 20);

		GridBagConstraints c12 = new GridBagConstraints();
		c12.gridx = 1;
		c12.gridy = 2;
		c12.weightx = 1;
		c12.anchor = GridBagConstraints.WEST;
		c12.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 3;
		c3.weightx = 1;
		c3.gridwidth = 2;
		c3.anchor = GridBagConstraints.CENTER;
		c3.insets = new Insets(20, 20, 10, 20);

		container.add(lblStroke, c00);

		stroke.add(btnStrokeColor);
		stroke.add(Box.createGlue());
		stroke.add(lblThickness);
		stroke.add(Box.createHorizontalStrut(15));
		stroke.add(cbStrokeThickness);

		container.add(stroke, c10);
		container.add(lblFill, c01);
		container.add(chbGradient, c11);

		fill.add(btnFillPrimColor);
		fill.add(Box.createHorizontalStrut(20));
		fill.add(btnFillSecColor);

		container.add(fill, c12);

		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(40));
		okCancelBox.add(btnCancel);

		container.add(okCancelBox, c3);

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

	public boolean isDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(boolean dialogResult) {
		this.dialogResult = dialogResult;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Color getFillPrimColor() {
		return fillPrimColor;
	}

	public void setFillPrimColor(Color fillColor) {
		this.fillPrimColor = fillColor;
	}

	public Color getFillSecColor() {
		return fillSecColor;
	}

	public void setFillSecColor(Color fillColor) {
		this.fillSecColor = fillColor;
	}

	public float getStrokeThickness() {
		return strokeThickness;
	}

	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}

	public float getCbStrokeThickness() {
		return Float.parseFloat(cbStrokeThickness.getSelectedItem().toString());
	}

	public void setCbStrokeThickness(float strokeThickness) {
		this.cbStrokeThickness.setSelectedItem(strokeThickness);
	}

	public boolean isGradient() {
		return chbGradient.isSelected();
	}

	public void setGradient(boolean check) {
		this.chbGradient.setSelected(check);
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
