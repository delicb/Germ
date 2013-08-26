package germ.gui.windows;

import germ.i18n.Messages;
import germ.util.Cursors;
import germ.util.URLOpener;

import java.awt.Container;
import java.awt.Cursor;
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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Klasa reprezentuje prozor sa informacijama o programu i njegovim autorima i
 * pomagacima na projektu
 */
@SuppressWarnings("serial")
public class AboutWindow extends JDialog {

	private JLabel lblGERMProgram = new JLabel("*GERM*"); //$NON-NLS-1$
	private JLabel lblInfo = new JLabel(Messages.getString("AboutWindow.0")); //$NON-NLS-1$
	private JLabel lblLink = new JLabel("http://www.germ.tk");
	private JLabel lblDelInfo = new JLabel(
			"<HTML>Bojan Delić e11510<BR>Mail: bojan@delic.in.rs</HTML>"); //$NON-NLS-1$
	private JLabel lblWikiInfo = new JLabel(
			"<HTML>Viktor Bek e11482<BR>Mail: wiktorns@yahoo.com</HTML>"); //$NON-NLS-1$
	private JLabel lblZeljoInfo = new JLabel(
			"<HTML>Željko Vrbaški e11442<BR>Mail: vrbaskiz@gmail.com</HTML>"); //$NON-NLS-1$
	private JLabel lblCacheInfo = new JLabel(
			"<HTML>Dušan Krivošija e11484<BR>Mail: dusankrivosija@gmail.com</HTML>"); //$NON-NLS-1$
	private JLabel lblDelPicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/bojan.jpg")); //$NON-NLS-1$
	private JLabel lblWikiPicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/viktor.jpg")); //$NON-NLS-1$
	private JLabel lblZeljoPicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/zeljko.jpg")); //$NON-NLS-1$
	private JLabel lblCachePicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/dusan.jpg")); //$NON-NLS-1$
	private JLabel lblGERMPicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/novi.png")); //$NON-NLS-1$
	private JLabel lblGERMTeamPicture = new JLabel(new ImageIcon(
			"germ/gui/windows/images/GERMTeam.jpg")); //$NON-NLS-1$
	private JButton btnClose = new JButton(Messages.getString("AboutWindow.1")); //$NON-NLS-1$
	private Box boxDel = Box.createHorizontalBox();
	private Box boxWiki = Box.createHorizontalBox();
	private Box boxZeljo = Box.createHorizontalBox();
	private Box boxCache = Box.createHorizontalBox();
	private Dimension pctSize = new Dimension(80, 80);
	private JTabbedPane tabs = new JTabbedPane();
	private JPanel pGerm = new JPanel(new GridBagLayout());
	private JPanel pTeam = new JPanel(new GridBagLayout());
	private JPanel pContributors = new JPanel(new GridBagLayout());
	private JLabel lblThanks = new JLabel(Messages.getString("AboutWindow.12")); //$NON-NLS-1$
	private JLabel lblPerson1 = new JLabel("Marija Vranešević"); //$NON-NLS-1$
	private JLabel lblPerson2 = new JLabel("Ana Ćosić"); //$NON-NLS-1$
	private JLabel lblPerson3 = new JLabel("Ervin Senji"); //$NON-NLS-1$
	private JLabel lblPerson4 = new JLabel("Jelena Kovačević"); //$NON-NLS-1$
	private JLabel lblPerson5 = new JLabel("Marija Vranešević"); //$NON-NLS-1$
	private JLabel lblPerson6 = new JLabel("Ervin Senji"); //$NON-NLS-1$
	private JLabel lblPerson7 = new JLabel("dr. Iskra Biller");
	private JLabel lblTranslation1 = new JLabel(Messages
			.getString("AboutWindow.19")); //$NON-NLS-1$
	private JLabel lblTranslation2 = new JLabel(Messages
			.getString("AboutWindow.20")); //$NON-NLS-1$
	private JLabel lblTranslation3 = new JLabel(Messages
			.getString("AboutWindow.21")); //$NON-NLS-1$
	private JLabel lblTranslation4 = new JLabel(Messages
			.getString("AboutWindow.22")); //$NON-NLS-1$
	private JLabel lblTranslation5 = new JLabel(Messages
			.getString("AboutWindow.23")); //$NON-NLS-1$
	private JLabel lblTranslation6 = new JLabel(Messages
			.getString("AboutWindow.2"));
	private JLabel lblDesign = new JLabel(Messages.getString("AboutWindow.24")); //$NON-NLS-1$

