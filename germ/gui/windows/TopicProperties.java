package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Stakeholder;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;


/**
 * Klasa predstavlja prozor za prikaz trenutnih svojstava Topic-a
 */
@SuppressWarnings("serial")
public class TopicProperties extends PropertyWindow {

	private JLabel lblAuthor = new JLabel(Messages.getString("TopicProperties.0")); //$NON-NLS-1$
	private JLabel lblClosed = new JLabel(Messages.getString("TopicProperties.1")); //$NON-NLS-1$
	private JLabel lblTopicCloser = new JLabel(Messages.getString("TopicProperties.2")); //$NON-NLS-1$
	private JComboBox cbAuthor = new JComboBox();
	private JCheckBox chbClosed = new JCheckBox();
	private JComboBox cbTopicCloser = new JComboBox();
	private JTextArea taDescription = new JTextArea(5, 20);
	private JScrollPane spScroll = new JScrollPane(taDescription);
	private JLabel lblDescription = new JLabel(Messages.getString("TopicProperties.3")); //$NON-NLS-1$
	private JLabel lblCreationDate = new JLabel(Messages.getString("TopicProperties.4")); //$NON-NLS-1$
	private JLabel lblDateCreated = new JLabel(""); //$NON-NLS-1$
	private JLabel lblLastChangeDAte = new JLabel(Messages.getString("TopicProperties.6")); //$NON-NLS-1$
	private JLabel lblDateChanged = new JLabel(""); //$NON-NLS-1$

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
	
	public TopicProperties() {
		super();

		taDescription.setLineWrap(true);

		setTitle(Messages.getString("TopicProperties.8")); //$NON-NLS-1$
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		JTextComponent editorTC = (JTextComponent) cbTopicCloser.getEditor().getEditorComponent();
		editorTC.setDocument(new AutoCompleteCombo(cbTopicCloser));
		cbTopicCloser.setEditable(true);
		JTextComponent editorA = (JTextComponent) cbAuthor.getEditor().getEditorComponent();
		editorA.setDocument(new AutoCompleteCombo(cbAuthor));
		cbAuthor.setEditable(true);
		cbTopicCloser.setPreferredSize(new Dimension(222, 20));

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
		c10.gridwidth = 2;
		c10.anchor = GridBagConstraints.CENTER;
		c10.insets = new Insets(35, 0, 10, 0);

		container.add(lblDescription, c01);
		container.add(spScroll, c11);
		container.add(lblAuthor, c05);
		container.add(cbAuthor, c15);
		container.add(lblTopicCloser, c06);
		container.add(cbTopicCloser, c16);
		container.add(lblClosed, c07);
		container.add(chbClosed, c17);
		container.add(lblCreationDate, c08);
		container.add(lblDateCreated, c18);
		container.add(lblLastChangeDAte, c09);
		container.add(lblDateChanged, c19);
		container.add(okCancelBox, c10);
		
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);	
	}

	public String getAuthor() {
		if (cbAuthor.getSelectedItem() == null)
			return ""; //$NON-NLS-1$
		else
			return cbAuthor.getSelectedItem().toString();
	}

	public void setAuthor(String author) {
		this.cbAuthor.setSelectedItem(author);
	}

	public boolean getClosed() {
		return this.chbClosed.isSelected();
	}

	public void setClosed(boolean chbClosed) {
		this.chbClosed.setSelected(chbClosed);
	}

	public String getTopicCloser() {
		if (cbTopicCloser.getSelectedItem() == null)
			return ""; //$NON-NLS-1$
		else
			return cbTopicCloser.getSelectedItem().toString();
	}

	public void setTopicCloser(String topicCloser) {
		this.cbTopicCloser.setSelectedItem(topicCloser);
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
	
	public void initializeStakeholders(){
		cbTopicCloser.removeAllItems();
		cbAuthor.removeAllItems();
		GERMModel m = Application.getInstance().getModel();
		for(Node node : m.getNodes())
			if(node instanceof Stakeholder){
				cbTopicCloser.addItem(node.getName());
				cbAuthor.addItem(node.getName());
			}
	}
}
