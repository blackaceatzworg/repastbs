/**
 * File: ObjectComponent.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

/**
 * Simple object component which holds object as its value, it has no functionality, should be extended by other compoennts
 * @author  ¼udovít Hajzer
 */
public class ObjectComponent extends AbstractComponent {


	/** */
	private static final long serialVersionUID = -6579664345321794782L;

	/** */
	public static final String ID = "OBJECT";
	
	private Object value;
	
	/**
	 * @param name
	 * @param value 
	 */
	public ObjectComponent(String name, Object value) {
		super(name);
		this.value= value;
		setId(ID);
	}
	
	/**
	 * @param name
	 */
	public ObjectComponent(String name) {
		this(name,null);
	}

	/**
	 * @return  the value
	 * @uml.property  name="value"
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value  the value to set
	 * @uml.property  name="value"
	 */
	public void setValue(Object value) {
		this.value = value;
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}
	
	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName()+" = '"+value+"'";
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
	}
}