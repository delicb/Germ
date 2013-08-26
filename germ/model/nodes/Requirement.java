package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.PropertyWindow;
import germ.gui.windows.RequirementProperties;
import germ.i18n.Messages;
import germ.model.GERMModel;
import germ.model.Node;
import germ.model.workspace.Project;
import germ.view.painters.RequirementPainter;

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
 * Klasa reprezentuje Zahtev u modelu.
 * 
 */
public class Requirement extends Node {

	/**
	 * Prioritet zahteva.
	 */
	private String priority;
	/**
	 * Poddijagram zahteva.
	 */
	private transient GERMModel subDiagram;
	/**
	 * Naziv poddijagrama.
	 */
	private String subDiagramName;
	private static PropertyWindow nodePropertyWindow = new RequirementProperties();
	
	/**
	 * Ponasanje koje se zahteva.
	 */
	private String requirements;
	/**
	 * Autor zahteva
	 */
	private String author;
	/**
	 * Verzija zahteva
	 */
	private String version;
	/**
	 * Izvor ideje o zahtevu
	 */
	private String source;
	/**
	 * Reference na ostale zahteve
	 */
	private ArrayList<String> references;
	/**
	 * Status zahteva
	 */
	private String status;
	
	public Requirement(Point2D position, Dimension size, Stroke stroke,
			Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new RequirementPainter(this);
	}

