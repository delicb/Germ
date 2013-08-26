package germ.state;

import germ.app.Application;
import germ.util.Cursors;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class HandMoveState extends State {

	/**
	 * Pamti prethodnu poziciju na kojoj se nalazi mis zbog
	 * racunanja pomeraja canvasa.
	 */
	Point previousPosition = null;

	/**
	 * Računa razliku između trenutne pozicije miša i prethodne i pomera
	 * radnu površinu za taj izračunati offset.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		GERMView v = Application.getInstance().getView();
		if (previousPosition == null) {
			previousPosition = e.getPoint();
		} else {
			AffineTransform transform = v.getTransform();
			Point newPosition = e.getPoint();
			double offsetX = (newPosition.getX() - previousPosition.getX())/transform.getScaleX();
			double offsetY = (newPosition.getY() - previousPosition.getY())/transform.getScaleY();
			previousPosition = newPosition;
			v.moveWorkspace(offsetX, offsetY);
		}
	}

	/**
	 * Vraca se u podrazumevano stanje i vraca kursor na podrazumevani.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		previousPosition = null;
		Application.getInstance().getStateMachine()
				.setState(State.defaultState);
	}

	@Override
	public void enter() {
		Application.getInstance().getView().setCursor(
				Cursors.getCursor("hand"));
	}

	
}
