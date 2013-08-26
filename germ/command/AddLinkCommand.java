package germ.command;

import germ.app.Application;
import germ.model.Link;

/**
 * Dodavanje linka.
 */
public class AddLinkCommand implements Command {

	/**
	 * Link koji se dodaje.
	 */
	private Link link;
	
	public AddLinkCommand(Link link)
	{
		this.link = link;
	}
	
	/**
	 * Dodaje link u model.
	 */
	public void doCommand() {
		Application.getInstance().getModel().addLink(link);
	}

	/**
	 * Uklanja link iz modela.
	 */
	public void undoCommand() {
		Application.getInstance().getModel().removeLink(link);
	}
}
