package germ.gui.windows;

import germ.i18n.Messages;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Stakeholder-a
 */
@SuppressWarnings("serial")
public class StakeholderProperties extends PropertyWindow {

	private JLabel lblSurname = new JLabel(Messages.getString("StakeholderProperties.0")); //$NON-NLS-1$
	private JLabel lblPosition = new JLabel(Messages.getString("StakeholderProperties.1")); //$NON-NLS-1$
	private JLabel lblCompany = new JLabel(Messages.getString("StakeholderProperties.2")); //$NON-NLS-1$
	private JLabel lblAdress = new JLabel(Messages.getString("StakeholderProperties.3")); //$NON-NLS-1$
	private JLabel lblTelephone = new JLabel(Messages.getString("StakeholderProperties.4")); //$NON-NLS-1$
	private JLabel lblMail = new JLabel(Messages.getString("StakeholderProperties.5")); //$NON-NLS-1$
	private JTextField tfSurname = new JTextField(20);
	private JTextField tfPosition = new JTextField(20);
	private JTextField tfCompany = new JTextField(20);
	private JTextField tfAdress = new JTextField(20);
	private JTextField tfTelephone = new JTextField(20);
	private JTextField tfMail = new JTextField(20);
	private JLabel lblCreationDate = new JLabel(Messages.getString("StakeholderProperties.6")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("StakeholderProperties.8")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$

	public StakeholderProperties() {

		super();

		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setTitle(Messages.getString("StakeholderProperties.10")); //$NON-NLS-1$
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		Container container = getContentPane();

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.EAST;
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
		c16.fill = GridBagConstraints.HORIZONTAL;
		c16.anchor = GridBagConstraints.WEST;
		c16.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c07 = new GridBagConstraints();
		c07.gridx = 0;
		c07.gridy = 7;
		c07.anchor = GridBagConstraints.EAST;
		c07.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c17 = new GridBagConstraints();
		c17.gridx = 1;
		c17.gridy = 7;
		c17.weightx = 1;
		c17.fill = GridBagConstraints.HORIZONTAL;
		c17.anchor = GridBagConstraints.WEST;
		c17.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c08 = new GridBagConstraints();
		c08.gridx = 0;
		c08.gridy = 8;
		c08.anchor = GridBagConstraints.EAST;
		c08.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c18 = new GridBagConstraints();
		c18.gridx = 1;
		c18.gridy = 8;
		c18.weightx = 1;
		c18.fill = GridBagConstraints.HORIZONTAL;
		c18.anchor = GridBagConstraints.WEST;
		c18.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c09 = new GridBagConstraints();
		c09.gridx = 0;
		c09.gridy = 9;
		c09.anchor = GridBagConstraints.EAST;
		c09.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c19 = new GridBagConstraints();
		c19.gridx = 1;
		c19.gridy = 9;
		c19.weightx = 1;
		c19.fill = GridBagConstraints.HORIZONTAL;
		c19.anchor = GridBagConstraints.WEST;
		c19.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c010 = new GridBagConstraints();
		c010.gridx = 0;
		c010.gridy = 10;
		c010.anchor = GridBagConstraints.EAST;
		c010.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c110 = new GridBagConstraints();
		c110.gridx = 1;
		c110.gridy = 10;
		c110.anchor = GridBagConstraints.WEST;
		c110.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c011 = new GridBagConstraints();
		c011.gridx = 0;
		c011.gridy = 11;
		c011.anchor = GridBagConstraints.EAST;
		c011.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c111 = new GridBagConstraints();
		c111.gridx = 1;
		c111.gridy = 11;
		c111.anchor = GridBagConstraints.WEST;
		c111.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c12 = new GridBagConstraints();
		c12.gridx = 0;
		c12.gridy = 12;
		c12.weightx = 0;
		c12.gridwidth = 2;
		c12.anchor = GridBagConstraints.CENTER;
		c12.insets = new Insets(35, 0, 10, 0);

		container.add(lblSurname, c01);
		container.add(tfSurname, c11);
		container.add(lblPosition, c05);
		container.add(tfPosition, c15);
		container.add(lblCompany, c06);
		container.add(tfCompany, c16);
		container.add(lblAdress, c07);
		container.add(tfAdress, c17);
		container.add(lblTelephone, c08);
		container.add(tfTelephone, c18);
		container.add(lblMail, c09);
		container.add(tfMail, c19);
		container.add(lblCreationDate, c010);
		container.add(lblDateCreated, c110);
		container.add(lblLastChangeDAte, c011);
		container.add(lblDateChanged, c111);
		container.add(okCancelBox, c12);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
		
	}

	public String getSurname() {
		return tfSurname.getText();
	}

	public void setSurname(String tfSurname) {
		this.tfSurname.setText(tfSurname);
	}

	public String getPosition() {
		return tfPosition.getText();
	}

	public void setPosition(String tfPosition) {
		this.tfPosition.setText(tfPosition);
	}

	public String getCompany() {
		return tfCompany.getText();
	}

	public void setCompany(String tfCompany) {
		this.tfCompany.setText(tfCompany);
	}

	public String getAdress() {
		return tfAdress.getText();
	}

	public void setAdress(String tfAdress) {
		this.tfAdress.setText(tfAdress);
	}

	public String getTelephone() {
		return tfTelephone.getText();
	}

	public void setTelephone(String tfTelephone) {
		this.tfTelephone.setText(tfTelephone);
	}

	public String getMail() {
		return tfMail.getText();
	}

	public void setMail(String tfMail) {
		this.tfMail.setText(tfMail);
	}
	public void setDateCreated(String dateCreated) {
		this.lblDateCreated.setText(dateCreated);
	}

	public void setDateChanged(String dateChanged) {
		this.lblDateChanged.setText(dateChanged);
	}
}
