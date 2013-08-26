package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Requirement;
import germ.view.NodePainter;

import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Requirement.
 */
public class RequirementPainter extends NodePainter {

	public RequirementPainter(Node aNode) {
		super(aNode);
		Requirement req = (Requirement) aNode;
		GeneralPath outline = new GeneralPath();
		int width = (int) (req.getSize().width/req.getScaleX());
		int height = (int) (req.getSize().height/req.getScaleY());

		outline.moveTo(width * 5 / 6, 0);
		outline.lineTo(0, 0);
		outline.lineTo(0, height);
		outline.lineTo(width, height);
		outline.lineTo(width, height * 1 / 6);
		outline.lineTo(width * 5 / 6, 0);
		outline.lineTo(width * 5 / 6,
				height * 1 / 6);
		outline.lineTo(width, height * 1 / 6);
		outline.closePath();

		shape = outline;
	}
}
