/**
 * File: VariablesComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

import org.dom4j.Node;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.VariablesProp;
import org.repastbs.model.Model;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Variables component is container holding Variable components
 * @author Ľudovít Hajzer
 *
 */
public class VariablesComponent extends AbstractComponent implements DynamicChanger, XMLSerializable {


	/** */
	private static final long serialVersionUID = 7171703519151877211L;

	/** */
	public static final String ID = "VARIABLES";
	
	private VariablesProp variablesProp;

	/**
	 * default constructor
	 */
	public VariablesComponent() {
		super("Variables");
		setId(ID);
	}

	/**
	 * @param name
	 * @param type 
	 * @param defaultValue 
	 * @param accesible 
	 * @param parameter 
	 * @param removable 
	 * @return newly created action
	 */
	public Variable createVariable(String name, String type, String defaultValue, 
			boolean accesible, boolean parameter, boolean removable) {
		Variable v = new Variable(name,type,defaultValue,accesible,parameter);
		addVariable(v);
		v.setRemovable(removable);
		return v;
	}

	/**
	 * @param name
	 * @param type 
	 * @param defaultValue 
	 * @param accesible 
	 * @param parameter 
	 * @return newly created action
	 */
	public Variable createVariable(String name, String type, String defaultValue, 
			boolean accesible, boolean parameter) {
		return createVariable(name,type,defaultValue,accesible,parameter, true);
	}

	/**
	 * @param name
	 * @param type 
	 * @return newly created action
	 */
	public Variable createVariable(String name,String type) {
		return createVariable(name,type,null,false,false);
	}

	/**
	 * @param name
	 * @return newly created action
	 */
	public Variable createVariable(String name) {
		return createVariable(name,null,null,false,false);
	}

	/**
	 * @param v
	 */
	public void addVariable(Variable v) {
		if(v==null)
			return;
		variablesProp.getVariable().add(v.getVariableProp());
		super.add(v);
	}

	/**
	 * @return actions
	 */
	public Enumeration getVariables() {
		return children();
	}

	private List<Variable> getParameters() {
		List<Variable> result = new ArrayList<Variable>();
		Enumeration e = children();
		while(e.hasMoreElements()) {
			Variable v = (Variable)e.nextElement();
			if(v.getVariableProp().isParameter())
				result.add(v);
		}

		return result;
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
			SAXUtils.start(handler, "variables",atts);
			if(getChildCount()>0) {
				for (Iterator iter = children.iterator(); iter.hasNext();) {
					Variable variable = (Variable) iter.next();
					variable.writeToXML(handler);
				}
			}
			SAXUtils.end(handler, "variables");
		} catch (SAXException e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		List variableNodes = node.selectNodes("variable");
		for(int i=0;i<variableNodes.size();i++) {
			Node actualNode = (Node)variableNodes.get(i);
			Variable v = new Variable("");
			v.createFromXML(actualNode);
			addVariable(v);
		}
	}

	/**
	 * @param parent
	 */
	public void setParent(Component parent) {
		if(parent == null)
			throw new IllegalArgumentException("Parent of variables component can't be null");
		super.setParent(parent);
	}

	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName()+" - "+getChildCount()+" variable(s)";
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		removeAllChildren();
		variablesProp = new VariablesProp(); 
	}
	
	/**
	 * @see org.repastbs.component.AbstractComponent#getChildByName(java.lang.String)
	 */
	public Variable getChildByName(String name) {
		return (Variable)super.getChildByName(name);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		Enumeration e = getVariables();
		while(e.hasMoreElements())
			((Variable)e.nextElement()).generateFields(generator);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//if this variables components is in model, set up model init params
		if(getParent() instanceof Model) {
			List<Variable> params = getParameters();
			StringBuffer initParams = new StringBuffer();
			initParams.append("initParam = new String[")
			.append(params.size())
			.append("];");
			for(int i = 0; i < params.size() ; i++) {
				initParams.append("initParam[").append(i)
				.append("] = \"")
				.append(params.get(i).getName())
				.append("\";");
			}
			try {
				generator.insertAfter(null, null, initParams.toString());
			} catch (DynamicException x) {
				generator.addConstructor(null, null, initParams.toString());
			}			
		}
	}

	/**
	 * @return the variablesProp
	 */
	public VariablesProp getVariablesProp() {
		return variablesProp;
	}

	/**
	 * @param variablesProp the variablesProp to set
	 */
	public void setVariablesProp(VariablesProp variablesProp) {
		this.variablesProp = variablesProp;
	}
	
}
