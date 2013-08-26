package germ.model.workspace;

import germ.configuration.ConfigurationManager;
import germ.i18n.Messages;
import germ.util.Cursors;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.tree.TreeNode;

public class Workspace implements TreeNode {

	private ArrayList<Project> projects = new ArrayList<Project>();

	public Workspace() {
	}

	public String toString() {
		return Messages.getString("Workspace.0"); //$NON-NLS-1$
	}

	public void addProject(Project diagram) {
		projects.add(diagram);
	}
	
	public void removeProject(Project p) {
		projects.remove(p);
	}

	public Project getProject(int index) {
		return projects.get(index);
	}

	public int getProjectIndex(Project project) {
		return projects.indexOf(project);
	}

	public int getProjectsCount() {
		return projects.size();
	}

	public boolean isLeaf() {
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		return getProjectsCount();
	}

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressWarnings("unchecked")
    public Enumeration children() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String workspaceChooser(Component parent) {
		String currentWorkspace = ConfigurationManager.getInstance().getString(
				"workspace"); //$NON-NLS-1$
		JFileChooser workspaceChooser;
		if (isValidWorkspace(currentWorkspace)) {
			File w = new File(currentWorkspace);
			workspaceChooser = new JFileChooser(w);
		}
		else {
			workspaceChooser = new JFileChooser(new File(System.getProperty("user.home"))); //$NON-NLS-1$
		}
		workspaceChooser.setDialogTitle(Messages.getString("Workspace.3")); //$NON-NLS-1$
		workspaceChooser.setCursor(Cursors.getCursor("default"));
		workspaceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (workspaceChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			String workspace = workspaceChooser.getSelectedFile()
					.getAbsolutePath();
			return workspace;
		}
		return null;
	}

	public static boolean isValidWorkspace(String workspace) {
		if (workspace == null)
			return false;
		File w = new File(workspace);
		return w.exists() && w.isDirectory();
	}
}
