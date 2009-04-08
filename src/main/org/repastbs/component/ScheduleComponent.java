/**
 * File: ScheduleComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Node;
import org.repastbs.generated.ScheduleProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Container for holding scheduled actions
 * @author Ludovit Hajzer
 *
 */
public class ScheduleComponent extends AbstractComponent implements XMLSerializable {


	/** */
	private static final long serialVersionUID = 7171703519151877211L;

	/** */
	public static final String ID = "SCHEDULE";

	private ScheduleProp scheduleProp = new ScheduleProp();
	/**
	 * default constructor
	 */
	public ScheduleComponent() {
		super("Schedule");
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
	 * @param action 
	 * @param execution 
	 * @param tick 
	 * @param executeLast 
	 * @return newly created scheduled action
	 */
	public ScheduledAction createScheduleAction(String name, 
			Action action, int execution, 
			int tick, boolean executeLast) {
		ScheduledAction s = new ScheduledAction(name,action,execution,tick,executeLast,getChildCount());
		addScheduledAction(s);
		return s;
	}


	/**
	 * @param s
	 */
	public void addScheduledAction(ScheduledAction s) {
		if(s==null)
			return;
		scheduleProp.getScheduledAction().add(s.getScheduledActionProp());
		super.add(s);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	@SuppressWarnings("unchecked")
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
			SAXUtils.start(handler, "schedule",atts);
			if(getChildCount()>0) {
				for (Iterator iter = children.iterator(); iter.hasNext();) {
					ScheduledAction action = (ScheduledAction) iter.next();
					action.writeToXML(handler);
				}
			}
			SAXUtils.end(handler, "schedule");
		} catch (SAXException e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	@SuppressWarnings("unchecked")
	public void createFromXML(Node node) throws XMLSerializationException {
		List variableNodes = node.selectNodes("scheduledAction");
		for(int i=0;i<variableNodes.size();i++) {
			Node actualNode = (Node)variableNodes.get(i);
			ScheduledAction s = new ScheduledAction();
			addScheduledAction(s);
			s.createFromXML(actualNode);
		}
	}

	/**
	 * @param parent
	 */
	public void setParent(Component parent) {
		if(parent == null)
			throw new IllegalArgumentException("Parent of schedule component can't be null");
		super.setParent(parent);
	}

	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName()+" - "+getChildCount()+" scheduled item(s)";
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		removeAllChildren();
		scheduleProp = new ScheduleProp(); 
	}

	/**
	 * @return schedulable parent object
	 */
	public Schedulable getSchedulable() {
		return (Schedulable)parent;
	}

	/**
	 * @return scheduleProp
	 */
	public ScheduleProp getScheduleProp() {
		return scheduleProp;
	}

	/**
	 * @param scheduleProp
	 */
	public void setScheduleProp(ScheduleProp scheduleProp) {
		this.scheduleProp = scheduleProp;
	}
	
	/**
	 * @see org.repastbs.component.AbstractComponent#removeChildProp(org.repastbs.component.Component)
	 */
	public void removeChildProp(Component comp) {
		ScheduledAction sa = (ScheduledAction) comp;
		scheduleProp.getScheduledAction().remove(sa.getScheduledActionProp());
	}
}
