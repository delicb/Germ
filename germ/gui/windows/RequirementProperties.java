package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Requirement;
import germ.model.nodes.Stakeholder;
import germ.model.workspace.Project;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.PlainDocument;


/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Requirement-a
 */
@SuppressWarnings("serial")
public class RequirementProperties extends PropertyWindow {

	//1.2
	private JTextArea taDescription = new JTextArea(5, 20);	
	private JLabel lblDescription = new JLabel(Messages.getString("RequirementProperties.2")); //$NON-NLS-1$
	private JScrollPane spScrollDesc = new JScrollPane(taDescription);	
	//1.5
	private JLabel lblSource = new JLabel(Messages.getString("RequirementProperties.10"));
	private JTextField tfSource = new JTextField(20);
	//1.6
	private JLabel lblPriority = new JLabel(Messages.getString("RequirementProperties.0")); //$NON-NLS-1$	
	private ButtonGroup priorityGroup = new ButtonGroup();
	private JRadioButton rbHigh = new JRadioButton(Messages.getString("RequirementProperties.11"));
	private JRadioButton rbMedium = new JRadioButton(Messages.getString("RequirementProperties.12"));
	private JRadioButton rbLow = new JRadioButton(Messages.getString("RequirementProperties.13"));
	//ne treba
	private JCheckBox chbComplex = new JCheckBox();
	//2.1
	private JTextArea taRequirements = new JTextArea(5,20);
	private JLabel lblRequirements = new JLabel(Messages.getString("RequirementProperties.14"));
	private JScrollPane spScrollRequ = new JScrollPane(taRequirements);	
	//2.2
	private JLabel lblReferences = new JLabel(Messages.getString("RequirementProperties.15"));
	private List lReferences = new List(7);
	private JButton btnAddReferences = new JButton(Messages.getString("RequirementProperties.16"));
	private JScrollPane spScrollRefe = new JScrollPane(lReferences);	
	//2.3
	private JComboBox cbAuthor = new JComboBox();
	private JLabel lblAuthor = new JLabel(Messages.getString("RequirementProperties.17"));
	//2.4
	private JTextField tfVersion = new JTextField(20);
	private JLabel lblVersion = new JLabel(Messages.getString("RequirementProperties.18"));
	//2.5
	private ButtonGroup statusGroup = new ButtonGroup();
	private JLabel lblStatus = new JLabel(Messages.getString("RequirementProperties.19"));
	private JRadioButton rbStatusApproved = new JRadioButton(Messages.getString("RequirementProperties.20"));
	private JRadioButton rbStatusWaiting = new JRadioButton(Messages.getString("RequirementProperties.21"));
	private JRadioButton rbStatusReview = new JRadioButton(Messages.getString("RequirementProperties.22"));
	private JRadioButton rbStatusVerified = new JRadioButton(Messages.getString("RequirementProperties.23"));
	private JRadioButton rbStatusValidated = new JRadioButton(Messages.getString("RequirementProperties.24"));
	private JRadioButton rbStatusRejected = new JRadioButton(Messages.getString("RequirementProperties.25"));
	//2.6
	private JLabel lblSubDiagram = new JLabel(Messages.getString("RequirementProperties.7")); //$NON-NLS-1$
	private JComboBox cbDiagrams = new JComboBox();
	private JButton btnNewDiagram = new JButton("..."); //$NON-NLS-1$
	private Box diagramBox= Box.createHorizontalBox();
	//2.7
	private JLabel lblCreationDate = new JLabel(Messages.getString("RequirementProperties.3")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	//2.8
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("RequirementProperties.5")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$
	
	private String requirementName = "";

	class AutoCompleteCombo extends PlainDocument {
		JComboBox comboBox;
		ComboBoxModel model;
		JTextComponent editor;
		// flag to indicate if setSelectedItem has been called
		// subsequent calls to remove/insertString should be ignored
		boolean selecting = false;

		public AutoCompleteCombo(final JComboBox comboBox) {
			this.comboBox = comboBox;
			model = comboBox.getModel();
			editor = (JTextComponent) comboBox.getEditor()
					.getEditorComponent();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!selecting)
						highlightCompletedText(0);
				}
			});
			editor.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (comboBox.isDisplayable())
						comboBox.setPopupVisible(false);
				}
			});
		}

		public void remove(int offs, int len) throws BadLocationException {
			// return immediately when selecting an item
			if (selecting)
				return;
			super.remove(offs, len);
		}

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			// return immediately when selecting an item
			if (selecting)
				return;
			// insert the string into the document
			super.insertString(offs, str, a);
			// lookup and select a matching item
			Object item = lookupItem(getText(0, getLength()));
			if (item != null) {
				setSelectedItem(item);
				setText(item.toString());
				highlightCompletedText(offs + str.length());
			}
		}

		private void setText(String text) throws BadLocationException {
			// remove all text and insert the completed string
			super.remove(0, getLength());
			super.insertString(0, text, null);
		}

		private void highlightCompletedText(int start) {
			editor.setSelectionStart(start);
			editor.setSelectionEnd(getLength());
		}

		private void setSelectedItem(Object item) {
			selecting = true;
			model.setSelectedItem(item);
			selecting = false;
		}

		private Object lookupItem(String pattern) {
			Object selectedItem = model.getSelectedItem();
			// only search for a different item if the currently selected
			// does not match
			if (selectedItem != null
					&& startsWith(selectedItem.toString(), pattern)) {
				return selectedItem;
			} else {
				// iterate over all items
				for (int i = 0, n = model.getSize(); i < n; i++) {
					Object currentItem = model.getElementAt(i);
					// current item starts with the pattern?
					if (startsWith(currentItem.toString(), pattern)) {
						return currentItem;
					}
				}
			}
			// no item starts with the pattern => return null
			return null;
		}

		// checks if str1 starts with str2 - ignores case
		private boolean startsWith(String str1, String str2) {
			return str1.startsWith(str2);
		}
	}
	
	public RequirementProperties() {

		super();
		taDescription.setLineWrap(true);
		taRequirements.setLineWrap(true);
		chbComplex.setEnabled(false);
		

		enableLineBreaking();
		JTextComponent editorA = (JTextComponent) cbAuthor.getEditor().getEditorComponent();
		editorA.setDocument(new AutoCompleteCombo(cbAuthor));
		cbAuthor.setEditable(true);
		lReferences.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {	
				if(e.getClickCount()==2){
					String selected = lReferences.getSelectedItem();
					GERMModel m = Application.getInstance().getModel();
					for(Node node : m.getNodes()){
						if(node instanceof Requirement)
							if(node.getName().equals(selected)){
								RequirementProperties property = ((Requirement)node).getNewPropertyWindow();
								property.setLocation(property.getLocation().x + 30, property.getLocation().y + 30);
								property.setVisible(true);
							}
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		btnAddReferences.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> references = new ArrayList<String>();
				for(String listItem : lReferences.getItems())
					references.add(listItem);
				ReferencesWindow rw = new ReferencesWindow(references,getRequirementName());
				rw.setVisible(true);
				updateReferences(rw.getReferences());
		}});
		
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setTitle(Messages.getString("RequirementProperties.9")); //$NON-NLS-1$
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		initializeRadioButtons();

		Container container = getContentPane();

		/*PRVA KOLONA*/
		/*Name nasledjen*/
		/*SubD L*/
		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.anchor = GridBagConstraints.NORTHEAST;
		c01.insets = new Insets(10, 20, 0, 0);

		/*SubD P*/
		GridBagConstraints c11 = new GridBagConstraints();
		c11.gridx = 1;
		c11.gridy = 1;
		c11.weightx = 1;
		c11.fill = GridBagConstraints.HORIZONTAL;
		c11.anchor = GridBagConstraints.WEST;
		c11.insets = new Insets(10, 20, 0, 20);

		/*Desc L*/
		GridBagConstraints c05 = new GridBagConstraints();
		c05.gridx = 0;
		c05.gridy = 5;
		c05.anchor = GridBagConstraints.NORTHEAST;
		c05.insets = new Insets(10, 20, 0, 0);

		/*Desc P*/
		GridBagConstraints c15 = new GridBagConstraints();
		c15.gridx = 1;
		c15.gridy = 5;
		c15.weightx = 1;
		c15.fill = GridBagConstraints.HORIZONTAL;
		c15.anchor = GridBagConstraints.WEST;
		c15.insets = new Insets(10, 20, 0, 20);

		/*Prio L*/
		GridBagConstraints c06 = new GridBagConstraints();
		c06.gridx = 0;
		c06.gridy = 6;
		c06.anchor = GridBagConstraints.NORTHEAST;
		c06.insets = new Insets(12, 20, 0, 0);

		/*Prio P*/
		GridBagConstraints c16 = new GridBagConstraints();
		c16.gridx = 1;
		c16.gridy = 6;
		c16.weightx = 1;
		c16.anchor = GridBagConstraints.WEST;
		c16.insets = new Insets(10, 20, 0, 20);

		/*Requ L*/
		GridBagConstraints c07 = new GridBagConstraints();
		c07.gridx = 0;
		c07.gridy = 7;
		c07.anchor = GridBagConstraints.NORTHEAST;
		c07.insets = new Insets(13, 20, 0, 0);
		
		/*Requ P*/
		GridBagConstraints c17 = new GridBagConstraints();
		c17.gridx = 1;
		c17.gridy = 7;
		c17.fill = GridBagConstraints.HORIZONTAL;
		c17.anchor = GridBagConstraints.WEST;
		c17.insets = new Insets(10, 20, 0, 20);
		
		/*DRUGA KOLONA*/
		/*Auth L*/
		GridBagConstraints c20 = new GridBagConstraints();
		c20.gridx = 3;
		c20.gridy = 0;
		c20.anchor = GridBagConstraints.EAST;
		c20.insets = new Insets(20, 20, 0, 0);
		
		/*Auth P*/
		GridBagConstraints c30 = new GridBagConstraints();
		c30.gridx = 4;
		c30.gridy = 0;
		c30.fill = GridBagConstraints.HORIZONTAL;
		c30.anchor = GridBagConstraints.WEST;
		c30.insets = new Insets(20, 20, 0, 20);
		
		/*Vers L*/
		GridBagConstraints c21 = new GridBagConstraints();
		c21.gridx = 3;
		c21.gridy = 1;
		c21.anchor = GridBagConstraints.EAST;
		c21.insets = new Insets(10, 20, 0, 0);
		
		/*Vers P*/
		GridBagConstraints c31 = new GridBagConstraints();
		c31.gridx = 4;
		c31.gridy = 1;
		c31.weightx = 1;
		c31.fill = GridBagConstraints.HORIZONTAL;
		c31.anchor = GridBagConstraints.CENTER;
		c31.insets = new Insets(10, 20, 0, 20);
		
		/*Sour L*/
		GridBagConstraints c22 = new GridBagConstraints();
		c22.gridx = 3;
		c22.gridy = 2;
		c22.anchor = GridBagConstraints.EAST;
		c22.insets = new Insets(10, 20, 0, 0);
		
		/*Sour P*/
		GridBagConstraints c32 = new GridBagConstraints();
		c32.gridx = 4;
		c32.gridy = 2;
		c32.weightx = 1;
		c32.fill = GridBagConstraints.HORIZONTAL;
		c32.anchor = GridBagConstraints.CENTER;
		c32.insets = new Insets(10, 20, 0, 20);
		
		/*CreD L*/
		GridBagConstraints c23 = new GridBagConstraints();
		c23.gridx = 3;
		c23.gridy = 3;
		c23.anchor = GridBagConstraints.EAST;
		c23.insets = new Insets(10, 20, 0, 0);
		
		/*CreD P*/
		GridBagConstraints c33 = new GridBagConstraints();
		c33.gridx = 4;
		c33.gridy = 3;
		c33.weightx = 1;
		c33.fill = GridBagConstraints.HORIZONTAL;
		c33.anchor = GridBagConstraints.CENTER;
		c33.insets = new Insets(10, 20, 0, 20);
		
		/*LasD L*/
		GridBagConstraints c24 = new GridBagConstraints();
		c24.gridx = 3;
		c24.gridy = 4;
		c24.anchor = GridBagConstraints.EAST;
		c24.insets = new Insets(10, 20, 0, 0);
		
		/*LasD P*/
		GridBagConstraints c34 = new GridBagConstraints();
		c34.gridx = 4;
		c34.gridy = 4;
		c34.weightx = 1;
		c34.fill = GridBagConstraints.HORIZONTAL;
		c34.anchor = GridBagConstraints.CENTER;
		c34.insets = new Insets(10, 20, 0, 20);
		
		/*Refe L*/
		GridBagConstraints c25 = new GridBagConstraints();
		c25.gridx = 3;
		c25.gridy = 5;
		c25.gridheight = 2;
		c25.anchor = GridBagConstraints.NORTHEAST;
		c25.insets = new Insets(10, 20, 0, 0);
		
		/*Refe P*/
		GridBagConstraints c35 = new GridBagConstraints();
		c35.gridx = 4;
		c35.gridy = 5;
		c35.gridheight = 2;
		c35.weightx = 1;
		c35.fill = GridBagConstraints.HORIZONTAL;
		c35.anchor = GridBagConstraints.NORTHWEST;
		c35.insets = new Insets(10, 20, 0, 20);
		
		/*Stat L*/
		GridBagConstraints c27 = new GridBagConstraints();
		c27.gridx = 3;
		c27.gridy = 7;
		c27.anchor = GridBagConstraints.NORTHEAST;
		c27.insets = new Insets(12, 20, 0, 0);
		
		/*Stat P*/
		GridBagConstraints c37 = new GridBagConstraints();
		c37.gridx = 4;
		c37.gridy = 7;
		c37.weightx = 1;
		c37.fill = GridBagConstraints.HORIZONTAL;
		c37.anchor = GridBagConstraints.CENTER;
		c37.insets = new Insets(10, 20, 0, 20);
		
		
		GridBagConstraints cOK = new GridBagConstraints();
		cOK.gridx = 0;
		cOK.gridy = 8;
		cOK.gridwidth = 5;
		cOK.anchor = GridBagConstraints.CENTER;
		cOK.insets = new Insets(20, 0, 10, 0);
		
		GridBagConstraints s = new GridBagConstraints();
		s.gridx = 2;
		s.gridy = 0;
		s.gridheight = 8;
		s.anchor = GridBagConstraints.CENTER;
		s.fill = GridBagConstraints.VERTICAL;
		s.insets = new Insets(20, 0, 0, 0);
		
		/*prva kolona*/	
		
		diagramBox.add(cbDiagrams);
		diagramBox.add(Box.createHorizontalStrut(10));
		diagramBox.add(btnNewDiagram);	
		
		container.add(lblSubDiagram, c01);
		container.add(diagramBox, c11);
		container.add(lblDescription, c05);
		container.add(spScrollDesc, c15);
		
		Box priorityBox = Box.createVerticalBox();
		priorityBox.add(rbHigh);
		priorityBox.add(Box.createVerticalStrut(5));
		priorityBox.add(rbMedium);
		priorityBox.add(Box.createVerticalStrut(5));
		priorityBox.add(rbLow);
		
		priorityGroup.add(rbHigh);
		priorityGroup.add(rbMedium);
		priorityGroup.add(rbLow);
		
		container.add(lblPriority, c06);
		container.add(priorityBox, c16);
		container.add(lblRequirements, c07);
		container.add(spScrollRequ, c17);
		
		/*separator*/
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		container.add(separator,s);
		
		/*druga kolona*/

		container.add(lblAuthor, c20);
		container.add(cbAuthor, c30);
		container.add(lblVersion, c21);
		container.add(tfVersion, c31);
		container.add(lblCreationDate, c22);
		container.add(lblDateCreated, c32);
		container.add(lblLastChangeDAte, c23);
		container.add(lblDateChanged, c33);
		container.add(lblSource, c24);
		container.add(tfSource, c34);
		
		Box referencesBox = Box.createVerticalBox();
		referencesBox.add(spScrollRefe);
		referencesBox.add(Box.createVerticalStrut(130));
		referencesBox.add(btnAddReferences);
		
		container.add(lblReferences, c25);
		container.add(referencesBox, c35);
		container.add(lblReferences, c25);
		container.add(spScrollRefe, c35);
		
		Box statusBox1 = Box.createVerticalBox();
		statusBox1.add(rbStatusApproved);
		statusBox1.add(Box.createVerticalStrut(5));
		statusBox1.add(rbStatusWaiting);
		statusBox1.add(Box.createVerticalStrut(5));
		statusBox1.add(rbStatusRejected);
		
		Box statusBox2 = Box.createVerticalBox();
		statusBox2.add(rbStatusReview);
		statusBox2.add(Box.createVerticalStrut(5));
		statusBox2.add(rbStatusValidated);
		statusBox2.add(Box.createVerticalStrut(5));
		statusBox2.add(rbStatusVerified);
		
		Box statusBox = Box.createHorizontalBox();
		statusBox.add(statusBox1);
		statusBox.add(Box.createHorizontalStrut(10));
		statusBox.add(statusBox2);
		
		statusGroup.add(rbStatusApproved);
		statusGroup.add(rbStatusWaiting);
		statusGroup.add(rbStatusRejected);
		statusGroup.add(rbStatusReview);
		statusGroup.add(rbStatusValidated);
		statusGroup.add(rbStatusVerified);
		
		container.add(lblStatus, c27);
		container.add(statusBox, c37);
		
		container.add(okCancelBox, cOK);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);

	}

	public String getPriority() {
		String priority = "";
		if(rbHigh.isSelected()) priority = rbHigh.getName();
		if(rbMedium.isSelected()) priority = rbMedium.getName();
		if(rbLow.isSelected()) priority = rbLow.getName();
		return priority;
	}

	public void setPriority(String priority) {
		if(rbHigh.getName().equals(priority)) rbHigh.setSelected(true);
		else if(rbMedium.getName().equals(priority)) rbMedium.setSelected(true);
		else rbLow.setSelected(true);
	}

	public boolean getComplex() {
		return chbComplex.isSelected();
	}

	public void setComplex(boolean chbComplex) {
		this.chbComplex.setSelected(chbComplex);
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
	
	public String getSubDiagram(){
		try{
			return cbDiagrams.getSelectedItem().toString();
		}catch(Exception e){
			return null;
		}
	}
	
	public void setSubDiagram(String name){
		this.cbDiagrams.setEditable(true);
		this.cbDiagrams.setSelectedItem(name);
		this.cbDiagrams.setEditable(false);
	}
	
	public String getSource() {
		return tfSource.getText();
	}

	public void setSource(String source) {
		this.tfSource.setText(source);
	}

	public String getRequirements() {
		return taRequirements.getText();
	}

	public void setRequirements(String requirements) {
		this.taRequirements.setText(requirements);
	}

	public ArrayList<String> getReferences() {
		ArrayList<String> references = new ArrayList<String>();
		for(String reference : lReferences.getItems())
			references.add(reference);
		return references;
	}

	public void setReferences(ArrayList<String> references) {
		lReferences.removeAll();
		if(references != null)
			for(String reference : references)
				lReferences.add(reference);
	}

	public String getAuthor() {
		if(cbAuthor.getSelectedItem() != null)
			return cbAuthor.getSelectedItem().toString();
		else return "";
	}

	public void setAuthor(String author) {
		this.cbAuthor.setSelectedItem(author);
	}

	public String getVersion() {
		return tfVersion.getText();
	}

	public void setVersion(String version) {
		this.tfVersion.setText(version);
	}

	public String getStatus(){
		String status = "";
		if(rbStatusApproved.isSelected()) status = rbStatusApproved.getName();
		else if(rbStatusRejected.isSelected()) status = rbStatusRejected.getName();
		else if(rbStatusReview.isSelected()) status = rbStatusReview.getName();
		else if(rbStatusValidated.isSelected()) status = rbStatusValidated.getName();
		else if(rbStatusVerified.isSelected()) status = rbStatusVerified.getName();
		else if(rbStatusWaiting.isSelected()) status = rbStatusWaiting.getName();
		return status;
	}

	public void setStatus(String status) {
		if(rbStatusApproved.getName().equals(status)) rbStatusApproved.setSelected(true);
		else if(rbStatusRejected.getName().equals(status)) rbStatusRejected.setSelected(true);
		else if(rbStatusValidated.getName().equals(status)) rbStatusValidated.setSelected(true);
		else if(rbStatusVerified.getName().equals(status)) rbStatusVerified.setSelected(true);
		else if(rbStatusWaiting.getName().equals(status)) rbStatusWaiting.setSelected(true);
		else rbStatusReview.setSelected(true);
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	/**
	 * Funkcija vrsi inicijalizaciju sadrzaja Diagrams comboBoxa, odnosno na osnovu projekta i dijagrama u kojem
	 * se aktuelni zahtev nalazi popunjava comboBox imenima svih dijagrama koji mogu biti poddijagram aktuelnog zahteva.
	 */
	public void initializeDiagrams(){
		int diagram = 0;
		cbDiagrams.removeAllItems();
		Project currentProject = Application.getInstance().getModel().getProject();
		for(int i = 0; i != currentProject.getDiagramCount(); i++){
			if(currentProject.getDiagram(i).getName().equals(Application.getInstance().getModel().getName()))
				diagram = i;
			cbDiagrams.insertItemAt(currentProject.getDiagram(i), i);
		}
		cbDiagrams.removeItemAt(diagram);
	}
	
	/**
	 * Funkcija vrsi inicijalizaciju action listenera za dugme koje treba da napravi novi dijagram i ubaci ga
	 * u Diagrams comboBox kao opciju za selektovanje. Ova funkcija se izvsava svaki put kada se pozove funkcija
	 * getProperties nekog zahteva.
	 */
	public void initializeNewDiagramListener(){
		if(btnNewDiagram.getActionListeners().length > 0)
			btnNewDiagram.removeActionListener(btnNewDiagram.getActionListeners()[0]);
		btnNewDiagram.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Project currentProject = Application.getInstance().getModel().getProject();
				CreateNewDiagramWindow nd = new CreateNewDiagramWindow();
				nd.setCbProjects(currentProject.getName());
				nd.disableCbProjects();
				nd.setVisible(true);
				if(nd.isDialogResult()){
					cbDiagrams.insertItemAt(nd.getEnteredName(), cbDiagrams.getItemCount());
					cbDiagrams.setEditable(true);
					cbDiagrams.setSelectedItem(nd.getEnteredName());
					cbDiagrams.setEditable(false);
					
					GERMModel newDiagram = new GERMModel(nd.getEnteredName(),currentProject);
					newDiagram.addUpdateListener(Application.getInstance().getWorkspace());
					currentProject.addDiagram(newDiagram);
					newDiagram.save();
					Application.getInstance().getModel().updatePerformed();
				}
				
			}
		});
	}
	
	/**
	 * Funkcija inicijalizuje imena svakom od postojecih radio dugmica.
	 */
	public void initializeRadioButtons(){
	rbHigh.setName("High");
	rbMedium.setName("Medium");
	rbLow.setName("Low");
	
	rbStatusApproved.setName("Approved");
	rbStatusWaiting.setName("Waiting");
	rbStatusReview.setName("Review");
	rbStatusVerified.setName("Verified");
	rbStatusValidated.setName("Validated");
	rbStatusRejected.setName("Rejected");
	}
	
	/**
	 * Funkcija updateuje prikaz u listi referenci na osnovu prosledjene liste referenci.
	 * 
	 * @param newReferences - lista imena novih zahteva-referenci
	 */
	public void updateReferences(ArrayList<String> newReferences){
		lReferences.removeAll();
		for(String ref : newReferences)
			lReferences.add(ref);
	}
	
	@SuppressWarnings("static-access")
	public void enableLineBreaking(){
		   final JTextComponent.KeyBinding[] defaultBindings = {
			     new JTextComponent.KeyBinding(
			       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK),
			       DefaultEditorKit.insertBreakAction),
			   };

			   JTextComponent c = new JTextArea();
			   Keymap k = c.getKeymap();
			   taDescription.loadKeymap(k, defaultBindings, c.getActions());
			   taRequirements.loadKeymap(k, defaultBindings, c.getActions());

	}
	
	/**
	 * Funkcija puni cbAuthors svim postojecim Stakeholderima na dijagramu.
	 */
	public void initializeAuthors(){
		cbAuthor.removeAllItems();
		GERMModel m = Application.getInstance().getModel();
		for(Node node : m.getNodes())
			if(node instanceof Stakeholder)
				cbAuthor.addItem(node.getName());
	};

	/**
	 * Proverava da li na dijagramu postoji vise od jednog Zahteva pa na osnovu tog podatka enable-uje/disable-uje
	 * "Change References" dugme. Ukoliko ima vise od jednog Zahteva dugme ce biti enable-ovano...
	 */
	public void checkRequirements(){
		int noOfRequirements = 0;
		GERMModel m = Application.getInstance().getModel();
		for(Node node : m.getNodes())
			if(node instanceof Requirement)
				noOfRequirements++;
		if(noOfRequirements > 1)
			btnAddReferences.setEnabled(true);
		else
			btnAddReferences.setEnabled(false);
	}
}
