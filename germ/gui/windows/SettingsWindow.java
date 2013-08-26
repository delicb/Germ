package germ.gui.windows;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.text.MaskFormatter;

/**
 * Klasa predstavlja prozor za podesavanja svih generalnih svojstava programa.
 */
@SuppressWarnings("serial")
public class SettingsWindow extends JDialog {

	private JLabel lblURStackSize = new JLabel(Messages
			.getString("SettingsWindow.0")); //$NON-NLS-1$
	private JFormattedTextField tfURStackSize;
	private JLabel lblTheme = new JLabel(Messages.getString("SettingsWindow.1")); //$NON-NLS-1$
	private JLabel lblArrowMoveStep = new JLabel(Messages
			.getString("SettingsWindow.2")); //$NON-NLS-1$
	private JFormattedTextField tfArrowMoveStep;
	private JLabel lblShowGrid = new JLabel(Messages
			.getString("SettingsWindow.3")); //$NON-NLS-1$
	private JCheckBox chbShowGrid = new JCheckBox();
	private JLabel lblTreeSize = new JLabel(Messages
			.getString("SettingsWindow.5")); //$NON-NLS-1$
	private JFormattedTextField tfTreeSize;
	private JLabel lblNodes = new JLabel(Messages.getString("SettingsWindow.6")); //$NON-NLS-1$
	private JButton btnNodeProperties = new JButton(Messages
			.getString("SettingsWindow.7")); //$NON-NLS-1$	
	private Box okCancelBox = Box.createHorizontalBox();
	private JButton btnOK = new JButton(Messages.getString("SettingsWindow.8")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages
			.getString("SettingsWindow.9")); //$NON-NLS-1$
	private JLabel lblShadow = new JLabel(Messages
			.getString("SettingsWindow.11"));
	private JCheckBox chbShadow = new JCheckBox();
	private JLabel lblLasoOverNode = new JLabel(Messages
			.getString("SettingsWindow.12"));
	private JCheckBox chbLaso = new JCheckBox();
	private JLabel lblBacgroundColor = new JLabel(Messages
			.getString("SettingsWindow.13"));
	private JButton btnBackColor = new JButton(Messages
			.getString("SettingsWindow.14"));
	private JLabel lblGridColor = new JLabel(Messages
			.getString("SettingsWindow.15"));
	private JButton btnGridColor = new JButton(Messages
			.getString("SettingsWindow.16"));
	private JLabel lblAnimation = new JLabel(Messages
			.getString("SettingsWindow.18"));
	private JCheckBox chbAnimation = new JCheckBox();
	/**
	 * Rezultat dijaloga: false = cancel, true = ok
	 */
	protected boolean dialogResult = false;

	/**
	 * Lista raspolozivih tema za aplikaciju.
	 */
	private ArrayList<String> themeItems = ConfigurationManager.getInstance()
			.getStringArray("allThemes"); //$NON-NLS-1$
	private JComboBox cbTheme = new JComboBox();
	/**
	 * Trenutno podesena tema aplikacije.
	 */
	private String theme = ConfigurationManager.getInstance()
			.getString("theme"); //$NON-NLS-1$
	/**
	 * Trenutno podesena velicina undo - redo steka
	 */
	private int urStackSize = ConfigurationManager.getInstance().getInt(
			"undoRedoStackSize"); //$NON-NLS-1$
	/**
	 * Trenutno podesen korak pomeraja kanvasa pri koriscenju strelica
	 */
	private int arrowMoveStep = ConfigurationManager.getInstance().getInt(
			"arrowMoveStep"); //$NON-NLS-1$
	/**
	 * Trenutno podesena velicina JTree komponente u aplikaciji.
	 */
	private int treeSize = ConfigurationManager.getInstance()
			.getInt("treeSize"); //$NON-NLS-1$
	/**
	 * Trenutno podesena informacija o prikazivanju grida na kanvasu pri
	 * pokretanju aplikacije. True = grid se prikazuje, FALSE = grid se ne
	 * prikazuje
	 */
	private boolean showGrid = ConfigurationManager.getInstance().getBoolean(
			"showGrid"); //$NON-NLS-1$

