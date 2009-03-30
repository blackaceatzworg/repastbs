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
	private VariableProp variableProp = new VariableProp();
	
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
	 * @param accessible
	 * @param parameter
	 */
	public Variable(String name, String type, String defaultValue, 
			boolean accessible, boolean parameter) {
		super(name);
		this.variableProp.setType(type);
		if(type==null)
			this.variableProp.setType("");
		this.variableProp.setDefaultValue(defaultValue);
		this.variableProp.setAccessible(accessible);
		this.variableProp.setParameter(parameter);
		this.variableProp.setName(name);
		setId(ID);
	}

	/**
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public VariableProp getVariableProp() {
		return variableProp;
	}

	/**
	 * @param variableprop 
	 * @uml.property  name="type"
	 */
	public void setVariableProp(VariableProp variableprop) {
		this.variableProp = variableprop;
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
	    atts.addAttribute("", "name", "name", "CDATA", getName());
	    atts.addAttribute("", "class", "class", "CDATA", variableProp.getType());
	    if(variableProp.getDefaultValue()!=null)
	    	atts.addAttribute("", "defaultValue", "defaultValue", "CDATA", variableProp.getDefaultValue().toString());
	    atts.addAttribute("", "accessible", "accessible", "CDATA", 
	    		new StringBuffer().append(variableProp.isAccessible()).toString());
	    atts.addAttribute("", "parameter", "parameter", "CDATA", 
	    		new StringBuffer().append(variableProp.isParameter()).toString());
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
		this.variableProp.setType(node.valueOf("@class"));
		this.variableProp.setDefaultValue(node.valueOf("@defaultValue"));
		this.variableProp.setAccessible(Boolean.parseBoolean(node.valueOf("@accessible")));
		this.variableProp.setParameter(Boolean.parseBoolean(node.valueOf("@parameter")));
	}

	/**
	 * @param generator
	 * @throws DynamicException
	 */
	public void changeDynamicClass(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), variableProp.getType(), variableProp.getDefaultValue(), variableProp.isAccessible());
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		this.setName("newVariable");
		this.variableProp.setName("newVariable");
		this.variableProp.setType("String");
		this.variableProp.setDefaultValue("");
		this.variableProp.setAccessible(false);
		this.variableProp.setParameter(false);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField(getName(), variableProp.getType(), variableProp.getDefaultValue(), variableProp.isAccessible());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//DO NOTHING
	}

	/**
	 * @see org.repastbs.component.AbstractComponent#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
		variableProp.setName(name);
	}	
}