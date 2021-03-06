/**
 * File: ActionParameter.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.dom4j.Node;
import org.repastbs.generated.ActionParameterProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Class representing action parameter
 * @author  Ľudovít Hajzer
 */
public class ActionParameter extends AbstractComponent implements XMLSerializable {
	
	/** */
	private static final long serialVersionUID = -6847100055693204162L;
	
	/**  */
	public static final String ID = "ACTION_PARAMETER";
	
	private ActionParameterProp actionParameterProp = new ActionParameterProp();

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
		this.actionParameterProp.setParameter(type);
		setId(ID);
	}

	/**
	 * @return actionParameterProp
	 */
	public ActionParameterProp getActionparameterprop() {
		return actionParameterProp;
	}

	/**
	 * @param actionparameterprop
	 */
	public void setActionparameterprop(ActionParameterProp actionparameterprop) {
		this.actionParameterProp = actionparameterprop;
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		actionParameterProp.setParameter("java.lang.Object");
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.actionParameterProp.setParameter(node.valueOf("text()"));
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			SAXUtils.text(handler, "parameter", actionParameterProp.getParameter());
		} catch (SAXException e) {
			throw new XMLSerializationException("ActionParameter.writeToXML: "+e.getMessage(),e);
		}
	}
	
	/**
	 * @see org.repastbs.component.AbstractComponent#toString()
	 */
	public String toString() {
		return getName() + " "+actionParameterProp.getParameter();
	}
}
