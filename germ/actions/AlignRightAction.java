package germ.actions;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.i18n.Messages;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Akcija za redjanje elemenata po desnoj ivici.
 *
 */
@SuppressWarnings("serial")
public class AlignRightAction extends AbstractGERMAction {

    public AlignRightAction() {
        putValue(SMALL_ICON, loadIcon("arrow_right.png")); 
        putValue(NAME, Messages.getString("AlignRightAction.0"));  
    }
    /** 
     * Metoda promeni polozaj svim node elementima tako da budu poredjani po desnoj ivici.
     * Koristi MoveCommand kako bi mogao da se radi undo/redo za ovu akciju
     */
    public void actionPerformed(ActionEvent e) {
        ArrayList<Node> nodes = Application.getInstance().getModel().getSelectedNodes();
        if(nodes.size() < 2) return;
        double max = nodes.get(0).getPosition().getX() + nodes.get(0).getSize().getWidth();
        //kreiramo novu move komandu
        MoveCommand moveCommand = new MoveCommand();
        
        //trazimo maximalnu y vrednost tj x + width
        for(Node n : nodes) {
            if(n.getPosition().getX() + n.getSize().getWidth() > max)
                max = n.getPosition().getX() + n.getSize().getWidth();
        }
        
        //postavljamo sve elemente na poziciju da budu poravnati
        for(Node n : nodes) {
            double pos = max - n.getSize().getWidth();
            n.getPosition().setLocation(pos, n.getPosition().getY());
        }
        
        moveCommand.moveEnded();
        Application.getInstance().getCommandManager().doCommand(moveCommand);
        Application.getInstance().getModel().updatePerformed();
    }

}
