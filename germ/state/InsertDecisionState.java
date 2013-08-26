package germ.state;

import germ.app.Application;
import germ.command.AddCommand;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.nodes.Decision;
import germ.util.Cursors;
import germ.view.GERMView;

import java.awt.event.MouseEvent;

public class InsertDecisionState extends State {

	/**
	 * Dodaje Odluku na poziciju kursora ako na toj poziciji već
	 * ne postoji nod.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// zaustavljamo beskonacan scroll
		Application.getInstance().getMainWindow().stopScroll();
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			GERMModel m = Application.getInstance().getModel();
			GERMView v = Application.getInstance().getView();
			int node = m.getNodeAtPosition(v.lastPosition);
			if (node == -1) {
				Node n = Decision.createDefault(v.lastPosition, m
						.getNodeCount() + 1);
				AddCommand command = new AddCommand(n);
				Application.getInstance().getCommandManager()
						.doCommand(command);
			}
		}
	}
	
	/**
	 * Ako je pritisnut desni taster miša prelazi u podrazumevano stanje
	 * i postavlja podrazumevni kursor.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			Application.getInstance().getStateMachine().setState(
					State.defaultState);
		}
	}

	@Override
	public void enter() {
		Application.getInstance().getView().setCursor(
				Cursors.getCursor("insertDecision"));
	}
}
