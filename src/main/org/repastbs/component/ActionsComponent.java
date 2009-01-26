/**
 * File: ActionsComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.Enumeration;

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
 * Actions component is container, which contains action components
 * @author �udov�t Hajzer
 *
 */
public class ActionsComponent extends AbstractComponent implements DynamicChanger, XMLSerializable {

	/** */
	private static final long serialVersionUID = 7897970834375132252L;
	
	/** */
	public static final String ID = "ACTIONS";
	
	/**
	 * default constructor
	 */
	public ActionsComponent() {
		super("Actions");
		setId(ID);
	}
	
	/**
	 * removes all actions
	 */
	public void clear() {
		removeAllChildren();
	}
	
	/**
	 * @param name
	 * @param imports
	 * @param source
	 * @param returnType
	 * @return newly created action
	 */
	public Action createAction(String name, String imports, String source, String returnType) {
		Action a = new Action(name,imports,source,returnType);
		addAction(a);
		return a;
	}
	
	/**
	 * @param name
	 * @param returnType 
	 * @return newly created action
	 */
	public Action createAction(String name, String returnType) {
		return createAction(name,"","",returnType);
	}
	
	/**
	 * @param name
	 * @return newly created action
	 */
	public Action createAction(String name) {
		return createAction(name,"","" ,null);
	}
	
	/**
	 * @param a
	 */
	public void addAction(Action a) {
		super.add(a);
	}
	
	/**
	 * @return actions
	 */
	public Enumeration getActions() {
		return children();
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
			SAXUtils.start(handler, "actions", atts);
			SAXUtils.serializeChildren(handler, this);
			SAXUtils.end(handler, "actions");
		} catch (SAXException e) {
			throw new XMLSerializationException("ActionsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		SAXUtils.createChildren(node, Action.class, this);
	}
	
	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName()+" - "+getChildCount()+" action(s)";
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		removeAllChildren();
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		Enumeration e = getActions();
		while(e.hasMoreElements())
			((Action)e.nextElement()).generateFields(generator);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		Enumeration e = getActions();
		while(e.hasMoreElements())
			((Action)e.nextElement()).generateMethods(generator);
	}
}
