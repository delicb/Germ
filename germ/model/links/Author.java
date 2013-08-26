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
 * Klasa linka koja povezuje medjusobno Stakeholdera sa bilo kojim drugim
 * node-om. Reprezentuje stakeholdera u vezi kao autora elementa na drugom kraju veze.
 * */
public class Author extends Link {

	public Author(Stroke stroke, Paint paint, Node source) {
		super(stroke, paint, source);
	}

	public static Link createDefault(Point2D pos, int elemNo, Node source) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX() - 50, position.getY() - 25);
		Paint fill = new GradientPaint(0, 0, Color.BLACK, 100, 50, Color.BLACK);

		float[] f = { 10.0f, 8.0f };

		Link l = new Author(new BasicStroke(3f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 1f, f, 0f), fill, source);
		l.setName(Messages.getString("Author.0") + new Integer(elemNo)); //$NON-NLS-1$
		return l;
	}
}
