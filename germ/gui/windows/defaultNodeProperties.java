package germ.gui.windows;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Color;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;


/**
 * Klasa predstavlja prozor za promenu defaultnih svojstava svake vrste node pojedinacno
 */
@SuppressWarnings("serial")
public class defaultNodeProperties extends JDialog {

	private Box okCancelBox = Box.createHorizontalBox();
	private JButton btnOK = new JButton(Messages.getString("defaultNodeProperties.0")); //$NON-NLS-1$
	private JButton btnCancel = new JButton(Messages.getString("defaultNodeProperties.1")); //$NON-NLS-1$
	private Tabs tabs = new Tabs();
	/**
	 * Rezultat dijaloga:  false = cancel, true = ok
	 */
	protected boolean dialogResult = false;

	public class Tabs extends JPanel {
		private JTabbedPane tpNodes = new JTabbedPane();
		private JPanel pArgument = new JPanel(new GridBagLayout());
		private JPanel pAssumption = new JPanel(new GridBagLayout());
		private JPanel pDecision = new JPanel(new GridBagLayout());
		private JPanel pPosition = new JPanel(new GridBagLayout());
		private JPanel pStakeholder = new JPanel(new GridBagLayout());
		private JPanel pTopic = new JPanel(new GridBagLayout());
		private JPanel pRequirement = new JPanel(new GridBagLayout());
		private Double items[] = new Double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };

		private JLabel lblArgumentSize = new JLabel(Messages.getString("defaultNodeProperties.2")); //$NON-NLS-1$
		private JLabel lblArgumentWidth = new JLabel(Messages.getString("defaultNodeProperties.3")); //$NON-NLS-1$
		private JFormattedTextField tfArgumentWidth;
		private JLabel lblArgumentHeight = new JLabel(Messages.getString("defaultNodeProperties.4")); //$NON-NLS-1$
		private JFormattedTextField tfArgumentHeight;
		private JLabel lblArgumentFill = new JLabel(Messages.getString("defaultNodeProperties.5")); //$NON-NLS-1$
		private JCheckBox chbArgumentGradient = new JCheckBox(Messages.getString("defaultNodeProperties.6")); //$NON-NLS-1$
		private JButton btnArgumentPrimary = new JButton(Messages.getString("defaultNodeProperties.7")); //$NON-NLS-1$
		private JButton btnArgumentSecondary = new JButton(Messages.getString("defaultNodeProperties.8")); //$NON-NLS-1$
		private JLabel lblArgumentStroke = new JLabel(Messages.getString("defaultNodeProperties.9")); //$NON-NLS-1$
		private JButton btnArgumentStrokeColor = new JButton(Messages.getString("defaultNodeProperties.10")); //$NON-NLS-1$
		private JLabel lblArgumentThickness = new JLabel(Messages.getString("defaultNodeProperties.11")); //$NON-NLS-1$
		private JComboBox cbArgumentStrokeThickness = new JComboBox(items);
		private Box argumentSizeBox = Box.createHorizontalBox();
		private Box argumentFillBox = Box.createHorizontalBox();
		private Box argumentStroke = Box.createHorizontalBox();

		private JLabel lblAssumptionSize = new JLabel(Messages.getString("defaultNodeProperties.12")); //$NON-NLS-1$
		private JLabel lblAssumptionWidth = new JLabel(Messages.getString("defaultNodeProperties.13")); //$NON-NLS-1$
		private JFormattedTextField tfAssumptionWidth;
		private JLabel lblAssumptionHeight = new JLabel(Messages.getString("defaultNodeProperties.14")); //$NON-NLS-1$
		private JFormattedTextField tfAssumptionHeight;
		private JLabel lblAssumptionFill = new JLabel(Messages.getString("defaultNodeProperties.15")); //$NON-NLS-1$
		private JCheckBox chbAssumptionGradient = new JCheckBox(Messages.getString("defaultNodeProperties.16")); //$NON-NLS-1$
		private JButton btnAssumptionPrimary = new JButton(Messages.getString("defaultNodeProperties.17")); //$NON-NLS-1$
		private JButton btnAssumptionSecondary = new JButton(Messages.getString("defaultNodeProperties.18")); //$NON-NLS-1$
		private JLabel lblAssumptionStroke = new JLabel(Messages.getString("defaultNodeProperties.19")); //$NON-NLS-1$
		private JButton btnAssumptionStrokeColor = new JButton(Messages.getString("defaultNodeProperties.20")); //$NON-NLS-1$
		private JLabel lblAssumptionThickness = new JLabel(Messages.getString("defaultNodeProperties.21")); //$NON-NLS-1$
		private JComboBox cbAssumptionStrokeThickness = new JComboBox(items);
		private Box assumptionSizeBox = Box.createHorizontalBox();
		private Box assumptionFillBox = Box.createHorizontalBox();
		private Box assumptionStroke = Box.createHorizontalBox();

		private JLabel lblDecisionSize = new JLabel(Messages.getString("defaultNodeProperties.22")); //$NON-NLS-1$
		private JLabel lblDecisionWidth = new JLabel(Messages.getString("defaultNodeProperties.23")); //$NON-NLS-1$
		private JFormattedTextField tfDecisionWidth;
		private JLabel lblDecisionHeight = new JLabel(Messages.getString("defaultNodeProperties.24")); //$NON-NLS-1$
		private JFormattedTextField tfDecisionHeight;
		private JLabel lblDecisionFill = new JLabel(Messages.getString("defaultNodeProperties.25")); //$NON-NLS-1$
		private JCheckBox chbDecisionGradient = new JCheckBox(Messages.getString("defaultNodeProperties.26")); //$NON-NLS-1$
		private JButton btnDecisionPrimary = new JButton(Messages.getString("defaultNodeProperties.27")); //$NON-NLS-1$
		private JButton btnDecisionSecondary = new JButton(Messages.getString("defaultNodeProperties.28")); //$NON-NLS-1$
		private JLabel lblDecisionStroke = new JLabel(Messages.getString("defaultNodeProperties.29")); //$NON-NLS-1$
		private JButton btnDecisionStrokeColor = new JButton(Messages.getString("defaultNodeProperties.30")); //$NON-NLS-1$
		private JLabel lblDecisionThickness = new JLabel(Messages.getString("defaultNodeProperties.31")); //$NON-NLS-1$
		private JComboBox cbDecisionStrokeThickness = new JComboBox(items);
		private Box decisionSizeBox = Box.createHorizontalBox();
		private Box decisionFillBox = Box.createHorizontalBox();
		private Box decisionStroke = Box.createHorizontalBox();

		private JLabel lblPositionSize = new JLabel(Messages.getString("defaultNodeProperties.32")); //$NON-NLS-1$
		private JLabel lblPositionWidth = new JLabel(Messages.getString("defaultNodeProperties.33")); //$NON-NLS-1$
		private JFormattedTextField tfPositionWidth;
		private JLabel lblPositionHeight = new JLabel(Messages.getString("defaultNodeProperties.34")); //$NON-NLS-1$
		private JFormattedTextField tfPositionHeight;
		private JLabel lblPositionFill = new JLabel(Messages.getString("defaultNodeProperties.35")); //$NON-NLS-1$
		private JCheckBox chbPositionGradient = new JCheckBox(Messages.getString("defaultNodeProperties.36")); //$NON-NLS-1$
		private JButton btnPositionPrimary = new JButton(Messages.getString("defaultNodeProperties.37")); //$NON-NLS-1$
		private JButton btnPositionSecondary = new JButton(Messages.getString("defaultNodeProperties.38")); //$NON-NLS-1$
		private JLabel lblPositionStroke = new JLabel(Messages.getString("defaultNodeProperties.39")); //$NON-NLS-1$
		private JButton btnPositionStrokeColor = new JButton(Messages.getString("defaultNodeProperties.40")); //$NON-NLS-1$
		private JLabel lblPositionThickness = new JLabel(Messages.getString("defaultNodeProperties.41")); //$NON-NLS-1$
		private JComboBox cbPositionStrokeThickness = new JComboBox(items);
		private Box positionSizeBox = Box.createHorizontalBox();
		private Box positionFillBox = Box.createHorizontalBox();
		private Box positionStroke = Box.createHorizontalBox();

