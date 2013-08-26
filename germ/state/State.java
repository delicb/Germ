package germ.state;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class State {

	public static DefaultState defaultState;
	public static InsertLinkState insertLinkState;
	public static InsertLinkBreakpointsState insertLinkBreakpointsState;
	public static LasoState lasoState;
	public static MoveState moveState;
	public static ResizeState resizeState;
	public static ZoomState zoomState;
	public static LasoZoomState lasoZoomState;
	public static HandMoveState handMoveState;
	public static ReconnectState reconnectState;
	public static MoveBreakpointState moveBreakpointState;

	public static InsertRequirementState insertRequirementState;
	public static InsertArgumentState insertArgumentState;
	public static InsertAssumptionState insertAssumptionState;
	public static InsertDecisionState insertDecisionState;
	public static InsertPositionState insertPositionState;
	public static InsertStakeholderState insertStakeholderState;
	public static InsertTopicState insertTopicState;

	public String getStateName() {
		String parts[] = this.getClass().getName().split("\\.");
		return parts[parts.length - 1];
	}

	static {
		defaultState = new DefaultState();
		insertLinkState = new InsertLinkState();
		insertLinkBreakpointsState = new InsertLinkBreakpointsState();
		lasoState = new LasoState();
		moveState = new MoveState();
		resizeState = new ResizeState();
		zoomState = new ZoomState();
		lasoZoomState = new LasoZoomState();
		handMoveState = new HandMoveState();

		insertRequirementState = new InsertRequirementState();
		insertArgumentState = new InsertArgumentState();
		insertAssumptionState = new InsertAssumptionState();
		insertDecisionState = new InsertDecisionState();
		insertPositionState = new InsertPositionState();
		insertStakeholderState = new InsertStakeholderState();
		insertTopicState = new InsertTopicState();
		reconnectState = new ReconnectState();
		moveBreakpointState = new MoveBreakpointState();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDoubleClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseEvent e) {
	}

	public void mouseMove(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void exit() {
	}

	public void enter() {
	}
}
