package germ.state;

import germ.app.Application;
import germ.command.MoveBreakpointCommand;
import germ.model.GERMModel;
import germ.view.GERMView;

import java.awt.event.MouseEvent;

public class MoveBreakpointState extends State {

	MoveBreakpointCommand moveBreakpointCommand;

	/**
	 * Pomera selektovanu prelomnu tačku i pomera radnu površinu
	 * ukoliko miš dođe do ivice.
	 */
	public void mouseDragged(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		double width = v.getWidth();
		double height = v.getHeight();
		double tolerance = 20;
		int moveStep = 6;
		int moveX = 0;
		int moveY = 0;
		
		if(e.isShiftDown()) m.updateSelectedBreakPointPosition90();
		else m.updateSelectedBreakPointPosition();

		// transliranje canvasa
		if (e.getX() >= width - tolerance) {
			moveX -= moveStep;
		}

		if (e.getX() <= tolerance) {
			moveX += moveStep;
		}

		if (e.getY() >= height - tolerance) {
			moveY -= moveStep;
		}

		if (e.getY() <= tolerance) {
			moveY += moveStep;
		}

		v.moveWorkspace(moveX, moveY);
	}
	
	/**
	 * Vraća state mašinu u podrazumevano stanje i javlja command manageru da 
	 * registruje pomeranje prelomne tačke da bi mogao undo da se uradi.
	 */
	public void mouseReleased(MouseEvent e) {
		Application app = Application.getInstance();
		app.getStateMachine().setState(State.defaultState);
		app.getModel().setSelectedBreakpoint(null);
		moveBreakpointCommand.moveEnded();
		app.getCommandManager().doCommand(moveBreakpointCommand);
	}
	
	/**
	 * Kreira komandu koja se koristi za undo-redo.
	 */
	public void initializeMove() {
		moveBreakpointCommand = new MoveBreakpointCommand();
	}
}
