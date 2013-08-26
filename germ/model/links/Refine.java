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
 * Klasa linka koji medjusobno povezuje zahteve. Veza opisuje radfinaciju jednog
 * zahteva drugim.
 */
public class Refine extends Link {

	public Refine(Stroke stroke, Paint paint, Node source) {
		super(stroke, paint, source);

	}

	public static Link createDefault(Point2D pos, int elemNo, Node source) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX() - 50, position.getY() - 25);
		Paint fill = new GradientPaint(0, 0, Color.BLACK, 100, 50, Color.BLACK);

		float[] f = { 20.0f, 8.0f, 10.0f, 8.0f };

		Link l = new Refine(new BasicStroke(3f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 1f, f, 0f), fill, source);
		l.setName(Messages.getString("Refine.0") + new Integer(elemNo)); //$NON-NLS-1$
		return l;
	}
}
