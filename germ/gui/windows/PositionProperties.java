package germ.gui.windows;

import germ.i18n.Messages;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Position-a
 */
@SuppressWarnings("serial")
public class PositionProperties extends PropertyWindow {

	private JTextArea taDescription = new JTextArea(5, 20);
	private JScrollPane spScroll = new JScrollPane(taDescription);
	private JLabel lblDescription = new JLabel(Messages.getString("PositionProperties.0")); //$NON-NLS-1$
	private JLabel lblCreationDate = new JLabel(Messages.getString("PositionProperties.1")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("PositionProperties.3")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$

	public PositionProperties() {
		super();

		taDescription.setLineWrap(true);

		setTitle(Messages.getString("PositionProperties.5")); //$NON-NLS-1$
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
		c05.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c15 = new GridBagConstraints();
		c15.gridx = 1;
		c15.gridy = 5;
		c15.anchor = GridBagConstraints.WEST;
		c15.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c06 = new GridBagConstraints();
		c06.gridx = 0;
		c06.gridy = 6;
		c06.anchor = GridBagConstraints.EAST;
		c06.insets = new Insets(10,20,0,0);
		
		GridBagConstraints c16 = new GridBagConstraints();
		c16.gridx = 1;
		c16.gridy = 6;
		c16.anchor = GridBagConstraints.WEST;
		c16.insets = new Insets(10,20,0,0);
	
		GridBagConstraints c7 = new GridBagConstraints();
		c7.gridx = 0;
		c7.gridy = 7;
		c7.weightx = 0;
		c7.gridwidth = 2;
		c7.anchor = GridBagConstraints.CENTER;
		c7.insets = new Insets(35, 0, 10, 0);

		container.add(lblDescription, c01);
		container.add(spScroll, c11);
		container.add(lblCreationDate, c05);
		container.add(lblDateCreated, c15);
		container.add(lblLastChangeDAte, c06);
		container.add(lblDateChanged, c16);
		container.add(okCancelBox, c7);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
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
