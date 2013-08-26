package germ.actions;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.i18n.Messages;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Akcija za redjanje elemenata po donjoj ivici.
 *
 */
@SuppressWarnings("serial")
public class AlignBottomAction extends AbstractGERMAction {

    public AlignBottomAction() {
        putValue(SMALL_ICON, loadIcon("arrow_down.png")); 
        putValue(NAME, Messages.getString("AlignBottomAction.0"));  
    }

    
    /** 
     * Metoda promeni polozaj svim node elementima tako da budu poredjani po donjoj ivici.
     * Koristi MoveCommand kako bi mogao da se radi undo/redo za ovu akciju
     */
    public void actionPerformed(ActionEvent e) {
        ArrayList<Node> nodes = Application.getInstance().getModel().getSelectedNodes();
        if(nodes.size() < 2) return;
        double max = nodes.get(0).getPosition().getY() + nodes.get(0).getSize().getHeight();
        //kreiramo novu move komandu
        MoveCommand moveCommand = new MoveCommand();
        
        //trazimo maximalnu y vrednost tj y + height
        for(Node n : nodes) {
            if(n.getPosition().getY() + n.getSize().getHeight() > max)
                max = n.getPosition().getY() + n.getSize().getHeight();
        }
        
        //postavljamo sve elemente na poziciju da budu poravnati
        for(Node n : nodes) {
            double pos = max - n.getSize().getHeight();
            n.getPosition().setLocation(n.getPosition().getX(), pos);
        }
        
        moveCommand.moveEnded();
        Application.getInstance().getCommandManager().doCommand(moveCommand);
        Application.getInstance().getModel().updatePerformed();
    }

}