		private JLabel lblRequirementSize = new JLabel(Messages.getString("defaultNodeProperties.42")); //$NON-NLS-1$
		private JLabel lblRequirementWidth = new JLabel(Messages.getString("defaultNodeProperties.43")); //$NON-NLS-1$
		private JFormattedTextField tfRequirementWidth;
		private JLabel lblRequirementHeight = new JLabel(Messages.getString("defaultNodeProperties.44")); //$NON-NLS-1$
		private JFormattedTextField tfRequirementHeight;
		private JLabel lblRequirementFill = new JLabel(Messages.getString("defaultNodeProperties.45")); //$NON-NLS-1$
		private JCheckBox chbRequirementGradient = new JCheckBox(Messages.getString("defaultNodeProperties.46")); //$NON-NLS-1$
		private JButton btnRequirementPrimary = new JButton(Messages.getString("defaultNodeProperties.47")); //$NON-NLS-1$
		private JButton btnRequirementSecondary = new JButton(Messages.getString("defaultNodeProperties.48")); //$NON-NLS-1$
		private JLabel lblRequirementStroke = new JLabel(Messages.getString("defaultNodeProperties.49")); //$NON-NLS-1$
		private JButton btnRequirementStrokeColor = new JButton(Messages.getString("defaultNodeProperties.50")); //$NON-NLS-1$
		private JLabel lblRequirementThickness = new JLabel(Messages.getString("defaultNodeProperties.51")); //$NON-NLS-1$
		private JComboBox cbRequirementStrokeThickness = new JComboBox(items);
		private Box requirementSizeBox = Box.createHorizontalBox();
		private Box requirementFillBox = Box.createHorizontalBox();
		private Box requirementStroke = Box.createHorizontalBox();

		private JLabel lblStakeholderSize = new JLabel(Messages.getString("defaultNodeProperties.52")); //$NON-NLS-1$
		private JLabel lblStakeholderWidth = new JLabel(Messages.getString("defaultNodeProperties.53")); //$NON-NLS-1$
		private JFormattedTextField tfStakeholderWidth;
		private JLabel lblStakeholderHeight = new JLabel(Messages.getString("defaultNodeProperties.54")); //$NON-NLS-1$
		private JFormattedTextField tfStakeholderHeight;
		private JLabel lblStakeholderFill = new JLabel(Messages.getString("defaultNodeProperties.55")); //$NON-NLS-1$
		private JCheckBox chbStakeholderGradient = new JCheckBox(Messages.getString("defaultNodeProperties.56")); //$NON-NLS-1$
		private JButton btnStakeholderPrimary = new JButton(Messages.getString("defaultNodeProperties.57")); //$NON-NLS-1$
		private JButton btnStakeholderSecondary = new JButton(Messages.getString("defaultNodeProperties.58")); //$NON-NLS-1$
		private JLabel lblStakeholderStroke = new JLabel(Messages.getString("defaultNodeProperties.59")); //$NON-NLS-1$
		private JButton btnStakeholderStrokeColor = new JButton(Messages.getString("defaultNodeProperties.60")); //$NON-NLS-1$
		private JLabel lblStakeholderThickness = new JLabel(Messages.getString("defaultNodeProperties.61")); //$NON-NLS-1$
		private JComboBox cbStakeholderStrokeThickness = new JComboBox(items);
		private Box stakeholderSizeBox = Box.createHorizontalBox();
		private Box stakeholderFillBox = Box.createHorizontalBox();
		private Box stakeholderStroke = Box.createHorizontalBox();

		private JLabel lblTopicSize = new JLabel(Messages.getString("defaultNodeProperties.62")); //$NON-NLS-1$
		private JLabel lblTopicWidth = new JLabel(Messages.getString("defaultNodeProperties.63")); //$NON-NLS-1$
		private JFormattedTextField tfTopicWidth;
		private JLabel lblTopicHeight = new JLabel(Messages.getString("defaultNodeProperties.64")); //$NON-NLS-1$
		private JFormattedTextField tfTopicHeight;
		private JLabel lblTopicFill = new JLabel(Messages.getString("defaultNodeProperties.65")); //$NON-NLS-1$
		private JCheckBox chbTopicGradient = new JCheckBox(Messages.getString("defaultNodeProperties.66")); //$NON-NLS-1$
		private JButton btnTopicPrimary = new JButton(Messages.getString("defaultNodeProperties.67")); //$NON-NLS-1$
		private JButton btnTopicSecondary = new JButton(Messages.getString("defaultNodeProperties.68")); //$NON-NLS-1$
		private JLabel lblTopicStroke = new JLabel(Messages.getString("defaultNodeProperties.69")); //$NON-NLS-1$
		private JButton btnTopicStrokeColor = new JButton(Messages.getString("defaultNodeProperties.70")); //$NON-NLS-1$
		private JLabel lblTopicThickness = new JLabel(Messages.getString("defaultNodeProperties.71")); //$NON-NLS-1$
		private JComboBox cbTopicStrokeThickness = new JComboBox(items);
		private Box topicSizeBox = Box.createHorizontalBox();
		private Box topicFillBox = Box.createHorizontalBox();
		private Box topicStroke = Box.createHorizontalBox();

		private Color argumentStrokeColor = ConfigurationManager.getInstance()
				.getColor("argumentStrokeColor"); //$NON-NLS-1$
		private Color argumentPrimaryColor = ConfigurationManager.getInstance()
				.getColor("argumentFillPrimColor"); //$NON-NLS-1$
		private Color argumentSecondaryColor = ConfigurationManager
				.getInstance().getColor("argumentFillSecColor"); //$NON-NLS-1$
		private float argumentStrokeThickness = ConfigurationManager
				.getInstance().getFloat("argumentStrokeThickness"); //$NON-NLS-1$
		private Dimension argumentSize = ConfigurationManager.getInstance()
				.getDimension("argumentSize"); //$NON-NLS-1$

		private Color assumptionStrokeColor = ConfigurationManager
				.getInstance().getColor("assumptionStrokeColor"); //$NON-NLS-1$
		private Color assumptionPrimaryColor = ConfigurationManager
				.getInstance().getColor("assumptionFillPrimColor"); //$NON-NLS-1$
		private Color assumptionSecondaryColor = ConfigurationManager
				.getInstance().getColor("assumptionFillSecColor"); //$NON-NLS-1$
		private float assumptionStrokeThickness = ConfigurationManager
				.getInstance().getFloat("assumptionStrokeThickness"); //$NON-NLS-1$
		private Dimension assumptionSize = ConfigurationManager.getInstance()
				.getDimension("assumptionSize"); //$NON-NLS-1$

