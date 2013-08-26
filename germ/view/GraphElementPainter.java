package germ.view;

import germ.model.GraphElement;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;


/**
 * GraphObjectPainter je apstraktna klasa koja deklariše metode za iscrtavanje
 * elementa i detekciju pogotka
 * @author igor
 *
 */
public abstract class GraphElementPainter {
	
	protected GraphElement element;

	public GraphElementPainter(GraphElement element) {
		this.element = element; 
	}

	public abstract void paint(Graphics2D g);
	
	public abstract boolean isElementAt(Point2D pos);
}
