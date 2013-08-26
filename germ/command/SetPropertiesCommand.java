// u ovaj kod ne zalaziti jer jos ne radi kako treba...
package germ.command;

import germ.app.Application;
import germ.model.GERMModel;
import germ.model.Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Postavljanje svojstava nodova.
 */
public class SetPropertiesCommand implements Command {
	/**
	 * Stara svojstva noda.
	 */
	private HashMap<String, Object> oldProperties;
	
	/**
	 * Nova svojstva noda.
	 */
	private HashMap<String, Object> newProperties;
	
	/**
	 * Nodovi čija se svojstva menjaju.
	 */
	private ArrayList<Node> nodes;

	/**
	 * Inicijalijzuje podatke potrebne za undo ove komande.
	 * @param nodes Nodovi kojima se menjaju svojstva
	 * @param oldProperties Stara svojstva
	 * @param newProperties Nova svojstva
	 */
	public SetPropertiesCommand(ArrayList<Node> nodes, HashMap<String, Object> oldProperties, HashMap<String, Object> newProperties) {
		this.nodes = nodes;
		this.oldProperties = oldProperties;
		this.newProperties = newProperties;
	}

	/**
	 * Postavlja nova svojstva na nodove.
	 */
	public void doCommand() {
		GERMModel m = Application.getInstance().getModel();
		for (Node node : nodes) {
			node.setProperties(newProperties);
		}
		m.updatePerformed();
	}

	/**
	 * Postavlja stara svojstva na nodove.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		for (Node node : nodes) {
			node.setProperties(oldProperties);
		}
		m.updatePerformed();
	}
	
	public void setOldProperties(HashMap<String, Object> oldProperties) {
		this.oldProperties = oldProperties;
	}

	public void setNewProperties(HashMap<String, Object> newProperties) {
		this.newProperties = newProperties;
	}

}
