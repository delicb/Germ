package germ.model;

import germ.view.GraphElementPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

/**
 * Apstraktna klasa koja opisuje bilo koji element dijagrama.
 * @author igor
 *
 */
public abstract class GraphElement {
	
	protected Paint paint;
	
	
	protected String name;
	protected String description;
	
	private float strokeThickness = 3.0f;
	private Color primColor = Color.WHITE;
	private Color secColor = Color.WHITE;
	private Color strokeColor = Color.BLACK;

	protected Stroke stroke = new BasicStroke(strokeThickness, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL);
	protected Paint strokePaint = strokeColor;
	
	public GraphElement(){}
	
	public Paint getStrokePaint() {
		return strokePaint;
	}

	public void setStrokePaint(Paint strokePaint) {
		this.strokePaint = strokePaint;
	}

	/**
	 * Instanciranje GraphObjectPainter-a obavljaju konkretni elementi
	 * prilikom svoje konstrukcije 
	 */
	protected GraphElementPainter graphElementPainter;
	
	public GraphElement(Stroke stroke, Paint paint){
		this.stroke = stroke;
		this.paint = paint;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GraphElementPainter getPainter() {
		return graphElementPainter;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public Color getPrimColor() {
		return primColor;
	}

	public void setPrimColor(Color primColor) {
		this.primColor = primColor;
	}

	public Color getSecColor() {
		return secColor;
	}

	public void setSecColor(Color secColor) {
		this.secColor = secColor;
	}
	
	public float getStrokeThickness() {
		return strokeThickness;
	}

	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}
	
	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	
	// ovo postoji samo da se olaksa clone u klasama naslednicama
	// ne moze nista konkretnije da uradi jer ne mozemo instancirati ovu klasu
	protected GraphElement clone(GraphElement ge) {
		ge.setName(getName());
		ge.setPaint(getPaint());
		ge.graphElementPainter = getPainter();
		ge.setStroke(getStroke());
		ge.setStrokePaint(getStrokePaint());
		ge.setPrimColor(getPrimColor());
		ge.setSecColor(getSecColor());
		ge.setStrokeColor(getStrokeColor());
		return ge;
	}
	
	// ovo mora da redefinise svaka klasa naslednica za sebe
	public abstract GraphElement clone();

}
