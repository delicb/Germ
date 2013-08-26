package germ.gui.windows;

import germ.app.Application;
import germ.command.MoveCommand;
import germ.configuration.InternalConfiguration;
import germ.model.Link;
import germ.model.Node;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 * Izvrsava se konstantno dok traje aplikacija i pomera canvas za odredjeni
 * prosledjeni offset.
 */
public class TimerThread extends Thread {
	private boolean run = true;
	private Integer offsetX;
	private Integer offsetY;
	private double zoomSliderValue;
	private Point2D relocate = null;
	private ArrayList<Node> relocatingNodes = null;
	private ArrayList<Point2D> relocNodesPos = null;
	private ArrayList<Link> relocatingLinks = null;
	private ArrayList<ArrayList<Point>> breakPointsPos = null;
	private boolean fastZoomAndScale = false;
	private boolean ignoreMinStepLimit = false;
	private MoveCommand moveCommand = null;
	private double newScale = -1;


	public TimerThread(Integer offsetX, Integer offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		zoomSliderValue = 0;
	}

	@Override
	public void run() {
		while (true) {
			if (!run)
				return;
			try {
				Thread.sleep(InternalConfiguration.SCROLL_THREAD_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GERMView v = Application.getInstance().getMainWindow().getView();
			// ZA BACANJE KANVASA MISEM
			// if (v.lastPosition != null && ! canvasThrowen) {
			// mousePath = v.lastPosition.distance(previousMousePosition);
			// throwTranslateX = (v.lastPosition.getX() -
			// previousMousePosition.getX());
			// throwTranslateY = (v.lastPosition.getY() -
			// previousMousePosition.getY());
			// previousMousePosition = v.lastPosition;
			// }
			// if (canvasThrowen) {
			// System.out.println("bacen canvas");
			// double speed = 30*( mousePath /
			// (double)InternalConfiguration.SCROLL_THREAD_SLEEP);
			//
			//				
			//				
			// if (throwTranslateX < 0) { throwTranslateX += speed; }
			// else{ throwTranslateX -= speed;}
			// if (throwTranslateY < 0) { throwTranslateY += speed;}
			// else { throwTranslateY -= speed;}
			//				
			// v.moveWorkspace(throwTranslateX, throwTranslateY);
			//				
			// speed -= 10;
			// System.out.println("X: " + throwTranslateX + ", Y: " +
			// throwTranslateY + ", speed: " + speed);
			// if (speed < 0)
			// setCanvasThrowen(false);
			// }

			// scrool barovi
			if (offsetX != 0 || offsetY != 0)
				v.moveWorkspace(offsetX
						* InternalConfiguration.SCROLL_THREAD_FACTOR, offsetY
						* InternalConfiguration.SCROLL_THREAD_FACTOR);

			// zoom slider
			if (zoomSliderValue != 0) {
				v.zoom(zoomSliderValue * v.getTransform().getScaleX() * 2);
			}

			// animacija za skaliranje kanvasa
			if (newScale > 0) {
				double oldScale = v.getTransform().getScaleX();
				double step = 0.05;

				if (fastZoomAndScale) {
					step *= 3;
				}

				step *= oldScale;

				if (Math.abs(oldScale - newScale) < step
						&& Math.abs(oldScale - newScale) >= step / 2)
					step /= 2;

				if (Math.abs(oldScale - newScale) < step && !ignoreMinStepLimit) {
					newScale = -1;
					if (relocate == null)
						fastZoomAndScale = false;
				} else {
					if (oldScale < newScale)
						v.zoom(step);
					else
						v.zoom(-step);
				}
				ignoreMinStepLimit = false;
			}

			// animacija slaganja nodova na nove pozicije
			if (relocatingNodes != null) {
				int unmovedNodesCounter = 0;
				Point2D.Double locationDiff = new Point2D.Double();
				for (int i = 0; i < relocatingNodes.size(); i++) {
					relocateCalculator(relocatingNodes.get(i).getPosition(),
							relocNodesPos.get(i), locationDiff);
					relocatingNodes.get(i).getPosition().setLocation(
							relocatingNodes.get(i).getPosition().getX()
									- locationDiff.x,
							relocatingNodes.get(i).getPosition().getY()
									- locationDiff.y);

					if (locationDiff.x == 0 && locationDiff.y == 0) {
						unmovedNodesCounter++;
					}
				}
				// ako ima linkova, oni će uraditi repaint kada se pomere
				if (relocatingLinks == null)
					v.repaint();

				if (unmovedNodesCounter == relocatingNodes.size()) {
					relocatingNodes = null;
					relocNodesPos = null;
					Application.getInstance().getMainWindow()
							.restoreStatusBar();
					if (moveCommand != null) {
						moveCommand.moveEnded();
						Application.getInstance().getCommandManager()
								.doCommand(moveCommand);
					}
				}
			}

			// animacija slaganja breakpointova na nove pozicije
			// podrazumeva se da se svi breakpointovi isto pomeraju
			if (relocatingLinks != null) {
				double totalMove = 0;
				Point2D locationDiff = new Point2D.Double();
				for (int i = 0; i < relocatingLinks.size(); i++) {
					for (int j = 0; j < breakPointsPos.get(i).size(); j++) {
						Point currentBreakpoint = relocatingLinks.get(i)
								.getBreakPointAt(j);
						relocateCalculator(currentBreakpoint, breakPointsPos
								.get(i).get(j), locationDiff);
						currentBreakpoint.setLocation(currentBreakpoint.getX()
								- locationDiff.getX(), currentBreakpoint.getY()
								- locationDiff.getY());
						totalMove += Math.abs(locationDiff.getX())
								+ Math.abs(locationDiff.getY());
					}
				}

				v.repaint();

				if (totalMove == 0) {
					relocatingLinks = null;
					breakPointsPos = null;
					Application.getInstance().getMainWindow()
							.restoreStatusBar();
				}
			}

			// animacija pomeranja kanvasa
			if (relocate != null) {
				Point screenCenter = v.getCanvasCenter();
				Point2D.Double pos = new Point2D.Double();
				relocateCalculator((Point2D) screenCenter, relocate, pos);
				v.moveWorkspace(pos.x, pos.y);
				if (pos.x == 0 && pos.y == 0) {
					if (newScale == -1)
						fastZoomAndScale = false;
					relocate = null;
					Application.getInstance().getMainWindow()
							.restoreStatusBar();
				}
			}
			// System.out.println(offset);
		}
	}

	/**
	 * Metoda služi za relociranje elemenata ili ekrana (bilo koje tačke) pri
	 * čemu pomeranje vrši u koracima kako bi se stvorio efekat animacije.
	 * Metoda prilikom pomeranja uvažava, tj. odrežuje veličinu pomeraja na
	 * osnovu trenutne udaljenosti trenutne pozicije i odredišta kao i trenutnog
	 * zooma kanvasa.
	 * 
	 * @param currentPos
	 *            trenutna pozicija tačke koja se pomera
	 * @param destination
	 *            odredišna pozicija tačke
	 * @param diffPos
	 *            diferencijalna tačka novog položaja
	 */
	public void relocateCalculator(Point2D currentPos, Point2D destination,
			Point2D diffPos) {
		double xTranslate = 0;
		double yTranslate = 0;

		double scaleFactor = 1 / Math.sqrt(Math.sqrt(Math.sqrt(Application
				.getInstance().getView().getTransform().getScaleX())));
		double precision = InternalConfiguration.ANIMATE_TRANSLATION_PRECISION
				* scaleFactor;

		double stepX = precision
				* Math.abs(currentPos.getX() - destination.getX()) / 50;
		double stepY = precision
				* Math.abs(currentPos.getY() - destination.getY()) / 50;

		if (fastZoomAndScale) {
			stepX *= 2;
			stepY *= 2;
		}

		if (stepX < precision)
			stepX = precision;
		if (stepX > precision * 30 && !fastZoomAndScale)
			stepX = precision * 30;

		if (stepY < precision)
			stepY = precision;
		if (stepY > precision * 30 && !fastZoomAndScale)
			stepY = precision * 30;

		xTranslate *= scaleFactor;
		yTranslate *= scaleFactor;

		if (Math.abs(currentPos.getX() - destination.getX()) < stepX)
			xTranslate = 0;
		else if (currentPos.getX() < destination.getX())
			xTranslate = -stepX;
		else if (currentPos.getX() > destination.getX())
			xTranslate = stepX;

		if (Math.abs(currentPos.getY() - destination.getY()) < stepY)
			yTranslate = 0;
		else if (currentPos.getY() < destination.getY())
			yTranslate = -stepY;
		else if (currentPos.getY() > destination.getY())
			yTranslate = stepY;

		diffPos.setLocation(xTranslate, yTranslate);
	}

	public void setOffset(Integer offsetX, Integer offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void setZoomSliderValue(double zoomSliderValue) {
		this.zoomSliderValue = zoomSliderValue;
	}

	public void setRun(boolean value) {
		run = value;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setNewScale(double newScale) {
		this.newScale = newScale;
	}

	public void setRelocate(Point2D.Double relocate) {
		this.relocate = relocate;
	}

	public void setFastZoomAndScale(boolean fzas) {
		this.fastZoomAndScale = fzas;
	}

	public void setRelocatingNodesParams(ArrayList<Node> relocatingNodes,
			ArrayList<Point2D> relocNodesPos, MoveCommand moveCommand) {
		finalizeNodesMove();
		this.relocatingNodes = relocatingNodes;
		this.relocNodesPos = relocNodesPos;
		this.moveCommand = moveCommand;
	}

	public void setRelocatingBreakpoints(ArrayList<Link> relocatingLinks,
			ArrayList<ArrayList<Point>> breakPointsPos) {
		finalizeBreakpointsMove();
		if (relocatingLinks!= null && relocatingLinks.size() > 0)
			this.relocatingLinks = relocatingLinks;
		if (breakPointsPos != null && breakPointsPos.size() > 0)
			this.breakPointsPos = breakPointsPos;
	}

	/**
	 * Završava pomeranje nodova (šalje ih direktno na odredišnu lokaciju. Vrši
	 * proveru da li takvi nodovi postoje. Predviđeno za upotrebu u slučaju
	 * prekida animiranja (recimo kod vezanih UNDO operacija).
	 */
	public void finalizeNodesMove() {
		if (relocatingNodes == null || relocNodesPos == null)
			return;
		for (int i = 0; i < relocatingNodes.size(); i++) {
			relocatingNodes.get(i).getPosition().setLocation(
					relocNodesPos.get(i).getX(), relocNodesPos.get(i).getY());
		}
		Application.getInstance().getMainWindow().restoreStatusBar();
	}

	/**
	 * Završava pomeranje breakpointova (šalje ih direktno da odredišnu
	 * lokaciju. Vrši proveru da li takvi breakpointovi postoje. Predviđeno za
	 * upotrebu u slučaju prekida animiranja (recimo kod vezanih UNDO
	 * operacija).
	 */
	public void finalizeBreakpointsMove() {
		if (relocatingLinks == null || breakPointsPos == null)
			return;
		for (int i = 0; i < relocatingLinks.size(); i++) {
			for (int j = 0; j < breakPointsPos.get(i).size(); j++) {
				relocatingLinks.get(i).getBreakPointAt(j).setLocation(
						breakPointsPos.get(i).get(j).getX(),
						breakPointsPos.get(i).get(j).getY());
			}
		}
		Application.getInstance().getMainWindow().restoreStatusBar();
	}

	/**
	 * Omogućuje da se koristi isti algoritam i kod za pomeranje jednog
	 * breakpointa linka, kao i za više njih
	 * 
	 * @param link
	 *            čiji breakpoint se pomera
	 * @param newPositions
	 *            su nove pozicije breakpointova
	 */
	public void setRelocatingBreakpoint(Link link, ArrayList<Point> newPositions) {
		ArrayList<Link> links = new ArrayList<Link>();
		links.add(link);
		ArrayList<ArrayList<Point>> breakpoints = new ArrayList<ArrayList<Point>>();
		breakpoints.add(newPositions);
		this.relocatingLinks = links;
		this.breakPointsPos = breakpoints;
	}

	public void setIgnoreMinStepLimit(boolean ignoreMinStepLimit) {
		this.ignoreMinStepLimit = ignoreMinStepLimit;
	}


}
