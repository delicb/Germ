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
 * Akcija za distribuciju elemenata po vertikali
 * 
 */
@SuppressWarnings("serial")
public class DistributeVerticalAction extends AbstractGERMAction {

	public DistributeVerticalAction() {
		putValue(NAME, Messages.getString("DistributeVerticalAction.0"));
	}

	public void actionPerformed(ActionEvent e) {
		if (Application.getInstance().getModel().getSelectedNodes().size() < 2)
			return;
		// kreiramo novu move komandu
		MoveCommand moveCommand = new MoveCommand();
		ArrayList<Node> nodes = sort(Application.getInstance().getModel()
				.getSelectedNodes());
		ArrayList<Point2D> newLocations = new ArrayList<Point2D>();
		double pos = nodes.get(0).getPosition().getY();

		if (ConfigurationManager.getInstance().getBoolean("animationEnabled")) {
			MainWindow mw = Application.getInstance().getMainWindow();
			mw.backupStatusBar();
			mw.setStatusBarMessage(Messages.getString("DistributeVerticalAction.1"), 0);

			for (Node n : nodes) {
				newLocations
						.add(new Point2D.Double(n.getPosition().getX(), pos));
				pos += n.getSize().getHeight()
						+ InternalConfiguration.DISTRIBUTE_DISTANCE;
			}

			Application.getInstance().getMainWindow().getTimerThread()
					.setRelocatingNodesParams(nodes, newLocations, moveCommand);
		} else {
			for (Node n : nodes) {
				n.getPosition().setLocation(n.getPosition().getX(), pos);
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
	 * Sortira prosledjenu listu po poziciji od najmanje y vrednosti do najvece
	 * i vraca kaoke novu listu
	 * 
	 * @param nodes
	 *            - lista koja se sortira
	 * @return sortirana lista
	 */
	private ArrayList<Node> sort(ArrayList<Node> nodes) {
		ArrayList<Node> sorted = new ArrayList<Node>();
		while (nodes.size() != 0) {
			double min = nodes.get(0).getPosition().getY();
			int index = 0;
			for (Node n : nodes) {
				if (n.getPosition().getY() < min) {
					min = n.getPosition().getY();
					index = nodes.indexOf(n);
				}
			}
			sorted.add(nodes.remove(index));
		}

		return sorted;

	}

}
