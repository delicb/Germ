package germ.command;

import germ.app.Application;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.MainWindow;
import germ.gui.windows.TimerThread;
import germ.model.GERMModel;
import germ.model.Link;
import germ.model.Node;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Pomeranje nodova i linkova.
 */
public class MoveCommand implements Command {
	/**
	 * pomereni nodovi.
	 */
	private ArrayList<Node> movedNodes;

	/**
	 * Početne pozicije nodova.
	 */
	private ArrayList<Point2D> startPositions = new ArrayList<Point2D>();

	/**
	 * Krajnje pozicije nodova.
	 */
	private ArrayList<Point2D> endPositions = new ArrayList<Point2D>();

	/**
	 * Pomereni linkovi;
	 */
	private ArrayList<Link> movedLinks;

	/**
	 * Sve počitne pozicije svih prelomnih tačaka svih pomerenih linkova.
	 */
	private ArrayList<ArrayList<Point>> breakPointStartPositions = null;

	/**
	 * Sve krajnje pozicije svih prelomnih tačaka svih pomerenih linkova.
	 */
	private ArrayList<ArrayList<Point>> breakPointEndPositions = null;

	/**
	 * Za Razlikovanje prvog poziva od redo-a
	 */
	private boolean firstTime = true;

	/**
	 * Pamti sve potrebne podatke da bi ova komanda mogla da se poništi.
	 */
	@SuppressWarnings("unchecked")
	public MoveCommand() {
		GERMModel m = Application.getInstance().getModel();
		movedNodes = (ArrayList<Node>) m.getSelectedNodes().clone();
		for (Node node : movedNodes) {
			startPositions.add((Point2D) node.getPosition().clone());
		}
		movedLinks = (ArrayList<Link>) m.loadAlteredLinks().clone();
		if (movedLinks.size() > 0) {
			breakPointStartPositions = new ArrayList<ArrayList<Point>>();
			breakPointEndPositions = new ArrayList<ArrayList<Point>>();
			for (Link link : movedLinks) {
				ArrayList<Point> newList = new ArrayList<Point>();
				for (int i = 0; i < link.getBreakPointCount(); i++) {
					newList.add((Point) link.getBreakPointAt(i).clone());
				}
				breakPointStartPositions.add(newList);
			}
		}
	}

	/**
	 * Pomera sve nodove i prelomne tačke linkova na krajnje pozicije.
	 */
	public void doCommand() {
		// prvi put nista ne radimo jer je pomeranje uradjeno u kontroleru
		if (!firstTime) {
			GERMModel m = Application.getInstance().getModel();
			if (ConfigurationManager.getInstance().getBoolean(
					"animationEnabled")) {
				MainWindow mw = Application.getInstance().getMainWindow();
				TimerThread tt = mw.getTimerThread();
				mw.backupStatusBar();
				mw.setStatusBarMessage("Redoing move...", 0);
				tt.setFastZoomAndScale(true);
				tt.setRelocatingNodesParams(movedNodes, endPositions, null);
				tt.setRelocatingBreakpoints(movedLinks, breakPointEndPositions);
			} else {
				for (int i = 0; i < movedNodes.size(); i++) {
					movedNodes.get(i).setPosition(endPositions.get(i));
				}
				for (int j = 0; j < movedLinks.size(); j++) {
					movedLinks.get(j).setBreakPoints(
							breakPointEndPositions.get(j));
				}
			}
			m.updatePerformed();
		}
		firstTime = false;
	}

	/**
	 * Pomera sve nodove i prelomne tačke linkova na početne pozicije.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		if (ConfigurationManager.getInstance().getBoolean("animationEnabled")) {
			MainWindow mw = Application.getInstance().getMainWindow();
			TimerThread tt = mw.getTimerThread();
			mw.backupStatusBar();
			mw.setStatusBarMessage("Undoing move...", 0);
			tt.setFastZoomAndScale(true);
			tt.setRelocatingNodesParams(movedNodes, startPositions, null);
			tt.setRelocatingBreakpoints(movedLinks, breakPointStartPositions);
		} else {
			for (int i = 0; i < movedNodes.size(); i++) {
				movedNodes.get(i).setPosition(startPositions.get(i));
			}
			for (int j = 0; j < movedLinks.size(); j++) {
				movedLinks.get(j).setBreakPoints(
						breakPointStartPositions.get(j));
			}
		}
		m.updatePerformed();
	}

	/**
	 * Obaveštava ovu komandu da je završeno pomeranje da bi pokupila potrebne
	 * podatke.
	 */
	public void moveEnded() {
		for (Node node : movedNodes) {
			endPositions.add((Point2D) node.getPosition().clone());
		}
		for (Link link : movedLinks) {
			ArrayList<Point> newList = new ArrayList<Point>();
			for (int i = 0; i < link.getBreakPointCount(); i++) {
				newList.add((Point) link.getBreakPointAt(i).clone());
			}
			breakPointEndPositions.add(newList);
		}
	}
}
