package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Topic;
import germ.view.NodePainter;

import java.awt.geom.Rectangle2D;


/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Topic
 */
public class TopicPainter extends NodePainter {

	public TopicPainter(Node device) {
		super(device);
		Topic topic = (Topic) device;

		shape = new Rectangle2D.Double(0, 0, topic.getSize().getWidth()/topic.getScaleX(), topic
				.getSize().getHeight()/topic.getScaleY());
	}
}
