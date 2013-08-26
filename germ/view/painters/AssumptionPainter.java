package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Assumption;
import germ.view.NodePainter;

import java.awt.geom.Ellipse2D;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Assumption
 */
public class AssumptionPainter extends NodePainter {

	public AssumptionPainter(Node device) {
		super(device);

		Assumption asum = (Assumption) device;

		shape = new Ellipse2D.Double(0, 0, asum.getSize().width/asum.getScaleX(),
				asum.getSize().height/asum.getScaleY());
	}
}
