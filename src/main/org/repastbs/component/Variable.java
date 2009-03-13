/**
 * File: Variable.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.dom4j.Node;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.VariableProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Variable component represents field in generated class
 * @author  Ľudovít Hajzer
 */
public class Variable extends AbstractComponent implements DynamicChanger, XMLSerializable {
	
	/** */
	private static final long serialVersionUID = 9024887132319543857L;
	
	/** */
	private VariableProp variableprop = new VariableProp();
	
	/** */
	public static final String ID = "VARIABLE";
	
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
		this.variableprop.setType(type);
		if(type==null)
			this.variableprop.setType("");
		this.variableprop.setDefaultValue(defaultValue);
		this.variableprop.setAccessible(accesible);
		this.variableprop.setParameter(parameter);
		setId(ID);
	}

	/**
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public VariableProp getVariableprop() {
		return variableprop;
	}

	/**
	 * @param type  the type to set
	 * @uml.property  name="type"
	 */
	public void setVariableprop(VariableProp variableprop) {
		this.variableprop = variableprop;
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
	    atts.addAttribute("", "name", "name", "CDATA", getName());
	    atts.addAttribute("", "class", "class", "CDATA", variableprop.getType());
	    if(variableprop.getDefaultValue()!=null)
	    	atts.addAttribute("", "defaultValue", "defaultValue", "CDATA", variableprop.getDefaultValue().toString());
	    atts.addAttribute("", "accessible", "accessible", "CDATA", 
	    		new StringBuffer().append(variableprop.isAccessible()).toString());
	    atts.addAttribute("", "parameter", "parameter", "CDATA", 
	    		new StringBuffer().append(variableprop.isParameter()).toString());
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
		//this.setName(node.valueOf("@name"));  ??? why is twice ???
		this.variableprop.setType(node.valueOf("@class"));
		this.variableprop.setDefaultValue(node.valueOf("@defaultValue"));
		this.variableprop.setAccessible(Boolean.parseBoolean(node.valueOf("@accessible")));
		this.variableprop.setParameter(Boolean.parseBoolean(node.valueOf("@parameter")));
	}

	/**
	 * @param generator
	 * @throws DynamicException
	 */
	public void changeDynamicClass(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), variableprop.getType(), variableprop.getDefaultValue(), variableprop.isAccessible());
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		this.setName("newVariable");
		this.variableprop.setType("String");
		this.variableprop.setDefaultValue("");
		this.variableprop.setAccessible(false);
		this.variableprop.setParameter(false);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), variableprop.getType(), variableprop.getDefaultValue(), variableprop.isAccessible());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//DO NOTHING
	}
}