	private JLabel lblLanguage = new JLabel(Messages
			.getString("SettingsWindow.10")); //$NON-NLS-1$
	/**
	 * Lista zastava koje predstavljaju jezike moguce za izbor.
	 */
	private ImageIcon[] flags;
	/**
	 * Lista Tekstualnih oznaka jezika mogucih za izbor.
	 */
	private String[] languages = { "ENG", "FRA", "GER", "HUN", "ITA", "SER",
			"СРБ", "ESP" };
	private JComboBox cbLanguages;

	/**
	 * Trenutno podeseni jezik interfejsa aplikacije.
	 */
	private String language = ConfigurationManager.getInstance().getString(
			"language");

	/**
	 * Trenutno podesena informacija o prikazivanju lasoa preko nodova. True =
	 * laso se prikazuje, FALSE = laso se ne prikazuje
	 */
	private boolean showLaso = ConfigurationManager.getInstance().getBoolean(
			"lasoOverNodeShow");

	/**
	 * Trenutno podesena informacija o prikazivanju senki nodova na kanvasu pri
	 * pokretanju aplikacije. True = senka se prikazuje, FALSE = senka se ne
	 * prikazuje
	 */
	private boolean showShadow = ConfigurationManager.getInstance().getBoolean(
			"nodeShadow");

	/**
	 * Trenutno podesena boja kanvasa aplikacije
	 */
	private Color backgroundColor = ConfigurationManager.getInstance()
			.getColor("background");

	/**
	 * Trenutno podesena boja mreze na kanvasu.
	 */
	private Color gridColor = ConfigurationManager.getInstance().getColor(
			"gridColor");

	/**
	 * Trenutno podesena informacija o prikazivanju svih animacija na kanvasu
	 * pri pokretanju aplikacije. True = animacija se prikazuje, FALSE =
	 * animacija se ne prikazuje
	 */
	private boolean showAnimation = ConfigurationManager.getInstance()
			.getBoolean("animationEnabled");

