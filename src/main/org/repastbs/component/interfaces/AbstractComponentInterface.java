/**
 * File: AbstractComponentInterface.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.interfaces;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Abstract implementation of ComponentInterface, 
 * all component interfaces should extend this class
 * @author Ludovit Hajzer
 *
 */
public abstract class AbstractComponentInterface extends AbstractComponent implements ComponentInterface {
	
	/** */
	private static final long serialVersionUID = -9116080094932780086L;
	
	/** */
	public static final String ID = "COMPONENT_INTERFACE";

	/**
	 * default constructor
	 * @param name
	 */
	public AbstractComponentInterface(String name) {
		super(name);
		setId(ID);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
			SAXUtils.start(handler, "interface",atts);
			SAXUtils.end(handler, "interface");
		} catch (SAXException e) {
			throw new XMLSerializationException("AbstractComponentInterface.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		//DO NOTHING
	}
}
