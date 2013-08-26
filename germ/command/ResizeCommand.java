package germ.command;

import germ.app.Application;
import germ.model.GERMModel;
import germ.model.Node;
import germ.view.GERMView;

import java.awt.Point;

/**
 * Promena veličine noda.
 */
public class ResizeCommand implements Command{

	/**
	 * Stari i novi scale faktori.
	 */
	private double oldScaleX, oldScaleY, newScaleX, newScaleY;
	
	/**
	 * Nod čija se veličina menja.
	 */
	Node resizedNode = null;
	
	/**
	 * Početna pozicija noda čija se veličina menja.
	 */
	Point startPosition = new Point();
	
	/**
	 * Krajnja pozicija noda čija se veličina menja.
	 */
	Point endPosition = new Point();
	
	/**
	 * Razlikovanje prvog pokretanja od redo-a.
	 */
	private boolean firstTime = true;
	
	/**
	 * Pamti sve podatke koji su potrebni da se poništi ova komanda.
	 */
	public ResizeCommand(){
		GERMView v = Application.getInstance().getView();
		resizedNode = v.getNodeChangingShape();
		startPosition.setLocation(resizedNode.getPosition());
		oldScaleX = resizedNode.getScaleX();
		oldScaleY = resizedNode.getScaleY();
	}

	/**
	 * Menja veličinu noda čija veličina treba da se promeni.
	 */
	public void doCommand() {
		if (!firstTime) {
			GERMModel m = Application.getInstance().getModel();
			resizedNode.setPosition(endPosition);
			resizedNode.setScaleX(newScaleX);
			resizedNode.setScaleY(newScaleY);
			m.updatePerformed();
		}
		firstTime = false;
	}

	/**
	 * Vraća veličinu noda na onu pre menjanja veličine.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		resizedNode.setPosition(new Point(startPosition));
		resizedNode.setScaleX(oldScaleX);
		resizedNode.setScaleY(oldScaleY);
		m.updatePerformed();
	}

	/**
	 * Javlja ovoj komandi da je menjanje veličina završeno da bi pokupila
	 * potrebne podatke za redo.
	 */
	public void resizeEnded() {
		endPosition.setLocation(resizedNode.getPosition());
		newScaleX = resizedNode.getScaleX();
		newScaleY = resizedNode.getScaleY();
	}
}
