package germ.gui.windows;

import germ.i18n.Messages;
import germ.model.nodes.ArgumentValue;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Argumenta
 */
@SuppressWarnings("serial")
public class ArgumentProperties extends PropertyWindow {

	private JLabel lblAuthor = new JLabel(Messages.getString("ArgumentProperties.0")); //$NON-NLS-1$
	private JTextField tfAuthor = new JTextField(20);
	private JLabel lblValue = new JLabel(Messages.getString("ArgumentProperties.1")); //$NON-NLS-1$
	private ButtonGroup radioGroup = new ButtonGroup();
	private JRadioButton rbtnAffirmative = new JRadioButton(Messages.getString("ArgumentProperties.2")); //$NON-NLS-1$
	private JRadioButton rbtnNegative = new JRadioButton(Messages.getString("ArgumentProperties.3")); //$NON-NLS-1$
	private JRadioButton rbtnNeutral = new JRadioButton(Messages.getString("ArgumentProperties.4")); //$NON-NLS-1$
	private JTextArea taDescription = new JTextArea(5, 20);
	private JScrollPane spScroll = new JScrollPane(taDescription);
	private JLabel lblDescription = new JLabel(Messages.getString("ArgumentProperties.5")); //$NON-NLS-1$
	private Box group = Box.createVerticalBox();
	private JLabel lblCreationDate = new JLabel(Messages.getString("ArgumentProperties.6")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("ArgumentProperties.8")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$
	
	public ArgumentProperties() {
		super();
		taDescription.setLineWrap(true);

		setTitle(Messages.getString("ArgumentProperties.10")); //$NON-NLS-1$
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();

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
		c11.fill = GridBagConstraints.BOTH;
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
		c15.weightx = 1;
		c15.fill = GridBagConstraints.HORIZONTAL;
		c15.anchor = GridBagConstraints.WEST;
		c15.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c06 = new GridBagConstraints();
		c06.gridx = 0;
		c06.gridy = 6;
		c06.anchor = GridBagConstraints.NORTHEAST;
		c06.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c16 = new GridBagConstraints();
		c16.gridx = 1;
		c16.gridy = 6;
		c16.weightx = 1;
		c16.anchor = GridBagConstraints.WEST;
		c16.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c07 = new GridBagConstraints();
		c07.gridx = 0;
		c07.gridy = 7;
		c07.anchor = GridBagConstraints.EAST;
		c07.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c17 = new GridBagConstraints();
		c17.gridx = 1;
		c17.gridy = 7;
		c17.anchor = GridBagConstraints.WEST;
		c17.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c08 = new GridBagConstraints();
		c08.gridx = 0;
		c08.gridy = 8;
		c08.anchor = GridBagConstraints.EAST;
		c08.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c18 = new GridBagConstraints();
		c18.gridx = 1;
		c18.gridy = 8;
		c18.anchor = GridBagConstraints.WEST;
		c18.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c9 = new GridBagConstraints();
		c9.gridx = 0;
		c9.gridy = 9;
		c9.gridwidth = 2;
		c9.anchor = GridBagConstraints.CENTER;
		c9.insets = new Insets(35, 0, 10, 0);

		container.add(lblDescription, c01);
		container.add(spScroll, c11);
		container.add(lblAuthor, c05);
		container.add(tfAuthor, c15);
		container.add(lblValue, c06);

		radioGroup.add(rbtnAffirmative);
		radioGroup.add(rbtnNeutral);
		radioGroup.add(rbtnNegative);

		group.add(rbtnAffirmative);
		group.add(Box.createVerticalStrut(5));
		group.add(rbtnNeutral);
		group.add(Box.createVerticalStrut(5));
		group.add(rbtnNegative);

		container.add(group, c16);
		container.add(lblCreationDate, c07);
		container.add(lblDateCreated, c17);
		container.add(lblLastChangeDAte, c08);
		container.add(lblDateChanged, c18);
		container.add(okCancelBox, c9);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);

	}

	public String getAuthor() {
		return tfAuthor.getText();
	}

	public void setAuthor(String tfAuthor) {
		this.tfAuthor.setText(tfAuthor);
	}

	public ArgumentValue getValue() {
		if (rbtnAffirmative.isSelected())
			return ArgumentValue.ARG_AFFIRMATIVE;
		else if (rbtnNegative.isSelected())
			return ArgumentValue.ARG_NEGATIVE;
		else
			return ArgumentValue.ARG_NEUTRAL;
	}

	public void setValue(ArgumentValue value) {
		if (value == ArgumentValue.ARG_AFFIRMATIVE)
			rbtnAffirmative.setSelected(true);
		else if (value == ArgumentValue.ARG_NEGATIVE)
			rbtnNegative.setSelected(true);
		else
			rbtnNeutral.setSelected(true);
	}

	public String getDescription() {
		return taDescription.getText();
	}

	public void setDescription(String taDescription) {
		this.taDescription.setText(taDescription);
	}

	public void setDateCreated(String dateCreated) {
		this.lblDateCreated.setText(dateCreated);
	}

	public void setDateChanged(String dateChanged) {
		this.lblDateChanged.setText(dateChanged);
	}

}
