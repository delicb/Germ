package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.AssumptionProperties;
import germ.gui.windows.PropertyWindow;
import germ.i18n.Messages;
import germ.model.Node;
import germ.view.painters.AssumptionPainter;

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
 * Klasa reprezentuje Pretpostavku u modelu.
 * 
 */
public class Assumption extends Node {

	/**
	 * Verovatnoca Pretpostavke.
	 */
	private String probability;
	/**
	 * Izvor Pretpostavke.
	 */
	private String source;
	private static PropertyWindow nodePropertyWindow = new AssumptionProperties();

	private Assumption() {
	}

	public Assumption(Point2D position, Dimension size, Stroke stroke,
			Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new AssumptionPainter(this);
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Assumption r = new Assumption();
		r.setSize(ConfigurationManager.getInstance().getDimension(
				"assumptionSize")); //$NON-NLS-1$
		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"assumptionStrokeThickness")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"assumptionStrokeColor")); //$NON-NLS-1$
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"assumptionFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"assumptionFillSecColor")); //$NON-NLS-1$
		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);

		r.setPosition(position);
		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setCreationDate(Calendar.getInstance());
		r.setStrokePaint(r.getStrokeColor());
		r.graphElementPainter = new AssumptionPainter(r);
		r
				.setName(Messages.getString("Assumption.5") + Application.getInstance().getModel().getCounter("assumption")); //$NON-NLS-1$ //$NON-NLS-2$
		return r;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		AssumptionProperties properties = (AssumptionProperties) nodePropertyWindow;
		properties.setName(this.getName());
		properties.setDescription(this.getDescription());
		properties.setProbability(this.getProbability());
		properties.initializeSources();
		properties.setSource(this.getSource());
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

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Assumption clone() {
		Assumption a = new Assumption();
		super.clone(a);
		a.graphElementPainter = new AssumptionPainter(a);
		a.setProbability(getProbability());
		a.setSource(getSource());
		return a;
	}

	@Override
	public void setProperties(boolean result) {
		if (result == true) {
			HashMap<String, Object> oldProperties = getProperties();

			AssumptionProperties properties = (AssumptionProperties) nodePropertyWindow;

			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName()); //$NON-NLS-1$
			newProperties.put("description", properties.getDescription()); //$NON-NLS-1$
			newProperties.put("probability", properties.getProbability()); //$NON-NLS-1$
			newProperties.put("source", properties.getSource()); //$NON-NLS-1$
			newProperties.put("primColor", properties.getFillPrimColor()); //$NON-NLS-1$
			if (properties.isGradient()) {
				newProperties.put("secColor", properties.getFillSecColor()); //$NON-NLS-1$
			} else {
				newProperties.put("secColor", properties.getFillPrimColor()); //$NON-NLS-1$
			}
			newProperties
					.put(
							"paint", new GradientPaint(0, 0, (Color) newProperties.get("primColor"), //$NON-NLS-1$ //$NON-NLS-2$
									size.width, size.height,
									(Color) newProperties.get("secColor"))); //$NON-NLS-1$
			newProperties.put("strokeColor", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put("strokePaint", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put(
					"strokeThickness", properties.getStrokeThickness()); //$NON-NLS-1$
			newProperties.put(
					"stroke", new BasicStroke(properties.getStrokeThickness(), //$NON-NLS-1$
							BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			newProperties.put("lastChangedDate", Calendar.getInstance()); //$NON-NLS-1$

			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(this);
			SetPropertiesCommand propetiesCommand = new SetPropertiesCommand(
					nodes, oldProperties, newProperties);
			Application.getInstance().getCommandManager().doCommand(
					propetiesCommand);
		}

	}

	@Override
	public HashMap<String, Object> getProperties() {
		HashMap<String, Object> rez = super.getNodeProperties();
		rez.put("probability", getProbability()); //$NON-NLS-1$
		rez.put("source", getSource()); //$NON-NLS-1$
		return rez;
	}

	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
		this.setProbability((String) properties.get("probability")); //$NON-NLS-1$
		this.setSource((String) properties.get("source")); //$NON-NLS-1$
	}

}
