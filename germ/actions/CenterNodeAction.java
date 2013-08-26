package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.view.GERMView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

/**Akicja koja ima za zadatak da selektovani node u JTree pronadje medju dijagramima, otvori dijagram
 * ako nije otvoren i centrira prikaz na dati node.
 * 
 * */
@SuppressWarnings("serial")
public class CenterNodeAction extends AbstractGERMAction {
	
	/**
	 * Dijagram kome pripada element na koji se centrira prikaz.
	 */
	private GERMModel model;
	/**
	 * Element na koji se centrira prikaz.
	 */
	private Node node;
	
	public void setCenterData(GERMModel model, Node node) {
		this.model = model;
		this.node = node;
	}

	public CenterNodeAction(){
		putValue(NAME, Messages.getString("CenterNodeAction.0")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("CenterNodeAction.1")); //$NON-NLS-1$
	}

	/**
	 * Metoda postavi dijagram node-a na kome se vrsi akcija da bude prikazan,
	 * selektuje taj nod na radnoj povrsini i centrira radnu povrsinu na njega.
	 */
	public void actionPerformed(ActionEvent arg0) {
		//uzmem view
		GERMView v = model.getView();
		//postavim da je to aktivni diaram
		Application.getInstance().setModel(model);
		model.updatePerformed();
		//selektuje se u tom dijagramu node
		model.deselectAllNodes();
		model.selectNode(node);
		//postavlja se rectange na koji ce biti uradjen zoom
		Dimension canvasSize = v.getSize();
		Rectangle2D rect = new Rectangle2D.Double();
		rect.setRect(node.getPosition().getX() - canvasSize.width / 2
				+ node.getSize().getWidth() / 2, node.getPosition().getY()
				- canvasSize.height / 2 + node.getSize().getHeight() / 2,
				canvasSize.width, canvasSize.height);
		v.zoomTo(rect);
		model = null;
		node = null;
	}

}
