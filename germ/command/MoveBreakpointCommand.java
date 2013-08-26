package germ.command;

import germ.app.Application;
import germ.gui.windows.MainWindow;
import germ.gui.windows.TimerThread;
import germ.model.GERMModel;
import germ.model.Link;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Pomeranje prelmne tačke linka.
 */
public class MoveBreakpointCommand implements Command {
	/**
	 * Link čija se prelomna tačka pomera.
	 */
	private Link movedLink;

	/**
	 * Početna pozicije prelomnih tačaka.
	 */
	private ArrayList<Point> startPositions = new ArrayList<Point>();

	/**
	 * Krajnje pozicije prelomih tačaka.
	 */
	private ArrayList<Point> endPositions = new ArrayList<Point>();

	/**
	 * Razlikovanje prvog pokretanja od ostalih (redo)
	 */
	private boolean firstTime = true;

	/**
	 * Pamti sve prelomne tačke selektovanog linka (njihove pozicije)
	 */
	public MoveBreakpointCommand() {
		GERMModel m = Application.getInstance().getModel();
		movedLink = m.getSelectedLink();
		for (Point breakpoint : movedLink.getBreakPoints()) {
			startPositions.add((Point) breakpoint.clone());
		}
	}

	/**
	 * Postavlja pozicije prelomnih tačaka na krajnje pozicije.
	 */
	public void doCommand() {
		if (!firstTime) {
			GERMModel m = Application.getInstance().getModel();
			MainWindow mw = Application.getInstance().getMainWindow();
			TimerThread tt = mw.getTimerThread();
			mw.backupStatusBar();
			mw.setStatusBarMessage("Redoing move...", 0);
			tt.setRelocatingBreakpoint(movedLink, endPositions);
			// for (int i = 0; i < movedLink.getBreakPoints().size(); i++) {
			// movedLink.getBreakPointAt(i).setLocation(endPositions.get(i));
			// }
			m.updatePerformed();
		}
		firstTime = false;
	}

	/**
	 * Vraća pozicije prelomnih tačaka na početne pozicije.
	 */
	public void undoCommand() {
		GERMModel m = Application.getInstance().getModel();
		MainWindow mw = Application.getInstance().getMainWindow();
		TimerThread tt = mw.getTimerThread();
		mw.backupStatusBar();
		mw.setStatusBarMessage("Undoing move...", 0);
		tt.setRelocatingBreakpoint(movedLink, startPositions);
		// for (int i = 0; i < movedLink.getBreakPoints().size(); i++) {
		// movedLink.getBreakPoints().get(i).setLocation(
		// (Point) startPositions.get(i).clone());
		// }
		m.updatePerformed();
	}

	/**
	 * Javlaja ovoj komandi da je pomeranje završeno da bi pokupila krajnje
	 * pozicije.
	 */
	public void moveEnded() {
		for (Point breakpoint : movedLink.getBreakPoints()) {
			endPositions.add((Point) breakpoint.clone());
		}
	}
}
