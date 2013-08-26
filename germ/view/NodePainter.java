package germ.view;

import germ.configuration.InternalConfiguration;
import germ.model.Node;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * ElementPainter je zadužen za crtanje elementa kao i za detekciju pogotka za
 * sta koristi Shape.
 * 
 * @author igor
 * 
 */
public class NodePainter extends GraphElementPainter {

	protected Shape shape;

	public NodePainter(Node device) {
		super(device);
	}

	public void paint(Graphics2D g) {
		Node node = (Node) element;
		//dodaje se translacija za sirinu node-a ako je uradjen flip elementa
		double translateX = node.getPosition().getX();
		if(node.getFlip() < 0)
		  translateX += node.getSize().getWidth();
		//postavljanje na poziciju elementa
		g.translate(translateX, node.getPosition().getY());
		
		
		//deformacija elementa
		g.scale(node.getScaleX()*node.getFlip(), node.getScaleY());
		
		
		// *******FILL
		g.setPaint(node.getPaint());
		g.fill(getShape());
		// *******OUTLINE
		g.setPaint(node.getStrokePaint());
		g.setStroke(node.getStroke());
		g.draw(getShape());
		// *******TEXT
		g.setPaint(Color.BLACK);
		
		// vracanje deformacije da ne bi uticala na tekst
		g.scale(1/node.getScaleX()/node.getFlip(), 1/node.getScaleY());
		//vracanje ispod node-a ako je node flip-ovan
		if(node.getFlip()<0)
		    g.translate(-node.getSize().getWidth(), 0);
		
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		Rectangle2D text = g.getFontMetrics()
				.getStringBounds(node.getName(), g);
		g.drawString(node.getName(), Math.round(node.getSize().width / 2
				- text.getWidth() / 2), Math.round(node.getSize().height
				+ text.getHeight() * 5 / 4));
		
		//vracanje na pocetak
		g.translate(-node.getPosition().getX(), -node.getPosition().getY());
		
	}

	public boolean isElementAt(Point2D pos) {
		Node node = (Node) element;
		return getShape().contains((pos.getX() - node.getPosition().getX())/node.getScaleX(),
				(pos.getY() - node.getPosition().getY())/node.getScaleY());
	}
	/**
	 * Metoda koja nam sluzi da vidimo da li je pogodjen centar nekog noda.
	 * Sluzi da bi otkrili da li korisnik zeli da izvrsi prevezivanje.
	 * 
	 * @param pos Trenutna pozicija (klika)
	 * @return true Ukoliko je pogodjen centar
	 */
	
	public boolean isCenterHit(Point2D pos){
		Node node = (Node) element;
		double newX,newY;
		float tol = InternalConfiguration.LINK_HIT_TOLERANCE;
		newX = node.getSize().getWidth()/2+node.getPosition().getX()-tol;
		newY = node.getSize().getHeight()/2+node.getPosition().getY()-tol;
		Rectangle2D rect = new Rectangle2D.Double();
		
		rect.setFrame(newX, newY, 2*tol, 2*tol);
		return rect.contains(pos);
	}
	
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void drawShadow(Graphics2D g){
		Node node = (Node) element;
		//dodaje se translacija za sirinu node-a ako je uradjen flip elementa
		double translateX = node.getPosition().getX();
		if(node.getFlip() < 0) translateX += node.getSize().getWidth();
		//postavljanje na poziciju elementa
		g.translate(translateX + 7, node.getPosition().getY() + 7);
		//deformacija elementa
		g.scale(node.getScaleX()*node.getFlip(), node.getScaleY());
		/*
		 * SENKA ELEMENTA
		 */
		g.setPaint(Color.BLACK);
		g.fill(getShape());
		/*
		 * sekvenca za vracanje transformacija
		 */
		g.scale(1/node.getScaleX()/node.getFlip(), 1/node.getScaleY());
		g.translate(-translateX-7, -node.getPosition().getY()-7);
		
	}

}
