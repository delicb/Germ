package germ.actions;

import germ.app.Application;
import germ.i18n.Messages;
import germ.model.Clipboard;
import germ.model.GERMModel;
import germ.model.Node;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.KeyStroke;

/**
 * Klasa akcije kopiranja elementa.
 */
@SuppressWarnings("serial")
public class CopyAction extends AbstractGERMAction {

	public CopyAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("editcopy.png")); //$NON-NLS-1$
		putValue(NAME, Messages.getString("CopyAction.1")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("CopyAction.2")); //$NON-NLS-1$
	}

	/**
	 * Metoda dodaje klonove svih selektovanih elemenata iz dijagrama na clipboard {@link Clipboard Clipboard}
	 */
	public void actionPerformed(ActionEvent e) {
		Application app = Application.getInstance();
		Clipboard c = app.getClipboard();
		GERMModel m = app.getModel();
		ArrayList<Node> selectedItems = m.getSelectedNodes();
		if (selectedItems.size() > 0) {
			ArrayList<Node> copiedNodes = new ArrayList<Node>();
			for (Node item : selectedItems) {
				copiedNodes.add((Node) item.clone());
			}
			c.addItems(copiedNodes);
			app.getActionManager().getPasteAction().setEnabled(true);
		}
	}

}
