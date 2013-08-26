package germ.command;

import germ.app.Application;
import germ.model.GERMModel;
import germ.model.Node;

import java.util.ArrayList;

/**
 * Obrtanje nodova.
 */
public class FlipCommand implements Command {

	/**
	 * Nodovi koji se obrću.
	 */
    private ArrayList<Node> flipedNodes = new ArrayList<Node>();
    
    /**
     * Da li se prvi put poziva, da razlikujemo prvo obrtanje od redo-a.
     */
    private boolean firstTime = true;
    
    public FlipCommand(){
        GERMModel m = Application.getInstance().getModel();
        if(firstTime) {
            for(Node n:m.getSelectedNodes())
            flipedNodes.add(n);
            firstTime=false;
        }
    }
    
    /**
     * Obrće selektovane nodove
     */
    public void doCommand() {
        GERMModel m = Application.getInstance().getModel();
        for (Node item : flipedNodes) {
            item.flip();
        }
        m.updatePerformed();
    }
    
    /**
     * Ponovo obrće nodove (vraća ih na staro)
     */
    public void undoCommand() {
        GERMModel m = Application.getInstance().getModel();
        for (int i = 0; i < flipedNodes.size(); i++) {
            flipedNodes.get(i).flip();
        }
        m.updatePerformed();
    }
}
