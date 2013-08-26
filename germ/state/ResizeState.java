package germ.state;

import germ.app.Application;
import germ.command.ResizeCommand;
import germ.model.Node;
import germ.view.GERMView;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class ResizeState extends State {

	private Point NW;
	private Point SE;
	ResizeCommand resizeCommand;

	public void mouseDragged(MouseEvent e) {
		GERMView v = Application.getInstance().getView();
		Point mousePosition = (Point) v.lastPosition;
		Node node = v.getNodeChangingShape();
		double nodeWidth = node.getSize().getWidth();
		double nodeHeight = node.getSize().getHeight();

		int moveStep = 10;
		int moveX = 0;
		int moveY = 0;
		double width = v.getWidth();
		double height = v.getHeight();
		double tolerance = 20;

		switch (v.getHandleMoving()) {
		case EAST: {
			double width2 = mousePosition.getX() - NW.getX();
			node.setScaleX(node.getScaleX() * width2 / nodeWidth);
			break;
		}

		case WEST: {
			double width2 = SE.getX() - mousePosition.getX();
			if ((node.getScaleX() * width2 / nodeWidth) >= 0.1
					&& (node.getScaleX() * width2 / nodeWidth) <= 10) {
				node.setScaleX(node.getScaleX() * width2 / nodeWidth);
				node.setPosition(new Point2D.Double(mousePosition.getX(), node
						.getPosition().getY()));
			}
			break;
		}

		case NORTH: {
			double height2 = SE.getY() - mousePosition.getY();
			if ((node.getScaleY() * height2 / nodeHeight) >= 0.1
					&& (node.getScaleY() * height2 / nodeHeight) <= 10) {
				node.setScaleY(node.getScaleY() * height2 / nodeHeight);
				node.setPosition(new Point2D.Double(node.getPosition().getX(),
						mousePosition.getY()));
			}
			break;
		}

		case SOUTH: {
			double height2 = mousePosition.getY() - NW.getY();
			node.setScaleY(node.getScaleY() * height2 / nodeHeight);
			break;
		}

		case SOUTHEAST: {
			double height2 = mousePosition.getY() - NW.getY();
			double width2 = mousePosition.getX() - NW.getX();

			node.setScaleX(node.getScaleX() * width2 / nodeWidth);
			if (e.isShiftDown()) // keep aspect ratio
				if (e.isControlDown()) // reset aspect ratio
					node.setScaleY(node.getScaleY() * width2 / nodeHeight);
				else
					node.setScaleY(node.getScaleY() * width2 / nodeWidth);
			else
				node.setScaleY(node.getScaleY() * height2 / nodeHeight);
			break;
		}

		case SOUTHWEST: {
			double height2 = mousePosition.getY() - NW.getY();
			double width2 = SE.getX() - mousePosition.getX();

			if (e.isShiftDown())
				if (e.isControlDown())
					node.setScaleY(node.getScaleY() * width2 / nodeHeight);
				else
					node.setScaleY(node.getScaleY() * width2 / nodeWidth);
			else
				node.setScaleY(node.getScaleY() * height2 / nodeHeight);

			if ((node.getScaleX() * width2 / nodeWidth) >= 0.1
					&& (node.getScaleX() * width2 / nodeWidth) <= 10) {
				node.setScaleX(node.getScaleX() * width2 / nodeWidth);
				node.setPosition(new Point2D.Double(mousePosition.getX(), node
						.getPosition().getY()));
			}

			break;
		}

		case NORTHEAST: {
			double height2 = SE.getY() - mousePosition.getY();
			double width2 = mousePosition.getX() - NW.getX();

			if ((node.getScaleY() * height2 / nodeHeight) >= 0.1
					&& (node.getScaleY() * height2 / nodeHeight) <= 10) {
				node.setScaleY(node.getScaleY() * height2 / nodeHeight);
				node.setPosition(new Point2D.Double(node.getPosition().getX(),
						mousePosition.getY()));
			}
			if (e.isShiftDown())
				if (e.isControlDown())
					node.setScaleX(node.getScaleX() * height2 / nodeWidth);
				else
					node.setScaleX(node.getScaleX() * height2 / nodeHeight);
			else
				node.setScaleX(node.getScaleX() * width2 / nodeWidth);

			break;
		}

		case NORTHWEST: {
			double height2 = SE.getY() - mousePosition.getY();
			double width2 = SE.getX() - mousePosition.getX();
			double dif = node.getPosition().getX() - mousePosition.getX();

			if ((node.getScaleX() * width2 / nodeWidth) >= 0.1
					&& (node.getScaleX() * width2 / nodeWidth) <= 10) {
				node.setScaleX(node.getScaleX() * width2 / nodeWidth);
				node.setPosition(new Point2D.Double(mousePosition.getX(), node
						.getPosition().getY()));
			}
			if ((node.getScaleY() * height2 / nodeHeight) >= 0.1
					&& (node.getScaleY() * height2 / nodeHeight) <= 10) {
				if (e.isShiftDown()) {
					if (e.isControlDown())
						node.setScaleY(node.getScaleY() * width2 / nodeHeight);
					else
						node.setScaleY(node.getScaleY() * width2 / nodeWidth);
					node.setPosition(new Point2D.Double(node.getPosition()
							.getX(), node.getPosition().getY() - dif));
				} else {
					node.setScaleY(node.getScaleY() * height2 / nodeHeight);
					node.setPosition(new Point2D.Double(node.getPosition()
							.getX(), mousePosition.getY()));
				}
			}
		}
		}

		// transliranje canvasa
		if (e.getX() >= width - tolerance) {
			moveX -= moveStep;
		}

		if (e.getX() <= tolerance) {
			moveX += moveStep;
		}

		if (e.getY() >= height - tolerance) {
			moveY -= moveStep;
		}

		if (e.getY() <= tolerance) {
			moveY += moveStep;
		}

		v.moveWorkspace(moveX, moveY);
	}

	public void initializeResize(Point position) {
		GERMView v = Application.getInstance().getView();
		Node node = v.getNodeChangingShape();
		resizeCommand = new ResizeCommand();
		NW = new Point((int) node.getPosition().getX(), (int) node
				.getPosition().getY());
		SE = new Point((int) (node.getPosition().getX() + node.getSize()
				.getWidth()), (int) (node.getPosition().getY() + node.getSize()
				.getHeight()));
	}

	public void mouseReleased(MouseEvent e) {
		resizeCommand.resizeEnded();
		Application.getInstance().getCommandManager().doCommand(resizeCommand);
		Application.getInstance().getView().setNodeChangingShape(null);
		Application.getInstance().getView().setHandleMoving(null);
		Application.getInstance().getStateMachine()
				.setState(State.defaultState);
	}
}
