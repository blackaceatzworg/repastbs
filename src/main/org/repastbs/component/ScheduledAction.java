/**
 * File: ScheduledAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.dom4j.Node;
import org.repastbs.generated.ScheduledActionProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Represents single action scheduled for execution in repast
 * @author  Ľudovít Hajzer
 */
public class ScheduledAction extends AbstractComponent implements XMLSerializable, Comparable<ScheduledAction> {
	
	/** execute action at every tick */
	public static final int EVERY_TICK = 0;
	/** execute action at single tick */
	public static final int AT_A_SINGLE_TICK = 1;
	/** execute action at specified interval */
	public static final int AT_INTERVAL = 2;
	/** execute action at end of simulation */
	public static final int AT_END = 3;
	/** execute action at pause */
	public static final int AT_PAUSE = 4;
	/** */
	public static final String ID = "SCHEDULE";

	private ScheduledActionProp scheduledActionProp = new ScheduledActionProp();
	
	/** */
	private static final long serialVersionUID = 9024887132319543857L;
	
	private Action action;
	
	/** */
	public ScheduledAction () {
		//this(null);
		super(null);
	}
	
	/**
	 * @param action
	 */
	public ScheduledAction (Action action) {
		this("ScheduledAction", action, EVERY_TICK, 1, false,-1);
	}
	
	/**
	 * @param name
	 * @param action 
	 * @param execution 
	 * @param tick 
	 * @param executeLast 
	 * @param index 
	 */
	public ScheduledAction (String name, Action action, int execution, 
			int tick, boolean executeLast, int index) {
		super(name);
		this.action = action;
		if(action!=null) {
			this.scheduledActionProp.setAction(action.getName());
		}
		this.scheduledActionProp.setExecution(execution);
		this.scheduledActionProp.setTick(tick);
		this.scheduledActionProp.setExecuteLast(executeLast);
		this.scheduledActionProp.setIndex(index);
		setId(ID);
	}

	public ScheduledAction(ScheduledActionProp scheduledActionProp) {
		//TODO repair this block
		super(scheduledActionProp.getAction());
		this.scheduledActionProp = scheduledActionProp;
		setId(ID);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
	    atts.addAttribute("", "action", "action", "CDATA", ""+getAction().getName());
	    atts.addAttribute("", "execution", "execution", "CDATA", ""+scheduledActionProp.getExecution());
	    atts.addAttribute("", "tick", "tick", "CDATA", ""+scheduledActionProp.getTick());
	    atts.addAttribute("", "executeLast", "executeLast", "CDATA", 
	    		new StringBuffer().append(scheduledActionProp.isExecuteLast()).toString());
	    atts.addAttribute("", "index", "index", "CDATA",""+scheduledActionProp.getIndex());
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
	    try {
			SAXUtils.start(handler, "scheduledAction", atts);
			SAXUtils.end(handler,"scheduledAction");
		} catch (SAXException e) {
			throw new XMLSerializationException("Exception creating ScheduledAction: "+e.getMessage());
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setName("");
		
		try {
			String actionName = node.valueOf("@action");
			ActionsComponent actions = (ActionsComponent)(
					(Component)getParent().getParent()).getChildById(ActionsComponent.ID);
			Action a = (Action)actions.getChildByName(actionName);
			setAction(a);
		} catch(Exception e) {
			throw new XMLSerializationException("Exception creating ScheduledAction: "+e.getMessage());
		}
		
		//NetworkDisplay a = 
		this.scheduledActionProp.setExecution(Integer.parseInt(node.valueOf("@execution")));
		this.scheduledActionProp.setTick(Integer.parseInt(node.valueOf("@tick")));
		this.scheduledActionProp.setExecuteLast(Boolean.parseBoolean(node.valueOf("@executeLast")));
		this.scheduledActionProp.setIndex(Integer.parseInt(node.valueOf("@index")));
	}

	/**
	 * @return scheduledActionProp
	 */
	public ScheduledActionProp getScheduledActionProp() {
		return scheduledActionProp;
	}

	/**
	 * @param scheduledActionProp
	 */
	public void setScheduledActionProp(ScheduledActionProp scheduledActionProp) {
		this.scheduledActionProp = scheduledActionProp;
	}

	/**
	 * @return  the action
	 * @uml.property  name="action"
	 */
	public Action getAction() {
		return action;
	}
	
	/**
	 * @return shortcut to action name
	 */
	public String getActionName() {
		return action.getName();
	}

	/**
	 * @param action  the action to set
	 * @uml.property  name="action"
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		
	}
	
	/**
	 * @see org.repastbs.component.AbstractComponent#getName()
	 */
	public String getName() {
		String result = "Execute "+(scheduledActionProp.isExecuteLast()?"last ":"")+action+ " ";
		switch(scheduledActionProp.getExecution()) {
			case EVERY_TICK: result+="at every tick"; break;
			case AT_A_SINGLE_TICK: result+="at tick "+scheduledActionProp.getTick(); break;
			case AT_INTERVAL: result+="at interval"; break;
			case AT_END: result+="at end"; break;
			case AT_PAUSE:  result+="at pause"; break;
		}
		return result;
	}

	/**
	 * @param other 
	 * @return negative, 0, or positive value
	 * @see java.lang.Comparable#compareTo(Object)
	 * 
	 */
	public int compareTo(ScheduledAction other) {
		return this.scheduledActionProp.getIndex()-other.getScheduledActionProp().getIndex();
	}
	
	/**
	 * @return parent schedulable object
	 */
	public Schedulable getSchedulable() {
		try {
			return ((ScheduleComponent)parent).getSchedulable();
		} catch(ClassCastException e) {
			throw new RuntimeException("Parent of Schedule is not Schedulable",e);
		}
	}
}