	public AboutWindow() {

		try {
			setIconImage(ImageIO.read(new File(
					"germ/gui/windows/images/programIcon.png")));
		} catch (Exception ex) {
		}
		setTitle(Messages.getString("AboutWindow.25")); //$NON-NLS-1$
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLayout(new GridBagLayout());
		setResizable(false);
		initializeBoxes();
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escapePressed();
			}
		});

		tabs.addTab("GERM", pGerm); //$NON-NLS-1$
		tabs.addTab("Team", pTeam); //$NON-NLS-1$
		tabs.addTab("Contributors", pContributors); //$NON-NLS-1$

		initializeContributors();

		GridBagConstraints g0 = new GridBagConstraints();
		g0.gridx = 0;
		g0.gridy = 0;
		g0.anchor = GridBagConstraints.CENTER;
		g0.fill = GridBagConstraints.HORIZONTAL;
		g0.weightx = 1;
		g0.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 0;
		g1.gridy = 1;
		g1.anchor = GridBagConstraints.CENTER;
		g1.weightx = 1;
		g1.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints g2 = new GridBagConstraints();
		g2.gridx = 0;
		g2.gridy = 2;
		g2.anchor = GridBagConstraints.CENTER;
		g2.weightx = 1;
		g2.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints g3 = new GridBagConstraints();
		g3.gridx = 0;
		g3.gridy = 3;
		g3.anchor = GridBagConstraints.CENTER;
		g3.weightx = 1;
		g3.insets = new Insets(0, 20, 10, 20);

		pGerm.add(lblGERMPicture, g0);
		pGerm.add(lblGERMProgram, g1);
		pGerm.add(lblInfo, g2);
		pGerm.add(lblLink, g3);

		lblLink.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() > 0) {
					// Runtime.getRuntime().exec(
					// "cmd.exe /c start http://www.germ.tk");
					URLOpener.openURL("http://www.germ.tk");
				}
			}
		});

		lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		GridBagConstraints t0 = new GridBagConstraints();
		t0.gridx = 0;
		t0.gridy = 0;
		t0.gridwidth = 2;
		t0.weightx = 1;
		t0.fill = GridBagConstraints.HORIZONTAL;
		t0.anchor = GridBagConstraints.WEST;
		t0.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints t1 = new GridBagConstraints();
		t1.gridx = 0;
		t1.gridy = 1;
		t1.fill = GridBagConstraints.HORIZONTAL;
		t1.anchor = GridBagConstraints.WEST;
		t1.insets = new Insets(10, 20, 0, 0);

		GridBagConstraints t2 = new GridBagConstraints();
		t2.gridx = 1;
		t2.gridy = 1;
		t2.fill = GridBagConstraints.HORIZONTAL;
		t2.anchor = GridBagConstraints.EAST;
		t2.insets = new Insets(10, 20, 0, 20);

		GridBagConstraints t3 = new GridBagConstraints();
		t3.gridx = 0;
		t3.gridy = 2;
		t3.fill = GridBagConstraints.HORIZONTAL;
		t3.anchor = GridBagConstraints.WEST;
		t3.insets = new Insets(0, 20, 10, 0);

		GridBagConstraints t4 = new GridBagConstraints();
		t4.gridx = 1;
		t4.gridy = 2;
		t4.fill = GridBagConstraints.HORIZONTAL;
		t4.anchor = GridBagConstraints.EAST;
		t4.insets = new Insets(0, 20, 10, 20);

		lblGERMTeamPicture.setMaximumSize(new Dimension(200, 80));
		pTeam.add(lblGERMTeamPicture, t0);
		pTeam.add(boxZeljo, t1);
		pTeam.add(boxDel, t2);
		pTeam.add(boxCache, t3);
		pTeam.add(boxWiki, t4);

		Container container = getContentPane();

		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = 0;
		c0.anchor = GridBagConstraints.WEST;
		c0.insets = new Insets(20, 20, 0, 20);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 1;
		c1.weightx = 1;
		c1.anchor = GridBagConstraints.EAST;
		c1.insets = new Insets(20, 30, 20, 20);

		container.add(tabs, c0);
		container.add(btnClose, c1);

		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$

		this.pack();
		setLocation(center.x - getSize().width / 2, center.y - getSize().height
				/ 2);

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
	}

	/**
	 * Funkcija popunjava horizontalBox-ove sa adektvatnim slikama i
	 * informacijama o autorima programa.
	 */
	public void initializeBoxes() {
		lblZeljoPicture.setMaximumSize(pctSize);
		lblWikiPicture.setMaximumSize(pctSize);
		lblCachePicture.setMaximumSize(pctSize);
		lblDelPicture.setMaximumSize(pctSize);

		boxZeljo.add(lblZeljoPicture);
		boxZeljo.add(Box.createHorizontalStrut(15));
		boxZeljo.add(lblZeljoInfo);

		boxWiki.add(lblWikiInfo);
		boxWiki.add(Box.createHorizontalStrut(15));
		boxWiki.add(lblWikiPicture);

		boxCache.add(lblCachePicture);
		boxCache.add(Box.createHorizontalStrut(15));
		boxCache.add(lblCacheInfo);

		boxDel.add(lblDelInfo);
		boxDel.add(Box.createHorizontalStrut(15));
		boxDel.add(lblDelPicture);
	}

	/**
	 * Funkcija inicijalizuje sve potrebne gridBagConstraints-ove i popunjava
	 * tab "Contributors"
	 */
	public void initializeContributors() {

		// Thanks
		GridBagConstraints th0 = new GridBagConstraints();
		th0.gridx = 0;
		th0.gridy = 0;
		th0.gridwidth = 3;
		th0.anchor = GridBagConstraints.WEST;
		th0.insets = new Insets(0, 20, 20, 20);

		// Person1
		GridBagConstraints th01 = new GridBagConstraints();
		th01.gridx = 0;
		th01.gridy = 1;
		th01.anchor = GridBagConstraints.WEST;
		th01.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th11 = new GridBagConstraints();
		th11.gridx = 1;
		th11.gridy = 1;
		th11.anchor = GridBagConstraints.WEST;
		th11.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th21 = new GridBagConstraints();
		th21.gridx = 2;
		th21.gridy = 1;
		th21.anchor = GridBagConstraints.WEST;
		th21.insets = new Insets(5, 20, 0, 20);

		// Person2
		GridBagConstraints th02 = new GridBagConstraints();
		th02.gridx = 0;
		th02.gridy = 2;
		th02.anchor = GridBagConstraints.WEST;
		th02.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th12 = new GridBagConstraints();
		th12.gridx = 1;
		th12.gridy = 2;
		th12.anchor = GridBagConstraints.WEST;
		th12.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th22 = new GridBagConstraints();
		th22.gridx = 2;
		th22.gridy = 2;
		th22.anchor = GridBagConstraints.WEST;
		th22.insets = new Insets(5, 20, 0, 20);

		// Person3
		GridBagConstraints th03 = new GridBagConstraints();
		th03.gridx = 0;
		th03.gridy = 3;
		th03.anchor = GridBagConstraints.WEST;
		th03.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th13 = new GridBagConstraints();
		th13.gridx = 1;
		th13.gridy = 3;
		th13.anchor = GridBagConstraints.WEST;
		th13.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th23 = new GridBagConstraints();
		th23.gridx = 2;
		th23.gridy = 3;
		th23.anchor = GridBagConstraints.WEST;
		th23.insets = new Insets(5, 20, 0, 20);

		// Person4
		GridBagConstraints th04 = new GridBagConstraints();
		th04.gridx = 0;
		th04.gridy = 4;
		th04.anchor = GridBagConstraints.WEST;
		th04.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th14 = new GridBagConstraints();
		th14.gridx = 1;
		th14.gridy = 4;
		th14.anchor = GridBagConstraints.WEST;
		th14.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th24 = new GridBagConstraints();
		th24.gridx = 2;
		th24.gridy = 4;
		th24.anchor = GridBagConstraints.WEST;
		th24.insets = new Insets(5, 20, 0, 20);

		// Person5
		GridBagConstraints th05 = new GridBagConstraints();
		th05.gridx = 0;
		th05.gridy = 5;
		th05.anchor = GridBagConstraints.WEST;
		th05.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th15 = new GridBagConstraints();
		th15.gridx = 1;
		th15.gridy = 5;
		th15.anchor = GridBagConstraints.WEST;
		th15.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th25 = new GridBagConstraints();
		th25.gridx = 2;
		th25.gridy = 5;
		th25.anchor = GridBagConstraints.WEST;
		th25.insets = new Insets(5, 20, 0, 20);

		// Person6
		GridBagConstraints th06 = new GridBagConstraints();
		th06.gridx = 0;
		th06.gridy = 6;
		th06.anchor = GridBagConstraints.WEST;
		th06.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th16 = new GridBagConstraints();
		th16.gridx = 1;
		th16.gridy = 6;
		th16.anchor = GridBagConstraints.WEST;
		th16.insets = new Insets(5, 20, 0, 20);
		GridBagConstraints th26 = new GridBagConstraints();
		th26.gridx = 2;
		th26.gridy = 6;
		th26.anchor = GridBagConstraints.WEST;
		th26.insets = new Insets(5, 20, 0, 20);

		// Separator
		GridBagConstraints th7 = new GridBagConstraints();
		th7.gridx = 0;
		th7.gridy = 7;
		th7.gridwidth = 1;
		th7.anchor = GridBagConstraints.WEST;
		th7.insets = new Insets(15, 20, 15, 20);

		// Person7
		GridBagConstraints th08 = new GridBagConstraints();
		th08.gridx = 0;
		th08.gridy = 8;
		th08.weightx = 1;
		th08.anchor = GridBagConstraints.WEST;
		th08.insets = new Insets(0, 20, 0, 20);
		GridBagConstraints th18 = new GridBagConstraints();
		th18.gridx = 1;
		th18.gridy = 8;
		th18.weightx = 1;
		th18.anchor = GridBagConstraints.WEST;
		th18.insets = new Insets(0, 20, 0, 20);
		GridBagConstraints th28 = new GridBagConstraints();
		th28.gridx = 2;
		th28.gridy = 8;
		th28.weightx = 3;
		th28.anchor = GridBagConstraints.WEST;
		th28.insets = new Insets(0, 20, 0, 20);

		pContributors.add(lblThanks, th0);
		pContributors.add(lblPerson1, th01);
		pContributors.add(new JLabel(" - "), th11); //$NON-NLS-1$
		pContributors.add(lblTranslation1, th21);
		pContributors.add(lblPerson5, th02);
		pContributors.add(new JLabel(" - "), th12); //$NON-NLS-1$
		pContributors.add(lblTranslation2, th22);
		pContributors.add(lblPerson2, th03);
		pContributors.add(new JLabel(" - "), th13); //$NON-NLS-1$
		pContributors.add(lblTranslation3, th23);
		pContributors.add(lblPerson3, th04);
		pContributors.add(new JLabel(" - "), th14); //$NON-NLS-1$
		pContributors.add(lblTranslation5, th24);
		pContributors.add(lblPerson4, th05);
		pContributors.add(new JLabel(" - "), th15); //$NON-NLS-1$
		pContributors.add(lblTranslation4, th25);
		pContributors.add(lblPerson7, th06);
		pContributors.add(new JLabel(" - "), th16); //$NON-NLS-1$
		pContributors.add(lblTranslation6, th26);
		pContributors.add(new JLabel("***************"), th7); //$NON-NLS-1$
		pContributors.add(lblPerson6, th08);
		pContributors.add(new JLabel(" - "), th18); //$NON-NLS-1$
		pContributors.add(lblDesign, th28);

	}

	/**
	 * Funkcija definise reakciju prozora na esc dugme na tastaturi ili pak
	 * pritisak na dugme "Close". Prozor se sakriva.
	 */
	public void escapePressed() {
		setVisible(false);
	}

}
