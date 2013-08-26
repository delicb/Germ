package germ.state;

import germ.app.Application;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class StateMachine {
	private State currentState;

	void setState(State s) {
		currentState.exit();
		currentState = s;
		currentState.enter();
		Application.getInstance().getMainWindow().setStatusBarMessage(
				s.getStateName(), 2);

	}

	public void mousePressed(MouseEvent e) {
		Application.getInstance().getView().requestFocusInWindow();
		currentState.mousePressed(e);
	}

	public void mouseClicked(MouseEvent e) {
		currentState.mouseClicked(e);
	}

	public void mouseDoubleClicked(MouseEvent e) {
		currentState.mouseDoubleClicked(e);
	}

	public void mouseReleased(MouseEvent e) {
		currentState.mouseReleased(e);
	}

	public void mouseWheelMoved(MouseEvent e) {
		currentState.mouseWheelMoved(e);
	}

	public void mouseMove(MouseEvent e) {
		currentState.mouseMove(e);
	}

	public void keyPressed(KeyEvent e) {
		if ((int) e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			setState(State.defaultState);
		} else
			currentState.keyPressed(e);
	}

	public void mouseDragged(MouseEvent e) {
		currentState.mouseDragged(e);
	}

	public void insertRequirement() {
		setState(State.insertRequirementState);
	}

	public void insertArgument() {
		setState(State.insertArgumentState);
	}

	public void insertAssumption() {
		setState(State.insertAssumptionState);
	}

	public void insertDecision() {
		setState(State.insertDecisionState);
	}

	public void insertPosition() {
		setState(State.insertPositionState);
	}

	public void insertLink() {
		setState(State.insertLinkState);
	}

	public void insertLinkBreakpoint() {
		setState(State.insertLinkBreakpointsState);
	}

	public void insertStakeholder() {
		setState(State.insertStakeholderState);
	}

	public void insertTopic() {
		setState(State.insertTopicState);
	}

	public void zoomClicked() {
		if (currentState == State.zoomState)
			setState(State.defaultState);
		else
			setState(State.zoomState);
	}

	public void lasoZoom() {
		setState(State.lasoZoomState);
	}

	public StateMachine() {
		currentState = State.defaultState;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void modelChanged() {
		currentState.exit();
		currentState = State.defaultState;
	}
}
