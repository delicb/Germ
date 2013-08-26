package germ.command;

import germ.app.Application;
import germ.model.Clipboard;
import germ.model.GERMModel;
import germ.model.Node;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Nalepljivanje kopiranih nodova :)
 */
public class PasteCommand implements Command {

	/**
	 * Nodovi koji se nalepljuju.
	 */
	private ArrayList<Node> pastedItems = new ArrayList<Node>();

	/**
	 * Da li je prvi put ili je redo?
	 */
	boolean firstTime = true;

	/**
	 * Stavlja nodove iz Clipboarda u model
	 */
	public void doCommand() {
		GERMModel m = Application.getInstance().getModel();
		Clipboard c = Application.getInstance().getClipboard();
		ArrayList<Node> items;

		int offsetX = 0;
		int offsetY = 0;

		if (firstTime) {
			items = c.getItems();
			Point reference;
			if (m.getView().isCursorOnCanvas())
				reference = (Point) m.getView().lastPosition;
			else
				reference = m.getView().getCanvasCenter();
			if (items.size() > 0) {
				Node n = items.get(0);
				offsetX = (int) (reference.getX() - n.getPosition().getX() - n
						.getSize().getWidth() / 2);
				offsetY = (int) (reference.getY() - n.getPosition().getY() - n
						.getSize().getHeight() / 2);
			}
		} else {
			items = pastedItems;
			offsetX = 0;
			offsetY = 0;
		}

		ArrayList<Node> forClipboard = new ArrayList<Node>();
		for (Node item : items) {
			if (firstTime)
				pastedItems.add(item);
			item.setPosition(new Point((int) item.getPosition().getX()
					+ offsetX, (int) item.getPosition().getY() + offsetY));
			m.addNode(item);
			Node newItem = (Node) item.clone();
			forClipboard.add(newItem);
		}

		c.addItems(forClipboard);

		firstTime = false;
	}

	/**
	 * Briše nodove iz modela koji su postavljeni u model.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		for (Node item : pastedItems) {
			m.removeNode(item);
		}
	}

}
