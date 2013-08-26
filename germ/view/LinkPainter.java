package germ.view;

import germ.configuration.InternalConfiguration;
import germ.model.GraphElement;
import germ.model.Link;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * Klasa zaduzena za iscrtavanje veza i proveru da li se veza nalazi na zadatoj lokaciji.
 *
 */
public class LinkPainter extends GraphElementPainter {

	/**
	 * Oblik za graficki prikaz veze.
	 */
	protected Shape shape;
	/**
	 * Veza koju prikzazuje.
	 */
	protected Link link;

	public LinkPainter(GraphElement element) {
		super(element);
		link = (Link) element;
	}

	/**
	 * Metoda iscrtava oblik veze i cuva ga u polju Link.shape
	 */
	private void reshape() {
		Link l = (Link) element;
		boolean sourceNotDefined = true;
		GeneralPath line = new GeneralPath();

		if (l.getSource() != null) {
			sourceNotDefined = false;
			line.moveTo(l.getSource().getPosition().getX()
					+ l.getSource().getSize().width / 2, l.getSource()
					.getPosition().getY()
					+ l.getSource().getSize().height / 2);
		}
		Iterator<Point> it = l.getBreakPointIterator();

		while (it.hasNext()) {
			Point p = it.next();
			if (sourceNotDefined)
				line.moveTo(p.getX(), p.getY());
			else
				line.lineTo(p.getX(), p.getY());
			sourceNotDefined = false;
		}
			

		if (l.getDestination() != null) {
			line.lineTo(l.getDestination().getPosition().getX()
					+ l.getDestination().getSize().width / 2, l
					.getDestination().getPosition().getY()
					+ l.getDestination().getSize().height / 2);
		}
		shape = line;
	}

	/*
	 * Ostavljeno za eventualno kasnije koriscenje
	 * 
	 * @Override public boolean isElementAt(Point2D pos) { Link link = (Link)
	 * element; System.out.println("Usao u isElementAT"); return
	 * getShape().contains( pos.getX() - link.getSource().getPosition().getX(),
	 * pos.getY() - link.getSource().getPosition().getY()); }
	 */

	/**
	 * Metoda vraca true ukoliko je na zadatoj poziciji link. Uvazava
	 * translaciju source i destination noda (koji se naleze u uglu noda, a ne u
	 * centru).
	 */
	public boolean isElementAt(Point2D pos) {
		float tol = InternalConfiguration.LINK_HIT_TOLERANCE;
		if (link.getBreakPointCount() < 1)
			return shape.intersects(pos.getX() - tol, pos.getY() - tol,
					2 * tol, 2 * tol);
		else {
			Line2D line = new Line2D.Double();
			double newX, newY;
			boolean noSource = true;
			if (link.getSource() != null) {
				noSource = false;
				newX = link.getSource().getSize().getWidth() / 2
						+ link.getSource().getPosition().getX();
				newY = link.getSource().getSize().getHeight() / 2
						+ link.getSource().getPosition().getY();
				line.setLine(newX, newY, 0, 0);
			}
			for (Point bp : link.getBreakPoints()) {
				if (noSource){
					line.setLine(bp.getX(), bp.getY(), 0, 0);
					noSource = false;
				}
				else {
					line.setLine(line.getX1(), line.getY1(), bp.getX(), bp
							.getY());
					if (line.intersects(pos.getX() - tol, pos.getY() - tol,
							2 * tol, 2 * tol))
						return true;
					line.setLine(bp.getX(), bp.getY(), 0, 0);
				}
			}
			if (link.getDestination() != null) {
				newX = link.getDestination().getSize().getWidth() / 2
						+ link.getDestination().getPosition().getX();
				newY = link.getDestination().getSize().getHeight() / 2
						+ link.getDestination().getPosition().getY();
				line.setLine(line.getX1(), line.getY1(), newX, newY);
				if (line.intersects(pos.getX() - tol, pos.getY() - tol,
						2 * tol, 2 * tol))
					return true;
			}
			return false;
		}
	}

	public Point getBreakpointAt(Point2D pos) {
		if (pos == null)
			return null;
		float tol = InternalConfiguration.LINK_HIT_TOLERANCE;
		Rectangle2D rect = new Rectangle2D.Double();
		for (Point current : link.getBreakPoints()) {
			rect.setFrame(current.getX() - tol, current.getY() - tol, 2 * tol,
					2 * tol);
			if (rect.contains(pos)) {
				return current;
			}
		}
		return null;
	}

	@Override
	public void paint(Graphics2D g) {
		Link node = (Link) element;

		reshape();
		g.setPaint(node.getStrokePaint());
		g.setStroke(node.getStroke());
		g.draw(getShape());
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
