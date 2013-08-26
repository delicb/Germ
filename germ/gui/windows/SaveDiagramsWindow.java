package germ.gui.windows;

import germ.i18n.Messages;
import germ.model.GERMModel;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;


/**
 * Klasa predstavlja prozor za obavestenje o nesacuvanih dijagramima neposredno pre gasenja aplikacije.
 */
@SuppressWarnings("serial")
public class SaveDiagramsWindow extends JDialog {

	/**
	 * Lista svih nesacuvanih dijagrama u trenutku gasenja aplikacije.
	 */
	private ArrayList<GERMModel> unsaved;
	/**
	 * Lista svih dijagrama chekiranih za snimanje.
	 */
	private ArrayList<GERMModel> checked = new ArrayList<GERMModel>();
	private JLabel lblSave = new JLabel(
			Messages.getString("SaveDiagramsWindow.0")); //$NON-NLS-1$
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	private JCheckBox chbSaveAll = new JCheckBox(Messages.getString("SaveDiagramsWindow.1")); //$NON-NLS-1$
	private JButton btnOK = new JButton(Messages.getString("SaveDiagramsWindow.2")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages.getString("SaveDiagramsWindow.3")); //$NON-NLS-1$
	private Box btnBox = Box.createHorizontalBox();
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	private boolean dialogResult = false;

	/**
	 * Konstruktor dijaloga za snimanje dijagrama pre izlaska iz programa
	 * 
	 * @param unsavedDiagrams - lista dijagrama koji nisu bili sacuvani u momentu kada se pokrenulo gasenje programa
	 */
	public SaveDiagramsWindow(ArrayList<GERMModel> unsavedDiagrams) {

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		int i = 0;
		setModalityType(ModalityType.APPLICATION_MODAL);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setTitle(Messages.getString("SaveDiagramsWindow.4")); //$NON-NLS-1$
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());

		Container container = getContentPane();

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(20, 20, 10, 20);

		container.add(lblSave, c);

		unsaved = unsavedDiagrams;

		for (i = 0; i != unsaved.size(); i++) {

			GridBagConstraints name = new GridBagConstraints();
			name.gridx = 0;
			name.gridy = i + 1;
			name.weightx = 1;
			name.anchor = GridBagConstraints.EAST;
			name.insets = new Insets(5, 0, 0, 10);

			GridBagConstraints check = new GridBagConstraints();
			check.gridx = 1;
			check.gridy = i + 1;
			check.weightx = 1;
			check.anchor = GridBagConstraints.WEST;
			check.insets = new Insets(5, 10, 0, 0);

			container.add(new JLabel(unsaved.get(i).getName()), name);
			JCheckBox chbSave = new JCheckBox();
			chbSave.setName("" + i); //$NON-NLS-1$
			container.add(chbSave, check);
			checkBoxes.add(chbSave);
		}

		GridBagConstraints ce0 = new GridBagConstraints();
		ce0.gridx = 0;
		ce0.gridy = i + 1;
		ce0.weightx = 1;
		ce0.gridwidth = 2;
		ce0.anchor = GridBagConstraints.WEST;
		ce0.insets = new Insets(10, 20, 20, 0);

		GridBagConstraints ce1 = new GridBagConstraints();
		ce1.gridx = 1;
		ce1.gridy = i + 1;
		ce1.weightx = 1;
		ce1.gridwidth = 2;
		ce1.anchor = GridBagConstraints.EAST;
		ce1.insets = new Insets(10, 0, 20, 20);

		container.add(chbSaveAll, ce0);

		btnBox.add(btnOK);
		btnBox.add(Box.createHorizontalStrut(20));
		btnBox.add(btnCancel);

		container.add(btnBox, ce1);

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
	
		chbSaveAll.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if (chbSaveAll.isSelected()) {
					for (JCheckBox checks : checkBoxes)
						checks.setSelected(true);
				} else {
					for (JCheckBox checks : checkBoxes)
						checks.setSelected(false);
				}
			}
		});

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

		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height
				/ 2);

	}

	/**
	 * Funkcija definise reakciju programa na pritisak entera ili "ok" dugmeta na prozoru.
	 * Popunjava se lista dijagrama koji treba da se sacuvaju pre zatvaranja programa. Lista se popunjava 
	 * na osnovu toga koji su dijagrami otkaceni a koji nisu. U listu ulaze samo otkaceni dijgrami.
	 * Rezultat dijaloga postaje potvrdan i dijalog se sakriva.
	 */
	public void enterPressed() {
		checked.clear();
		for (JCheckBox checks : checkBoxes) {
			if (checks.isSelected()) {
				checked.add(unsaved.get(Integer.parseInt(checks
						.getName())));
			}
		}
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

	public void setDialogResult(boolean result) {
		dialogResult = result;
	}

	public boolean isDialogResult() {
		return dialogResult;
	}

	public ArrayList<GERMModel> getCheckedDiagrams() {
		return checked;
	}

}
