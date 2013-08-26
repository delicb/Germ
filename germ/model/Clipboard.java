package germ.model;

import java.util.ArrayList;

/**
 * Implementacija clipboarda
 */
public class Clipboard {
	/**
	 * Stavke koje su dodate na clipboard i spremen su za paste-ovanje
	 */
	private ArrayList<Node> items;

	public Clipboard() {
		items = new ArrayList<Node>();
	}

	/**
	 * Dodavanje stavki na clipboard. Stare stavke se brisu
	 * @param items Stavke koje se dodaju na clipboard
	 */
	public void addItems(ArrayList<Node> items) {
		this.items = items;
	}

	public ArrayList<Node> getItems() {
		return items;
	}

	/**
	 * Proverava da li je clipboard prazan
	 * @return true ako na clipboardu ima nesto, false inače
	 */
	public boolean isEmpty() {
		if (items.size() > 0)
			return false;
		return true;
	}
}
