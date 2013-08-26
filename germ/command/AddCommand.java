package germ.command;

import germ.app.Application;
import germ.model.Node;

/**
 * Dodavanje noda. 
 */
public class AddCommand implements Command {
	/**
	 * Nod koji je dodat;
	 */
	private Node node;
	
	public AddCommand(Node node) {
		this.node = node;
	}

	/**
	 * Dodaje nod u model.
	 */
	public void doCommand() {
		Application.getInstance().getModel().addNode(node);
	}

	/**
	 * Uklanja nod iz modela;
	 */
	public void undoCommand() {
		Application.getInstance().getModel().removeNode(node);
	}

}
