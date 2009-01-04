/**
 * File: AgentsComponent.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.Enumeration;

import org.dom4j.Node;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.DynamicHolder;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Agents component is container, which contains agents
 * @author ¼udovít Hajzer
 *
 */
public class AgentsComponent extends AbstractComponent implements DynamicChanger, XMLSerializable {

	/**  */
	private static final long serialVersionUID = 5683751005691247209L;
	/** */
	public static final String ID = "AGENTS";

	/**
	 * Default empty constructor
	 */
	public AgentsComponent() {
		super("Agents");
		setId(ID);
	}

	/**
	 * @param a
	 */
	public void addAgent(Agent a) {
		if(a==null)
			return;
		super.add(a);
	}

	/**
	 * @return actions
	 */
	public Enumeration getAgents() {
		return children();
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
			SAXUtils.start(handler, "agents",atts);
			SAXUtils.serializeChildren(handler, this);
			SAXUtils.end(handler, "agents");
		} catch (SAXException e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		try {
			SAXUtils.createChildren(node, this);
		} catch (Exception e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName()+" - "+getChildCount()+" agents(s)";
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
		for (int i = 0; i < getChildCount(); i++) {
			Component c = (Component)getChildAt(i);
			if(c instanceof DynamicChanger) {
				DynamicChanger changer = (DynamicChanger)c;
				changer.generateFields(generator);
			}
			if(c instanceof DynamicHolder) {
				DynamicHolder holder = (DynamicHolder)c;
				holder.generateFields();
			}
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		for (int i = 0; i < getChildCount(); i++) {
			Component c = (Component)getChildAt(i);
			if(c instanceof DynamicChanger) {
				DynamicChanger changer = (DynamicChanger)c;
				changer.generateMethods(generator);
			}
			if(c instanceof DynamicHolder) {
				DynamicHolder holder = (DynamicHolder)c;
				holder.generateMethods();
			}
		}
	}
}