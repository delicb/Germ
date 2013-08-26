package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Position;
import germ.view.NodePainter;

import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Position
 */
public class PositionPainter extends NodePainter {

	public PositionPainter(Node node) {
		super(node);

		Position pos = (Position) node;

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(pos.getSize().getWidth()/pos.getScaleX(), 0);
		gp.lineTo(pos.getSize().getWidth()/pos.getScaleX() / 2, pos.getSize().getHeight()/pos.getScaleY());
		gp.closePath();

		shape = gp;
	}
}
