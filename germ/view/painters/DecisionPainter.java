package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Decision;
import germ.model.nodes.DecisionValue;
import germ.view.NodePainter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Decision.
 */
public class DecisionPainter extends NodePainter {

	public DecisionPainter(Node node) {
		super(node);
		Decision dec = (Decision) node;
		repaint(dec);
	}
	
	/**
	 * Metoda postavlja shape datog elementa na novu vrednost.
	 * Public jer je potrebno ponovljeno crtanje u slucaju promene stanja elementa.
	 * 
	 * @param dec Decision koji se iscrtava.
	 */
	public void repaint(Decision dec) {
		
		GeneralPath gp = new GeneralPath();
		gp.append(new Ellipse2D.Double(0, 0, dec.getSize().width/dec.getScaleX(),
				dec.getSize().height/dec.getScaleY()), false);
		gp.append(new Ellipse2D.Double(dec.getSize().width /dec.getScaleX()/ 2 - 1, dec
				.getSize().height/dec.getScaleY() / 2 - 1, 2, 2), false);
		gp.moveTo(gp.getCurrentPoint().getX(), gp.getCurrentPoint().getY());
		gp.closePath();
		if (dec.getValue() == DecisionValue.FINAL_DECISION)
			gp.append(new Rectangle2D.Double(0, 0, dec.getSize().width/dec.getScaleX(), dec
					.getSize().height/dec.getScaleY()), false);
		shape = gp;
	}
}
