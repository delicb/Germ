package germ.gui.windows;

import germ.actions.ActionManager;
import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.configuration.InternalConfiguration;
import germ.gui.workspace.WorkspaceTree;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.util.Cursors;
import germ.view.GERMView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Klasa glavnog prozora aplikacije.
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	/**
	 * Referenca na objekat aplikacije.
	 */
	private Application app;
	/**
	 * Polje koje predstavlja glavni meni.
	 */
	private JMenuBar menu;
	/**
	 *File menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu fileMenu;
	/**
	 *Edit menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu editMenu;
	/**
	 * Add menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu addMenu;
	/**
	 * View menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu viewMenu;
	/**
	 * Settings menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu settingsMenu;
	/**
	 * Select menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu selectMenu;
	/**
	 * Help menu koji se nalazi u {@link MainWindow#menu} polju.
	 */
	private JMenu helpMenu;
	/**
	 * Popup meni koji se pojavljuje na kanvasu.
	 */
	private JPopupMenu popupMenu;
	/**
	 * Popup meni koji se prikazuje na desni klik na tabu.
	 */
	private JPopupMenu tabPop;
	/**
	 * Popup meni koji se prikazuje na desni klik na elementu u stablu radne
	 * fascikle.
	 */
	private JPopupMenu treePopNode;
	/**
	 * Popup meni koji se prikazuje na desni klik na dijagram u stablu radne
	 * fascikle.
	 */
	private JPopupMenu treePopDiagram;
	/**
	 * Popup meni koji se prikazuje na desni klik na projekat u stablu radne
	 * fascikle
	 */
	private JPopupMenu treePopProject;
	/**
	 * Popup meni koji se prikazuje na desni klik na radnu fasciklu u stablu
	 * radne fascikle
	 */
	private JPopupMenu treePopWorkspace;

	/**
	 * Glavni meni sa alatkama.
	 */
	private JToolBar toolbarMain;
	/**
	 * Meni sa alatkama za dodavanje elemenata i veza.
	 */
	private JToolBar toolbarElements;

	/**
	 * Panel koji predstavlja statusnu liniju.
	 */
	private JPanel statusBar;

	/**
	 * Čuva staru vrednost status bara i omogućuje vraćanje stare vrednosti
	 * preko metoda restoreStatusBar() i backupStatusBar()
	 */
	private String statusBarBackup = "";

	/**
	 * Delilac panela glavnog prozora.
	 */
	private JSplitPane spliter;

	/**
	 * Panel sa tabovima.
	 */
	private JTabbedPane tabs;

	/**
	 * Klizac za zumiranje.
	 * 
	 */
	private JSlider zoomSlide;

	/**
	 * Referenca na {@link ActionManager menadzer akcija}
	 */
	private ActionManager actionManager;
	/**
	 * Referenca na {@link GERMView GERMView} trenutnog dijagrama.
	 */
	private GERMView view;

	/**
	 * Vrednost koja predstavlja brzinu pomeranja radne povrsine po X osi pri
	 * pomeranju klizaca.
	 */
	private Integer scrollOffsetX = 0;
	/**
	 * Vrednost koja predstavlja brzinu pomeranja radne povrsine po Y osi pri
	 * pomeranju klizaca.
	 */
	private Integer scrollOffsetY = 0;
	/**
	 * Nit koja se koristi translaciju i uvecanje/umanjenje radne povrsi.
	 */
	TimerThread timerThread;

	/**
	 * Panel u okviru koga su smesteni klizaci.
	 */
	JPanel viewPanel;

	/**
	 * Niz od tri labele koji se koristi za ispisivanje poruka na status bar-u.
	 * labels[0] - labela za generalne poruke labels[1] - prikaz trenutnih
	 * koordinata misa labels[2] - labela za prikaz trenutnog stanja u kome se
	 * nalazi sistem
	 */
	private JLabel[] labels;

	public transient ColorWindow colorWindow;
	
	/**
	 * Listener klasa koja reaguje na dogadjaje koje izazivaju scroll barovi
	 */
	private class ScrollController implements AdjustmentListener {
		private int type;

		public ScrollController(int type) {
			this.type = type;
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (type == InternalConfiguration.VERTICAL_SCROLL) {
				scrollOffsetX = 0;
				scrollOffsetY = (int) ((-(e.getValue() - 25)) / Math
						.abs(getView().getTransform().getScaleY()));
			} else if (type == InternalConfiguration.HORIZONTAL_SCROLL) {
				scrollOffsetX = (int) ((-(e.getValue() - 25)) / Math
						.abs(getView().getTransform().getScaleX()));
				scrollOffsetY = 0;
			}
			timerThread.setOffset(scrollOffsetX, scrollOffsetY);
		}
	}

	/**
	 * Listener klasa koja reaguje na dogadjaje koje izaziva klizac za uvecanje.
	 */
	private class ZoomSliderController implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			timerThread.setZoomSliderValue(zoomSlide.getValue()
					/ InternalConfiguration.ZOOM_SLIDER_DIV_FACTOR);
		}
	}

	/**
	 * Listener klasa koja reaguje na dogadjaje misa na klizacima za
	 * translaciju.
	 */
	private class ScrollMouseListener extends MouseAdapter {
		JScrollBar bar;

		public ScrollMouseListener(JScrollBar bar) {
			this.bar = bar;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			bar.setValue(25);
		}
	}

	/**
	 * Listener klasa koja reaguje na dogadjaje misa na klizacima za uvecanje.
	 */
	private class ZoomSlideListener extends MouseAdapter {
		JSlider bar;

		public ZoomSlideListener(JSlider bar) {
			this.bar = bar;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			bar.setValue(0);
		}
	}

	public MainWindow(Application app, ActionManager actionManager,
			GERMView view, WorkspaceTree tree) {

		try{
			setIconImage(ImageIO.read(new File("germ/gui/windows/images/programIcon.png")));
			}catch(Exception ex){}
		this.app = app;
		this.actionManager = actionManager;
		this.view = view;
		initialiseMenu();
		initialiseToolBar();
		initialiseStatusBar();
		initializeTabs();

		colorWindow = new ColorWindow();
		tabPop = new JPopupMenu();
		tabPop.add(actionManager.getTabCloseAction());
		tabPop.add(actionManager.getTabCloseOthersAction());
		tabPop.addSeparator();
		tabPop.add(actionManager.getRenameDiagramAction());
		tabPop.add(actionManager.getDeleteDiagramAction());

		spliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		spliter.setDividerLocation(ConfigurationManager.getInstance().getInt(
				"treeSize")); //$NON-NLS-1$
		spliter.add(new JScrollPane(tree));
		spliter.add(tabs);

		getContentPane().add(spliter);

		addWindowListener(new WindowAdapter() {
			// postavlja fokus na panel:
			@Override
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				// na startovanju aplikacije je view null
				// jer ne otvaramo nista po defaultu
				if (MainWindow.this.view != null)
					MainWindow.this.view.requestFocusInWindow();
			}

			// samo javlja aplikaciji da se program zatvara
			@Override
			public void windowClosing(WindowEvent e) {
				Application.getInstance().shutdown();
			}
		});

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				// samo escape mi prosledjujemo state masini, osalo sve hvata
				// neko drugi
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					Application.getInstance().getStateMachine().keyPressed(e);
				return false;
			}
		});

		timerThread = new TimerThread(scrollOffsetX, scrollOffsetY);
		timerThread.start();

		// default cursor
		setCursor(Cursors.getCursor("default")); //$NON-NLS-1$

		// maximiziran prozor po defaultu
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}

	/**
	 * Kreira naziv taba na osnovu modela. U sustini ovo ce biti jednako
	 * m.getName(), sa dodatkom zvezdice ako je model menjan.
	 * 
	 * @param m
	 *            Model za koji se generise ime
	 */
	private String getTabName(GERMModel m) {
		return m.getName() + " " + (m.isChanged() ? "*" : "");
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public JPopupMenu getTreePopNode() {
		return treePopNode;
	}

	public JPopupMenu getTreePopDiagram() {
		return treePopDiagram;
	}

	public JPopupMenu getTreePopProject() {
		return treePopProject;
	}

	public JPopupMenu getTreePopWorkspace() {
		return treePopWorkspace;
	}

	public JSlider getZoomSlide() {
		return zoomSlide;
	}

	public JSplitPane getSpliter() {
		return spliter;
	}

	public JTabbedPane getTabs() {
		return this.tabs;
	}

	public ArrayList<GERMView> getViewsInTabs() {
		ArrayList<GERMView> views = new ArrayList<GERMView>();
		for (int i = 0; i < this.tabs.getTabCount(); i++) {
			GERMView view = (GERMView) ((JPanel) this.tabs.getComponent(i))
					.getComponent(2);
			views.add(view);
		}
		return views;
	}

	/**
	 * Ovo postoji samo zbog promene teme, da bi nakon kreiranja novog prozora
	 * bili isti tabovi otvoreni. Ne koristiti u druge svrhe jer moze da promeni
	 * dosta stvari...
	 * 
	 * @param tabs
	 */
	public void setTabs(ArrayList<GERMView> views) {
		for (GERMView view : views) {
			addDiagram(view);
		}
	}

	/**
	 * Vrsi inicijalizaciju {@link MainWindow#tabs tabs promenljive} Ukljucuje i
	 * Listener klase za tabove.
	 */
	private void initializeTabs() {
		tabs = new JTabbedPane();

		tabs.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				JPanel viewPanel = (JPanel) source.getSelectedComponent();
				GERMView view = (GERMView) viewPanel.getComponent(2);
				MainWindow.this.view = view;
				MainWindow.this.app.diagramChanged(view.getModel());
				MainWindow.this.app.getStateMachine().modelChanged();
				view.getModel().getCommandManager().refreshUndoRedoButtons(MainWindow.this.app);
				view.getModel().updateActionButtons(MainWindow.this.app);
			}
		});

		tabs.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Application.getInstance().getActionManager()
							.getDeleteDiagramAction().setDiagram(
									Application.getInstance().getModel());
					tabPop.show(tabs, e.getX(), e.getY());
				}
				if (e.getButton() == MouseEvent.BUTTON2)
					if (tabs.getComponentCount() > 1) {
						tabs.remove(tabs.getSelectedIndex());
					}
			}
		});
	}

	/**
	 * Vraca index taba koji sadrzi trazeni view...
	 * 
	 * @param view
	 *            koji se trazi
	 * @return index taba u kome je trazeni view, a ako ga nema vraca -1
	 */
	public int getTab(GERMView view) {
		Component[] components = tabs.getComponents();
		for (int i = 0; i < components.length; i++) {
			JPanel panel = (JPanel) components[i];
			if (panel.getComponent(2) == view) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Vraća trenutno aktivan GERMView
	 * 
	 * @return
	 */
	public GERMView getTab() {
		return (GERMView) ((JPanel) tabs.getSelectedComponent())
				.getComponent(2);
	}

	/**
	 * Dodaje tab sa novim dijagramom. Ako tab sa tim dijagramom vec postoji
	 * samo ga fokusira.
	 * 
	 * @param view
	 *            dijagrama koji treba dodati
	 */
	public void addDiagram(GERMView view) {
		// ako dijagram vec postoji samo ga fokusiramo
		int tab = getTab(view);
		if (tab != -1) {
			tabs.setSelectedIndex(tab);
		} else {
			JPanel viewPanel = createViewPanel(view);
			tabs.addTab(getTabName(view.getModel()), viewPanel);
			tabs.setSelectedComponent(viewPanel);
			view.getModel().getCommandManager().refreshUndoRedoButtons(MainWindow.this.app);
			view.getModel().updateActionButtons(MainWindow.this.app);
		}
	}

	private JPanel createViewPanel(GERMView view) {
		JPanel viewPanel = new JPanel(new BorderLayout());

		JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 25, 50, 0, 100);
		JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 25, 50, 0, 100);

		vbar.addAdjustmentListener(new ScrollController(
				InternalConfiguration.VERTICAL_SCROLL));
		hbar.addAdjustmentListener(new ScrollController(
				InternalConfiguration.HORIZONTAL_SCROLL));

		vbar.addMouseListener(new ScrollMouseListener(vbar));
		hbar.addMouseListener(new ScrollMouseListener(hbar));

		viewPanel.add(hbar, BorderLayout.SOUTH);
		viewPanel.add(vbar, BorderLayout.EAST);
		viewPanel.add(view);

		return viewPanel;
	}

	public void closeAllButLastTab() {
		for (int i = 0; i < tabs.getComponentCount() - 1; i++)
			tabs.remove(i);
	}

	/**
	 * Radi rename dijagrama, odnosno, samo menja ime taba.
	 * 
	 * @param newDiagram
	 */
	public void renameDiagram(GERMModel newDiagram) {
		int tab = getTab(newDiagram.getView());
		if (tab != -1) {
			tabs.setTitleAt(tab, getTabName(newDiagram));
		}
	}

	/**
	 * Vrsi inicijalizaciju promenljivih:
	 * <ul>
	 * <li>{@link MainWindow#toolbarMain}
	 * <li>{@link MainWindow#toolbarElements}
	 * <li>{@link MainWindow#zoomSlide}
	 * </ul>
	 */
	private void initialiseToolBar() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		toolbarMain = new JToolBar();
		toolbarElements = new JToolBar();

		toolbarMain.add(actionManager.getFileSaveAction());
		toolbarMain.addSeparator();
		// undo-redo
		toolbarMain.add(actionManager.getUndoAction());
		toolbarMain.add(actionManager.getRedoAction());
		toolbarMain.addSeparator();
		// cut-copy-paste-delete
		toolbarMain.add(actionManager.getCutAction());
		toolbarMain.add(actionManager.getCopyAction());
		toolbarMain.add(actionManager.getPasteAction());
		toolbarMain.add(actionManager.getDeleteAction());
		// zoom akcije
		toolbarMain.addSeparator();
		toolbarMain.add(actionManager.getZoomAction());
		toolbarMain.add(actionManager.getZoomBestFitAction());
		toolbarMain.add(actionManager.getZoomBestFitSelectionAction());
		toolbarMain.add(actionManager.getZoomLasoAction());
		toolbarMain.addSeparator();

		zoomSlide = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

		zoomSlide.addMouseListener(new ZoomSlideListener(zoomSlide));
		zoomSlide.addChangeListener(new ZoomSliderController());
		zoomSlide.setSnapToTicks(true);
		toolbarMain.addSeparator();
		toolbarMain.add(zoomSlide);
		toolbarMain.add(Box.createHorizontalGlue());

		toolbarElements.add(actionManager.getArgumentAction());
		toolbarElements.add(actionManager.getAssumptionAction());
		toolbarElements.add(actionManager.getDecisionAction());
		toolbarElements.add(actionManager.getPositionAction());
		toolbarElements.add(actionManager.getRequirementAction());
		toolbarElements.add(actionManager.getStakeholderAction());
		toolbarElements.add(actionManager.getTopicAction());
		toolbarElements.addSeparator();
		toolbarElements.add(actionManager.getLinkAction());

		toolbarMain.setAlignmentX(0);
		toolbarElements.setAlignmentX(0);

		panel.add(toolbarMain);
		panel.add(toolbarElements);

		getContentPane().add(panel, BorderLayout.NORTH);
	}

	/**
	 * Zaustavlja scroll. Sluzi da se iz spoljasnjeg izvora javi glavnom prozoru
	 * da se desio događaj zbog koga treba zaustaviti scroll.
	 */
	public void stopScroll() {
		if (timerThread.getOffsetX() != 0 || timerThread.getOffsetY() != 0) {
			timerThread.setOffset(0, 0);
			JPanel p = (JPanel) this.tabs.getComponent(getTab(getTab()));
			((JScrollBar) p.getComponent(0)).setValue(25);
			((JScrollBar) p.getComponent(1)).setValue(25);
		}
	}

	/**
	 * Vrsi inicijalizaciju statusne linije.
	 */
	private void initialiseStatusBar() {
		statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		labels = new JLabel[3];
		labels[0] = new JLabel(Messages.getString("MainWindow.2")); //$NON-NLS-1$
		labels[1] = new JLabel(" "); //$NON-NLS-1$
		labels[2] = new JLabel("<Default State>"); //$NON-NLS-1$

		statusBar.add(Box.createHorizontalStrut(5));
		statusBar.add(labels[0]);
		statusBar.add(Box.createHorizontalGlue());
		statusBar.add(labels[1]);
		statusBar.add(Box.createHorizontalGlue());
		statusBar.add(labels[2]);
		statusBar.add(Box.createHorizontalStrut(5));
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Vrsi inicijalizaciju promenljivih:
	 * <ul>
	 * <li>{@link MainWindow#menu menu},
	 * <li>{@link MainWindow#fileMenu fileMenu},
	 * <li>{@link MainWindow#editMenu editMenu},
	 * <li>{@link MainWindow#selectMenu selectMenu},
	 * <li>{@link MainWindow#viewMenu viewMenu},
	 * <li>{@link MainWindow#addMenu addMenu},
	 * <li>{@link MainWindow#settingsMenu settingsMenu},
	 * <li>{@link MainWindow#helpMenu helpMenu}
	 * </ul>
	 */
	private void initialiseMenu() {
		menu = new JMenuBar();

		fileMenu = new JMenu(Messages.getString("MainWindow.5")); //$NON-NLS-1$
		fileMenu.setMnemonic(KeyEvent.VK_F);

		editMenu = new JMenu(Messages.getString("MainWindow.6")); //$NON-NLS-1$
		editMenu.setMnemonic(KeyEvent.VK_E);

		addMenu = new JMenu(Messages.getString("MainWindow.7")); //$NON-NLS-1$
		addMenu.setMnemonic(KeyEvent.VK_D);

		viewMenu = new JMenu(Messages.getString("MainWindow.8")); //$NON-NLS-1$
		viewMenu.setMnemonic(KeyEvent.VK_V);

		selectMenu = new JMenu(Messages.getString("MainWindow.9")); //$NON-NLS-1$
		selectMenu.setMnemonic(KeyEvent.VK_S);

		settingsMenu = new JMenu(Messages.getString("MainWindow.10")); //$NON-NLS-1$
		settingsMenu.setMnemonic(KeyEvent.VK_T);

		helpMenu = new JMenu(Messages.getString("MainWindow.11")); //$NON-NLS-1$
		helpMenu.setMnemonic(KeyEvent.VK_H);

		popupMenu = new JPopupMenu();

		popupMenu.add(actionManager.getCutAction());
		popupMenu.add(actionManager.getCopyAction());
		popupMenu.add(actionManager.getPasteAction());
		popupMenu.add(actionManager.getDeleteAction());
		popupMenu.addSeparator();
		popupMenu.add(actionManager.getOpenSubDiagramAction());
		popupMenu.add(actionManager.getFlipAction());
		popupMenu.add(actionManager.getSelectLinkedAction());
		popupMenu.addSeparator();
		popupMenu.add(actionManager.getShowPropertyAction());
		popupMenu.add(actionManager.getShowColorPropertiesAction());

		initializeTreePopupMenus();

		JMenu zoomMenu = new JMenu(Messages.getString("MainWindow.12")); //$NON-NLS-1$
		zoomMenu.add(actionManager.getZoomAction());
		zoomMenu.add(actionManager.getZoomBestFitAction());
		zoomMenu.add(actionManager.getZoomBestFitSelectionAction());
		zoomMenu.add(actionManager.getZoomLasoAction());
		viewMenu.add(zoomMenu);

		JMenu alignMenu = new JMenu(Messages.getString("MainWindow.17"));
		alignMenu.add(actionManager.getAlignTopAction());
		alignMenu.add(actionManager.getAlignBottomAction());
		alignMenu.add(actionManager.getAlignLeftAction());
		alignMenu.add(actionManager.getAlignRightAction());
		alignMenu.addSeparator();
		alignMenu.add(actionManager.getDistributeHorizontalAction());
		alignMenu.add(actionManager.getDistributeVerticalAction());
		viewMenu.add(alignMenu);

		JCheckBoxMenuItem showGrid = new JCheckBoxMenuItem(actionManager
				.getSwitchShowGridAction());
		showGrid.setSelected(ConfigurationManager.getInstance().getBoolean(
				"showGrid")); //$NON-NLS-1$
		viewMenu.add(showGrid);

		addMenu.add(actionManager.getArgumentAction());
		addMenu.add(actionManager.getAssumptionAction());
		addMenu.add(actionManager.getDecisionAction());
		addMenu.add(actionManager.getPositionAction());
		addMenu.add(actionManager.getRequirementAction());
		addMenu.add(actionManager.getStakeholderAction());
		addMenu.add(actionManager.getTopicAction());
		addMenu.addSeparator();
		JMenu addLink = new JMenu(Messages.getString("MainWindow.14")); //$NON-NLS-1$
		addLink.add(actionManager.getLinkAuthorAction());
		addLink.add(actionManager.getLinkSupportAction());
		addLink.add(actionManager.getLinkInterestAction());
		addLink.add(actionManager.getLinkRefineAction());
		addLink.add(actionManager.getLinkDependencyAction());
		addMenu.add(addLink);

		editMenu.add(actionManager.getUndoAction());
		editMenu.add(actionManager.getRedoAction());
		editMenu.addSeparator();
		editMenu.add(actionManager.getFindReplaceAction());
		editMenu.addSeparator();
		editMenu.add(actionManager.getCutAction());
		editMenu.add(actionManager.getCopyAction());
		editMenu.add(actionManager.getPasteAction());
		editMenu.add(actionManager.getDeleteAction());

		selectMenu.add(actionManager.getSelectAllAction());
		selectMenu.add(actionManager.getInverseSelectionAction());
		JMenu selectAll = new JMenu(Messages.getString("MainWindow.15")); //$NON-NLS-1$
		selectAll.add(actionManager.getSelectAllArgumentsAction());
		selectAll.add(actionManager.getSelectAllAssumptionsAction());
		selectAll.add(actionManager.getSelectAllDecisionsAction());
		selectAll.add(actionManager.getSelectAllPositionsAction());
		selectAll.add(actionManager.getSelectAllRequirementsAction());
		selectAll.add(actionManager.getSelectAllStakeholdersAction());
		selectAll.add(actionManager.getSelectAllTopicsAction());
		selectMenu.add(selectAll);

		fileMenu.add(actionManager.getCreateNewProjectAction());
		fileMenu.add(actionManager.getCreateNewDiagramAction());
		fileMenu.addSeparator();
		fileMenu.add(actionManager.getFileSaveAction());
		fileMenu.addSeparator();
		fileMenu.add(actionManager.getChangeWorkspaceAction());
		fileMenu.addSeparator();
		fileMenu.add(actionManager.getImportAction());
		fileMenu.addSeparator();
		fileMenu.add(actionManager.getQuitAction());

		settingsMenu.add(actionManager.getSettingsAction());

		helpMenu.add(actionManager.getHelpAction()); //$NON-NLS-1$
		helpMenu.addSeparator();
		helpMenu.add(actionManager.getAboutAction());

		menu.add(fileMenu);
		menu.add(editMenu);
		menu.add(addMenu);
		menu.add(viewMenu);
		menu.add(selectMenu);
		menu.add(settingsMenu);
		menu.add(Box.createHorizontalGlue());
		menu.add(helpMenu);

		setJMenuBar(menu);

	}

	/**
	 * Inicijalizuje popupMenu komponente za WorkspaceTree. Inicijalizuju se
	 * atributi: treePopNode, treePopDiagram, treePopProject, treePopWorkspace
	 */
	private void initializeTreePopupMenus() {

		// inicijalizacija za treePopNode
		treePopNode = new JPopupMenu();
		// dodavanje akcija u popup menu
		treePopNode.add(actionManager.getCutAction());
		treePopNode.add(actionManager.getCopyAction());
		treePopNode.add(actionManager.getPasteAction());
		treePopNode.addSeparator();
		treePopNode.add(actionManager.getDeleteAction());
		treePopNode.addSeparator();
		treePopNode.add(actionManager.getCenterNodeAction());

		// inicijalizacija za treePopDiagram
		treePopDiagram = new JPopupMenu();
		treePopDiagram.add(actionManager.getRenameDiagramAction());
		treePopDiagram.add(actionManager.getDeleteDiagramAction());

		// inicijalizacija za treePopProject
		treePopProject = new JPopupMenu();
		treePopProject.add(actionManager.getCreateNewDiagramAction());
		treePopProject.addSeparator();
		treePopProject.add(actionManager.getRenameProjectAction());
		treePopProject.add(actionManager.getDeleteProjectAction());
		treePopProject.addSeparator();
		treePopProject.add(actionManager.getExportACtion());

		// inicijalizacija za treePopWorkspace
		treePopWorkspace = new JPopupMenu();
		treePopWorkspace.add(actionManager.getCreateNewProjectAction());
		treePopWorkspace.add(actionManager.getImportAction());
		treePopWorkspace.add(actionManager.getChangeWorkspaceAction());

	}

	/**
	 * Metoda postavlja poruku na status bar u okviru indeksirane labele.
	 * 
	 * @param message
	 *            - String koji predstavlja poruku
	 * @param index
	 *            - indeks indeksirane labele {0 - general message(levo),1 -
	 *            (srednje),2 - state(desno)}
	 */
	public void setStatusBarMessage(String message, int index) {
		if (index > 2 || index < 0)
			return;
		if (index == 2)
			labels[2].setText("<" + message + ">"); //$NON-NLS-1$ //$NON-NLS-2$
		else
			labels[index].setText(message);
	}
	
	/**
	 * Postavlja novo stablo u levi deo splitera (samo kad se menja workspace)
	 * @param newTree Novo stablo
	 */
	public void setWorkspaceTree(WorkspaceTree newTree) {
		int oldSpliterLocation = spliter.getDividerLocation();
		spliter.setLeftComponent(newTree);
		spliter.setDividerLocation(oldSpliterLocation);
		spliter.validate();
	}
	
	/**
	 * Čuva stanje prve labele status bara u statusBarBackup
	 */
	public void backupStatusBar() {
		statusBarBackup = labels[0].getText();
	}

	/**
	 * Vraća prethodno sačuvano stanje prve labele statusBara
	 */
	public void restoreStatusBar() {
		labels[0].setText(statusBarBackup);
	}

	public GERMView getView() {
		return this.view;
	}

	public JToolBar getToolbarElements() {
		return toolbarElements;
	}

	public TimerThread getTimerThread() {
		return timerThread;
	}
}
