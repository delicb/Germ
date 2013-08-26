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
 * Klasa linka koja povezuje medjusobno: Stakeholdera i Requirement. Veza
 * modeluje interest stakeholdera prema nekom postavljenom zahtevu.
 * */
public class Interest extends Link {

	public Interest(Stroke stroke, Paint paint, Node source) {
		super(stroke, paint, source);

	}

	public static Link createDefault(Point2D pos, int elemNo, Node source) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX() - 50, position.getY() - 25);
		Paint fill = new GradientPaint(0, 0, Color.BLACK, 100, 50, Color.BLACK);

		float[] f = { 0.0f, 5.0f };

		Link l = new Interest(new BasicStroke(3f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 1f, f, 0f), fill, source);
		l.setName(Messages.getString("Interest.0") + new Integer(elemNo)); //$NON-NLS-1$
		return l;
	}
}