		private Color decisionStrokeColor = ConfigurationManager.getInstance()
				.getColor("decisionStrokeColor"); //$NON-NLS-1$
		private Color decisionPrimaryColor = ConfigurationManager.getInstance()
				.getColor("decisionFillPrimColor"); //$NON-NLS-1$
		private Color decisionSecondaryColor = ConfigurationManager
				.getInstance().getColor("decisionFillSecColor"); //$NON-NLS-1$
		private float decisionStrokeThickness = ConfigurationManager
				.getInstance().getFloat("decisionStrokeThickness"); //$NON-NLS-1$
		private Dimension decisionSize = ConfigurationManager.getInstance()
				.getDimension("decisionSize"); //$NON-NLS-1$

		private Color positionStrokeColor = ConfigurationManager.getInstance()
				.getColor("positionStrokeColor"); //$NON-NLS-1$
		private Color positionPrimaryColor = ConfigurationManager.getInstance()
				.getColor("positionFillPrimColor"); //$NON-NLS-1$
		private Color positionSecondaryColor = ConfigurationManager
				.getInstance().getColor("positionFillSecColor"); //$NON-NLS-1$
		private float positionStrokeThickness = ConfigurationManager
				.getInstance().getFloat("positionStrokeThickness"); //$NON-NLS-1$
		private Dimension positionSize = ConfigurationManager.getInstance()
				.getDimension("positionSize"); //$NON-NLS-1$

		private Color requirementStrokeColor = ConfigurationManager
				.getInstance().getColor("requirementStrokeColor"); //$NON-NLS-1$
		private Color requirementPrimaryColor = ConfigurationManager
				.getInstance().getColor("requirementFillPrimColor"); //$NON-NLS-1$
		private Color requirementSecondaryColor = ConfigurationManager
				.getInstance().getColor("requirementFillSecColor"); //$NON-NLS-1$
		private float requirementStrokeThickness = ConfigurationManager
				.getInstance().getFloat("requirementStrokeThickness"); //$NON-NLS-1$
		private Dimension requirementSize = ConfigurationManager.getInstance()
				.getDimension("requirementSize"); //$NON-NLS-1$

		private Color stakeholderStrokeColor = ConfigurationManager
				.getInstance().getColor("stakeholderStrokeColor"); //$NON-NLS-1$
		private Color stakeholderPrimaryColor = ConfigurationManager
				.getInstance().getColor("stakeholderFillPrimColor"); //$NON-NLS-1$
		private Color stakeholderSecondaryColor = ConfigurationManager
				.getInstance().getColor("stakeholderFillSecColor"); //$NON-NLS-1$
		private float stakeholderStrokeThickness = ConfigurationManager
				.getInstance().getFloat("stakeholderStrokeThickness"); //$NON-NLS-1$
		private Dimension stakeholderSize = ConfigurationManager.getInstance()
				.getDimension("stakeholderSize"); //$NON-NLS-1$

		private Color topicStrokeColor = ConfigurationManager.getInstance()
				.getColor("topicStrokeColor"); //$NON-NLS-1$
		private Color topicPrimaryColor = ConfigurationManager.getInstance()
				.getColor("topicFillPrimColor"); //$NON-NLS-1$
		private Color topicSecondaryColor = ConfigurationManager.getInstance()
				.getColor("topicFillSecColor"); //$NON-NLS-1$
		private float topicStrokeThickness = ConfigurationManager.getInstance()
				.getFloat("topicStrokeThickness"); //$NON-NLS-1$
		private Dimension topicSize = ConfigurationManager.getInstance()
				.getDimension("topicSize"); //$NON-NLS-1$

