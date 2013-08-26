package germ.actions;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.configuration.ConfigurationManager;
import germ.configuration.InternalConfiguration;
import germ.gui.windows.MainWindow;
import germ.i18n.Messages;
import germ.model.Node;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *Akcija za distribuciju selektovanih elemenata po horizontali
 * 
 */
@SuppressWarnings("serial")
public class DistributeHorizontalAction extends AbstractGERMAction {

	public DistributeHorizontalAction() {
		putValue(NAME, Messages.getString("DistributeHorizontalAction.0"));
	}

	public void actionPerformed(ActionEvent arg0) {
		if (Application.getInstance().getModel().getSelectedNodes().size() < 2)
			return;
		MoveCommand moveCommand = new MoveCommand();
		ArrayList<Node> nodes = sort(Application.getInstance().getModel()
				.getSelectedNodes());
		ArrayList<Point2D> newLocations = new ArrayList<Point2D>();

		// minimalna vrednost pozicije za x koordinatu
		double pos = nodes.get(0).getPosition().getX();
		// kreiramo novu move komandu

		if (ConfigurationManager.getInstance().getBoolean("animationEnabled")) {
			MainWindow mw = Application.getInstance().getMainWindow();
			mw.backupStatusBar();
			mw.setStatusBarMessage(Messages.getString("DistributeHorizontalAction.1"), 0);

			for (Node n : nodes) {
				newLocations
						.add(new Point2D.Double(pos, n.getPosition().getY()));
				pos += n.getSize().getWidth()
						+ InternalConfiguration.DISTRIBUTE_DISTANCE;
			}
			Application.getInstance().getMainWindow().getTimerThread()
					.setRelocatingNodesParams(nodes, newLocations, moveCommand);
		} else {
			for (Node n : nodes) {
				n.getPosition().setLocation(pos, n.getPosition().getY());
				pos += n.getSize().getWidth()
						+ InternalConfiguration.DISTRIBUTE_DISTANCE;
			}

			moveCommand.moveEnded();
			Application.getInstance().getCommandManager()
					.doCommand(moveCommand);
			Application.getInstance().getModel().updatePerformed();
		}
	}

	/**
	 * Sortira prosledjenu listu po poziciji od najmanje x vrednosti do najvece
	 * i vraca kao novu listu
	 * 
	 * @param nodes
	 *            - lista koja se sortira
	 * @return sortirana lista
	 */
	private ArrayList<Node> sort(ArrayList<Node> nodes) {
		ArrayList<Node> sorted = new ArrayList<Node>();
		while (nodes.size() != 0) {
			double min = nodes.get(0).getPosition().getX();
			int index = 0;
			for (Node n : nodes) {
				if (n.getPosition().getX() < min) {
					min = n.getPosition().getX();
					index = nodes.indexOf(n);
				}
			}
			sorted.add(nodes.remove(index));
		}

		return sorted;
	}

}
