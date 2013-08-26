package germ.state;

import germ.app.Application;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class LasoZoomState extends State{
	
	/**
	 * Menja kraj lasa koji se iscrtava i pomera radnu površinu 
	 * ako je laso došao do ivice.
	 */
	public void mouseDragged(MouseEvent e){
		GERMView v = Application.getInstance().getView();
		double width = v.getWidth();
		double height= v.getHeight();
		double tolerance = 20;
		int moveStep = 6;
		int moveX = 0;
		int moveY = 0;
		
		Application.getInstance().getModel().setLasoEnd((Point)v.lastPosition);
		v.lasoEnabled = true;
		
		if(e.getX() >= width-tolerance) {
			moveX-=moveStep;	
		}
		
		if(e.getX() <= tolerance) {
			moveX+=moveStep;	
		}
		
		if(e.getY() >= height-tolerance) {
			moveY-=moveStep;	
		}
		
		if(e.getY() <= tolerance) {
			moveY+=moveStep;	
		}
		
		v.moveWorkspace(moveX, moveY);	
	}
	
	/**
	 * Postavlja početak pravougaonika koji se iscritava.
	 */
	public void mousePressed(MouseEvent e){
		Application.getInstance().getModel().setLasoBegin((Point)Application.getInstance().getView().lastPosition);
	}
	
	/**
	 * Isključuje laso i zumira na veličinu nacrtanog lasa.
	 */
	public void mouseReleased(MouseEvent e){
		GERMView v = Application.getInstance().getView();
		v.zoomToLasoRectangle();
		v.lasoEnabled = false;
		Application.getInstance().getStateMachine().setState(
				State.defaultState);
	}
}
