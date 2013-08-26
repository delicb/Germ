package germ.model;

import germ.gui.windows.PropertyWindow;

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

public abstract class Node extends GraphElement {

	/**
	 * Velicina elementa.
	 */
	protected Dimension size;
	/**
	 * Pozicija elementa na radnoj povrsini.
	 */
	protected Point2D position;
	/**
	 * Datum kreiranja elementa.
	 */
	protected Calendar creationDate;
	/**
	 * Zadnja promena podataka elementa.
	 */
	protected Calendar lastChangeDate;
	/**
	 * Lista veza koje za pocetak ili kraj imaju ovaj element.
	 */
	protected ArrayList<Link> links = new ArrayList<Link>();
	/**
	 * Stepeni skaliranja elementa po X i Y osi.
	 */
	protected double scaleX = 1, scaleY = 1;
	/**
	 * Polje koje obelezava da li je element prikazan kao u ogledalu.
	 * 1 - nije; -1 - jeste
	 */
	protected int flip = 1;

	public Node() {
	}

	public Node(Point2D position, Dimension size, Stroke stroke, Paint paint) {
		super(stroke, paint);
		this.size = size;
		this.position = position;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void addLink(Link l) {
		links.add(l);
	}

	public void removeLink(Link l) {
		links.remove(l);
	}

	public void removeAllLinks() {
		links.clear();
	}

	public boolean hasLinks() {
		return !links.isEmpty();
	}

	public Point2D getPosition() {
	    //if(this.flip < 0)
	    //    return new Point2D.Double(position.getX()+size.getWidth(),position.getY());
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Dimension getSize() {
		Dimension dim = new Dimension();
		dim = (Dimension) size.clone();
		dim.setSize(dim.getWidth() * scaleX, dim.getHeight() * scaleY);
		return dim;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Calendar lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		if (scaleX < 0.1)
			scaleX = 0.1;
		if (scaleX > 10)
			scaleX = 10;
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		if (scaleY < 0.1)
			scaleY = 0.1;
		if (scaleY > 10)
			scaleY = 10;
		this.scaleY = scaleY;
	}

	public int getFlip() {
		return flip;
	}

	public void setFlip(int flip) {
		this.flip = flip;
	}

	public Node clone(Node n) {
		super.clone(n);
		n.setPosition((Point2D)getPosition().clone());
		n.setSize(new Dimension(size.width, size.height));
		n.setCreationDate(getCreationDate());
		n.setFlip(getFlip());
		n.setScaleX(getScaleX());
		n.setScaleY(getScaleY());
		return n;
	}

	public void setColorProperties(boolean gradient, Color fillPrim,
			Color fillSec, Color strokeCol, float strokeThickness) {
		if (fillPrim != null) {
			this.setPrimColor(fillPrim);
			if (!gradient) {
				this.setSecColor(fillPrim);
			}

		}
		if (fillSec != null) {
			if (gradient) {
				this.setSecColor(fillSec);
			} else
				this.setSecColor(fillPrim);
		}

		this.setPaint(new GradientPaint(0, 0, getPrimColor(), size.width,
				size.height, getSecColor()));

		if (strokeCol != null) {
			this.setStrokeColor(strokeCol);
		}
		this.setStrokePaint(getStrokeColor());
		if (strokeThickness != 0.0f) {
			this.setStrokeThickness(strokeThickness);
			this.setStroke(new BasicStroke(strokeThickness,
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		}

	}

	public abstract PropertyWindow getPropertyWindow();

	public abstract void setProperties(boolean result);

	/**
	 * Koristi se kao pomocna metoda koju ce korisiti svaka naslednica ove klase
	 * da bi napisala svoje metode <code>getProperties</code>. Ovde se konkterno
	 * realizuje smestanje u HashMap-u atributa poznatih na ovom nivou.
	 * 
	 * @return HashMap-u sa parametrima o Nodu koji su poznati na ovom nivou
	 */
	protected HashMap<String, Object> getNodeProperties() {
		HashMap<String, Object> rez = new HashMap<String, Object>();
		rez.put("name", getName());
		rez.put("description", getDescription());

		rez.put("primColor", getPrimColor());
		rez.put("secColor", getSecColor());
		rez.put("paint", getPaint());
		rez.put("strokeColor", getStrokeColor());
		rez.put("strokePaint", getStrokePaint());
		rez.put("strokeThickness", getStrokeThickness());
		rez.put("stroke", getStroke());
		rez.put("lastChangedDate", getLastChangeDate());
		return rez;
	}

	/**
	 * Koristi se kao pomocna metoda koju ce korisiti svaka naslednica ove klase
	 * da bi napisala svoju metodu <code>setProperties</code>. Ovde se konkterno
	 * realizuje smestanje u ovaj objekat atributa poznatih na ovom nivou.
	 * 
	 * @param properties
	 *            HashMapa sa atributima (mora je metoda naslednjica kreirati)
	 */
	protected void setNodeProperties(HashMap<String, Object> properties) {
		this.setName((String) properties.get("name"));
		this.setDescription((String) properties.get("description"));
		this.setPrimColor((Color) properties.get("primColor"));
		this.setSecColor((Color) properties.get("secColor"));
		this.setPaint((Paint) properties.get("paint"));
		this.setStrokeColor((Color) properties.get("strokeColor"));
		this.setStrokePaint((Paint) properties.get("strokePaint"));
		this.setStrokeThickness((Float) properties.get("strokeThickness"));
		this.setStroke((Stroke) properties.get("stroke"));
		this.setLastChangeDate((Calendar) properties.get("lastChangedDate"));
	}

	public String toString() {
		return getName();
	}

	/**
	 * Generise HashMapu na sa svim podesavanjima ovog noda. Mapa je
	 * kompatibilna sa <code>setProperties</code> metodom koja se koristi da ova
	 * podesavanja posle snimi.
	 * 
	 * @return
	 */
	public abstract HashMap<String, Object> getProperties();

	/**
	 * Podesava vrednosti noda na osnovu HashMap-e. Koristi se prilikom menjanja
	 * podesavanja bilo kog noda da bi se lakse realizovao Undo-Redo menjanja
	 * podesavanja.
	 * 
	 * @param properties
	 *            HashMapa sa podesavanjima koja treba da se primene
	 */
	public abstract void setProperties(HashMap<String, Object> properties);

	public void flip() {
		this.flip = -this.flip;
	}
}
