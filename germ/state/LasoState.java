package germ.state;

import germ.app.Application;
import germ.model.GERMModel;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;


public class LasoState extends State {
	
	/**
	 * Ažurira kraj lasa na poslednju poziciju miša i pomera radnu površinu
	 * ukoliko je kraj lasa došao do ivice.
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
	 * Izlazi iz ovog stanja i selektijue sve nodove koje se nalaze
	 * u icritanom pravougaoniku.
	 */
	public void mouseReleased(MouseEvent e){
		GERMModel m = Application.getInstance().getModel();
		GERMView v = Application.getInstance().getView();
		
		//selekcija lasom
		if(v.lasoEnabled) 
			m.selectNode(v.getLasoRectangle());
		v.lasoEnabled = false;
		Application.getInstance().getStateMachine().setState(
				State.defaultState);
	}
}
