package germ.actions;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.i18n.Messages;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Akcija za redjanje elemenata po gornjoj ivici.
 *
 */
@SuppressWarnings("serial")
public class AlignTopAction extends AbstractGERMAction {

    public AlignTopAction() {
        super();
        putValue(SMALL_ICON, loadIcon("arrow_up.png")); 
        putValue(NAME, Messages.getString("AlignTopAction.0"));  
    }

    /** 
     * Metoda promeni polozaj svim node elementima tako da budu poredjani po gornjoj ivici.
     * Koristi MoveCommand kako bi mogao da se radi undo/redo za ovu akciju
     */
    public void actionPerformed(ActionEvent arg0) {
        ArrayList<Node> nodes = Application.getInstance().getModel().getSelectedNodes();
        if(nodes.size() < 2) return;
        double min = nodes.get(0).getPosition().getY();
        //kreiramo novu move komandu
        MoveCommand moveCommand = new MoveCommand();
        
        //trazimo maximalnu y vrednost tj y + height
        for(Node n : nodes) {
            if(n.getPosition().getY() < min)
                min = n.getPosition().getY();
        }
        
        //postavljamo sve elemente na poziciju da budu poravnati
        for(Node n : nodes) {
            double pos = min;
            n.getPosition().setLocation(n.getPosition().getX(), pos);
        }
        
        moveCommand.moveEnded();
        Application.getInstance().getCommandManager().doCommand(moveCommand);
        Application.getInstance().getModel().updatePerformed();
    }

}
