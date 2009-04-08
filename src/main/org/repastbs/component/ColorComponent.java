/**
 * File: ColorComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.awt.Color;
import org.dom4j.Node;
import org.repastbs.generated.ColorComponentProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Simple component holding Color, as its value
 * @author  Ludovit Hajzer
 */
public class ColorComponent extends AbstractComponent implements XMLSerializable {
	
	/** */
	private static final long serialVersionUID = -4858653344360448240L;
	
	/** */
	public static final String ID = "COLOR";

	private ColorComponentProp colorComponentProp = new ColorComponentProp();

	private Color color;

	/**
	 * Default empty constructor
	 */
	public ColorComponent() {
		this("Color");
	}
	
	/**
	 * Default empty constructor
	 * @param name 
	 */
	public ColorComponent(String name) {
		this(name,Color.BLACK);
	}
	
	/**
	 * @param name
	 * @param color
	 */
	public ColorComponent(String name, Color color) {
		super(name);
		setColor(color);
		setId(ID);
	}

	/**
	 * @return  the color
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color  the color to set
	 * @uml.property  name="color"
	 */
	public void setColor(Color color) {
		this.color = color;
		colorComponentProp.setBlue(color.getBlue());
		colorComponentProp.setGreen(color.getGreen());
		colorComponentProp.setRed(color.getRed());
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {
			SAXUtils.start(handler, "color", atts);
			SAXUtils.text(handler,"red", ""+color.getRed());
			SAXUtils.text(handler,"green", ""+color.getGreen());
			SAXUtils.text(handler,"blue", ""+color.getBlue());
			SAXUtils.end(handler, "color");
		} catch (SAXException e) {
			throw new XMLSerializationException("ColorComponent.writeToXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		int red = Integer.parseInt(node.valueOf("red/text()"));
		int green = Integer.parseInt(node.valueOf("green/text()"));
		int blue = Integer.parseInt(node.valueOf("blue/text()"));
		color = new Color(red,green,blue);
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return false;
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
	}

	/**
	 * @return the colorComponentProp
	 */
	public ColorComponentProp getColorComponentProp() {
		return colorComponentProp;
	}

	/**
	 * @param colorComponentProp the colorComponentProp to set
	 */
	public void setColorComponentProp(ColorComponentProp colorComponentProp) {
		this.colorComponentProp = colorComponentProp;
	}
}
