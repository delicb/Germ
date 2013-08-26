package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.PositionProperties;
import germ.gui.windows.PropertyWindow;
import germ.i18n.Messages;
import germ.model.Node;
import germ.view.painters.PositionPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Klasa reprezentuje Poziciju u modelu.
 * 
 */
public class Position extends Node {

	private static PropertyWindow nodePropertyWindow = new PositionProperties();

	private Position() {
	}

	public Position(Point2D position, Dimension size, Stroke stroke, Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new PositionPainter(this);
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Position r = new Position();
		r.setSize(ConfigurationManager.getInstance().getDimension(
				"positionSize")); //$NON-NLS-1$

		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);

		r.setPosition(position);

		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"positionStrokeThickness")); //$NON-NLS-1$
		r.setStrokePaint(ConfigurationManager.getInstance().getColor(
				"positionStrokeColor")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"positionStrokeColor")); //$NON-NLS-1$
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"positionFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"positionFillSecColor")); //$NON-NLS-1$

		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setCreationDate(Calendar.getInstance());
		r.graphElementPainter = new PositionPainter(r);
		r.setName(Messages.getString("Position.0") + Application.getInstance().getModel().getCounter("position")); //$NON-NLS-1$ //$NON-NLS-2$
		return r;
	}

	public Position clone() {
		Position p = new Position();
		super.clone(p);
		p.graphElementPainter = new PositionPainter(p);
		return p;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		PositionProperties properties = (PositionProperties) nodePropertyWindow;
		properties.setName(this.getName());
		properties.setDescription(this.getDescription());
		properties.setFillPrimColor(this.getPrimColor());
		properties.setFillSecColor(this.getSecColor());
		properties.setStrokeThickness(this.getStrokeThickness());
		properties.setCbStrokeThickness(properties.getStrokeThickness());
		properties.setStrokeColor(this.getStrokeColor());
		properties.setGradient(!this.getPrimColor().equals(this.getSecColor()));
		properties.setDateCreated(this.getCreationDate().getTime().toString());
		if (this.getLastChangeDate() != null) {
			properties.setDateChanged(this.getLastChangeDate().getTime()
					.toString());
		} else
			properties.setDateChanged("###"); //$NON-NLS-1$
		return nodePropertyWindow;
	}

	@Override
	public void setProperties(boolean result) {
		if (result == true) {
			HashMap<String, Object> oldProperties = getProperties();
			
			PositionProperties properties = (PositionProperties) nodePropertyWindow;
			
			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName()); //$NON-NLS-1$
			newProperties.put("description", properties.getDescription()); //$NON-NLS-1$
			newProperties.put("primColor", properties.getFillPrimColor()); //$NON-NLS-1$
			if (properties.isGradient()) {
				newProperties.put("secColor", properties.getFillSecColor()); //$NON-NLS-1$
			}
			else {
				newProperties.put("secColor", properties.getFillPrimColor()); //$NON-NLS-1$
			}
			newProperties.put("paint", new GradientPaint(0, 0, (Color)newProperties.get("primColor"), //$NON-NLS-1$ //$NON-NLS-2$
					size.width, size.height, (Color)newProperties.get("secColor"))); //$NON-NLS-1$
			newProperties.put("strokeColor", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put("strokePaint", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put("strokeThickness", properties.getStrokeThickness()); //$NON-NLS-1$
			newProperties.put("stroke", new BasicStroke(properties.getStrokeThickness(), //$NON-NLS-1$
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			newProperties.put("lastChangedDate", Calendar.getInstance()); //$NON-NLS-1$
			
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(this);
			SetPropertiesCommand propetiesCommand = new SetPropertiesCommand(nodes, oldProperties, newProperties);
			Application.getInstance().getCommandManager().doCommand(propetiesCommand);
		}
	}
	
	@Override
	public HashMap<String, Object> getProperties() {
		HashMap<String, Object> rez = super.getNodeProperties();
		return rez;
	}

	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
	}
}
