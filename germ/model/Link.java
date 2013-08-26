package germ.model;

import germ.configuration.InternalConfiguration;
import germ.i18n.Messages;
import germ.view.GraphElementPainter;
import germ.view.LinkPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Link extends GraphElement {

	/**
	 * Pocetak veze.
	 */
	protected Node source;
	/**
	 * Kraj veze.
	 */
	protected Node destination;

	/**
	 * Pozicije prelomnih tacaka linka
	 */
	protected ArrayList<Point> breakPoints = new ArrayList<Point>();

	private Link() {
	}

	public ArrayList<Point> getBreakPoints() {
		return breakPoints;
	}

	public void setBreakPoints(ArrayList<Point> newBreakPoints) {
		this.breakPoints = newBreakPoints;
	}

	public Link(Stroke stroke, Paint paint, Node source) {
		super(stroke, paint);
		this.source = source;
		graphElementPainter = new LinkPainter(this);

	}

	public static Link createDefault(Point2D pos, int elemNo, Node source) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX() - 50, position.getY() - 25);
		Paint fill = new GradientPaint(0, 0, Color.BLACK, 100, 50, Color.BLACK);

		Link l = new Link(new BasicStroke(3f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND), fill, source);
		l.setName(Messages.getString("Link.0") + new Integer(elemNo)); //$NON-NLS-1$
		return l;
	}

	public void deleteBreakpoint(Point point) {
		breakPoints.remove(point);
	}

	public void addBreakPoint(Point p) {
		breakPoints.add((Point) p.clone());
	}

	public void addBreakPointBegining(Point p) {
		breakPoints.add(0, (Point) p.clone());
	}

	public void deleteBreakpointTail() {
		breakPoints.remove(breakPoints.size() - 1);
	}

	public void deleteBreakpointHead() {
		breakPoints.remove(0);
	}

	public void insertNewBreakpoint(Point pos) {

		boolean inserted = false;
		float tol = InternalConfiguration.LINK_HIT_TOLERANCE;
		double newX = source.getSize().getWidth() / 2 + source.position.getX();
		double newY = source.getSize().getHeight() / 2 + source.position.getY();
		Line2D line = new Line2D.Double(newX, newY, 0, 0);
		for (int i = 0; i < breakPoints.size(); i++) {
			line.setLine(line.getX1(), line.getY1(), breakPoints.get(i).getX(),
					breakPoints.get(i).getY());
			if (line.intersects(pos.getX() - tol, pos.getY() - tol, 2 * tol,
					2 * tol)) {
				breakPoints.add(i, (Point) pos.clone());
				inserted = true;
				break;
			}
			line.setLine(breakPoints.get(i).getX(), breakPoints.get(i).getY(),
					0, 0);
		}
		if (!inserted)
			breakPoints.add((Point) pos.clone());
	}

	public void removeBreakPoint(Point p) {
		breakPoints.remove(p);
	}

	public Point getBreakPointAt(int i) {
		return breakPoints.get(i);
	}
	
	public int getBreakPointCount() {
		return breakPoints.size();
	}

	public Iterator<Point> getBreakPointIterator() {
		return breakPoints.iterator();
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
		if (destination != null)
			destination.addLink(this);
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
		if (source != null)
			source.addLink(this);
	}
	
	/**
	 * Računa centar noda koji je postavljen za source.
	 * @return centralnu tačku
	 */
	public Point getSourceCenterPosition(){
		if (source == null) return null;
		Point point = new Point();
		point.setLocation(getSource().getPosition().getX()
				+ getSource().getSize().getWidth() / 2,
				getSource().getPosition().getY()
				+ getSource().getSize().getHeight() / 2);
		return point;
	}
	
	/**
	 * Računa centar noda koji je postavljen za destination.
	 * @return centralnu tačku
	 */
	public Point getDestinationCenterPosition(){
		if (destination == null) return null;
		Point point = new Point();
		point.setLocation(getDestination().getPosition().getX()
					+ getDestination().getSize().getWidth() / 2,
					getDestination().getPosition().getY()
					+ getDestination().getSize().getHeight() / 2);
		return point;
	}
	

	public boolean hasBreakPoints() {
		return !breakPoints.isEmpty();
	}

	public GraphElementPainter getPainter() {
		return graphElementPainter;
	}

	@Override
	public Link clone() {
		Link l = new Link();
		super.clone(l);
		l.setDestination(getDestination());
		l.setSource(getSource());
		Iterator<Point> it = getBreakPointIterator();
		while (it.hasNext()) {
			l.addBreakPoint((Point)(it.next().clone()));
		}

		return l;
	}
}
