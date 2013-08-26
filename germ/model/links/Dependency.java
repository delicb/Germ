package germ.model.links;

import germ.i18n.Messages;
import germ.model.Link;
import germ.model.Node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 * Klasa Linka koja povezuje medjusobno: assumption/argument, argument/position,
 * position/topic, topic/decision, decision/decision. Veza opisuje zavisnost
 * izmedju razlicitih elemenata dijagrama.
 */
public class Dependency extends Link {

	public Dependency(Stroke stroke, Paint paint, Node source) {
		super(stroke, paint, source);
	}

	public static Link createDefault(Point2D pos, int elemNo, Node source) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX() - 50, position.getY() - 25);
		Paint fill = new GradientPaint(0, 0, Color.BLACK, 100, 50, Color.BLACK);

		Link l = new Dependency(new BasicStroke(3f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL), fill, source);
		l.setName(Messages.getString("Dependency.0") + new Integer(elemNo)); //$NON-NLS-1$
		return l;
	}
}
