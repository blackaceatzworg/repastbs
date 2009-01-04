/**
 * File: EditorEvent.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.util.EventObject;
import org.repastbs.component.Component;

/**
 * Editor event fired when editor created, or edited component
 * @author  ¼udovít Hajzer
 */
public class EditorEvent extends EventObject {
	
	/** */
	private static final long serialVersionUID = -8584606457954990402L;
	
	/** signals that this value has been created*/
	public static final int VALUE_CREATED = 0;
	/** signals that this value has been edited*/
	public static final int VALUE_EDITED = 1;
	
	private Component newValue;
	private int type=VALUE_EDITED;

	/**
	 * @param source
	 * @param newValue
	 * @param type
	 */
	public EditorEvent(Object source, Component newValue,int type) {
		super(source);
		this.newValue=newValue;
		this.type=type;
	}
	
	/**
	 * @param source
	 * @param newValue
	 */
	public EditorEvent(Object source, Component newValue) {
		this(source,newValue,VALUE_EDITED);
	}

	/**
	 * @return  the newValue
	 * @uml.property  name="newValue"
	 */
	public Component getNewValue() {
		return newValue;
	}

	/**
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type  the type to set
	 * @uml.property  name="type"
	 */
	public void setType(int type) {
		this.type = type;
	}
}
