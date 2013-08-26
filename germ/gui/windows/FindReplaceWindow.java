package germ.gui.windows;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Argument;
import germ.model.nodes.Assumption;
import germ.model.nodes.Decision;
import germ.model.nodes.Position;
import germ.model.nodes.Requirement;
import germ.model.nodes.Stakeholder;
import germ.model.nodes.Topic;
import germ.model.workspace.Project;
import germ.util.Cursors;
import germ.view.GERMView;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;


/**
 * Klasa predstavlja prozor za pronalazenje i / ili zamenu nodova (i/ili njihovim imena) na dijagramu.
 */
@SuppressWarnings("serial")
public class FindReplaceWindow extends JDialog {

	/**
	 * Niz tipova nodova za pretragu. Prazan string predstavlja bilo koji tip noda.
	 */
	private String[] types = new String[] {
			"", Messages.getString("FindReplaceWindow.1"), Messages.getString("FindReplaceWindow.2"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Messages.getString("FindReplaceWindow.3"), Messages.getString("FindReplaceWindow.4"), Messages.getString("FindReplaceWindow.5"), Messages.getString("FindReplaceWindow.6"), Messages.getString("FindReplaceWindow.7") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private JLabel lblFind = new JLabel(Messages
			.getString("FindReplaceWindow.8")); //$NON-NLS-1$
	private JLabel lblReplace = new JLabel(Messages
			.getString("FindReplaceWindow.9")); //$NON-NLS-1$
	private JLabel lblType = new JLabel(Messages
			.getString("FindReplaceWindow.10")); //$NON-NLS-1$
	private JLabel lblOptions = new JLabel(Messages
			.getString("FindReplaceWindow.11")); //$NON-NLS-1$
	private JComboBox cbFind = new JComboBox();
	private JComboBox cbReplace = new JComboBox();
	private JComboBox cbType = new JComboBox(types);
	private JCheckBox chbCaseSensitive = new JCheckBox(Messages
			.getString("FindReplaceWindow.12")); //$NON-NLS-1$
	private JCheckBox chbWholeWord = new JCheckBox(Messages
			.getString("FindReplaceWindow.13")); //$NON-NLS-1$
	private JCheckBox chbWholeProject = new JCheckBox(Messages
			.getString("FindReplaceWindow.14")); //$NON-NLS-1$
	private JButton btnFindNext = new JButton(Messages
			.getString("FindReplaceWindow.15")); //$NON-NLS-1$
	private JButton btnReplace = new JButton(Messages
			.getString("FindReplaceWindow.16")); //$NON-NLS-1$
	private JButton btnReplaceAll = new JButton(Messages
			.getString("FindReplaceWindow.17")); //$NON-NLS-1$
	private JButton btnClose = new JButton(Messages
			.getString("FindReplaceWindow.18")); //$NON-NLS-1$
	private Box upperBox = Box.createHorizontalBox();
	private Box lowerBox = Box.createHorizontalBox();
	private Box wholeBox = Box.createVerticalBox();
	/**
	 * Index noda na trenutno aktivnom dijagramu u pretrazi koja je u toku
	 */
	private int nodeIndex = 0;
	/**
	 * Trenutno aktivni dijagram. Dijagram na kom je pokrenuta pretraga.
	 */
	private GERMModel currentDiagram = Application.getInstance().getModel();
	/**
	 * Projekat u kom se nalazi dijagram za koji je pokrenuta pretraga.
	 */
	private Project currentProject = currentDiagram.getProject();
	/**
	 * Index dijagrama u njegovom projektu od kog krece pretraga.
	 * Koristi se za slucaj pretrage u celom projektu.
	 */
	private int startingDiagramIndex = currentProject
			.getDiagramIndex(currentDiagram);
	/**
	 * Index sledeceg dijagrama sa pretragu.
	 * Koristi se za slucaj pretrage u celom projektu.
	 */
	private int newDiagram = startingDiagramIndex;
	/**
	 * Promenljiva sluzi pri tranziciji sa jednog dijagrama na sledeci.
	 */
	private int diagramIndex = startingDiagramIndex;
	/**
	 * Broj dijagrama u aktuelnom projektu.
	 */
	private int diagramCount = currentProject.getDiagramCount();
	/**
	 * Lista svih prethodnih upita za Replace nekog naziva noda.
	 * Cuva se sve dok program radi.
	 */
	private ArrayList<String> pReplaces = Application.getInstance()
			.getPreviousRaplaces();
	/**
	 * Lista svih prethodnih upita za Find nekog naziva noda.
	 * Cuva se sve dok program radi.
	 */
	private ArrayList<String> pFinds = Application.getInstance()
			.getPreviousFinds();

	/**
	 * Klasa omogucava auto-complete opciju za combo boxove za Find i Replace imena nodova.
	 * 
	 * @author WiktorNS - ma jok :D, kod je "pozajmljen" sa neta...
	 *
	 */
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
	
	public FindReplaceWindow() {


		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		// get the combo boxes editor component
		JTextComponent editorF = (JTextComponent) cbFind.getEditor()
				.getEditorComponent();
		JTextComponent editorR = (JTextComponent) cbReplace.getEditor()
		.getEditorComponent();
		// change the editor's document
		editorF.setDocument(new AutoCompleteCombo(cbFind));
		editorR.setDocument(new AutoCompleteCombo(cbReplace));

		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle(Messages.getString("FindReplaceWindow.19")); //$NON-NLS-1$
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();

		cbFind.setEditable(true);
		cbReplace.setEditable(true);
		initializeComboBoxes();

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
		c11.fill = GridBagConstraints.HORIZONTAL;
		c11.anchor = GridBagConstraints.WEST;
		c11.insets = new Insets(10, 20, 0, 20);

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

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 3;
		c3.gridwidth = 2;
		c3.anchor = GridBagConstraints.WEST;
		c3.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 0;
		c4.gridy = 4;
		c4.gridwidth = 2;
		c4.anchor = GridBagConstraints.WEST;
		c4.insets = new Insets(0, 75, 0, 0);

		GridBagConstraints c5 = new GridBagConstraints();
		c5.gridx = 0;
		c5.gridy = 5;
		c5.gridwidth = 2;
		c5.anchor = GridBagConstraints.WEST;
		c5.insets = new Insets(0, 75, 0, 0);

		GridBagConstraints c6 = new GridBagConstraints();
		c6.gridx = 0;
		c6.gridy = 6;
		c6.gridwidth = 2;
		c6.anchor = GridBagConstraints.WEST;
		c6.insets = new Insets(0, 75, 0, 0);

		GridBagConstraints c7 = new GridBagConstraints();
		c7.gridx = 0;
		c7.gridy = 7;
		c7.gridwidth = 2;
		c7.anchor = GridBagConstraints.CENTER;
		c7.insets = new Insets(35, 20, 10, 20);

		upperBox.add(Box.createHorizontalGlue());
		upperBox.add(btnFindNext);
		upperBox.add(Box.createHorizontalStrut(20));
		upperBox.add(btnReplace);
		upperBox.add(Box.createHorizontalGlue());

		lowerBox.add(Box.createHorizontalGlue());
		lowerBox.add(btnReplaceAll);
		lowerBox.add(Box.createHorizontalStrut(20));
		lowerBox.add(btnClose);
		lowerBox.add(Box.createHorizontalGlue());

		wholeBox.add(upperBox);
		wholeBox.add(Box.createVerticalStrut(15));
		wholeBox.add(lowerBox);

		container.add(lblFind, c00);
		container.add(cbFind, c10);
		container.add(lblReplace, c01);
		container.add(cbReplace, c11);
		container.add(lblType, c02);
		container.add(cbType, c12);
		container.add(lblOptions, c3);
		container.add(chbCaseSensitive, c4);
		container.add(chbWholeWord, c5);
		container.add(chbWholeProject, c6);
		container.add(wholeBox, c7);

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escapePressed();
			}
		});

		btnFindNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findNext();
			}
		});

		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String find = getFind();
					String replace = getReplace();
					if (!pReplaces.contains(replace))
						pReplaces.add(replace);
					GERMModel m = Application.getInstance().getModel();
					Node node = m.getSelectedNodes().get(0);
					String name = node.getName().toLowerCase();
					String newName = name.replaceAll(find.toLowerCase(), replace);
					node.setName(newName);
					node.setLastChangeDate(Calendar.getInstance());
					m.updatePerformed();
				}catch(Exception ex){
					
				}
			}
		});

		btnReplaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!pReplaces.contains(getReplace())) {
					pReplaces.add(getReplace());
				}
				if (!pFinds.contains(getFind())) {
					pFinds.add(getFind());
				}
				replaceAll();
			}
		});

		ChangeListener change = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nodeIndex = 0;
			}
		};

		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nodeIndex = 0;
			}

		};

		chbCaseSensitive.addChangeListener(change);
		chbWholeProject.addChangeListener(change);
		chbWholeWord.addChangeListener(change);
		cbFind.addActionListener(action);
		cbType.addActionListener(action);
		cbReplace.addActionListener(action);

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					escapePressed();
				}
				return false;
			}
		});

		setCursor(Cursors.getCursor(Messages.getString("FindReplaceWindow.20"))); //$NON-NLS-1$
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height
				/ 2);
	}

	public String getFind() {
		return cbFind.getSelectedItem().toString();
	}

	public String getReplace() {
		return cbReplace.getSelectedItem().toString();
	}

	public String getType() {
		return cbType.getSelectedItem().toString();
	}

	public boolean isCaseSensitive() {
		return chbCaseSensitive.isSelected();
	}

	public boolean isWholeWord() {
		return chbWholeWord.isSelected();
	}

	public boolean isWholeProject() {
		return chbWholeProject.isSelected();
	}

	/**
	 * Funkcija prvo vrsi analizu stanja checkBoxova pre vrsenja pretrage, zatim u zavisnosti od 
	 * zahteva pretrage (koji su checkBoxovi otkaceni) trazi prvi sledeci nod koji odgovara zahtevima pretrage.
	 * Ukoliko ga pronadje prikazuje dijagram pronadjenog noda i na njemu centrira taj nod na prikazu, a u suprotnom
	 * prikazuje adekvatnu poruku.Funkcija nastavlja pretragu od nodeIndexa odnosno odatle gde je prethodni put stala. 
	 * Ukoliko je zapoceta nova pretraga,nodeIndex se resetuje na 0. Takodje svaki upit pretrage (rec upisana u Find comboBox)
	 * se pamti sve dok program radi. 
	 */
	public void findNext() {
		int type = cbType.getSelectedIndex();
		String find = getFind();
		GERMView v = Application.getInstance().getView();
		boolean wholeProject = isWholeProject();
		boolean wholeWord = isWholeWord();
		boolean caseSensitive = isCaseSensitive();
		if (find.length() != 0) {
			if (!pFinds.contains(find)) {
				pFinds.add(find);
			}
			if (!wholeProject) {
				if (!wholeWord) {
					if (!caseSensitive) {
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().contains(
											find.toLowerCase())) {
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
						if (nodeIndex + 1 >= currentDiagram.getNodeCount()) {
							nodeIndex = 0;
							JOptionPane.showMessageDialog(Application
									.getInstance().getView(), Messages
									.getString("FindReplaceWindow.21"), //$NON-NLS-1$
									Messages.getString("FindReplaceWindow.22"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							btnReplace.setEnabled(false);
						}
					} else {
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().contains(find)) {
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
						if (nodeIndex + 1 >= currentDiagram.getNodeCount()) {
							nodeIndex = 0;
							JOptionPane.showMessageDialog(Application
									.getInstance().getView(), Messages
									.getString("FindReplaceWindow.23"), //$NON-NLS-1$
									Messages.getString("FindReplaceWindow.22"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							btnReplace.setEnabled(false);
						}

					}
				} else {
					if (!caseSensitive) {
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().equals(
											find.toLowerCase())) {
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
						if (nodeIndex + 1 >= currentDiagram.getNodeCount()) {
							nodeIndex = 0;
							JOptionPane.showMessageDialog(Application
									.getInstance().getView(), Messages
									.getString("FindReplaceWindow.25"), //$NON-NLS-1$
									Messages.getString("FindReplaceWindow.22"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							btnReplace.setEnabled(false);
						}

					} else {
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().equals(find)) {
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
						if (nodeIndex + 1 >= currentDiagram.getNodeCount()) {
							nodeIndex = 0;
							JOptionPane.showMessageDialog(Application
									.getInstance().getView(), Messages
									.getString("FindReplaceWindow.27"), //$NON-NLS-1$
									Messages.getString("FindReplaceWindow.22"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							btnReplace.setEnabled(false);
						}

					}
				}
			} else {
				if (!wholeWord) {
					if (!caseSensitive) {
						if (nodeIndex >= currentDiagram.getNodeCount()) {
							newDiagram = changeDiagram(++diagramIndex);
						}
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().contains(
											find.toLowerCase())) {
								if (Application.getInstance().getModel() != currentProject
										.getDiagram(newDiagram)) {
									Application.getInstance().setModel(
											currentProject
													.getDiagram(newDiagram));
								}
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}

					} else {
						if (nodeIndex >= currentDiagram.getNodeCount()) {
							newDiagram = changeDiagram(++diagramIndex);
						}
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().contains(find)) {
								if (Application.getInstance().getModel() != currentProject
										.getDiagram(newDiagram)) {
									Application.getInstance().setModel(
											currentProject
													.getDiagram(newDiagram));
								}
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
					}
				} else {
					if (!caseSensitive) {
						if (nodeIndex >= currentDiagram.getNodeCount()) {
							newDiagram = changeDiagram(++diagramIndex);
						}
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().equals(
											find.toLowerCase())) {
								if (Application.getInstance().getModel() != currentProject
										.getDiagram(newDiagram)) {
									Application.getInstance().setModel(
											currentProject
													.getDiagram(newDiagram));
								}
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
					} else {
						if (nodeIndex >= currentDiagram.getNodeCount()) {
							newDiagram = changeDiagram(++diagramIndex);
						}
						for (int i = nodeIndex; i != currentDiagram
								.getNodeCount(); i++) {
							nodeIndex++;
							Node node = currentDiagram.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().equals(find)) {
								if (Application.getInstance().getModel() != currentProject
										.getDiagram(newDiagram)) {
									Application.getInstance().setModel(
											currentProject
													.getDiagram(newDiagram));
								}
								currentDiagram.deselectAllNodes();
								currentDiagram.selectNode(node);
								v.centerNode(node);
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Funkcija proverava da li pronadjeni nod ogovara trazenom tipu noda iz pretrage.
	 * 
	 * @param node - pronadjeni nod ciji naziv odgovara upitu pretrage
	 * @param type - indeks selektovanog elementa iz Type comboBoxa
	 * 
	 * @return - povratna vrednost je boolean vrednost odnosno informacija da li prosledjeni nod
	 * 			odgovara zahtevanom tipu noda po pretrazi.
	 */
	public boolean checkType(Node node, int type) {

		boolean adequateType = true;

		switch (type) {

		case 0:
			adequateType = true;
			break;
		case 1:
			if (node instanceof Argument)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 2:
			if (node instanceof Assumption)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 3:
			if (node instanceof Decision)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 4:
			if (node instanceof Position)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 5:
			if (node instanceof Requirement)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 6:
			if (node instanceof Stakeholder)
				adequateType = true;
			else
				adequateType = false;
			break;
		case 7:
			if (node instanceof Topic)
				adequateType = true;
			else
				adequateType = false;
			break;
		}

		return adequateType;
	}

	/**
	 * Funkcija prvo vrsi analizu stanja podataka vezanih za pretragu, i zatim u odnosu na zahteve pretrage
	 * za pocinje iteraciju kroz sve nodove i svakom nodu koji odgovara pretrazi zamenjuje deo teksta u
	 * Find comboBoxu sa delom teksta u Replace comboBoxu. Kada se iteracija zavrsi ispisuje se adekvatan poruka
	 * sa informacijom o broju nodova kojima me uspesno izvrsena zamena.
	 */
	public void replaceAll() {
		int replacedNodes = 0;
		String find = getFind();
		String replace = getReplace();
		boolean wholeProject = isWholeProject();
		boolean wholeWord = isWholeWord();
		boolean caseSensitive = isCaseSensitive();
		int type = cbType.getSelectedIndex();
		if (find.length() != 0) {
			GERMModel m = Application.getInstance().getModel();
			if (!wholeProject) {
				if (caseSensitive) {
					if (wholeWord) {
						for (int i = nodeIndex; i != m.getNodeCount(); i++) {
							Node node = m.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().equals(find)) {
								String name = node.getName().toLowerCase();
								String newName = name.replaceAll(find
										.toLowerCase(), replace);
								node.setName(newName);
								node.setLastChangeDate(Calendar.getInstance());
								replacedNodes++;
							}
						}
					} else {
						for (int i = nodeIndex; i != m.getNodeCount(); i++) {
							nodeIndex++;
							Node node = m.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().contains(find)) {
								String name = node.getName().toLowerCase();
								String newName = name.replaceAll(find
										.toLowerCase(), replace);
								node.setName(newName);
								node.setLastChangeDate(Calendar.getInstance());
								replacedNodes++;
							}
						}
					}
				} else {
					if (wholeWord) {
						for (int i = nodeIndex; i != m.getNodeCount(); i++) {
							nodeIndex++;
							Node node = m.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().equals(
											find.toLowerCase())) {
								String name = node.getName().toLowerCase();
								String newName = name.replaceAll(find
										.toLowerCase(), replace);
								node.setName(newName);
								node.setLastChangeDate(Calendar.getInstance());
								replacedNodes++;
							}
						}
					} else {
						for (int i = nodeIndex; i != m.getNodeCount(); i++) {
							nodeIndex++;
							Node node = m.getNodeAt(i);
							if (checkType(node, type)
									&& node.getName().toLowerCase().contains(
											find.toLowerCase())) {
								String name = node.getName().toLowerCase();
								String newName = name.replaceAll(find
										.toLowerCase(), replace);
								node.setName(newName);
								node.setLastChangeDate(Calendar.getInstance());
								replacedNodes++;
							}
						}
					}
				}
			} else {
				if (caseSensitive) {
					if (wholeWord) {
						for (int j = 0; j != diagramCount; j++) {
							m = currentProject.getDiagram(j);
							nodeIndex = 0;
							for (int i = nodeIndex; i != m.getNodeCount(); i++) {
								Node node = m.getNodeAt(i);
								if (checkType(node, type)
										&& node.getName().equals(getFind())) {
									String name = node.getName().toLowerCase();
									String newName = name.replaceAll(find
											.toLowerCase(), replace);
									node.setName(newName);
									node.setLastChangeDate(Calendar
											.getInstance());
									replacedNodes++;
								}
							}
						}
					} else {
						for (int j = 0; j != diagramCount; j++) {
							m = currentProject.getDiagram(j);
							nodeIndex = 0;
							for (int i = nodeIndex; i != m.getNodeCount(); i++) {
								nodeIndex++;
								Node node = m.getNodeAt(i);
								if (checkType(node, type)
										&& node.getName().contains(getFind())) {
									String name = node.getName().toLowerCase();
									String newName = name.replaceAll(find
											.toLowerCase(), replace);
									node.setName(newName);
									node.setLastChangeDate(Calendar
											.getInstance());
									replacedNodes++;
								}
							}
						}
					}
				} else {
					if (wholeWord) {
						for (int j = 0; j != diagramCount; j++) {
							m = currentProject.getDiagram(j);
							nodeIndex = 0;
							for (int i = nodeIndex; i != m.getNodeCount(); i++) {
								nodeIndex++;
								Node node = m.getNodeAt(i);
								if (checkType(node, type)
										&& node.getName().toLowerCase().equals(
												getFind().toLowerCase())) {
									String name = node.getName().toLowerCase();
									String newName = name.replaceAll(find
											.toLowerCase(), replace);
									node.setName(newName);
									node.setLastChangeDate(Calendar
											.getInstance());
									replacedNodes++;
								}
							}
						}
					} else {
						for (int j = 0; j != diagramCount; j++) {
							m = currentProject.getDiagram(j);
							nodeIndex = 0;
							for (int i = nodeIndex; i != m.getNodeCount(); i++) {
								nodeIndex++;
								Node node = m.getNodeAt(i);
								if (checkType(node, type)
										&& node
												.getName()
												.toLowerCase()
												.contains(
														getFind().toLowerCase())) {
									String name = node.getName().toLowerCase();
									String newName = name.replaceAll(find
											.toLowerCase(), replace);
									node.setName(newName);
									node.setLastChangeDate(Calendar
											.getInstance());
									replacedNodes++;
								}
							}
						}
					}
				}
			}
			JOptionPane
					.showMessageDialog(
							Application.getInstance().getView(),
							Messages.getString("FindReplaceWindow.29") + replacedNodes + Messages.getString("FindReplaceWindow.30"), //$NON-NLS-1$ //$NON-NLS-2$
							Messages.getString("FindReplaceWindow.22"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			m.updatePerformed();
		}
	}

	/**
	 * Funkcija za inicijalizaciju sadrazaja Find i Replace comboBoxova. Pri svakom pokretanju prozora
	 * comboBoxovi se prazne i popunjavaju svim prethodno unesenim upitima koji se cuvaju u samoj aplikaciji.
	 */
	public void initializeComboBoxes() {
		int i, j;
		cbFind.removeAllItems();
		cbReplace.removeAllItems();
		if (pFinds.size() > 0) {
			for (i = pFinds.size() - 1; i != -1; i--) {
				cbFind.addItem(pFinds.get(i));

			}
			cbFind.setSelectedIndex(i);
		} else
			cbFind.setSelectedItem(""); //$NON-NLS-1$
		if (pReplaces.size() > 0) {
			for (j = pReplaces.size() - 1; j != -1; j--) {
				cbReplace.addItem(pReplaces.get(j));

			}
			cbReplace.setSelectedIndex(j);
		} else
			cbReplace.setSelectedItem(""); //$NON-NLS-1$
	}

	/**
	 * Funkcija vrsi promenu indexa trenutno aktuelnog dijagrama u pretrazi.
	 * 
	 * @param index - index dijagrama za koji je upravo zavrsena pretraga
	 * @return povratna vrednost je index sledeceg dijagrama za koji ce se vrsiti pretraga
	 */
	public int changeDiagram(int index) {
		int nextDiagramIndex = 0;
		nodeIndex = 0;
		if (index > diagramCount - 1) {
			nextDiagramIndex = index - diagramCount;
		} else
			nextDiagramIndex = index;

		if (nextDiagramIndex == startingDiagramIndex) {
			JOptionPane
					.showMessageDialog(
							Application.getInstance().getView(),
							Messages.getString("FindReplaceWindow.34"), Messages.getString("FindReplaceWindow.22"), //$NON-NLS-1$ //$NON-NLS-2$
							JOptionPane.INFORMATION_MESSAGE);
			btnReplace.setEnabled(false);
			return startingDiagramIndex;
		} else {
			GERMModel nextDiagram = Application.getInstance().getModel()
					.getProject().getDiagram(nextDiagramIndex);
			currentDiagram = nextDiagram;
			return nextDiagramIndex;
		}
	}
	
	/**
	 * Funkcija definise reakciju programa na pritisak escape-a ili "Cancel" dugmeta na prozoru.
	 * Promenljiva nodeIndex se resetuje. Dugme replace se iskljucuje dok ne bude potreban i prozor se zatvara.
	 */
	public void escapePressed(){
		nodeIndex = 0;
		setVisible(false);
	}

}
