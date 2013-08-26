package germ.actions;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.i18n.Messages;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Akcija za redjanje elemenata po levoj ivici.
 *
 */
@SuppressWarnings("serial")
public class AlignLeftAction extends AbstractGERMAction {

    public AlignLeftAction() {
        putValue(SMALL_ICON, loadIcon("arrow_left.png")); 
        putValue(NAME, Messages.getString("AlignLeftAction.0"));  
    }
    /** 
     * Metoda promeni polozaj svim node elementima tako da budu poredjani po levoj ivici.
     * Koristi MoveCommand kako bi mogao da se radi undo/redo za ovu akciju
     */
    public void actionPerformed(ActionEvent e) {
        ArrayList<Node> nodes = Application.getInstance().getModel().getSelectedNodes();
        if(nodes.size() < 2) return;
        double min = nodes.get(0).getPosition().getX();
        //kreiramo novu move komandu
        MoveCommand moveCommand = new MoveCommand();
        
        //trazimo maximalnu y vrednost tj y + height
        for(Node n : nodes) {
            if(n.getPosition().getX() < min)
                min = n.getPosition().getX();
        }
        
        //postavljamo sve elemente na poziciju da budu poravnati
        for(Node n : nodes) {
            double pos = min;
            n.getPosition().setLocation(pos, n.getPosition().getY());
        }
        
        moveCommand.moveEnded();
        Application.getInstance().getCommandManager().doCommand(moveCommand);
        Application.getInstance().getModel().updatePerformed();

    }

}
