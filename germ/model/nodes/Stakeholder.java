package germ.model.nodes;

import germ.app.Application;
import germ.command.SetPropertiesCommand;
import germ.configuration.ConfigurationManager;
import germ.gui.windows.PropertyWindow;
import germ.gui.windows.StakeholderProperties;
import germ.i18n.Messages;
import germ.model.Node;
import germ.view.painters.StakeholderPainter;

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
 * Klasa reprezentuje Stakeholdera u modelu.
 * 
 */
public class Stakeholder extends Node {

	/**
	 * Ime stakeholdera.
	 */
	private String stakeholdersName;
	/**
	 * Prezime stakeholdera
	 */
	private String stakeholdersSurname;
	/**
	 * Pozicija stakeholdera u odnosu na program za koji se vrsi specifikacija.
	 */
	private String stakeholderPosition;
	/**
	 * Kompanija u kojoj je stakholder zaposlen.
	 */
	private String company;
	/**
	 * Adresa srakeholdera.
	 */
	private String adress;
	/**
	 * E-Mail stakeholdera.
	 */
	private String mail;
	/**
	 * Telefon stakeholdera.
	 */
	private String telephone;
	private static PropertyWindow nodePropertyWindow = new StakeholderProperties();

	public Stakeholder(Point2D position, Dimension size, Stroke stroke,
			Paint paint) {
		super(position, size, stroke, paint);
		graphElementPainter = new StakeholderPainter(this);
	}

	private Stakeholder() {
	}

	public static Node createDefault(Point2D pos, int elemNo) {
		Point2D position = (Point2D) pos.clone();
		Stakeholder r = new Stakeholder();
		r.setSize(ConfigurationManager.getInstance().getDimension(
				"stakeholderSize")); //$NON-NLS-1$

		position.setLocation(position.getX() - r.getSize().width / 2, position
				.getY()
				- r.getSize().height / 2);

		r.setPosition(position);

		r.setStrokeThickness(ConfigurationManager.getInstance().getFloat(
				"stakeholderStrokeThickness")); //$NON-NLS-1$
		r.setStrokePaint(ConfigurationManager.getInstance().getColor(
				"stakeholderStrokeColor")); //$NON-NLS-1$
		r.setStrokeColor(ConfigurationManager.getInstance().getColor(
				"stakeholderStrokeColor")); //$NON-NLS-1$
		r.setPrimColor(ConfigurationManager.getInstance().getColor(
				"stakeholderFillPrimColor")); //$NON-NLS-1$
		r.setSecColor(ConfigurationManager.getInstance().getColor(
				"stakeholderFillSecColor")); //$NON-NLS-1$

		r.setStroke(new BasicStroke(r.getStrokeThickness(),
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		r.setPaint(new GradientPaint(0, 0, r.getPrimColor(), r.size.width,
				r.size.height, r.getSecColor()));
		r.setCreationDate(Calendar.getInstance());
		r.graphElementPainter = new StakeholderPainter(r);
		long no = Application.getInstance().getModel().getCounter("stakeholder"); //$NON-NLS-1$
		r.setName(Messages.getString("Stakeholder.0") + no); //$NON-NLS-1$
		r.setStakeholdersName(Messages.getString("Stakeholder.1")); //$NON-NLS-1$
		r.setStakeholdersSurname("" + no); //$NON-NLS-1$
		return r;
	}

	public String getStakeholdersName() {
		return stakeholdersName;
	}

	public void setStakeholdersName(String stakeholdersName) {
		this.stakeholdersName = stakeholdersName;
	}

	public String getStakeholdersSurname() {
		return stakeholdersSurname;
	}

	public void setStakeholdersSurname(String stakeholdersSurname) {
		this.stakeholdersSurname = stakeholdersSurname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setStakeholderPosition(String stakeholderPosition) {
		this.stakeholderPosition = stakeholderPosition;
	}

	public String getStakeholderPosition() {
		return stakeholderPosition;
	}

	public Stakeholder clone() {
		Stakeholder s = new Stakeholder();
		super.clone(s);
		s.graphElementPainter = new StakeholderPainter(s);
		s.setName(getStakeholdersName() + " " + getStakeholdersSurname()); //$NON-NLS-1$
		s.setStakeholdersName(getStakeholdersName());
		s.setStakeholdersSurname(getStakeholdersSurname());
		s.setStakeholderPosition(getStakeholderPosition());
		s.setCompany(getCompany());
		s.setAdress(getAdress());
		s.setMail(getMail());
		s.setTelephone(getTelephone());
		return s;
	}

	@Override
	public PropertyWindow getPropertyWindow() {
		StakeholderProperties properties = (StakeholderProperties) nodePropertyWindow;
		properties.setName(this.getStakeholdersName());
		properties.setSurname(this.getStakeholdersSurname());
		properties.setPosition(this.getStakeholderPosition());
		properties.setCompany(this.getCompany());
		properties.setAdress(this.getAdress());
		properties.setMail(this.getMail());
		properties.setTelephone(this.getTelephone());
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
			
			StakeholderProperties properties = (StakeholderProperties) nodePropertyWindow;
			
			// predstavlja nove vrednosti propertija
			HashMap<String, Object> newProperties = new HashMap<String, Object>();
			newProperties.put("name", properties.getName() + " " + properties.getSurname()); //$NON-NLS-1$ //$NON-NLS-2$
			newProperties.put("stekeholdersSurname", properties.getSurname()); //$NON-NLS-1$
			newProperties.put("stakeholdersName", properties.getName()); //$NON-NLS-1$
			newProperties.put("stakeholdersPosition", properties.getPosition()); //$NON-NLS-1$
			newProperties.put("company", properties.getCompany()); //$NON-NLS-1$
			newProperties.put("address", properties.getAdress()); //$NON-NLS-1$
			newProperties.put("mail", properties.getMail()); //$NON-NLS-1$
			newProperties.put("telephone", properties.getTelephone());			 //$NON-NLS-1$
			
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
		rez.put("stekeholdersSurname", getStakeholdersSurname()); //$NON-NLS-1$
		rez.put("stakeholdersName", getStakeholdersName()); //$NON-NLS-1$
		rez.put("stakeholdersPosition", getStakeholderPosition()); //$NON-NLS-1$
		rez.put("company", getCompany()); //$NON-NLS-1$
		rez.put("address", getAdress()); //$NON-NLS-1$
		rez.put("mail", getMail()); //$NON-NLS-1$
		rez.put("telephone", getTelephone()); //$NON-NLS-1$
		return rez;
	}

	@Override
	public void setProperties(HashMap<String, Object> properties) {
		super.setNodeProperties(properties);
		setStakeholdersName((String)properties.get("stakeholdersName")); //$NON-NLS-1$
		setStakeholdersSurname((String)properties.get("stekeholdersSurname")); //$NON-NLS-1$
		setStakeholderPosition((String)properties.get("stakeholdersPosition")); //$NON-NLS-1$
		setCompany((String)properties.get("company")); //$NON-NLS-1$
		setAdress((String)properties.get("address")); //$NON-NLS-1$
		setMail((String)properties.get("mail")); //$NON-NLS-1$
		setTelephone((String)properties.get("telephone")); //$NON-NLS-1$
	}
}
