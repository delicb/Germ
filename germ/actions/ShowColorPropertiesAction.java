package germ.actions;

import germ.app.Application;
import germ.gui.windows.NodeColorProperties;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Klasa predstavlja akciju prozora za izmenu boje selektovane grupe nodova.
 */
@SuppressWarnings("serial")
public class ShowColorPropertiesAction extends AbstractGERMAction {

    public ShowColorPropertiesAction() {
        putValue(SMALL_ICON, loadIcon("property.png")); //$NON-NLS-1$
        putValue(NAME, Messages.getString("ShowColorPropertiesAction.1")); //$NON-NLS-1$
        putValue(SHORT_DESCRIPTION, Messages.getString("ShowColorPropertiesAction.2")); //$NON-NLS-1$
    }
	
	public void actionPerformed(ActionEvent e) {
		GERMModel m = Application.getInstance().getModel();
		ArrayList<Node> selectedNodes = m.getSelectedNodes();
		NodeColorProperties colorPoperties = new NodeColorProperties();
		colorPoperties.setVisible(true);
		if (colorPoperties.isDialogResult()) {
			for (Node node : selectedNodes) {
				node.setColorProperties(colorPoperties.isGradient(),
						colorPoperties.getFillPrimColor(), colorPoperties
								.getFillSecColor(), colorPoperties
								.getStrokeColor(), colorPoperties
								.getStrokeThickness());
			}
		}
		m.updatePerformed();
	}
}