	/**
	 * Klasa omogucava prikaz zastavica i tekstualnih oznaka jezika u comboBoxu
	 * cbLanguages.
	 * 
	 * @author WiktorNS - ma jok :D, kod je "pozajmljen" sa neta...
	 * 
	 */
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {

		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// Get the selected index. (The index param isn't
			// always valid, so just use the value.)
			int selectedIndex = ((Integer) value).intValue();

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			// Set the icon and text. If icon was null, say so.
			ImageIcon icon = flags[selectedIndex];
			String pet = languages[selectedIndex];
			setIcon(icon);
			if (icon != null) {
				setText(pet);
				setFont(list.getFont());
			}

			return this;
		}
	}

	public SettingsWindow() {
		try {
			setIconImage(ImageIO.read(new File(
					"germ/gui/windows/images/programIcon.png")));
		} catch (Exception ex) {
		}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setTitle(Messages.getString("SettingsWindow.17")); //$NON-NLS-1$
		setResizable(false);
		setLayout(new GridBagLayout());

		comboFlags();

		cbTheme.setEditable(false);
		cbTheme.setPreferredSize(new Dimension(65, 20));
		for (String s : themeItems) {
			cbTheme.addItem(s);
		}

		btnNodeProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultNodeProperties dn = new defaultNodeProperties();
				dn.setVisible(true);
				if (dn.isDialogResult()) {
					setNewSettings(dn);
				}
			}
		});

		btnGridColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setColor(gridColor);
				cw.setVisible(true);
				if (cw.isDialogResult()) {
					setGridColor(cw.getColor());
				}

			}
		});

		btnBackColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
				cw.setColor(backgroundColor);
				cw.setVisible(true);
				if (cw.isDialogResult()) {
					setBackgroundColor(cw.getColor());
				}
			}
		});

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

		try {
			MaskFormatter mask0 = new MaskFormatter("#***");
			mask0.setValidCharacters("0123456789 ");
			tfURStackSize = new JFormattedTextField(mask0);
			tfURStackSize.setColumns(5);
			MaskFormatter mask1 = new MaskFormatter("#***");
			mask1.setValidCharacters("0123456789 ");
			tfArrowMoveStep = new JFormattedTextField(mask1);
			tfArrowMoveStep.setColumns(5);
			MaskFormatter mask2 = new MaskFormatter("#***");
			mask2.setValidCharacters("0123456789 ");
			tfTreeSize = new JFormattedTextField(mask2);
			tfTreeSize.setColumns(5);
		} catch (java.text.ParseException exc) {

		}

		Container container = getContentPane();

		GridBagConstraints c00 = new GridBagConstraints();
		c00.gridx = 0;
		c00.gridy = 0;
		c00.anchor = GridBagConstraints.NORTHEAST;
		c00.insets = new Insets(20, 20, 0, 0);

		GridBagConstraints c10 = new GridBagConstraints();
		c10.gridx = 1;
		c10.gridy = 0;
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
		c13.anchor = GridBagConstraints.WEST;
		c13.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints c04 = new GridBagConstraints();
		c04.gridx = 0;
		c04.gridy = 4;
		c04.anchor = GridBagConstraints.EAST;
		c04.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c14 = new GridBagConstraints();
		c14.gridx = 1;
		c14.gridy = 4;
		c14.anchor = GridBagConstraints.WEST;
		c14.insets = new Insets(10, 17, 0, 20);

		GridBagConstraints c05 = new GridBagConstraints();
		c05.gridx = 0;
		c05.gridy = 5;
		c05.anchor = GridBagConstraints.EAST;
		c05.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c15 = new GridBagConstraints();
		c15.gridx = 1;
		c15.gridy = 5;
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
		c18.anchor = GridBagConstraints.WEST;
		c18.insets = new Insets(10, 17, 0, 20);

		GridBagConstraints c09 = new GridBagConstraints();
		c09.gridx = 0;
		c09.gridy = 9;
		c09.anchor = GridBagConstraints.EAST;
		c09.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c19 = new GridBagConstraints();
		c19.gridx = 1;
		c19.gridy = 9;
		c19.anchor = GridBagConstraints.WEST;
		c19.insets = new Insets(10, 17, 0, 20);

		GridBagConstraints c010 = new GridBagConstraints();
		c010.gridx = 0;
		c010.gridy = 10;
		c010.anchor = GridBagConstraints.EAST;
		c010.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c110 = new GridBagConstraints();
		c110.gridx = 1;
		c110.gridy = 10;
		c110.anchor = GridBagConstraints.WEST;
		c110.insets = new Insets(10, 17, 0, 20);

		GridBagConstraints c011 = new GridBagConstraints();
		c011.gridx = 0;
		c011.gridy = 11;
		c011.anchor = GridBagConstraints.EAST;
		c011.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints c111 = new GridBagConstraints();
		c111.gridx = 1;
		c111.gridy = 11;
		c111.anchor = GridBagConstraints.WEST;
		c111.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints cOK = new GridBagConstraints();
		cOK.gridx = 0;
		cOK.gridy = 12;
		cOK.gridwidth = 2;
		cOK.anchor = GridBagConstraints.CENTER;
		cOK.insets = new Insets(35, 0, 10, 0);

		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(40));
		okCancelBox.add(btnCancel);

		container.add(lblTheme, c00);
		container.add(cbTheme, c10);
		container.add(lblURStackSize, c01);
		container.add(tfURStackSize, c11);
		container.add(lblArrowMoveStep, c02);
		container.add(tfArrowMoveStep, c12);
		container.add(lblTreeSize, c03);
		container.add(tfTreeSize, c13);
		container.add(lblShowGrid, c04);
		container.add(chbShowGrid, c14);
		container.add(lblGridColor, c05);
		container.add(btnGridColor, c15);
		container.add(lblBacgroundColor, c06);
		container.add(btnBackColor, c16);
		container.add(lblNodes, c07);
		container.add(btnNodeProperties, c17);
		container.add(lblShadow, c08);
		container.add(chbShadow, c18);
		container.add(lblLasoOverNode, c09);
		container.add(chbLaso, c19);
		container.add(lblAnimation, c010);
		container.add(chbAnimation, c110);
		container.add(lblLanguage, c011);
		container.add(cbLanguages, c111);
		container.add(okCancelBox, cOK);

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

		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$
		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height
				/ 2);
	}

	public boolean isDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(boolean dialogResult) {
		this.dialogResult = dialogResult;
	}

	/**
	 * Funkcija postavlja u konfiguracioni menadzer sva nova podesavanja za
	 * nodove koja se pokupe iz prethodno zatvorenog prozora za podesavanje
	 * defaultnih vrednosti za nodove.
	 * 
	 * @param dn
	 *            - prozor za podesavanje defaultnih vrednosti za nodove.
	 */
	public void setNewSettings(defaultNodeProperties dn) {

		try {
			dn.getTabs().setArgumentSize(
					new Dimension(Integer.parseInt(dn.getTabs().getArgumentWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getArgumentHeight().trim().replaceAll(" ", ""))));
			dn.getTabs().setAssumptionSize(
					new Dimension(Integer.parseInt(dn.getTabs().getAssumptionWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getAssumptionHeight().trim().replaceAll(" ", ""))));
			dn.getTabs().setDecisionSize(
					new Dimension(Integer.parseInt(dn.getTabs().getDecisionWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getDecisionHeight().trim().replaceAll(" ", ""))));
			dn.getTabs().setPositionSize(
					new Dimension(Integer.parseInt(dn.getTabs().getPositionWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getPositionHeight().trim().replaceAll(" ", ""))));
			dn.getTabs().setStakeholderSize(
					new Dimension(Integer.parseInt(dn.getTabs().getStakeholderWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getStakeholderHeight().trim().replaceAll(" ", ""))));
			dn.getTabs().setTopicSize(
					new Dimension(Integer.parseInt(dn.getTabs().getTopicWidth().trim().replaceAll(" ", "")),
							Integer.parseInt(dn.getTabs().getTopicHeight().trim().replaceAll(" ",""))));
		} catch (Exception ex) {}

		ConfigurationManager.getInstance().setColor("argumentStrokeColor", //$NON-NLS-1$
				dn.getTabs().getArgumentStrokeColor());
		ConfigurationManager.getInstance().setColor("argumentFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getArgumentPrimaryColor());
		ConfigurationManager.getInstance().setColor("argumentFillSecColor", //$NON-NLS-1$
				dn.getTabs().getArgumentSecondaryColor());
		ConfigurationManager.getInstance().setFloat("argumentStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getArgumentStrokeThickness());
		ConfigurationManager.getInstance().setDimention("argumentSize", //$NON-NLS-1$
				dn.getTabs().getArgumentSize());

		ConfigurationManager.getInstance().setColor("assumptionStrokeColor", //$NON-NLS-1$
				dn.getTabs().getAssumptionStrokeColor());
		ConfigurationManager.getInstance().setColor("assumptionFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getAssumptionPrimaryColor());
		ConfigurationManager.getInstance().setColor("assumptionFillSecColor", //$NON-NLS-1$
				dn.getTabs().getAssumptionSecondaryColor());
		ConfigurationManager.getInstance().setFloat(
				"assumptionStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getAssumptionStrokeThickness());
		ConfigurationManager.getInstance().setDimention("assumptionSize", //$NON-NLS-1$
				dn.getTabs().getAssumptionSize());

		ConfigurationManager.getInstance().setColor("decisionStrokeColor", //$NON-NLS-1$
				dn.getTabs().getDecisionStrokeColor());
		ConfigurationManager.getInstance().setColor("decisionFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getDecisionPrimaryColor());
		ConfigurationManager.getInstance().setColor("decisionFillSecColor", //$NON-NLS-1$
				dn.getTabs().getDecisionSecondaryColor());
		ConfigurationManager.getInstance().setFloat("decisionStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getDecisionStrokeThickness());
		ConfigurationManager.getInstance().setDimention("decisionSize", //$NON-NLS-1$
				dn.getTabs().getDecisionSize());

		ConfigurationManager.getInstance().setColor("positionStrokeColor", //$NON-NLS-1$
				dn.getTabs().getPositionStrokeColor());
		ConfigurationManager.getInstance().setColor("positionFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getPositionPrimaryColor());
		ConfigurationManager.getInstance().setColor("positionFillSecColor", //$NON-NLS-1$
				dn.getTabs().getPositionSecondaryColor());
		ConfigurationManager.getInstance().setFloat("positionStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getPositionStrokeThickness());
		ConfigurationManager.getInstance().setDimention("positionSize", //$NON-NLS-1$
				dn.getTabs().getPositionSize());

		ConfigurationManager.getInstance().setColor("stakeholderStrokeColor", //$NON-NLS-1$
				dn.getTabs().getStakeholderStrokeColor());
		ConfigurationManager.getInstance().setColor("stakeholderFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getStakeholderPrimaryColor());
		ConfigurationManager.getInstance().setColor("stakeholderFillSecColor", //$NON-NLS-1$
				dn.getTabs().getStakeholderSecondaryColor());
		ConfigurationManager.getInstance().setFloat(
				"stakeholderStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getStakeholderStrokeThickness());
		ConfigurationManager.getInstance().setDimention("stakeholderSize", //$NON-NLS-1$
				dn.getTabs().getStakeholderSize());

		ConfigurationManager.getInstance().setColor("topicStrokeColor", //$NON-NLS-1$
				dn.getTabs().getTopicStrokeColor());
		ConfigurationManager.getInstance().setColor("topicFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getTopicPrimaryColor());
		ConfigurationManager.getInstance().setColor("topicFillSecColor", //$NON-NLS-1$
				dn.getTabs().getTopicSecondaryColor());
		ConfigurationManager.getInstance().setFloat("topicStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getTopicStrokeThickness());
		ConfigurationManager.getInstance().setDimention("topicSize", //$NON-NLS-1$
				dn.getTabs().getTopicSize());

		dn.getTabs().setRequirementSize(
				new Dimension(Integer.parseInt(dn.getTabs()
						.getRequirementWidth().trim().replaceAll(" ", "")),
						Integer.parseInt(dn.getTabs().getRequirementHeight()
								.trim().replaceAll(" ", ""))));
		ConfigurationManager.getInstance().setColor("requirementStrokeColor", //$NON-NLS-1$
				dn.getTabs().getRequirementStrokeColor());
		ConfigurationManager.getInstance().setColor("requirementFillPrimColor", //$NON-NLS-1$
				dn.getTabs().getRequirementPrimaryColor());
		ConfigurationManager.getInstance().setColor("requirementFillSecColor", //$NON-NLS-1$
				dn.getTabs().getRequirementSecondaryColor());
		ConfigurationManager.getInstance().setFloat(
				"requirementStrokeThickness", //$NON-NLS-1$
				dn.getTabs().getRequirementStrokeThickness());
		ConfigurationManager.getInstance().setDimention("requirementSize", //$NON-NLS-1$
				dn.getTabs().getRequirementSize());
	}

	/**
	 * Funkcija definise reakciju programa na pritisak entera ili "ok" dugmeta
	 * na prozoru. Rezultat dijaloga postaje potvrdan i dijalog se sakriva.
	 */
	public void enterPressed() {
		setDialogResult(true);
		setVisible(false);
	}

	/**
	 * Funkcija definise reakciju programa na pritisak escape-a ili "Cancel"
	 * dugmeta na prozoru. Rezultat dijaloga postaje odrican i dijalog se
	 * sakriva.
	 */
	public void escapePressed() {
		setDialogResult(false);
		setVisible(false);
	}

	public String getURStackSize() {
		return tfURStackSize.getText();
	}

	public void setURStackSize(String urStackSize) {
		this.tfURStackSize.setText(urStackSize);
	}

	public String getTheme() {
		return cbTheme.getSelectedItem().toString().trim() + "/"; //$NON-NLS-1$
	}

	public void setTheme(String theme) {
		this.cbTheme.setEditable(true);
		this.cbTheme.setSelectedItem(theme.replace('/', ' '));
		this.cbTheme.setEditable(false);
	}

	public String getArrowMoveStep() {
		return tfArrowMoveStep.getText();
	}

	public void setArrowMoveStep(String arrowMoveStep) {
		this.tfArrowMoveStep.setText(arrowMoveStep);
	}

	public boolean isShowGrid() {
		return chbShowGrid.isSelected();
	}

	public void setShowGrid(boolean showGrid) {
		this.chbShowGrid.setSelected(showGrid);
	}

	public boolean isAnimation() {
		return chbAnimation.isSelected();
	}

	public void setAnimation(boolean animation) {
		this.chbAnimation.setSelected(animation);
	}

	public String getTreeSize() {
		return tfTreeSize.getText();
	}

	public void setTreeSize(String treeSize) {
		this.tfTreeSize.setText(treeSize);
	}

	public String getLanguage(int index) {
		return languages[index];
	}

	public String getLanguageCurrent() {
		return cbLanguages.getSelectedItem().getClass().getName();
	}

	public void setLanguageCurrent(int index) {
		cbLanguages.setSelectedIndex(index);
	}

	public boolean isShadow() {
		return chbShadow.isSelected();
	}

	public void setShadow(boolean showShadow) {
		this.chbShadow.setSelected(showShadow);
	}

	public boolean isLaso() {
		return chbLaso.isSelected();
	}

	public void setLaso(boolean showLaso) {
		this.chbLaso.setSelected(showLaso);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}

	/**
	 * Funkcija inicijalizuje vrednosti svih polja u prozoru na osnovu vrednosti
	 * promenjivih koje sadrze vrednosti procitane iz konfiguracionog menadzera.
	 */
	public void getSettings() {
		this.setArrowMoveStep("" + arrowMoveStep); //$NON-NLS-1$
		this.setTreeSize("" + treeSize); //$NON-NLS-1$
		this.setURStackSize("" + urStackSize); //$NON-NLS-1$
		this.setShowGrid(showGrid);
		this.setTheme(theme.substring(0, theme.length() - 1));
		for (int i = 0; i != languages.length; i++)
			if (language.equals(languages[i]))
				this.setLanguageCurrent(i);
		this.setShadow(showShadow);
		this.setLaso(showLaso);
		this.setAnimation(showAnimation);
	}

	/**
	 * Funkcija na osnovu vrednosti svih polja u prozoru popunjava
	 * konfiguracioni menadzer novim vrednostima podesavanja.
	 */
	public void setSettings() {
		ConfigurationManager cm = ConfigurationManager.getInstance();
		cm.setBoolean("showGrid", isShowGrid()); //$NON-NLS-1$
		try {
			cm
					.setInt(
							"undoRedoStackSize", Integer.parseInt(getURStackSize().trim().replaceAll(" ", ""))); //$NON-NLS-1$
			cm
					.setInt(
							"arrowMoveStep", Integer.parseInt(getArrowMoveStep().trim().replaceAll(" ", ""))); //$NON-NLS-1$
			cm
					.setInt(
							"treeSize", Integer.parseInt(getTreeSize().trim().replaceAll(" ", ""))); //$NON-NLS-1$
		} catch (Exception ex) {
		}
		cm.setString("theme", getTheme()); //$NON-NLS-1$
		cm.setString("language", getLanguage((Integer.parseInt(cbLanguages
				.getSelectedItem().toString()))));
		cm.setBoolean("nodeShadow", isShadow()); //$NON-NLS-1$
		cm.setBoolean("lasoOverNodeShow", isLaso()); //$NON-NLS-1$
		cm.setColor("background", getBackgroundColor());
		cm.setColor("gridColor", getGridColor());
		cm.setBoolean("animationEnabled", isAnimation());
	}

	/**
	 * Funkcija inicijalizuje niz ikonica koji popunjava adekvatnim slikama i
	 * odredjenim redosledom
	 */
	public void initializeFlags() {
		flags = new ImageIcon[8];
		flags[0] = new ImageIcon("germ/gui/windows/images/flags/english.jpg");
		flags[1] = new ImageIcon("germ/gui/windows/images/flags/france.jpg");
		flags[2] = new ImageIcon("germ/gui/windows/images/flags/german.jpg");
		flags[3] = new ImageIcon("germ/gui/windows/images/flags/hungarian.jpg");
		flags[4] = new ImageIcon("germ/gui/windows/images/flags/italy.jpg");
		flags[5] = new ImageIcon("germ/gui/windows/images/flags/serbian.jpg");
		flags[6] = new ImageIcon("germ/gui/windows/images/flags/serbian.jpg");
		flags[7] = new ImageIcon("germ/gui/windows/images/flags/spain.jpg");

	}

	/**
	 * Funkcija popunjava Lanuage comboBox sa adekvatnim slicicama i jezicima
	 */
	public void comboFlags() {
		initializeFlags();
		Integer[] intArray = new Integer[languages.length];
		for (int i = 0; i < languages.length; i++) {
			intArray[i] = new Integer(i);
		}
		cbLanguages = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		cbLanguages.setRenderer(renderer);
	}

}
