package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
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
import javax.swing.JTextField;


/**
 * Apstrakna klasa koja predstavlja osnovu za sve property prozore pojedinacnih nodova.
 */
@SuppressWarnings("serial")
public abstract class PropertyWindow extends JDialog {

	/**
	 * Predlozene vrednosti za izbor debljine okvira noda.
	 */
	private Double items[] = new Double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };

	private JLabel lblName = new JLabel(Messages.getString("PropertyWindow.0")); //$NON-NLS-1$
	private JLabel lblStroke = new JLabel(Messages.getString("PropertyWindow.1")); //$NON-NLS-1$
	private JLabel lblFill = new JLabel(Messages.getString("PropertyWindow.2")); //$NON-NLS-1$
	private JLabel lblThickness = new JLabel(Messages.getString("PropertyWindow.3")); //$NON-NLS-1$
	private JTextField tfName = new JTextField(20);
	private JButton btnStrokeColor = new JButton(Messages.getString("PropertyWindow.4")); //$NON-NLS-1$
	private JCheckBox chbGradient = new JCheckBox(Messages.getString("PropertyWindow.5")); //$NON-NLS-1$
	private JButton btnFillPrimColor = new JButton(Messages.getString("PropertyWindow.6")); //$NON-NLS-1$
	private JButton btnFillSecColor = new JButton(Messages.getString("PropertyWindow.7")); //$NON-NLS-1$
	private Box fill = Box.createHorizontalBox();
	private JComboBox cbStrokeThickness = new JComboBox(items);
	private Box stroke = Box.createHorizontalBox();
	protected JButton btnOK = new JButton(Messages.getString("PropertyWindow.8")); //$NON-NLS-1$
	protected JButton btnCancel = new JButton(Messages.getString("PropertyWindow.9")); //$NON-NLS-1$
	protected Box okCancelBox = Box.createHorizontalBox();
	
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	protected boolean dialogResult = false;
	/**
	 * Trenutna boja okvira noda.
	 */
	protected Color strokeColor = null;
	/**
	 * Trenutna primarna boja za gradijent noda.
	 */
	protected Color fillPrimColor = null;
	/**
	 * Trenutna sekundarna boja za gradijent noda.
	 */
	protected Color fillSecColor = null;
	/**
	 * Trenutna debljina okvira noda.
	 */
	protected float strokeThickness = 0.0f;

	public PropertyWindow() {
		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);

		cbStrokeThickness.setEditable(true);
		cbStrokeThickness.setPreferredSize(new Dimension(50, 20));
		cbStrokeThickness.setSelectedIndex(2);
		chbGradient.setSelected(false);

		setResizable(false);
		
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

		btnStrokeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setColor(getStrokeColor());
				cw.setVisible(true);
				if (cw.isDialogResult())
					setStrokeColor(cw.getColor());
			}
		});

		btnFillPrimColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setColor(getFillPrimColor());
				cw.setVisible(true);
				if (cw.isDialogResult())
					setFillPrimColor(cw.getColor());

			}
		});

		btnFillSecColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setColor(getFillSecColor());
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
							Messages.getString("PropertyWindow.10"), //$NON-NLS-1$
							Messages.getString("PropertyWindow.11"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
					cbStrokeThickness.setSelectedItem(strokeThickness);
				}
			}
		});

		chbGradient.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				btnFillSecColor.setEnabled(chbGradient.isSelected());
		}});

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
		c10.anchor = GridBagConstraints.WEST;
		c10.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c02 = new GridBagConstraints();
		c02.gridx = 0;
		c02.gridy = 2;
		c02.anchor = GridBagConstraints.EAST;
		c02.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c12 = new GridBagConstraints();
		c12.gridx = 1;
		c12.gridy = 2;
		c12.weightx = 1;
		c12.fill = GridBagConstraints.HORIZONTAL;
		c12.anchor = GridBagConstraints.WEST;
		c12.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c03 = new GridBagConstraints();
		c03.gridx = 0;
		c03.gridy = 3;
		c03.anchor = GridBagConstraints.EAST;
		c03.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c13 = new GridBagConstraints();
		c13.gridx = 1;
		c13.gridy = 3;
		c13.weightx = 1;
		c13.anchor = GridBagConstraints.WEST;
		c13.insets = new Insets(10, 15, 0, 20);

		GridBagConstraints c14 = new GridBagConstraints();
		c14.gridx = 1;
		c14.gridy = 4;
		c14.weightx = 1;
		c14.anchor = GridBagConstraints.WEST;
		c14.insets = new Insets(10, 20, 0, 20);

		container.add(lblName, c00);
		container.add(tfName, c10);
		container.add(lblStroke, c02);

		stroke.add(btnStrokeColor);
		stroke.add(Box.createGlue());
		stroke.add(lblThickness);
		stroke.add(Box.createHorizontalStrut(15));
		stroke.add(cbStrokeThickness);

		container.add(stroke, c12);
		container.add(lblFill, c03);
		container.add(chbGradient, c13);

		fill.add(btnFillPrimColor);
		fill.add(Box.createHorizontalStrut(20));
		fill.add(btnFillSecColor);

		container.add(fill, c14);

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					escapePressed();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isShiftDown()) {
					enterPressed();
				}
				return false;
			}
		});
		
		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
		btnFillSecColor.setEnabled(isGradient());
	}

	public String getName() {
		return tfName.getText();
	}

	public void setName(String tfName) {
		this.tfName.setText(tfName);
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
