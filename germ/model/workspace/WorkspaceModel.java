package germ.model.workspace;

import germ.model.GERMModel;
import germ.model.Node;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class WorkspaceModel extends DefaultTreeModel {

	public WorkspaceModel(){
		super(new Workspace());
	}
	
	public Object getChild(Object parent, int index) {
		if(parent instanceof Node) {
			return null;
		} else if (parent instanceof GERMModel) {
			return ((GERMModel)parent).getNodeAtImmutableIndex(index);
		} else if (parent instanceof Workspace) {
			return ((Workspace)parent).getProject(index);
		} else if (parent instanceof Project) {
			return ((Project)parent).getDiagram(index);
		}
		return getRoot(); 
	}

	public int getChildCount(Object parent) {
		if (parent instanceof GERMModel) {
			return ((GERMModel)parent).getNodeCount();
		} else if (parent instanceof Project) {
			return ((Project)parent).getDiagramCount();
		} else if (parent instanceof Workspace) {
			return ((Workspace)parent).getProjectsCount();
		}

		return 0;
	}

	public boolean isLeaf(Object node) {
		return (node instanceof Node);
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(parent instanceof Node) {
			return 0;
		} else if (parent instanceof GERMModel) {
			if(child instanceof Node)
				return ((GERMModel)parent).getNodeIndexImmutable((Node)child);
		} else if (parent instanceof Workspace) {
			if(child instanceof Project)
				return ((Workspace)parent).getProjectIndex((Project)child);
		} else if (parent instanceof Project) {
			return ((Project)parent).getDiagramIndex((GERMModel)child);
		}
		return -1;
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}
}