		public Tabs() {

			tpNodes.addTab(Messages.getString("defaultNodeProperties.72"), pArgument); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.73"), pAssumption); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.109"), pDecision); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.110"), pPosition); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.111"), pRequirement); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.112"), pStakeholder); //$NON-NLS-1$
			tpNodes.addTab(Messages.getString("defaultNodeProperties.113"), pTopic); //$NON-NLS-1$
			initialiseArgumentTab();
			initialiseAssumptionTab();
			initialiseDecisionTab();
			initialisePositionTab();
			initialiseRequirementTab();
			initialiseStakeholderTab();
			initialiseTopicTab();

			add(tpNodes);
		}

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Argument.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseArgumentTab() {
			cbArgumentStrokeThickness.setPreferredSize(new Dimension(50, 20));
			cbArgumentStrokeThickness.setEditable(true);
			btnArgumentSecondary.setEnabled(false);

			btnArgumentStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getArgumentStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setArgumentStrokeColor(cw.getColor());
				}
			});

			btnArgumentPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getArgumentPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setArgumentPrimaryColor(cw.getColor());
				}
			});

			btnArgumentSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getArgumentSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setArgumentSecondaryColor(cw.getColor());
				}
			});

			cbArgumentStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setArgumentStrokeThickness(Float
								.parseFloat(cbArgumentStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.114"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.115"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbArgumentStrokeThickness
								.setSelectedItem(getArgumentStrokeThickness());
					}
				}
			});

			chbArgumentGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbArgumentGradient.isSelected())
						btnArgumentSecondary.setEnabled(true);
					else
						btnArgumentSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfArgumentWidth = new JFormattedTextField(mask0);
				tfArgumentWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfArgumentHeight = new JFormattedTextField(mask1);
				tfArgumentHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pArgument.add(lblArgumentSize, c00);

			argumentSizeBox.add(lblArgumentWidth);
			argumentSizeBox.add(Box.createHorizontalStrut(10));
			argumentSizeBox.add(tfArgumentWidth);
			argumentSizeBox.add(Box.createHorizontalStrut(20));
			argumentSizeBox.add(lblArgumentHeight);
			argumentSizeBox.add(Box.createHorizontalStrut(10));
			argumentSizeBox.add(tfArgumentHeight);

			pArgument.add(argumentSizeBox, c10);
			pArgument.add(lblArgumentFill, c01);
			pArgument.add(chbArgumentGradient, c11);

			argumentFillBox.add(btnArgumentPrimary);
			argumentFillBox.add(Box.createHorizontalStrut(20));
			argumentFillBox.add(btnArgumentSecondary);

			pArgument.add(argumentFillBox, c12);
			pArgument.add(lblArgumentStroke, c03);

			argumentStroke.add(btnArgumentStrokeColor);
			argumentStroke.add(Box.createHorizontalStrut(15));
			argumentStroke.add(lblArgumentThickness);
			argumentStroke.add(Box.createHorizontalStrut(15));
			argumentStroke.add(cbArgumentStrokeThickness);

			pArgument.add(argumentStroke, c13);
		}

		
		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Assumption.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseAssumptionTab() {

			cbAssumptionStrokeThickness.setPreferredSize(new Dimension(50, 20));
			cbAssumptionStrokeThickness.setEditable(true);
			btnAssumptionSecondary.setEnabled(false);

			btnAssumptionStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getAssumptionStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setAssumptionStrokeColor(cw.getColor());
				}
			});

			btnAssumptionPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getAssumptionPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setAssumptionPrimaryColor(cw.getColor());

				}
			});

			btnAssumptionSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getAssumptionSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setAssumptionSecondaryColor(cw.getColor());

				}
			});

			cbAssumptionStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setAssumptionStrokeThickness(Float
								.parseFloat(cbAssumptionStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.116"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.117"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbAssumptionStrokeThickness
								.setSelectedItem(getAssumptionStrokeThickness());
					}
				}
			});

			chbAssumptionGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbAssumptionGradient.isSelected())
						btnAssumptionSecondary.setEnabled(true);
					else
						btnAssumptionSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfAssumptionWidth = new JFormattedTextField(mask0);
				tfAssumptionWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfAssumptionHeight = new JFormattedTextField(mask1);
				tfAssumptionHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pAssumption.add(lblAssumptionSize, c00);

			assumptionSizeBox.add(lblAssumptionWidth);
			assumptionSizeBox.add(Box.createHorizontalStrut(10));
			assumptionSizeBox.add(tfAssumptionWidth);
			assumptionSizeBox.add(Box.createHorizontalStrut(20));
			assumptionSizeBox.add(lblAssumptionHeight);
			assumptionSizeBox.add(Box.createHorizontalStrut(10));
			assumptionSizeBox.add(tfAssumptionHeight);

			pAssumption.add(assumptionSizeBox, c10);
			pAssumption.add(lblAssumptionFill, c01);
			pAssumption.add(chbAssumptionGradient, c11);

			assumptionFillBox.add(btnAssumptionPrimary);
			assumptionFillBox.add(Box.createHorizontalStrut(20));
			assumptionFillBox.add(btnAssumptionSecondary);

			pAssumption.add(assumptionFillBox, c12);
			pAssumption.add(lblAssumptionStroke, c03);

			assumptionStroke.add(btnAssumptionStrokeColor);
			assumptionStroke.add(Box.createHorizontalStrut(15));
			assumptionStroke.add(lblAssumptionThickness);
			assumptionStroke.add(Box.createHorizontalStrut(15));
			assumptionStroke.add(cbAssumptionStrokeThickness);

			pAssumption.add(assumptionStroke, c13);

		};

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Decision.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseDecisionTab() {

			cbDecisionStrokeThickness.setPreferredSize(new Dimension(50, 20));
			cbDecisionStrokeThickness.setEditable(true);
			btnDecisionSecondary.setEnabled(false);

			btnDecisionStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getDecisionStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setDecisionStrokeColor(cw.getColor());
				}
			});

			btnDecisionPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getDecisionPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setDecisionPrimaryColor(cw.getColor());

				}
			});

			btnDecisionSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getDecisionSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setDecisionSecondaryColor(cw.getColor());

				}
			});

			cbDecisionStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setDecisionStrokeThickness(Float
								.parseFloat(cbDecisionStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.118"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.119"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbDecisionStrokeThickness
								.setSelectedItem(getDecisionStrokeThickness());
					}
				}
			});

			chbDecisionGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbDecisionGradient.isSelected())
						btnDecisionSecondary.setEnabled(true);
					else
						btnDecisionSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfDecisionWidth = new JFormattedTextField(mask0);
				tfDecisionWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfDecisionHeight = new JFormattedTextField(mask1);
				tfDecisionHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pDecision.add(lblDecisionSize, c00);

			decisionSizeBox.add(lblDecisionWidth);
			decisionSizeBox.add(Box.createHorizontalStrut(10));
			decisionSizeBox.add(tfDecisionWidth);
			decisionSizeBox.add(Box.createHorizontalStrut(20));
			decisionSizeBox.add(lblDecisionHeight);
			decisionSizeBox.add(Box.createHorizontalStrut(10));
			decisionSizeBox.add(tfDecisionHeight);

			pDecision.add(decisionSizeBox, c10);
			pDecision.add(lblDecisionFill, c01);
			pDecision.add(chbDecisionGradient, c11);

			decisionFillBox.add(btnDecisionPrimary);
			decisionFillBox.add(Box.createHorizontalStrut(20));
			decisionFillBox.add(btnDecisionSecondary);

			pDecision.add(decisionFillBox, c12);
			pDecision.add(lblDecisionStroke, c03);

			decisionStroke.add(btnDecisionStrokeColor);
			decisionStroke.add(Box.createHorizontalStrut(15));
			decisionStroke.add(lblDecisionThickness);
			decisionStroke.add(Box.createHorizontalStrut(15));
			decisionStroke.add(cbDecisionStrokeThickness);

			pDecision.add(decisionStroke, c13);
		};

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Position.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialisePositionTab() {

			cbPositionStrokeThickness.setPreferredSize(new Dimension(50, 20));
			cbPositionStrokeThickness.setEditable(true);
			btnPositionSecondary.setEnabled(false);

			btnPositionStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getPositionStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setPositionStrokeColor(cw.getColor());
				}
			});

			btnPositionPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getPositionPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setPositionPrimaryColor(cw.getColor());

				}
			});

			btnPositionSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getPositionSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setPositionSecondaryColor(cw.getColor());

				}
			});

			cbPositionStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setPositionStrokeThickness(Float
								.parseFloat(cbPositionStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.120"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.121"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbPositionStrokeThickness
								.setSelectedItem(getPositionStrokeThickness());
					}
				}
			});

			chbPositionGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbPositionGradient.isSelected())
						btnPositionSecondary.setEnabled(true);
					else
						btnPositionSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfPositionWidth = new JFormattedTextField(mask0);
				tfPositionWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfPositionHeight = new JFormattedTextField(mask1);
				tfPositionHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pPosition.add(lblPositionSize, c00);

			positionSizeBox.add(lblPositionWidth);
			positionSizeBox.add(Box.createHorizontalStrut(10));
			positionSizeBox.add(tfPositionWidth);
			positionSizeBox.add(Box.createHorizontalStrut(20));
			positionSizeBox.add(lblPositionHeight);
			positionSizeBox.add(Box.createHorizontalStrut(10));
			positionSizeBox.add(tfPositionHeight);

			pPosition.add(positionSizeBox, c10);
			pPosition.add(lblPositionFill, c01);
			pPosition.add(chbPositionGradient, c11);

			positionFillBox.add(btnPositionPrimary);
			positionFillBox.add(Box.createHorizontalStrut(20));
			positionFillBox.add(btnPositionSecondary);

			pPosition.add(positionFillBox, c12);
			pPosition.add(lblPositionStroke, c03);

			positionStroke.add(btnPositionStrokeColor);
			positionStroke.add(Box.createHorizontalStrut(15));
			positionStroke.add(lblPositionThickness);
			positionStroke.add(Box.createHorizontalStrut(15));
			positionStroke.add(cbPositionStrokeThickness);

			pPosition.add(positionStroke, c13);
		};

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Requirement.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseRequirementTab() {

			cbRequirementStrokeThickness
					.setPreferredSize(new Dimension(50, 20));
			cbRequirementStrokeThickness.setEditable(true);
			btnRequirementSecondary.setEnabled(false);

			btnRequirementStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getRequirementStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setRequirementStrokeColor(cw.getColor());
				}
			});

			btnRequirementPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getRequirementPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setRequirementPrimaryColor(cw.getColor());

				}
			});

			btnRequirementSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getRequirementSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setRequirementSecondaryColor(cw.getColor());

				}
			});

			cbRequirementStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setRequirementStrokeThickness(Float
								.parseFloat(cbRequirementStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.122"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.123"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbRequirementStrokeThickness
								.setSelectedItem(getRequirementStrokeThickness());
					}
				}
			});

			chbRequirementGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbRequirementGradient.isSelected())
						btnRequirementSecondary.setEnabled(true);
					else
						btnRequirementSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfRequirementWidth = new JFormattedTextField(mask0);
				tfRequirementWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfRequirementHeight = new JFormattedTextField(mask1);
				tfRequirementHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pRequirement.add(lblRequirementSize, c00);

			requirementSizeBox.add(lblRequirementWidth);
			requirementSizeBox.add(Box.createHorizontalStrut(10));
			requirementSizeBox.add(tfRequirementWidth);
			requirementSizeBox.add(Box.createHorizontalStrut(20));
			requirementSizeBox.add(lblRequirementHeight);
			requirementSizeBox.add(Box.createHorizontalStrut(10));
			requirementSizeBox.add(tfRequirementHeight);

			pRequirement.add(requirementSizeBox, c10);
			pRequirement.add(lblRequirementFill, c01);
			pRequirement.add(chbRequirementGradient, c11);

			requirementFillBox.add(btnRequirementPrimary);
			requirementFillBox.add(Box.createHorizontalStrut(20));
			requirementFillBox.add(btnRequirementSecondary);

			pRequirement.add(requirementFillBox, c12);
			pRequirement.add(lblRequirementStroke, c03);

			requirementStroke.add(btnRequirementStrokeColor);
			requirementStroke.add(Box.createHorizontalStrut(15));
			requirementStroke.add(lblRequirementThickness);
			requirementStroke.add(Box.createHorizontalStrut(15));
			requirementStroke.add(cbRequirementStrokeThickness);

			pRequirement.add(requirementStroke, c13);
		};

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Stakeholdera.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseStakeholderTab() {

			cbStakeholderStrokeThickness
					.setPreferredSize(new Dimension(50, 20));
			cbStakeholderStrokeThickness.setEditable(true);
			btnStakeholderSecondary.setEnabled(false);

			btnStakeholderStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getStakeholderStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setStakeholderStrokeColor(cw.getColor());
				}
			});

			btnStakeholderPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getStakeholderPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setStakeholderPrimaryColor(cw.getColor());

				}
			});

			btnStakeholderSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getStakeholderSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setStakeholderSecondaryColor(cw.getColor());

				}
			});

			cbStakeholderStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setStakeholderStrokeThickness(Float
								.parseFloat(cbStakeholderStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.124"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.125"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbStakeholderStrokeThickness
								.setSelectedItem(getStakeholderStrokeThickness());
					}
				}
			});

			chbStakeholderGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbStakeholderGradient.isSelected())
						btnStakeholderSecondary.setEnabled(true);
					else
						btnStakeholderSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfStakeholderWidth = new JFormattedTextField(mask0);
				tfStakeholderWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfStakeholderHeight = new JFormattedTextField(mask1);
				tfStakeholderHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pStakeholder.add(lblStakeholderSize, c00);

			stakeholderSizeBox.add(lblStakeholderWidth);
			stakeholderSizeBox.add(Box.createHorizontalStrut(10));
			stakeholderSizeBox.add(tfStakeholderWidth);
			stakeholderSizeBox.add(Box.createHorizontalStrut(20));
			stakeholderSizeBox.add(lblStakeholderHeight);
			stakeholderSizeBox.add(Box.createHorizontalStrut(10));
			stakeholderSizeBox.add(tfStakeholderHeight);

			pStakeholder.add(stakeholderSizeBox, c10);
			pStakeholder.add(lblStakeholderFill, c01);
			pStakeholder.add(chbStakeholderGradient, c11);

			stakeholderFillBox.add(btnStakeholderPrimary);
			stakeholderFillBox.add(Box.createHorizontalStrut(20));
			stakeholderFillBox.add(btnStakeholderSecondary);

			pStakeholder.add(stakeholderFillBox, c12);
			pStakeholder.add(lblStakeholderStroke, c03);

			stakeholderStroke.add(btnStakeholderStrokeColor);
			stakeholderStroke.add(Box.createHorizontalStrut(15));
			stakeholderStroke.add(lblStakeholderThickness);
			stakeholderStroke.add(Box.createHorizontalStrut(15));
			stakeholderStroke.add(cbStakeholderStrokeThickness);

			pStakeholder.add(stakeholderStroke, c13);
		};

		/**
		 * Funkcija vrsi inicijalizaciju taba sa svim podacima vezanim za Topic.
		 * Dodaju se svi potrebni listeneri za akcije i komponente.
		 */
		public void initialiseTopicTab() {

			cbTopicStrokeThickness.setPreferredSize(new Dimension(50, 20));
			cbTopicStrokeThickness.setEditable(true);
			btnTopicSecondary.setEnabled(false);

			btnTopicStrokeColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getTopicStrokeColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setTopicStrokeColor(cw.getColor());
				}
			});

			btnTopicPrimary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getTopicPrimaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setTopicPrimaryColor(cw.getColor());

				}
			});

			btnTopicSecondary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColorWindow cw = Application.getInstance().getMainWindow().colorWindow;
					cw.setColor(getTopicSecondaryColor());
					cw.setVisible(true);
					if (cw.isDialogResult())
						setTopicSecondaryColor(cw.getColor());

				}
			});

			cbTopicStrokeThickness.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						setTopicStrokeThickness(Float
								.parseFloat(cbTopicStrokeThickness
										.getSelectedItem().toString()));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Application.getInstance()
								.getView(),
								Messages.getString("defaultNodeProperties.126"), //$NON-NLS-1$
								Messages.getString("defaultNodeProperties.127"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						cbTopicStrokeThickness
								.setSelectedItem(getTopicStrokeThickness());
					}
				}
			});

			chbTopicGradient.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					if (chbTopicGradient.isSelected())
						btnTopicSecondary.setEnabled(true);
					else
						btnTopicSecondary.setEnabled(false);

				}
			});
			
			try{
				MaskFormatter mask0 = new MaskFormatter("##**");
				mask0.setValidCharacters("0123456789 ");
				tfTopicWidth = new JFormattedTextField(mask0);
				tfTopicWidth.setColumns(5);
				MaskFormatter mask1 = new MaskFormatter("##**");
				mask1.setValidCharacters("0123456789 ");
				tfTopicHeight = new JFormattedTextField(mask1);
				tfTopicHeight.setColumns(5);
			}catch(java.text.ParseException exc){
				
			}

			GridBagConstraints c00 = new GridBagConstraints();
			c00.gridx = 0;
			c00.gridy = 0;
			c00.anchor = GridBagConstraints.EAST;
			c00.insets = new Insets(20, 20, 0, 0);

			GridBagConstraints c10 = new GridBagConstraints();
			c10.gridx = 1;
			c10.gridy = 0;
			c10.weightx = 1;
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
			c11.anchor = GridBagConstraints.WEST;
			c11.insets = new Insets(10, 17, 0, 20);

			GridBagConstraints c12 = new GridBagConstraints();
			c12.gridx = 1;
			c12.gridy = 2;
			c12.weightx = 1;
			c12.anchor = GridBagConstraints.WEST;
			c12.insets = new Insets(10, 20, 0, 20);

			GridBagConstraints c03 = new GridBagConstraints();
			c03.gridx = 0;
			c03.gridy = 3;
			c03.anchor = GridBagConstraints.EAST;
			c03.insets = new Insets(10, 20, 20, 0);

			GridBagConstraints c13 = new GridBagConstraints();
			c13.gridx = 1;
			c13.gridy = 3;
			c13.weightx = 1;
			c13.anchor = GridBagConstraints.WEST;
			c13.insets = new Insets(10, 20, 20, 20);

			pTopic.add(lblTopicSize, c00);

			topicSizeBox.add(lblTopicWidth);
			topicSizeBox.add(Box.createHorizontalStrut(10));
			topicSizeBox.add(tfTopicWidth);
			topicSizeBox.add(Box.createHorizontalStrut(20));
			topicSizeBox.add(lblTopicHeight);
			topicSizeBox.add(Box.createHorizontalStrut(10));
			topicSizeBox.add(tfTopicHeight);

			pTopic.add(topicSizeBox, c10);
			pTopic.add(lblTopicFill, c01);
			pTopic.add(chbTopicGradient, c11);

			topicFillBox.add(btnTopicPrimary);
			topicFillBox.add(Box.createHorizontalStrut(20));
			topicFillBox.add(btnTopicSecondary);

			pTopic.add(topicFillBox, c12);
			pTopic.add(lblTopicStroke, c03);

			topicStroke.add(btnTopicStrokeColor);
			topicStroke.add(Box.createHorizontalStrut(15));
			topicStroke.add(lblTopicThickness);
			topicStroke.add(Box.createHorizontalStrut(15));
			topicStroke.add(cbTopicStrokeThickness);

			pTopic.add(topicStroke, c13);

		}

		/**
		 * Funkcija popunjava sve tabove sa adekvatnim podacima o nodovima koji su prethodno procitani iz konfiguracionog menadzera.
		 */
		public void fillAllFields() {

			setArgumentWidth("" + argumentSize.width); //$NON-NLS-1$
			setArgumentHeight("" + argumentSize.height); //$NON-NLS-1$
			boolean argumentGradient = false;
			if (!argumentPrimaryColor.equals(argumentSecondaryColor)) {
				argumentGradient = true;
				btnArgumentSecondary.setEnabled(true);
			}
			setArgumentGradient(argumentGradient);
			setCbArgumentStrokeThickness(argumentStrokeThickness);

			setAssumptionWidth("" + assumptionSize.width); //$NON-NLS-1$
			setAssumptionHeight("" + assumptionSize.height); //$NON-NLS-1$
			boolean assumptionGradient = false;
			if (!assumptionPrimaryColor.equals(assumptionSecondaryColor)) {
				assumptionGradient = true;
				btnAssumptionSecondary.setEnabled(true);
			}
			setAssumptionGradient(assumptionGradient);
			setCbAssumptionStrokeThickness(assumptionStrokeThickness);

			setDecisionWidth("" + decisionSize.width); //$NON-NLS-1$
			setDecisionHeight("" + decisionSize.height); //$NON-NLS-1$
			boolean decisionGradient = false;
			if (!decisionPrimaryColor.equals(decisionSecondaryColor)) {
				decisionGradient = true;
				btnDecisionSecondary.setEnabled(true);
			}
			setDecisionGradient(decisionGradient);
			setCbDecisionStrokeThickness(decisionStrokeThickness);

			setPositionWidth("" + positionSize.width); //$NON-NLS-1$
			setPositionHeight("" + positionSize.height); //$NON-NLS-1$
			boolean positionGradient = false;
			if (!positionPrimaryColor.equals(positionSecondaryColor)) {
				positionGradient = true;
				btnPositionSecondary.setEnabled(true);
			}
			setPositionGradient(positionGradient);
			setCbPositionStrokeThickness(positionStrokeThickness);

			setRequirementWidth("" + requirementSize.width); //$NON-NLS-1$
			setRequirementHeight("" + requirementSize.height); //$NON-NLS-1$
			boolean requirementGradient = false;
			if (!requirementPrimaryColor.equals(requirementSecondaryColor)) {
				requirementGradient = true;
				btnRequirementSecondary.setEnabled(true);
			}
			setRequirementGradient(requirementGradient);
			setCbRequirementStrokeThickness(requirementStrokeThickness);

			setStakeholderWidth("" + stakeholderSize.width); //$NON-NLS-1$
			setStakeholderHeight("" + stakeholderSize.height); //$NON-NLS-1$
			boolean stakeholderGradient = false;
			if (!stakeholderPrimaryColor.equals(stakeholderSecondaryColor)) {
				stakeholderGradient = true;
				btnStakeholderSecondary.setEnabled(true);
			}
			setStakeholderGradient(stakeholderGradient);
			setCbStakeholderStrokeThickness(stakeholderStrokeThickness);

			setTopicWidth("" + topicSize.width); //$NON-NLS-1$
			setTopicHeight("" + topicSize.height); //$NON-NLS-1$
			boolean topicGradient = false;
			if (!topicPrimaryColor.equals(topicSecondaryColor)) {
				topicGradient = true;
				btnTopicSecondary.setEnabled(true);
			}
			setTopicGradient(topicGradient);
			setCbTopicStrokeThickness(topicStrokeThickness);

		}

		public Color getArgumentStrokeColor() {
			return argumentStrokeColor;
		}

		public void setArgumentStrokeColor(Color argumentStrokeColor) {
			this.argumentStrokeColor = argumentStrokeColor;
		}

		public Color getArgumentPrimaryColor() {
			return argumentPrimaryColor;
		}

		public void setArgumentPrimaryColor(Color argumentPrimaryColor) {
			this.argumentPrimaryColor = argumentPrimaryColor;
		}

		public Color getArgumentSecondaryColor() {
			return argumentSecondaryColor;
		}

		public void setArgumentSecondaryColor(Color argumentSecondaryColor) {
			this.argumentSecondaryColor = argumentSecondaryColor;
		}

		public float getArgumentStrokeThickness() {
			return argumentStrokeThickness;
		}

		public void setArgumentStrokeThickness(float argumentStrokeThickness) {
			this.argumentStrokeThickness = argumentStrokeThickness;
		}

		public Dimension getArgumentSize() {
			return argumentSize;
		}

		public void setArgumentSize(Dimension argumentSize) {
			this.argumentSize = argumentSize;
		}

		public Color getAssumptionStrokeColor() {
			return assumptionStrokeColor;
		}

		public void setAssumptionStrokeColor(Color assumptionStrokeColor) {
			this.assumptionStrokeColor = assumptionStrokeColor;
		}

		public Color getAssumptionPrimaryColor() {
			return assumptionPrimaryColor;
		}

		public void setAssumptionPrimaryColor(Color assumptionPrimaryColor) {
			this.assumptionPrimaryColor = assumptionPrimaryColor;
		}

		public Color getAssumptionSecondaryColor() {
			return assumptionSecondaryColor;
		}

		public void setAssumptionSecondaryColor(Color assumptionSecondaryColor) {
			this.assumptionSecondaryColor = assumptionSecondaryColor;
		}

		public float getAssumptionStrokeThickness() {
			return assumptionStrokeThickness;
		}

		public void setAssumptionStrokeThickness(float assumptionStrokeThickness) {
			this.assumptionStrokeThickness = assumptionStrokeThickness;
		}

		public Dimension getAssumptionSize() {
			return assumptionSize;
		}

		public void setAssumptionSize(Dimension assumptionSize) {
			this.assumptionSize = assumptionSize;
		}

		public Color getDecisionStrokeColor() {
			return decisionStrokeColor;
		}

		public void setDecisionStrokeColor(Color decisionStrokeColor) {
			this.decisionStrokeColor = decisionStrokeColor;
		}

		public Color getDecisionPrimaryColor() {
			return decisionPrimaryColor;
		}

		public void setDecisionPrimaryColor(Color decisionPrimaryColor) {
			this.decisionPrimaryColor = decisionPrimaryColor;
		}

		public Color getDecisionSecondaryColor() {
			return decisionSecondaryColor;
		}

		public void setDecisionSecondaryColor(Color decisionSecondaryColor) {
			this.decisionSecondaryColor = decisionSecondaryColor;
		}

		public float getDecisionStrokeThickness() {
			return decisionStrokeThickness;
		}

		public void setDecisionStrokeThickness(float decisionStrokeThickness) {
			this.decisionStrokeThickness = decisionStrokeThickness;
		}

		public Dimension getDecisionSize() {
			return decisionSize;
		}

		public void setDecisionSize(Dimension decisionSize) {
			this.decisionSize = decisionSize;
		}

		public Color getPositionStrokeColor() {
			return positionStrokeColor;
		}

		public void setPositionStrokeColor(Color positionStrokeColor) {
			this.positionStrokeColor = positionStrokeColor;
		}

		public Color getPositionPrimaryColor() {
			return positionPrimaryColor;
		}

		public void setPositionPrimaryColor(Color positionPrimaryColor) {
			this.positionPrimaryColor = positionPrimaryColor;
		}

		public Color getPositionSecondaryColor() {
			return positionSecondaryColor;
		}

		public void setPositionSecondaryColor(Color positionSecondaryColor) {
			this.positionSecondaryColor = positionSecondaryColor;
		}

		public float getPositionStrokeThickness() {
			return positionStrokeThickness;
		}

		public void setPositionStrokeThickness(float positionStrokeThickness) {
			this.positionStrokeThickness = positionStrokeThickness;
		}

		public Dimension getPositionSize() {
			return positionSize;
		}

		public void setPositionSize(Dimension positionSize) {
			this.positionSize = positionSize;
		}

		public Color getRequirementStrokeColor() {
			return requirementStrokeColor;
		}

		public void setRequirementStrokeColor(Color requirementStrokeColor) {
			this.requirementStrokeColor = requirementStrokeColor;
		}

		public Color getRequirementPrimaryColor() {
			return requirementPrimaryColor;
		}

		public void setRequirementPrimaryColor(Color requirementPrimaryColor) {
			this.requirementPrimaryColor = requirementPrimaryColor;
		}

		public Color getRequirementSecondaryColor() {
			return requirementSecondaryColor;
		}

		public void setRequirementSecondaryColor(Color requirementSecondaryColor) {
			this.requirementSecondaryColor = requirementSecondaryColor;
		}

		public float getRequirementStrokeThickness() {
			return requirementStrokeThickness;
		}

		public void setRequirementStrokeThickness(
				float requirementStrokeThickness) {
			this.requirementStrokeThickness = requirementStrokeThickness;
		}

		public Dimension getRequirementSize() {
			return requirementSize;
		}

		public void setRequirementSize(Dimension requirementSize) {
			this.requirementSize = requirementSize;
		}

		public Color getStakeholderStrokeColor() {
			return stakeholderStrokeColor;
		}

		public void setStakeholderStrokeColor(Color stakeholderStrokeColor) {
			this.stakeholderStrokeColor = stakeholderStrokeColor;
		}

		public Color getStakeholderPrimaryColor() {
			return stakeholderPrimaryColor;
		}

		public void setStakeholderPrimaryColor(Color stakeholderPrimaryColor) {
			this.stakeholderPrimaryColor = stakeholderPrimaryColor;
		}

		public Color getStakeholderSecondaryColor() {
			return stakeholderSecondaryColor;
		}

		public void setStakeholderSecondaryColor(Color stakeholderSecondaryColor) {
			this.stakeholderSecondaryColor = stakeholderSecondaryColor;
		}

		public float getStakeholderStrokeThickness() {
			return stakeholderStrokeThickness;
		}

		public void setStakeholderStrokeThickness(
				float stakeholderStrokeThickness) {
			this.stakeholderStrokeThickness = stakeholderStrokeThickness;
		}

		public Dimension getStakeholderSize() {
			return stakeholderSize;
		}

		public void setStakeholderSize(Dimension stakeholderSize) {
			this.stakeholderSize = stakeholderSize;
		}

		public Color getTopicStrokeColor() {
			return topicStrokeColor;
		}

		public void setTopicStrokeColor(Color topicStrokeColor) {
			this.topicStrokeColor = topicStrokeColor;
		}

		public Color getTopicPrimaryColor() {
			return topicPrimaryColor;
		}

		public void setTopicPrimaryColor(Color topicPrimaryColor) {
			this.topicPrimaryColor = topicPrimaryColor;
		}

		public Color getTopicSecondaryColor() {
			return topicSecondaryColor;
		}

		public void setTopicSecondaryColor(Color topicSecondaryColor) {
			this.topicSecondaryColor = topicSecondaryColor;
		}

		public float getTopicStrokeThickness() {
			return topicStrokeThickness;
		}

		public void setTopicStrokeThickness(float topicStrokeThickness) {
			this.topicStrokeThickness = topicStrokeThickness;
		}

		public Dimension getTopicSize() {
			return topicSize;
		}

		public void setTopicSize(Dimension topicSize) {
			this.topicSize = topicSize;
		}

		public String getArgumentWidth() {
			return tfArgumentWidth.getText();
		}

		public void setArgumentWidth(String argumentWidth) {
			this.tfArgumentWidth.setText(argumentWidth);
		}

		public String getArgumentHeight() {
			return tfArgumentHeight.getText();
		}

		public void setArgumentHeight(String argumentHeight) {
			this.tfArgumentHeight.setText(argumentHeight);
		}

		public boolean isArgumentGradient() {
			return chbArgumentGradient.isSelected();
		}

		public void setArgumentGradient(boolean argumentGradient) {
			this.chbArgumentGradient.setSelected(argumentGradient);
		}

		public float getCbArgumentStrokeThickness() {
			return Float.parseFloat(cbArgumentStrokeThickness.getSelectedItem()
					.toString());
		}

		public void setCbArgumentStrokeThickness(float cbArgumentStrokeThickness) {
			this.cbArgumentStrokeThickness
					.setSelectedItem(cbArgumentStrokeThickness);
		}

		public String getAssumptionWidth() {
			return tfAssumptionWidth.getText();
		}

		public void setAssumptionWidth(String assumptionWidth) {
			this.tfAssumptionWidth.setText(assumptionWidth);
		}

		public String getAssumptionHeight() {
			return tfAssumptionHeight.getText();
		}

		public void setAssumptionHeight(String assumptionHeight) {
			this.tfAssumptionHeight.setText(assumptionHeight);
		}

		public boolean isAssumptionGradient() {
			return chbAssumptionGradient.isSelected();
		}

		public void setAssumptionGradient(boolean assumptionGradient) {
			this.chbAssumptionGradient.setSelected(assumptionGradient);
		}

		public float getCbAssumptionStrokeThickness() {
			return Float.parseFloat(cbAssumptionStrokeThickness
					.getSelectedItem().toString());
		}

		public void setCbAssumptionStrokeThickness(
				float cbAssumptionStrokeThickness) {
			this.cbAssumptionStrokeThickness
					.setSelectedItem(cbAssumptionStrokeThickness);
		}

		public String getDecisionWidth() {
			return tfDecisionWidth.getText();
		}

		public void setDecisionWidth(String decisionWidth) {
			this.tfDecisionWidth.setText(decisionWidth);
		}

		public String getDecisionHeight() {
			return tfDecisionHeight.getText();
		}

		public void setDecisionHeight(String decisionHeight) {
			this.tfDecisionHeight.setText(decisionHeight);
		}

		public boolean isDecisionGradient() {
			return chbDecisionGradient.isSelected();
		}

		public void setDecisionGradient(boolean decisionGradient) {
			this.chbDecisionGradient.setSelected(decisionGradient);
		}

		public float getCbDecisionStrokeThickness() {
			return Float.parseFloat(cbDecisionStrokeThickness.getSelectedItem()
					.toString());
		}

		public void setCbDecisionStrokeThickness(float cbDecisionStrokeThickness) {
			this.cbDecisionStrokeThickness
					.setSelectedItem(cbDecisionStrokeThickness);
		}

		public String getPositionWidth() {
			return tfPositionWidth.getText();
		}

		public void setPositionWidth(String positionWidth) {
			this.tfPositionWidth.setText(positionWidth);
		}

		public String getPositionHeight() {
			return tfPositionHeight.getText();
		}

		public void setPositionHeight(String positionHeight) {
			this.tfPositionHeight.setText(positionHeight);
		}

		public boolean isPositionGradient() {
			return chbPositionGradient.isSelected();
		}

		public void setPositionGradient(boolean positionGradient) {
			this.chbPositionGradient.setSelected(positionGradient);
		}

		public float getCbPositionStrokeThickness() {
			return Float.parseFloat(cbPositionStrokeThickness.getSelectedItem()
					.toString());
		}

		public void setCbPositionStrokeThickness(float cbPositionStrokeThickness) {
			this.cbPositionStrokeThickness
					.setSelectedItem(cbPositionStrokeThickness);
		}

		public String getRequirementWidth() {
			return tfRequirementWidth.getText();
		}

		public void setRequirementWidth(String requirementWidth) {
			this.tfRequirementWidth.setText(requirementWidth);
		}

		public String getRequirementHeight() {
			return tfRequirementHeight.getText();
		}

		public void setRequirementHeight(String requirementHeight) {
			this.tfRequirementHeight.setText(requirementHeight);
		}

		public boolean isRequirementGradient() {
			return chbRequirementGradient.isSelected();
		}

		public void setRequirementGradient(boolean requirementGradient) {
			this.chbRequirementGradient.setSelected(requirementGradient);
		}

		public float getCbRequirementStrokeThickness() {
			return Float.parseFloat(cbRequirementStrokeThickness
					.getSelectedItem().toString());
		}

		public void setCbRequirementStrokeThickness(
				float cbRequirementStrokeThickness) {
			this.cbRequirementStrokeThickness
					.setSelectedItem(cbRequirementStrokeThickness);
		}

		public String getStakeholderWidth() {
			return tfStakeholderWidth.getText();
		}

		public void setStakeholderWidth(String stakeholderWidth) {
			this.tfStakeholderWidth.setText(stakeholderWidth);
		}

		public String getStakeholderHeight() {
			return tfStakeholderHeight.getText();
		}

		public void setStakeholderHeight(String stakeholderHeight) {
			this.tfStakeholderHeight.setText(stakeholderHeight);
		}

		public boolean isStakeholderGradient() {
			return chbStakeholderGradient.isSelected();
		}

		public void setStakeholderGradient(boolean stakeholderGradient) {
			this.chbStakeholderGradient.setSelected(stakeholderGradient);
		}

		public float getCbStakeholderStrokeThickness() {
			return Float.parseFloat(cbStakeholderStrokeThickness
					.getSelectedItem().toString());
		}

		public void setCbStakeholderStrokeThickness(
				float cbStakeholderStrokeThickness) {
			this.cbStakeholderStrokeThickness
					.setSelectedItem(cbStakeholderStrokeThickness);
		}

		public String getTopicWidth() {
			return tfTopicWidth.getText();
		}

		public void setTopicWidth(String topicWidth) {
			this.tfTopicWidth.setText(topicWidth);
		}

		public String getTopicHeight() {
			return tfTopicHeight.getText();
		}

		public void setTopicHeight(String topicHeight) {
			this.tfTopicHeight.setText(topicHeight);
		}

		public boolean isTopicGradient() {
			return chbTopicGradient.isSelected();
		}

		public void setTopicGradient(boolean topicGradient) {
			this.chbTopicGradient.setSelected(topicGradient);
		}

		public float getCbTopicStrokeThickness() {
			return Float.parseFloat(cbTopicStrokeThickness.getSelectedItem()
					.toString());
		}

		public void setCbTopicStrokeThickness(float cbTopicStrokeThickness) {
			this.cbTopicStrokeThickness.setSelectedItem(cbTopicStrokeThickness);
		}

		/**
		 * Funkcija kupi sve podatke sa tabova i setuje promenjive na svoje nove vrednosti podesavanja.
		 * Iz ovih promenjivih se kasnije kupe podaci za popnjavanje konfiguracionog fajla novim podesavanjima.
		 */
		public void setNewDefaultSettings() {

			setArgumentSize(new Dimension(Integer.parseInt(getArgumentWidth().trim()),
					Integer.parseInt(getArgumentHeight().trim())));
			setArgumentStrokeThickness(getCbArgumentStrokeThickness());

			setDecisionSize(new Dimension(Integer.parseInt(getDecisionWidth().trim()),
					Integer.parseInt(getDecisionHeight().trim())));
			setDecisionStrokeThickness(getCbDecisionStrokeThickness());

			setAssumptionSize(new Dimension(Integer
					.parseInt(getAssumptionWidth().trim()), Integer
					.parseInt(getAssumptionHeight().trim())));
			setAssumptionStrokeThickness(getCbAssumptionStrokeThickness());

			setPositionSize(new Dimension(Integer.parseInt(getPositionWidth().trim()),
					Integer.parseInt(getPositionHeight().trim())));
			setPositionStrokeThickness(getCbPositionStrokeThickness());

			setRequirementSize(new Dimension(Integer
					.parseInt(getRequirementWidth().trim()), Integer
					.parseInt(getRequirementHeight().trim())));
			setRequirementStrokeThickness(getCbRequirementStrokeThickness());

			setStakeholderSize(new Dimension(Integer
					.parseInt(getStakeholderWidth().trim()), Integer
					.parseInt(getStakeholderHeight().trim())));
			setStakeholderStrokeThickness(getCbStakeholderStrokeThickness());

			setTopicSize(new Dimension(Integer.parseInt(getTopicWidth().trim()),
					Integer.parseInt(getTopicHeight().trim())));
			setTopicStrokeThickness(getCbTopicStrokeThickness());

		}
	}

	public defaultNodeProperties() {

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setTitle(Messages.getString("defaultNodeProperties.142")); //$NON-NLS-1$
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		tabs.fillAllFields();

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
		
		Container container = getContentPane();
		
		GridBagConstraints c00 = new GridBagConstraints();
		c00.gridx = 0;
		c00.gridy = 0;
		c00.fill = GridBagConstraints.HORIZONTAL;
		c00.anchor = GridBagConstraints.CENTER;
		c00.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints c01 = new GridBagConstraints();
		c01.gridx = 0;
		c01.gridy = 1;
		c01.weightx = 1;
		c01.anchor = GridBagConstraints.CENTER;
		c01.insets = new Insets(0, 0, 10, 0);

		container.add(tabs, c00);

		okCancelBox.add(btnOK);
		okCancelBox.add(Box.createHorizontalStrut(40));
		okCancelBox.add(btnCancel);

		container.add(okCancelBox, c01);

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
		setLocation(center.x - getSize().width / 2, center.y - getSize().height / 2);
	}

	public boolean isDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(boolean dialogResult) {
		this.dialogResult = dialogResult;
	}

	public Tabs getTabs() {
		return this.tabs;
	}
	
	/**
	 * Funkcija definise reakciju programa na pritisak entera ili "ok" dugmeta na prozoru.
	 * Rezultat dijaloga postaje potvrdan i dijalog se sakriva.
	 */
	public void enterPressed() {
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
	

}
