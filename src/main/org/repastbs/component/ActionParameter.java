/**
 * File: ActionParameter.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.dom4j.Node;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Class representing action parameter
 * @author  ¼udovít Hajzer
 */
public class ActionParameter extends AbstractComponent implements XMLSerializable {
	
	/** */
	private static final long serialVersionUID = -6847100055693204162L;
	
	/**  */
	public static final String ID = "ACTION_PARAMETER";
	
	private String type;
	
	/**
	 * 
	 */
	public ActionParameter() {
		this(null);
	}
	
	/**
	 * @param type - type of this action parameter
	 */
	public ActionParameter(String type) {
		super("Parameter");
		this.type = type;
		setId(ID);
	}

	/**
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type  the type to set
	 * @uml.property  name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		type = "java.lang.Object";
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setType(node.valueOf("text()"));
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			SAXUtils.text(handler, "parameter", getType());
		} catch (SAXException e) {
			throw new XMLSerializationException("ActionParameter.writeToXML: "+e.getMessage(),e);
		}
	}
	
	/**
	 * @see org.repastbs.component.AbstractComponent#toString()
	 */
	public String toString() {
		return getName() + " "+getType();
	}
}
