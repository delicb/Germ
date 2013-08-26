package germ.state;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.model.GERMModel;
import germ.model.Node;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class MoveState extends State {

	private Point lastPosition;
	MoveCommand moveCommand;
	private Node hitNode;

	/**
	 * Pomera selektovane nodove i pomera radnu površinu ako
	 * miš dođe do ivice.
	 */
	public void mouseDragged(MouseEvent e) {
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		Point mousePosition = (Point) v.lastPosition;
		double width = v.getWidth();
		double height = v.getHeight();
		double tolerance = 20;
		int moveStep = 6;
		int moveX = 0;
		int moveY = 0;

		int xOffset = lastPosition.x - mousePosition.x;
		int yOffset = lastPosition.y - mousePosition.y;

		if (m.getSelectedNodes().size() > 0){
			if(e.isShiftDown())
				m.updateSelectedNodesPosition90(hitNode, xOffset, yOffset);
			else	
				m.updateSelectedNodesPosition(xOffset, yOffset);
		}

		lastPosition = mousePosition;

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
	 * Javlja command manageru da je uradjeno pomeranje da bi mogao da se uradi 
	 * undo i vraća state mašinu i podrazumevano stanje.
	 */
	public void mouseReleased(MouseEvent e) {
		Application app = Application.getInstance();
		app.getStateMachine().setState(State.defaultState);
		moveCommand.moveEnded();
		app.getCommandManager().doCommand(moveCommand);
		
	}

	
	
	@Override
    public void exit() {
        Application.getInstance().getModel().getGhostNodes().clear();
        Application.getInstance().getModel().updatePerformed();
    }

    /**
	 * Kreira komandu koja se koristi za undo-redo.
	 */
	public void initializeMove(Node hitNode) {
		GERMModel m = Application.getInstance().getModel();
		lastPosition = (Point) Application.getInstance().getView().lastPosition;
		this.hitNode = hitNode;
		//TODO Ubaciavanje ghost nodova
		for(Node current: m.getSelectedNodes()){
			m.addGhostNode(current);
		}
		moveCommand = new MoveCommand();
		
	}
}
