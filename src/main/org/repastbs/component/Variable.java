/**
 * File: Variable.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.dom4j.Node;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Variable component represents field in generated class
 * @author  ¼udovít Hajzer
 */
public class Variable extends AbstractComponent implements DynamicChanger, XMLSerializable {
	
	/** */
	private static final long serialVersionUID = 9024887132319543857L;
	
	/** */
	public static final String ID = "VARIABLE";
	
	private String type;
	
	private String defaultValue;
	
	private boolean accesible;
	
	private boolean parameter;
	
	/**
	 * @param name 
	 * 
	 */
	public Variable(String name) {
		this(name,null,null,false,false);
	}
	
	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @param accesible
	 * @param parameter
	 */
	public Variable(String name, String type, String defaultValue, 
			boolean accesible, boolean parameter) {
		super(name);
		this.type = type;
		if(type==null)
			this.type = "";
		this.defaultValue = defaultValue;
		this.accesible = accesible;
		this.parameter = parameter;
		setId(ID);
	}

	/**
	 * @return  the accesible
	 * @uml.property  name="accesible"
	 */
	public boolean isAccesible() {
		return accesible;
	}

	/**
	 * @param accesible  the accesible to set
	 * @uml.property  name="accesible"
	 */
	public void setAccesible(boolean accesible) {
		this.accesible = accesible;
	}

	/**
	 * @return  the defaultValue
	 * @uml.property  name="defaultValue"
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue  the defaultValue to set
	 * @uml.property  name="defaultValue"
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return  the parameter
	 * @uml.property  name="parameter"
	 */
	public boolean isParameter() {
		return parameter;
	}

	/**
	 * @param parameter  the parameter to set
	 * @uml.property  name="parameter"
	 */
	public void setParameter(boolean parameter) {
		this.parameter = parameter;
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
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
	    atts.addAttribute("", "name", "name", "CDATA", getName());
	    atts.addAttribute("", "class", "class", "CDATA", getType());
	    if(defaultValue!=null)
	    	atts.addAttribute("", "defaultValue", "defaultValue", "CDATA", getDefaultValue().toString());
	    atts.addAttribute("", "accesible", "accesible", "CDATA", 
	    		new StringBuffer().append(isAccesible()).toString());
	    atts.addAttribute("", "parameter", "parameter", "CDATA", 
	    		new StringBuffer().append(isParameter()).toString());
	    try {
			SAXUtils.start(handler, "variable", atts);
			SAXUtils.end(handler, "variable");
		} catch (SAXException e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setName(node.valueOf("@name"));
		this.setName(node.valueOf("@name"));
		this.setType(node.valueOf("@class"));
		this.setDefaultValue(node.valueOf("@defaultValue"));
		this.setAccesible(Boolean.parseBoolean(node.valueOf("@accesible")));
		this.setParameter(Boolean.parseBoolean(node.valueOf("@parameter")));
	}

	/**
	 * @param generator
	 * @throws DynamicException
	 */
	public void changeDynamicClass(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), type, defaultValue,accesible);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		this.setName("newVariable");
		this.setType("String");
		this.setDefaultValue("");
		this.setAccesible(false);
		this.setParameter(false);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), type, defaultValue, accesible);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//DO NOTHING
	}
}