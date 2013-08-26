package germ.state;

import germ.app.Application;
import germ.util.Cursors;
import germ.view.GERMView;

import java.awt.event.MouseEvent;

public class ZoomState extends State {

	@Override
	public void mousePressed(MouseEvent e) {
		GERMView v = Application.getInstance().getView();
		if (e.getButton() == MouseEvent.BUTTON1)
			v.zoom(e, true);
		else if (e.getButton() == MouseEvent.BUTTON3)
			v.zoom(e, false);
	}

	@Override
	public void enter() {
		Application.getInstance().getView()
				.setCursor(Cursors.getCursor("zoom"));
	}

}
