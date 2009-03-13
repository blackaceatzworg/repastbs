/**
 * File: Action.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.List;
import java.util.StringTokenizer;
import org.dom4j.Node;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.ActionProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Basic model action, action is generated as method in generated class
 * @author  Ľudovít Hajzer
 */
public class Action extends AbstractComponent implements DynamicChanger, XMLSerializable {
	
	/** */
	private static final long serialVersionUID = -4858653344360448240L;
	
	/** */
	public static final String ID = "ACTION";

	private ActionProp actionprop = new ActionProp();

	private boolean changeSignature = true;

	/**
	 * Default empty constructor
	 */
	public Action() {
		this("Unknown action");
	}
	
	/**
	 * Creates action with given name
	 * @param name 
	 */
	public Action(String name) {
		this(name,null,null,null);
	}
	
	/**
	 * Creates action with given parameters
	 * @param name
	 * @param imports
	 * @param source
	 * @param returnType
	 */
	public Action(String name, String imports, String source, String returnType) {
		super(name);
		this.actionprop.setImports(imports==null?"":imports);
		this.actionprop.setSource(source);
		this.actionprop.setReturnType(returnType);
		setId(ID);
	}

	public ActionProp getActionprop() {
		return actionprop;
	}

	public void setActionprop(ActionProp actionprop) {
		this.actionprop = actionprop;
	}

	/**
	 * Returns whether user can change this action signature,  or he can change only action source
	 * @return  true if user can change action's signature, false otherwise
	 * @uml.property  name="changeSignature"
	 */
	public boolean isChangeSignature() {
		return changeSignature;
	}

	/**
	 * Sets whether user can change signature of this action
	 * @param canChangeSignature  the canChangeSignature to set
	 * @uml.property  name="changeSignature"
	 */
	public void setChangeSignature(boolean canChangeSignature) {
		this.changeSignature = canChangeSignature;
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "name", "name", "CDATA", getName());
		atts.addAttribute("", "returnType", "returnType", "CDATA", actionprop.getReturnType());
		try {
			SAXUtils.start(handler, "action", atts);
			SAXUtils.text(handler, "imports", actionprop.getImports());
			SAXUtils.text(handler, "source", actionprop.getSource());
			List<Component> parameters = getChildsById(ActionParameter.ID);
			if(parameters.size()>0) {
				SAXUtils.start(handler, "parameters");
				for (int i = 0; i < parameters.size(); i++) {
					ActionParameter element = (ActionParameter)parameters.get(i);
					element.writeToXML(handler);
				}
				SAXUtils.end(handler, "parameters");
			}
			SAXUtils.end(handler, "action");
		} catch (SAXException e) {
			throw new XMLSerializationException("Action.writeToXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setName(node.valueOf("@name"));
		this.actionprop.setReturnType(node.valueOf("@returnType"));
		this.actionprop.setImports(node.valueOf("imports/text()"));
		this.actionprop.setSource(node.valueOf("source/text()"));
		List parameters = node.selectNodes("parameters/parameter");
		for (int i = 0; i < parameters.size(); i++) {
			ActionParameter param = new ActionParameter();
			add(param);
			param.createFromXML((Node)parameters.get(i));
		}
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
		this.setName("newAction");
		this.actionprop.setReturnType(null);
		this.actionprop.setImports("");
		this.actionprop.setSource("");
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		StringTokenizer st = new StringTokenizer(actionprop.getImports(),"\n");
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			generator.addImport(token);
		}
		List<Component> params = getChildsById(ActionParameter.ID);
		String[] paramNames = new String[params.size()];
		String[] paramTypes = new String[params.size()];
		for (int i = 0; i < params.size(); i++) {
			paramNames[i] = "param"+i;
			paramTypes[i] = ((ActionParameter)params.get(i)).getActionparameterprop().getType();
		}
		//add empty method declaration
		generator.addMethod(getName(), (actionprop.getReturnType()==null || actionprop.getReturnType().compareTo("")==0)?"void":actionprop.getReturnType(),
				paramNames, paramTypes, actionprop.getReturnType()==null?"":"return null;");	
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		List<Component> params = getChildsById(ActionParameter.ID);
		String[] paramTypes = new String[params.size()];
		for (int i = 0; i < params.size(); i++)
			paramTypes[i] = ((ActionParameter)params.get(i)).getActionparameterprop().getType();
		generator.insertBefore(getName(), paramTypes, actionprop.getSource());
	}
	
	/**
	 * Adds parameter to this action
	 * @param type
	 */
	public void addParameter(String type) {
		add(new ActionParameter(type));
	}
}