package germ.app;

import germ.actions.ActionManager;
import germ.command.CommandManager;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.GERMSplashScreen;
import germ.gui.windows.MainWindow;
import germ.gui.windows.SaveDiagramsWindow;
import germ.gui.workspace.WorkspaceTree;
import germ.i18n.Messages;
import germ.model.Clipboard;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Requirement;
import germ.model.workspace.Project;
import germ.model.workspace.Workspace;
import germ.model.workspace.WorkspaceModel;
import germ.state.StateMachine;
import germ.view.GERMView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Application {

	/**
	 * Jedina instanca aplikacije
	 */
	private static Application instance = null;
	/**
	 * Trenutno aktivni dijagram u aplikaciji
	 */
	private GERMModel model;
	/**
	 * Menadzer svih akcija u aplikaciji
	 */
	private ActionManager actionManager;
	/**
	 * State masina aplikacije
	 */
	private StateMachine stateMachine;
	/**
	 * Clipboard aplikacije
	 */
	private Clipboard clipboard;
	/**
	 * Stablo radnog direktorijuma
	 */
	private WorkspaceTree workspaceTree;
	/**
	 * Lista projekata aktuelnog radnog direktorijuma
	 */
	private List<Project> projects;
	/**
	 * Lista svih prethodno unetih upita za Find nodova od pocetka rada aplikacije
	 */
	private ArrayList<String> previousFinds = new ArrayList<String>();
	/**
	 * Lista svih prethodno unetih upita za Replace nodova od pocetka rada aplikacije
	 */
	private ArrayList<String> previousRaplaces = new ArrayList<String>();
	/**
	 * Glavni prozor aplikacije
	 */
	private MainWindow mainWindow;

	private Application() {

		setActionManager(new ActionManager());
		setStateMachine(new StateMachine());
		setClipboard(new Clipboard());

		initializeWorkspace();

		setMainWindow(new MainWindow(this, actionManager, projects.get(0)
				.getDiagram(0).getView(), getWorkspace()));

		// postavljamo default model
		// na osnovu initializeProjects on uvek postoji
		setModel(projects.get(0).getDiagram(0));
		// jedan update performed da bi se inicijalizovao JTree
		projects.get(0).getDiagram(0).updatePerformed();

	}

	public GERMModel getModel() {
		return model;
	}

	/**
	 * Postavlja model. Pored toga javlja MainWindowu da je postavljen novi
	 * model, a u zavisnosti od toga MainWindows treba da otvori novi tab ili da
	 * selektuje vec otvoren.
	 * 
	 * @param model
	 */
	public void setModel(GERMModel model) {
		this.model = model;
		getMainWindow().addDiagram(model.getView());
		getMainWindow().setTitle(
				ConfigurationManager.getInstance().getString("programName") //$NON-NLS-1$
						+ " -> " + model); //$NON-NLS-1$
	}

	public GERMView getView() {
		return getModel().getView();
	}

	public void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	public StateMachine getStateMachine() {
		return stateMachine;
	}

	public CommandManager getCommandManager() {
		return getModel().getCommandManager();
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public WorkspaceTree getWorkspace() {
		return workspaceTree;
	}

	public void setWorkspace(WorkspaceTree workspace) {
		this.workspaceTree = workspace;
	}

	public int getProjectsCount() {
		return this.projects.size();
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	/**
	 * Vraca projekat pod trazenim imenom. Ako postoji vise projekata sa istim
	 * imenom vraca prvi.
	 * 
	 * @param name
	 *            Ime projekta
	 * @return projekat koji je trazen
	 */
	public Project getProject(String name) {
		Project project = null;
		for (int i = 0; i != getProjectsCount(); i++) {
			if (getProjects().get(i).getName().equals(name)) {
				project = getProjects().get(i);
				return project;
			}
		}
		return project;
	}

	/**
	 * Obavestava aplikaciju da je promenjen workspace kako bi odradila
	 * neophodne izmene
	 */
	public void workspaceChanged() {
		this.initializeWorkspace();
		setModel(projects.get(0).getDiagram(0));
		getMainWindow().closeAllButLastTab();
		getMainWindow().setWorkspaceTree(getWorkspace());
	}

	/**
	 * Cita sve direktorijume u workspace-u i kreira projekte od njih. Iz svih
	 * tih direktorijuma cita fajlove i kreira dijagrame od njih.
	 * 
	 * @param model
	 *            na koji dodajemo sve projekte i workspace
	 */
	private void initializeWorkspace() {
		setWorkspace(new WorkspaceTree());
		WorkspaceModel workspaceModel = new WorkspaceModel();
		getWorkspace().setModel(workspaceModel);

		projects = new ArrayList<Project>();

		// root
		Workspace root = (Workspace) getWorkspace().getModel().getRoot();
		// treba nam workspace, podesava se u konfiguraciji
		String workspacePath = ConfigurationManager.getInstance().getString(
				"workspace"); //$NON-NLS-1$
		
		File workspace = new File(workspacePath);
		if (Workspace.isValidWorkspace(workspacePath)) {
			// iteriramo kroz sve fajlove
			for (String f : workspace.list()) {
				Project p;
				try {
					File project = new File(workspace.getCanonicalPath()
							+ File.separator + f);
					if (project.isDirectory()) {
						p = new Project(f, project, getWorkspace());
						projects.add(p);
						root.addProject(p);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		// ako u workspace-u nema projekata ( novi workspace)
		// pravimo default
		if (root.getProjectsCount() == 0) {
			File newProject = new File(workspacePath + File.separator
					+ Messages.getString("Application.3")); //$NON-NLS-1$
			newProject.mkdir();
			Project p = new Project(
					Messages.getString("Application.3"), newProject, getWorkspace()); //$NON-NLS-1$
			root.addProject(p);
			projects.add(p);
		}

		// povezivanje subDiagrama
		connectSubDiagrams();
	}

	/**
	 * Dodaje novi projekat sa zadatim imenom i u njega stavlja dijagram sa
	 * zadatim imenom. Ako je bilo koje od imena prazno ime ce biti untitled
	 * 
	 * @param projectName
	 *            Ime projekta
	 * @param diagramName
	 *            Ime dijagrama
	 */
	public void addProject(String projectName, String diagramName) {

		String workspace = ConfigurationManager.getInstance().getString(
				"workspace"); //$NON-NLS-1$
		File newProjectDir = new File(workspace + File.separator + projectName);
		if (newProjectDir.exists()) {
			JOptionPane.showMessageDialog(Application.getInstance()
					.getMainWindow(), Messages.getString("Application.6")); //$NON-NLS-1$
			return;
		} else {
			if (!newProjectDir.mkdir()) {
				JOptionPane.showMessageDialog(Application.getInstance()
						.getMainWindow(), Messages.getString("Application.7")); //$NON-NLS-1$
				return;
			}
		}
		if (projectName.equals("")) { //$NON-NLS-1$
			projectName = Messages.getString("Application.9"); //$NON-NLS-1$
		}
		ArrayList<GERMModel> diagrams = new ArrayList<GERMModel>();
		Project newProject = new Project(projectName, newProjectDir, diagrams);
		projects.add(newProject);
		if (diagramName.equals("")) { //$NON-NLS-1$
			diagramName = Messages.getString("Application.11"); //$NON-NLS-1$
		}
		GERMModel d = new GERMModel(diagramName, newProject);
		d.addUpdateListener(getWorkspace());
		diagrams.add(d);
		d.save();
		Workspace root = (Workspace) getWorkspace().getModel().getRoot();
		root.addProject(newProject);
		getModel().updatePerformed();
	}

	/**
	 * Dodajep projekat na osnovu njegovog direktorijuma. Koristi se kod
	 * importa.
	 * 
	 * @param project
	 *            DIrektorijum koji se dodaje
	 */
	public void addProject(File project) {
		Project p = new Project(project.getName(), project, getWorkspace());
		projects.add(p);
		Workspace root = (Workspace) getWorkspace().getModel().getRoot();
		root.addProject(p);
		getModel().updatePerformed();
	}
	
	/**
	 * Uklanja projekat iz aplikacije
	 * @param p Projekat koji treba ukloniti
	 */
	public void removeProject(Project p) {
		Workspace root = (Workspace) getWorkspace().getModel().getRoot();
		root.removeProject(p);
		projects.remove(p);
		getModel().updatePerformed();
	}

	/**
	 * Pomocu ove metode MainWindow javlja aplikaciji da je promenjen tab i
	 * salje koji je aktuealn model, da bi svi kojima treba trenutni model mogli
	 * da dodju do njega.
	 * 
	 * @param model
	 *            Novi model
	 */
	public void diagramChanged(GERMModel model) {
		this.model = model;
	}

	/**
	 * Javlja aplikaciji da je promenjena tema. Metoda pravi novi MainWindow i
	 * novi ActionManager da bi se sve ikonice ponovo inicijalizovale.
	 */
	public void themeChanged() {
		ArrayList<GERMView> tabs = getMainWindow().getViewsInTabs();
		String title = getMainWindow().getTitle();
		getMainWindow().setVisible(false);
		actionManager = new ActionManager();
		setMainWindow(new MainWindow(this, actionManager, projects.get(0)
				.getDiagram(0).getView(), getWorkspace()));
		getMainWindow().setTabs(tabs);
		getMainWindow().setVisible(true);
		getMainWindow().setTitle(title);
	}
	
	/**
	 * Metoda kojom main windows obavestava aplikaciju da korisnik zatvara
	 * aplikaciju.
	 */
	public void shutdown() {

		ArrayList<GERMModel> unsavedDiagrams = getUnsavedDiagrams();
		if (unsavedDiagrams.size() == 0) {
			getMainWindow().getTimerThread().setRun(false);
			// sacuvamo podesavanja...
			ConfigurationManager.getInstance().writeFile();
			System.exit(0);
		}
		
		if (unsavedDiagrams.size() > 0) {
			SaveDiagramsWindow saveDiagramWindow = new SaveDiagramsWindow(
					getUnsavedDiagrams());
			saveDiagramWindow.setVisible(true);
			if (saveDiagramWindow.isDialogResult()) {
				ArrayList<GERMModel> diagramsToSave = saveDiagramWindow.getCheckedDiagrams();
				for (GERMModel m : diagramsToSave) {
					m.save();
				}
				getMainWindow().getTimerThread().setRun(false);
				// sacuvamo podesavanja...
				ConfigurationManager.getInstance().writeFile();
				System.exit(0);
			}

		}
	}
	
	/**
	 * Prolazi kroz sve dijagrame u svim projektima i trazi one koji su menjani
	 * a nisu sacuvani.
	 * 
	 * @return Sve nesacuvane dijagrame
	 */
	private ArrayList<GERMModel> getUnsavedDiagrams() {
		ArrayList<GERMModel> rez = new ArrayList<GERMModel>();
		for (Project p : projects) {
			for (int i = 0; i < p.getDiagramCount(); i++) {
				GERMModel d = p.getDiagram(i);
				if (d.isChanged())
					rez.add(d);
			}
		}
		return rez;
	}
	

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

	public ArrayList<String> getPreviousFinds() {
		return previousFinds;
	}

	public ArrayList<String> getPreviousRaplaces() {
		return previousRaplaces;
	}

	/**
	 * Ne pitajte me nista za ovu metodu :/
	 * 
	 * @param name
	 *            Naziv dijagrama
	 * @return Vraca dijagram sa imenom koji je u istom projektu kao dijagram u
	 *         kome je nod koji je pozvao ovu metodu
	 */
	private GERMModel getModelByName(String name, Node node) {
		// malo optimizacija :)
		if (!(node instanceof Requirement))
			return null;

		GERMModel m = null;
		Project project = null;
		// trazimo projekat u kom je dijagram u kome se nalazi nod
		// koji ima za sebe vezan subDijagram
		for (Project p : projects) {
			for (int i = 0; i < p.getDiagramCount(); i++) {
				GERMModel d = p.getDiagram(i);
				for (Node n : d.getNodes()) {
					// samo na requirement zanima, jer samo on moze da pozove
					// ovo
					// znam, znam, ruzno
					if (n instanceof Requirement) {
						n = (Requirement) n;
						// ovaj nod nas je pozvao
						if (n == node) {
							project = p;
						}
					}
				}
			}
		}

		// sad kad znamo projekat trazimo dijagram u njemu sa trazenim imenom
		for (int i = 0; i < project.getDiagramCount(); i++) {
			GERMModel d = null;
			d = project.getDiagram(i);
			if (name.equals(d.getName()))
				m = d;
		}
		return m;
	}

	private void connectSubDiagrams() {
		for (Project p : projects) {
			for (int i = 0; i < p.getDiagramCount(); i++) {
				GERMModel d = p.getDiagram(i);
				for (Node n : d.getNodes()) {
					if (n instanceof Requirement) {
						if (((Requirement) n).getSubDiagramName() != null) {
							GERMModel m = getModelByName(((Requirement) n)
									.getSubDiagramName(), n);
							((Requirement) n).setSubDiagram(m);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// splash ce ucitati svasta nesto
		GERMSplashScreen ss = new GERMSplashScreen();
		ss.showSplash();
	}
}
