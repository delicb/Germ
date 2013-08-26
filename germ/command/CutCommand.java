package germ.command;

import germ.app.Application;
import germ.model.GERMModel;
import germ.model.Node;

import java.util.ArrayList;

/**
 * Cutovanje nodova.
 */
public class CutCommand implements Command {

	/**
	 * Lista svih cutovanih nodova.
	 */
	private ArrayList<Node> cuttedItems = new ArrayList<Node>();

	/**
	 * Uklanja sve selektovane nodove iz modela
	 * i stavlja ih na Clipboard.
	 */
	public void doCommand() {
		Application app = Application.getInstance();
		GERMModel m = app.getModel();
		ArrayList<Node> itemsToCopy = m.getSelectedNodes();
		if (itemsToCopy.size() > 0) {
			ArrayList<Node> nodesToRemove = new ArrayList<Node>();
			for (Node item : itemsToCopy) {
				cuttedItems.add((Node) item.clone());
				nodesToRemove.add(item);
			}

			for (Node item : nodesToRemove) {
				m.removeNode(item);
			}
			app.getClipboard().addItems(cuttedItems);
			app.getActionManager().getPasteAction().setEnabled(true);
		}
	}

	/**
	 * Vraca sve cut-ovane nodove na modek
	 */
	public void undoCommand() {
		for (Node item : cuttedItems) {
			Application.getInstance().getModel().addNode(item);
		}
	}
}
