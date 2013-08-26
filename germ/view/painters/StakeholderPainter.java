package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Stakeholder;
import germ.view.NodePainter;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Stakeholder
 */
public class StakeholderPainter extends NodePainter {

	public StakeholderPainter(Node node) {
		super(node);
		Stakeholder req = (Stakeholder) node;
		int width = (int) (req.getSize().width/req.getScaleX());
		int height = (int) (req.getSize().height/req.getScaleY());

		GeneralPath outline = new GeneralPath();
		outline.moveTo(0, height);
		outline.curveTo(width / 7, height *2/ 7, width * 6 / 7, height *2/ 7, width, height);
		outline.quadTo(width / 2, height * 5 / 7,
				0, height);

		Shape sp = new Ellipse2D.Double(width/2 - width * 5 / 28, 0, width * 5 / 14,
				height * 5 / 14);
		outline.closePath();
		outline.append(sp, false);

		outline.moveTo(outline.getCurrentPoint().getX(), outline
				.getCurrentPoint().getY());
		
		shape = outline;
	}
}
