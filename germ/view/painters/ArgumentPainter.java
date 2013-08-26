package germ.view.painters;

import germ.model.Node;
import germ.model.nodes.Argument;
import germ.model.nodes.ArgumentValue;
import germ.view.NodePainter;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja
 * Argument
 * 
 */
public class ArgumentPainter extends NodePainter {

	/**
	 * Privatno polje neophodno za hvatanje klika misha na element.
	 */
	Shape hexagone;
	
    public ArgumentPainter(Node node) {
        super(node);
        Argument req = (Argument) node;

        // SESTOUGAO
        reshape(req);
    }

    /**
     * Iscrtava oblik argumenta.
     * Metoda je neophodna kao publik radi ponovnog iscrtavanja shape objekta 
     * ukoliko se desi da se promeni stanje argumenta.
     * 
     * @param req Argument koji se iscrtava.
     */
    public void reshape(Argument req) {
        GeneralPath outline = new GeneralPath();
        Shape outline1;
        float width = (float) (req.getSize().width / req.getScaleX());
        float height = (float) (req.getSize().height / req.getScaleY());

        outline.moveTo(width / 4, 0);
        outline.lineTo(0, height / 2);
        outline.lineTo(width / 4, height);
        outline.lineTo(width * 3 / 4, height);
        outline.lineTo(width, height / 2);
        outline.lineTo(width * 3 / 4, 0);
        outline.lineTo(width / 4, 0);
        outline.lineTo(width / 4, 0);
        outline.closePath();
        //potrebno radi provere pogadjanja argumenta.
        hexagone = (Shape) outline.clone();
        // ZNAK U SREDINI
        if (req.getValue() == ArgumentValue.ARG_NEGATIVE) {
            outline1 = drawNegative(req);
        } else if (req.getValue() == ArgumentValue.ARG_AFFIRMATIVE) {
            outline1 = drawAffirmative(req);
        } else {
            outline1 = drawNeutral(req);
        }

        outline.setWindingRule(GeneralPath.WIND_EVEN_ODD);
        outline.append(outline1, false);
        shape = outline;
    }

    /**
     * Iscrtava sredishnji deo oblika argumenta za vrednost germ.model.nodes.Aergument.Value.ARG_NEUTRAL
     *  
     * 
     * @param req Argument koji se iscrtava
     * 
     * @return Shape u obliku slova i.
     */
    private Shape drawNeutral(Argument req) {
        GeneralPath outline = new GeneralPath();
        float width = (float) (req.getSize().width / req.getScaleX());
        float height = (float) (req.getSize().height / req.getScaleY());

        outline.moveTo(width * 3 / 8, height * 3 / 8);
        
        outline.curveTo(width * 3 / 8, height * 7 / 16, width * 5 / 8,
                height * 7 / 16, width * 5 / 8, height * 3 / 8);
        
        outline.curveTo(width * 9 / 16, height * 4 / 8, width * 9 / 16,
                height * 6 / 8, width * 5 / 8, height * 7 / 8);
        
        outline.curveTo(width * 5 / 8, height * 13 / 16, width * 3 / 8,
                height * 13 / 16, width * 3 / 8, height * 7 / 8);
        
        outline.curveTo(width * 7 / 16, height * 6 / 8, width * 7 / 16,
                height * 4 / 8, width * 3 / 8, height * 3 / 8);
        outline.closePath();
        
        outline.append(new Ellipse2D.Double(width * 3 / 8, height / 8,
                width / 4, height / 4), false);
        return outline;
    }

    /**
     * Iscrtava sredishnji deo oblika argumenta za vrednost germ.model.nodes.Aergument.Value.ARG_AFFIRMATIVE
     *  
     * 
     * @param req Argument koji se iscrtava
     * 
     * @return Shape u obliku znaka "striklirano"
     */
    private Shape drawAffirmative(Argument req) {
        GeneralPath outline = new GeneralPath();
        float width = (float) (req.getSize().width / req.getScaleX());
        float height = (float) (req.getSize().height / req.getScaleY());

        outline.moveTo(width * 6 / 8, height / 8);
        outline.lineTo(width * 3 / 8, height * 6 / 8);
        outline.lineTo(width / 8, height / 2);
        outline.lineTo(width * 3 / 8, height * 7 / 8);
        outline.closePath();

        return outline;
    }

    /**
     * Iscrtava sredishnji deo oblika argumenta za vrednost germ.model.nodes.Aergument.Value.ARG_NEGATIVE
     *  
     * 
     * @param req Argument koji se iscrtava
     * 
     * @return Shape u obliku slova X.
     */
    private Shape drawNegative(Argument req) {
        float width = (float) (req.getSize().width / req.getScaleX());
        float height = (float) (req.getSize().height / req.getScaleY());
        GeneralPath outline = new GeneralPath();

        outline.moveTo(width / 4, height / 16);
        outline.lineTo(width * 3 / 16, height / 4);
        outline.lineTo(width * 7 / 16, height / 2);
        outline.lineTo(width * 3 / 16, height * 3 / 4);
        outline.lineTo(width / 4, height * 15 / 16);
        outline.lineTo(width / 2, height * 9 / 16);
        outline.lineTo(width * 3 / 4, height * 15 / 16);
        outline.lineTo(width * 13 / 16, height * 3 / 4);
        outline.lineTo(width * 9 / 16, height / 2);
        outline.lineTo(width * 13 / 16, height / 4);
        outline.lineTo(width * 3 / 4, height / 16);
        outline.lineTo(width / 2, height * 7 / 16);
      
        outline.closePath();
        return outline;
    }

	@Override
	public boolean isElementAt(Point2D pos) {
		Node node = (Node) element;
		return hexagone.contains((pos.getX() - node.getPosition().getX())/node.getScaleX(),
				(pos.getY() - node.getPosition().getY())/node.getScaleY());
	}  
}
