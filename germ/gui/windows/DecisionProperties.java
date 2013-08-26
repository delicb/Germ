package germ.gui.windows;

import germ.i18n.Messages;
import germ.model.nodes.DecisionValue;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Dicision-a
 */
@SuppressWarnings("serial")
public class DecisionProperties extends PropertyWindow {

	private JLabel lblAuthor = new JLabel(Messages.getString("DecisionProperties.0")); //$NON-NLS-1$
	private JLabel lblFinalized = new JLabel(Messages.getString("DecisionProperties.1")); //$NON-NLS-1$
	private JLabel lblValue = new JLabel(Messages.getString("DecisionProperties.2")); //$NON-NLS-1$
	private JTextField tfAuthor = new JTextField(20);
	private JCheckBox chbFinalized = new JCheckBox();
	private JRadioButton rbtnInternal = new JRadioButton(Messages.getString("DecisionProperties.3")); //$NON-NLS-1$
	private JRadioButton rbtnFinal = new JRadioButton(Messages.getString("DecisionProperties.4")); //$NON-NLS-1$
	private ButtonGroup btnGroup = new ButtonGroup();
	private JTextArea taDescription = new JTextArea(5, 20);
	private JScrollPane spScroll = new JScrollPane(taDescription);
	private JLabel lblDescription = new JLabel(Messages.getString("DecisionProperties.5")); //$NON-NLS-1$
	private Box radioGroup = Box.createVerticalBox();
	private JLabel lblCreationDate = new JLabel(Messages.getString("DecisionProperties.6")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("DecisionProperties.8")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$

	public DecisionProperties() {
		super();

		taDescription.setLineWrap(true);

		setTitle(Messages.getString("DecisionProperties.10")); //$NON-NLS-1$
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		Container container = getContentPane();

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.NORTHEAST;
		c01.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 1;
		c11.gridy = 1;
		c11.weightx = 1;
		c11.fill = GridBagConstraints.HORIZONTAL;
		c11.anchor = GridBagConstraints.WEST;
		c11.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c05 = new GridBagConstraints();
		c05.gridx = 0;
		c05.gridy = 5;
		c05.anchor = GridBagConstraints.EAST;
		c05.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c15 = new GridBagConstraints();
		c15.gridx = 1;
		c15.gridy = 5;
		c15.fill = GridBagConstraints.HORIZONTAL;
		c15.weightx = 1;
		c15.fill = GridBagConstraints.HORIZONTAL;
		c15.anchor = GridBagConstraints.WEST;
		c15.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c06 = new GridBagConstraints();
		c06.gridx = 0;
		c06.gridy = 6;
		c06.anchor = GridBagConstraints.EAST;
		c06.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c16 = new GridBagConstraints();
		c16.gridx = 1;
		c16.gridy = 6;
		c16.weightx = 1;
		c16.anchor = GridBagConstraints.WEST;
		c16.insets = new Insets(10, 17, 0, 0);

		GridBagConstraints c07 = new GridBagConstraints();
		c07.gridx = 0;
		c07.gridy = 7;
		c07.anchor = GridBagConstraints.NORTHEAST;
		c07.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c17 = new GridBagConstraints();
		c17.gridx = 1;
		c17.gridy = 7;
		c17.weightx = 1;
		c17.anchor = GridBagConstraints.WEST;
		c17.insets = new Insets(10, 17, 0, 0);

		GridBagConstraints c08 = new GridBagConstraints();
		c08.gridx = 0;
		c08.gridy = 8;
		c08.anchor = GridBagConstraints.EAST;
		c08.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c18 = new GridBagConstraints();
		c18.gridx = 1;
		c18.gridy = 8;
		c18.anchor = GridBagConstraints.WEST;
		c18.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c09 = new GridBagConstraints();
		c09.gridx = 0;
		c09.gridy = 9;
		c09.anchor = GridBagConstraints.EAST;
		c09.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c19 = new GridBagConstraints();
		c19.gridx = 1;
		c19.gridy = 9;
		c19.anchor = GridBagConstraints.WEST;
		c19.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 0;
		c10.gridy = 10;
		c10.weightx = 0;
		c10.gridwidth = 2;
		c10.anchor = GridBagConstraints.CENTER;
		c10.insets = new Insets(35, 0, 10, 0);

		btnGroup.add(rbtnFinal);
		btnGroup.add(rbtnInternal);

		radioGroup.add(rbtnInternal);
		radioGroup.add(Box.createVerticalStrut(5));
		radioGroup.add(rbtnFinal);

		container.add(lblDescription, c01);
		container.add(spScroll, c11);
		container.add(lblAuthor, c05);
		container.add(tfAuthor, c15);
		container.add(lblFinalized, c06);
		container.add(chbFinalized, c16);
		container.add(lblValue, c07);
		container.add(radioGroup, c17);
		container.add(lblCreationDate, c08);
		container.add(lblDateCreated, c18);
		container.add(lblLastChangeDAte, c09);
		container.add(lblDateChanged, c19);
		container.add(okCancelBox, c10);

		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
	}

	public String getAuthor() {
		return tfAuthor.getText();
	}

	public void setAuthor(String tfAuthor) {
		this.tfAuthor.setText(tfAuthor);
	}

	public DecisionValue getValue() {
		if (rbtnFinal.isSelected())
			return DecisionValue.FINAL_DECISION;
		else
			return DecisionValue.INTERNAL_DECISION;
	}

	public void setValue(DecisionValue chbFinalized) {
		if (chbFinalized == DecisionValue.FINAL_DECISION)
			this.rbtnFinal.setSelected(true);
		else
			this.rbtnInternal.setSelected(true);
	}

	public String getDescription() {
		return taDescription.getText();
	}

	public void setDescription(String taDescription) {
		this.taDescription.setText(taDescription);
	}

	public boolean getFinalized() {
		return chbFinalized.isSelected();
	}

	public void setFinalized(boolean chbFinalized) {
		this.chbFinalized.setSelected(chbFinalized);
	}
	public void setDateCreated(String dateCreated) {
		this.lblDateCreated.setText(dateCreated);
	}

	public void setDateChanged(String dateChanged) {
		this.lblDateChanged.setText(dateChanged);
	}
}