	private Requirement() {
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Requirement r = new Requirement();
		r.setSize(ConfigurationManager.getInstance().getDimension(
				"requirementSize")); //$NON-NLS-1$

		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);
		r.setSubDiagram(null);
		r.setPosition(position);

		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"requirementStrokeThickness")); //$NON-NLS-1$
		r.setStrokePaint(ConfigurationManager.getInstance().getColor(
				"requirementStrokeColor")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"requirementStrokeColor")); //$NON-NLS-1$
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"requirementFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"requirementFillSecColor")); //$NON-NLS-1$

		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setCreationDate(Calendar.getInstance());
		r.graphElementPainter = new RequirementPainter(r);

		r.setName(Messages.getString("Requirement.6") + Application.getInstance().getModel().getCounter("requirement")); //$NON-NLS-1$ //$NON-NLS-2$
		return r;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isComplex() {
		return this.subDiagram != null;
	}

	public void setSubDiagram(GERMModel subDiagram) {
		this.subDiagram = subDiagram;
		if (subDiagram != null)
			this.subDiagramName = subDiagram.getName();
		;
	}
	
	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public ArrayList<String> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GERMModel getSubDiagram() {
		return this.subDiagram;
	}

	public Requirement clone() {
		Requirement r = new Requirement();
		super.clone(r);
		r.graphElementPainter = new RequirementPainter(r);
		r.setPriority(getPriority());
		r.setSubDiagram(getSubDiagram());
		r.setAuthor(getAuthor());
		r.setReferences(getReferences());
		r.setRequirements(getRequirements());
		r.setSource(getSource());
		r.setStatus(getStatus());
		r.setVersion(getVersion());
		r.setCreationDate(getCreationDate());
		r.setLastChangeDate(getLastChangeDate());
		return r;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		RequirementProperties properties = (RequirementProperties) nodePropertyWindow;
		properties.setName(this.getName());
		properties.setRequirementName(this.getName());
		properties.setDescription(this.getDescription());
		properties.setPriority(this.getPriority());
		properties.setComplex(this.isComplex());
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
		properties.initializeDiagrams();
		properties.initializeNewDiagramListener();
		if (this.getSubDiagram() != null)
			properties.setSubDiagram(this.getSubDiagram().getName());
		properties.initializeAuthors();
		properties.setAuthor(this.getAuthor());
		properties.setReferences(this.getReferences());
		properties.setRequirements(this.getRequirements());
		properties.setSource(this.getSource());
		properties.setStatus(this.getStatus());
		properties.setVersion(this.getVersion());
		properties.checkRequirements();
		return nodePropertyWindow;
	}

	@Override
	public void setProperties(boolean result) {
		if (result == true) {
			HashMap<String, Object> oldProperties = getProperties();

			RequirementProperties properties = (RequirementProperties) nodePropertyWindow;

			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName()); //$NON-NLS-1$
			newProperties.put("description", properties.getDescription()); //$NON-NLS-1$
			newProperties.put("priority", properties.getPriority()); //$NON-NLS-1$
			newProperties.put("complex", properties.getComplex()); //$NON-NLS-1$
			newProperties.put("primColor", properties.getFillPrimColor()); //$NON-NLS-1$
			if (properties.isGradient()) {
				newProperties.put("secColor", properties.getFillSecColor()); //$NON-NLS-1$
			} else {
				newProperties.put("secColor", properties.getFillPrimColor()); //$NON-NLS-1$
			}
			newProperties.put("paint", new GradientPaint(0, 0, //$NON-NLS-1$
					(Color) newProperties.get("primColor"), size.width, //$NON-NLS-1$
					size.height, (Color) newProperties.get("secColor"))); //$NON-NLS-1$
			newProperties.put("strokeColor", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put("strokePaint", properties.getStrokeColor()); //$NON-NLS-1$
			newProperties.put("strokeThickness", properties //$NON-NLS-1$
					.getStrokeThickness());
			newProperties.put("stroke", new BasicStroke(properties //$NON-NLS-1$
					.getStrokeThickness(), BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL));
			newProperties.put("lastChangedDate", Calendar.getInstance()); //$NON-NLS-1$
			newProperties.put("author",properties.getAuthor());
			newProperties.put("references",properties.getReferences());
			newProperties.put("requirements",properties.getRequirements());
			newProperties.put("source",properties.getSource());
			newProperties.put("status",properties.getStatus());
			newProperties.put("version",properties.getVersion());
			if (properties.getSubDiagram() != null) {
				Project currentProject = Application.getInstance().getModel()
						.getProject();
				GERMModel subDiagram = null;
				for (int i = 0; i != currentProject.getDiagramCount(); i++) {
					if (currentProject.getDiagram(i).getName().equals(
							properties.getSubDiagram())) {
						subDiagram = currentProject.getDiagram(i);
					}
				}

				if (subDiagram != null)
					newProperties.put("subDiagram", subDiagram); //$NON-NLS-1$
			}
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
		rez.put("priority", getPriority()); //$NON-NLS-1$
		rez.put("subDiagram", getSubDiagram()); //$NON-NLS-1$
		rez.put("author",getAuthor());
		rez.put("references",getReferences());
		rez.put("requirements",getRequirements());
		rez.put("source",getSource());
		rez.put("status",getStatus());
		rez.put("version",getVersion());
		return rez;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
		this.setPriority((String) properties.get("priority")); //$NON-NLS-1$
		this.setSubDiagram((GERMModel) properties.get("subDiagram")); //$NON-NLS-1$
		this.setAuthor((String) properties.get("author"));
		this.setReferences((ArrayList<String>) properties.get("references"));
		this.setRequirements((String) properties.get("requirements"));
		this.setSource((String) properties.get("source"));
		this.setStatus((String) properties.get("status"));
		this.setVersion((String) properties.get("version"));
	}

	public String getSubDiagramName() {
		return this.subDiagramName;
	}
	
	public RequirementProperties getNewPropertyWindow() {
		RequirementProperties properties = new RequirementProperties();
		properties.setName(this.getName());
		properties.setRequirementName(this.getName());
		properties.setDescription(this.getDescription());
		properties.setPriority(this.getPriority());
		properties.setComplex(this.isComplex());
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
		properties.initializeDiagrams();
		properties.initializeNewDiagramListener();
		if (this.getSubDiagram() != null)
			properties.setSubDiagram(this.getSubDiagram().getName());
		properties.initializeAuthors();
		properties.setAuthor(this.getAuthor());
		properties.setReferences(this.getReferences());
		properties.setRequirements(this.getRequirements());
		properties.setSource(this.getSource());
		properties.setStatus(this.getStatus());
		properties.setVersion(this.getVersion());
		properties.checkRequirements();
		
		return properties;
	}
}
