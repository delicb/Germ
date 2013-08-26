package germ.command;

import germ.app.Application;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;

import java.util.ArrayList;

/**
 * Brisanje nodova.
 */
public class DeleteCommand implements Command {

	/**
	 * Obrisani nodovi.
	 */
	private ArrayList<Node> deletedNodes;
	
	/**
	 * Obrisani link
	 */
	private Link deletedLink;
	
	/**
	 * Da li se izvrsava doCommand prvi put (da razlikujemo 
	 * redo od prvog izvrsavanja)
	 */
	private boolean firstTime = true;

	/**
	 * Uklanja link ili nod (u zavisnosti od toga sta je selektovano) iz modela
	 * i pamti ih da bi mogao undo da se uradi.
	 */
	@SuppressWarnings("unchecked")
	public void doCommand() {
		GERMModel m = Application.getInstance().getModel();
		ArrayList<Node> nodesToDelete = null;
		Link linkToDelete = null;
		if (firstTime) {
			nodesToDelete = (ArrayList<Node>) m.getSelectedNodes().clone();
			deletedNodes = nodesToDelete;
			if (m.getSelectedLink() != null) {
				linkToDelete = m.getSelectedLink();
				deletedLink = linkToDelete;
			}
		} else {
			nodesToDelete = deletedNodes;
			linkToDelete = deletedLink;
		}

		if (nodesToDelete.size() > 0) {
			for (Node item : nodesToDelete) {
				m.removeNode(item);
			}
		}
		if (linkToDelete != null) {
			m.removeLink(linkToDelete);
		}
		firstTime = false;
	}

	/**
	 * Vraća obrisane nodove ili linkove.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		if (deletedNodes.size() > 0) {
			for (Node item : deletedNodes) {
				m.addNode(item);
			}
		}
		if (deletedLink != null) {
			m.addLink(deletedLink);
		}
	}

}
