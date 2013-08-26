package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.DecisionProperties;
import germ.gui.windows.PropertyWindow;
import germ.i18n.Messages;
import germ.model.Node;
import germ.view.painters.DecisionPainter;

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
 * Klasa reprezentuje Odluku u modelu.
 * 
 */
public class Decision extends Node {

	/**
	 * Autor odluke.
	 */
	private String author;
	/**
	 * Oznacava da li je zavrsena diskusija o odluci.
	 */
	private boolean finalized;
	/**
	 * Stanje Odluke 
	 * 
	 * {@link germ.model.nodes.DecisionValue}
	 */
	private DecisionValue value;
	private static PropertyWindow nodePropertyWindow = new DecisionProperties();

	private Decision() {
	}

	public Decision(Point2D position, Dimension size, Stroke stroke, Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new DecisionPainter(this);
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Decision r = new Decision();
		r.setSize(ConfigurationManager.getInstance().getDimension(
				"decisionSize")); //$NON-NLS-1$
		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"decisionStrokeThickness")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"decisionStrokeColor")); //$NON-NLS-1$
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"decisionFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"decisionFillSecColor")); //$NON-NLS-1$

		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);

		r.setPosition(position);
		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setStrokePaint(r.getStrokeColor());
		r.setCreationDate(Calendar.getInstance());
		r.graphElementPainter = new DecisionPainter(r);
		r.setName(Messages.getString("Decision.5") + Application.getInstance().getModel().getCounter("decision")); //$NON-NLS-1$ //$NON-NLS-2$
		r.setValue(DecisionValue.INTERNAL_DECISION);
		return r;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	public DecisionValue getValue() {
		return value;
	}

	public void setValue(DecisionValue value) {
		this.value = value;
		((DecisionPainter) graphElementPainter).repaint(this);
	}

	public Decision clone() {
		Decision d = new Decision();
		super.clone(d);
		d.graphElementPainter = new DecisionPainter(d);
		d.setAuthor(getAuthor());
		d.setFinalized(isFinalized());
		d.setValue(getValue());
		return d;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		DecisionProperties properties = (DecisionProperties) nodePropertyWindow;
		properties.setName(this.getName());
		properties.setDescription(this.getDescription());
		properties.setAuthor(this.getAuthor());
		properties.setValue(this.getValue());
		properties.setFinalized(this.isFinalized());
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
			
			DecisionProperties properties = (DecisionProperties) nodePropertyWindow;
			
			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName()); //$NON-NLS-1$
			newProperties.put("description", properties.getDescription()); //$NON-NLS-1$
			newProperties.put("author", properties.getAuthor()); //$NON-NLS-1$
			newProperties.put("value", properties.getValue()); //$NON-NLS-1$
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
			newProperties.put("finalized", properties.getFinalized()); //$NON-NLS-1$
			
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(this);
			SetPropertiesCommand propetiesCommand = new SetPropertiesCommand(nodes, oldProperties, newProperties);
			Application.getInstance().getCommandManager().doCommand(propetiesCommand);
		}
	}
	
	@Override
	public HashMap<String, Object> getProperties() {
		HashMap<String, Object> rez = super.getNodeProperties();
		rez.put("author", getAuthor()); //$NON-NLS-1$
		rez.put("value", getValue()); //$NON-NLS-1$
		rez.put("finalized", isFinalized());		 //$NON-NLS-1$
		return rez;
	}

	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
		setAuthor((String)properties.get("author")); //$NON-NLS-1$
		setValue((DecisionValue)properties.get("value")); //$NON-NLS-1$
		setFinalized((Boolean)properties.get("finalized")); //$NON-NLS-1$
		
	}
}
