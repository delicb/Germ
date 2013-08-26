package germ.gui.workspace;

import germ.app.Application;
import germ.gui.windows.PropertyWindow;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.event.UpdateEvent;
import germ.model.event.UpdateListener;
import germ.model.workspace.Project;
import germ.model.workspace.Workspace;
import germ.view.GERMView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class WorkspaceTree extends JTree implements UpdateListener,
		TreeSelectionListener {
	DefaultTreeModel model = (DefaultTreeModel) getModel();

	public WorkspaceTree() {
		addMouseListener(ml);
		setEditable(false);
		setCellRenderer(new CustomIconRenderer());
	}

		public void treeNodesInserted(TreeModelEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void treeNodesRemoved(TreeModelEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void treeStructureChanged(TreeModelEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
	public void valueChanged(TreeSelectionEvent e) {
		Object node = e.getPath().getLastPathComponent();
		expandPath(e.getPath());
		if (node instanceof GERMModel) {
			GERMModel m = (GERMModel) node;
			Application.getInstance().setModel(m);
			Application.getInstance().getView().repaint();
			m.updatePerformed();
		}
		if (node instanceof Node) {
			GERMModel m = Application.getInstance().getModel();
			if (m == e.getPath().getParentPath().getLastPathComponent()) {
				m.deselectAllNodes();
				m.selectNode((Node) node);
				m.updatePerformed();
			}
		}
	}
	

	@Override
	public TreePath getPathForLocation(int x, int y) {
		return super.getPathForLocation(x, y);
	}

	public void updatePerformed(UpdateEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WorkspaceTree.this.updateUI();

			}
		});
	}

	MouseListener ml = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			if (e.getClickCount() == 1) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (selPath != null) {
						Object node = selPath.getLastPathComponent();
						refreshStatusBar(node);
						if (node instanceof Node) {
							GERMModel m = Application.getInstance().getModel();
							GERMView v = Application.getInstance().getView();
							if (m == selPath.getParentPath()
									.getLastPathComponent()) {
								if (e.isControlDown()) {
									if (!m.isNodeIsSelected((Node) node)){
										m.selectNode((Node) node);
										v.centerNode((Node) node);
									}
									else
										m.deselectNode((Node) node);
								} else {
									m.deselectAllNodes();
									m.selectNode((Node) node);
									v.centerNode((Node) node);
								}
								m.updatePerformed();
							}
						}
						if (node instanceof GERMModel) {
							GERMModel m = (GERMModel) node;
							int noNodes = m.getNodeCount();
							Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("WorkspaceTree.0") + noNodes, 0); //$NON-NLS-1$
							Application.getInstance().setModel(m);
							m.updatePerformed();
						}
					}
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					if (selPath != null) {
						Object node = selPath.getLastPathComponent();
						if (node instanceof Node) {
							GERMModel m = Application.getInstance().getModel();
							if (m == selPath.getParentPath().getLastPathComponent()) {
								m.deselectNode((Node)node);
								m.removeNode((Node)node);
								updatePerformed(null);
							}
						}
					}
				} else if(e.getButton() == MouseEvent.BUTTON3){
					//otvaranje popupMenu-a na desni klik
					/*	Potrebno
					 * -enable/disable akcija u njemu
					 * -definisanje popupMenu-a
					 * */
					if (selPath != null) {
						
						//za selektovanje elementa na koji je kliknuto
						Object node = selPath.getLastPathComponent();
						refreshStatusBar(node);
						WorkspaceTree.this.setSelectionPath(selPath);
						if (node instanceof Node) {
							GERMModel m = Application.getInstance().getModel();
							if (m == selPath.getParentPath()
									.getLastPathComponent()) {
								if (e.isControlDown()) {
									if (!m.isNodeIsSelected((Node) node))
										m.selectNode((Node) node);
									else
										m.deselectNode((Node) node);
									m.updatePerformed();
								} else {
									m.deselectAllNodes();
									m.updatePerformed();
								}
							}
						}
						if(node instanceof GERMModel){
							Application.getInstance().getMainWindow().addDiagram(((GERMModel)node).getView());
						}
						
						//za pogodjen Node prikaz menu-a
						if (node instanceof Node){
							//uzimanje popup menu
							JPopupMenu pop = Application.getInstance().getMainWindow().getTreePopNode();
							//disable/enable
							if(Application.getInstance().getClipboard().isEmpty())
								pop.getComponents()[2].setEnabled(false);
							else
								pop.getComponents()[2].setEnabled(true);
							
							if((GERMModel)selPath.getParentPath().getLastPathComponent() != Application.getInstance().getModel()){
								pop.getComponents()[0].setEnabled(false);
								pop.getComponents()[1].setEnabled(false);
								pop.getComponents()[4].setEnabled(false);
							}
							else{
								pop.getComponents()[0].setEnabled(true);
								pop.getComponents()[1].setEnabled(true);
								pop.getComponents()[4].setEnabled(true);
							}
							
							//za akciju CenterNodeAction je potrebno postaviti diagram i node
							//prikaz menija
							GERMModel model = (GERMModel)selPath.getParentPath().getLastPathComponent();
							Node n = (Node)node;
							Application.getInstance().getActionManager().getCenterNodeAction().setCenterData(model, n);
							// zbog bug-a sa center node ako dijagram nije otvoren
							Application.getInstance().setModel(model);
							model.selectNode(n);
							pop.show(WorkspaceTree.this, e.getX(), e.getY());
						}
						
						//za pogodjen Dijagram prikaz menu-a
						if(node instanceof GERMModel){
							//uzimanje popup menu
							JPopupMenu pop = Application.getInstance().getMainWindow().getTreePopDiagram();
							//setuje dijagram potrebnim akcijama
							Application.getInstance().getActionManager().getDeleteDiagramAction().setDiagram((GERMModel)node);
							//prikaz menija
							pop.show(WorkspaceTree.this, e.getX(), e.getY());
						}
						
						//za pogodjen Projekat
						if(node instanceof Project){
							//uzimanje popup menu
							JPopupMenu pop = Application.getInstance().getMainWindow().getTreePopProject();
							//setuje project za akcije kojima je to potrebno
							//CreateNewDiagram, RenameDiagram
							Application.getInstance().getActionManager().getRenameProjectAction().setProject((Project)node);
							Application.getInstance().getActionManager().getCreateNewDiagramAction().setProject((Project)node);
							Application.getInstance().getActionManager().getExportACtion().setProjectToExport((Project)node);
							Application.getInstance().getActionManager().getDeleteProjectAction().setProject((Project)node);
							//prikaz menija
							pop.show(WorkspaceTree.this, e.getX(), e.getY());
						}
						
						//za pogodjen workspace
						if(node instanceof Workspace){
							//uzimanje popup menu
							JPopupMenu pop = Application.getInstance().getMainWindow().getTreePopWorkspace();
							// disable/enable
							
							//prikaz menija
							pop.show(WorkspaceTree.this, e.getX(), e.getY());
						}
					}
				}
			}
			if (e.getClickCount() == 2) {
				if (selPath != null) {
					Object node = selPath.getLastPathComponent();
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (node instanceof Node) {
							GERMModel m = Application.getInstance().getModel();
							if (m == selPath.getParentPath()
									.getLastPathComponent()) {
								PropertyWindow pw = ((Node) node)
										.getPropertyWindow();
								pw.setVisible(true);
								((Node) node)
										.setProperties(pw.isDialogResult());

								m.updatePerformed();

							}
						}
					}
				}
			}
		}
	};

	@Override
	public void updateUI() {
		super.updateUI();
	}
	
	/**Metoda koja postavlja poruku u statusnu liniju u zavisnosti od selektovanog elementa na 
	 * stablu radne fascikle.
	 * 
	 * @param node element na stablu koji je selektovan
	 */
	public void refreshStatusBar(Object node){
		if (node instanceof GERMModel) {
			GERMModel m = (GERMModel) node;
			int noNodes = m.getNodeCount();
			Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("WorkspaceTree.0") + noNodes, 0); //$NON-NLS-1$
			Application.getInstance().setModel(m);
			Application.getInstance().getView().repaint();
		}
		else if (node instanceof Project){
			Project p = (Project) node;
			int noDiagrams = p.getDiagramCount();
			int noNodes = 0;
			for(int i = 0; i != p.getDiagramCount(); i++){
				noNodes += p.getDiagram(i).getNodeCount();
			}
			Application.getInstance().getMainWindow().setStatusBarMessage(Messages.getString("WorkspaceTree.2") + noDiagrams + Messages.getString("WorkspaceTree.3")+ noNodes, 0); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if (node instanceof Workspace){
			Workspace w = (Workspace) node;
			int noNodes = 0;
			int noDiagrams = 0;
			int noProjects = w.getProjectsCount();
			for(int i = 0; i != noProjects; i++){
				noDiagrams += w.getProject(i).getDiagramCount();
				for(int j = 0; j != w.getProject(i).getDiagramCount(); j++)
					noNodes += w.getProject(i).getDiagram(j).getNodeCount();
			}
			String message = Messages.getString("WorkspaceTree.4") + noProjects + Messages.getString("WorkspaceTree.5") + noDiagrams + Messages.getString("WorkspaceTree.3") + noNodes; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Application.getInstance().getMainWindow().setStatusBarMessage(message, 0);
		}
	}
}
