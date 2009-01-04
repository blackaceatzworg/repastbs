/**
 * File: ComponentEvent.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.EventObject;
import org.repastbs.component.Component;

/**
 * Component event fired when component  has been manipulated, changed, or removed
 * @author  ¼udovít Hajzer
 */
public class ComponentEvent extends EventObject {
	
	/** */
	private static final long serialVersionUID = -8584606457954990402L;
	
	/** signals that component has been changed*/
	public static final int COMPONENT_CHANGED=0;
	/** signals that component has been removed*/
	public static final int COMPONENT_REMOVED=1;

	private int type=COMPONENT_CHANGED;

	/**
	 * @param source
	 * @param type
	 */
	public ComponentEvent(Component source,int type) {
		super(source);
		this.type=type;
	}
	
	/**
	 * @param source
	 */
	public ComponentEvent(Component source) {
		this(source,COMPONENT_CHANGED);
	}
	
	/**
	 * @see java.util.EventObject#getSource()
	 */
	public Component getSource() {
		return (Component)super.getSource();
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
