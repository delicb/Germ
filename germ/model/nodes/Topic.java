package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.PropertyWindow;
import germ.gui.windows.TopicProperties;
import germ.i18n.Messages;
import germ.model.Node;
import germ.view.painters.TopicPainter;

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
 * Klasa reprezentuje Temu o kojoj se rapsravlja u modelu.
 * 
 */
public class Topic extends Node {

	/**
	 * Autor teme.
	 */
	private String author;
	/**
	 * Polje odredjuje da li je tema zatvorena.
	 */
	private boolean closed;
	/**
	 * Osoba koja je zatvorila temu.
	 */
	private String topicCloser;
	private static PropertyWindow nodePropertyWindow = new TopicProperties();

	private Topic() {
	}

	public Topic(Point2D position, Dimension size, Stroke stroke, Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new TopicPainter(this);
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Topic r = new Topic();
		r.setSize(ConfigurationManager.getInstance().getDimension("topicSize")); //$NON-NLS-1$

		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);

		r.setPosition(position);
		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"topicStrokeThickness")); //$NON-NLS-1$
		r.setStrokePaint(ConfigurationManager.getInstance().getColor(
				"topicStrokeColor")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"topicStrokeColor")); //$NON-NLS-1$
		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"topicFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"topicFillSecColor")); //$NON-NLS-1$
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setCreationDate(Calendar.getInstance());
		r.graphElementPainter = new TopicPainter(r);
		r.setName(Messages.getString("Topic.6") + Application.getInstance().getModel().getCounter("topic")); //$NON-NLS-1$ //$NON-NLS-2$
		return r;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getTopicCloser() {
		return topicCloser;
	}

	public void setTopicCloser(String topicCloser) {
		this.topicCloser = topicCloser;
	}

	public Topic clone() {
		Topic t = new Topic();
		super.clone(t);
		t.graphElementPainter = new TopicPainter(t);
		t.setAuthor(getAuthor());
		t.setClosed(isClosed());
		t.setTopicCloser(getTopicCloser());
		return t;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		TopicProperties properties = (TopicProperties) nodePropertyWindow;
		properties.setName(this.getName());
		properties.setDescription(this.getDescription());
		properties.initializeStakeholders();
		properties.setAuthor(this.getAuthor());
		properties.setClosed(this.isClosed());
		properties.setTopicCloser(this.getTopicCloser());
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
			
			TopicProperties properties = (TopicProperties) nodePropertyWindow;
			
			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName()); //$NON-NLS-1$
			newProperties.put("description", properties.getDescription()); //$NON-NLS-1$
			newProperties.put("author", properties.getAuthor()); //$NON-NLS-1$
			newProperties.put("closed", properties.getClosed()); //$NON-NLS-1$
			newProperties.put("topicCloser", properties.getTopicCloser()); //$NON-NLS-1$
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
		rez.put("author", getAuthor()); //$NON-NLS-1$
		rez.put("closed", isClosed()); //$NON-NLS-1$
		rez.put("topicCloser", getTopicCloser()); //$NON-NLS-1$
		return rez;
	}

	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
		this.setAuthor((String) properties.get("author")); //$NON-NLS-1$
		this.setClosed((Boolean) properties.get("closed")); //$NON-NLS-1$
		this.setTopicCloser((String)properties.get("topicCloser")); //$NON-NLS-1$
	}
}
