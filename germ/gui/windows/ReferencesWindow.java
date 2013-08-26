package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Requirement;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Klasa predstavlja prozor za dodavanje drugih zahteva kao referenci na neki zahtev
 */
@SuppressWarnings("serial")
public class ReferencesWindow extends JDialog {

	private JLabel lblReferences = new JLabel(Messages.getString("ReferencesWindow.0"));
	private JButton btnUpdate = new JButton(Messages.getString("ReferencesWindow.1"));
	/**
	 * Lista chechBoxova - za svaki postojeci Zahtev na dijagramu po jedan
	 */
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	/**
	 * Lista imena cekiranih zahteva koji ce postati reference
	 */
	private ArrayList<String> checked = new ArrayList<String>();
	
	/**
	 * Konstruktor dinamicki kreira prozor na osnovu broja prethodnih referenci nekog zahteva
	 * @param previousReferences - lista imena zahteva-referenci
	 * @param currentRequirement - naziv trenutnog zahteva
	 */
	ReferencesWindow(ArrayList<String> previousReferences, String currentRequirement){
		   
		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setTitle(Messages.getString("ReferencesWindow.2"));
		setLayout(new GridBagLayout());
		setResizable(false);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		GERMModel m = Application.getInstance().getModel();
		
		btnUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				checked.clear();
				for(JCheckBox box : checkBoxes)
					if(box.isSelected())
						checked.add(box.getName());	
			setVisible(false);
		}});

		Container container = getContentPane();
		
		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 0;
		c01.anchor = GridBagConstraints.WEST;
		c01.insets = new Insets(10, 10, 5, 0);
		
		container.add(lblReferences,c01);
		int g = 0;
		
		for(int i = 0; i != m.getNodeCount(); i++){
			Node node = m.getNodeAt(i);
			if(node instanceof Requirement){
				if(!node.getName().equals(currentRequirement)){
					g++; 
				
					GridBagConstraints c = new GridBagConstraints();
					c.gridx = 0;
					c.gridy = g;
					c.anchor = GridBagConstraints.WEST;
					c.insets = new Insets(5, 15, 0, 15);
				
					JCheckBox chbReference = new JCheckBox(node.getName());
					chbReference.setName(node.getName());
					checkBoxes.add(chbReference);
					container.add(chbReference,c);
				}
			}
		}
		
		GridBagConstraints c02 = new GridBagConstraints();
		c02.gridx = 0;
		c02.gridy = g + 1;
		c02.anchor = GridBagConstraints.EAST;
		c02.insets = new Insets(20, 0, 10, 15);
		
		container.add(btnUpdate,c02);
		
		for(JCheckBox boxes : checkBoxes)
			for(String refs : previousReferences)
				if(refs.equals(boxes.getName()))
					boxes.setSelected(true);
		
		pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
	}
	
	public ArrayList<String> getReferences(){
		return checked;
	}
	
	
	
}
