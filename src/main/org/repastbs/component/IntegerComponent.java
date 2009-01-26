/**
 * File: IntegerComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

/**
 * Simple object component, holding Integer as its value
 * @author �udov�t Hajzer
 *
 */
public class IntegerComponent extends ObjectComponent {
	
	/** */
	private static final long serialVersionUID = 8604534808303868199L;

	/** */
	public static final String ID = "INTEGER";
	
	/**
	 * @param name
	 * @param value 
	 */
	public IntegerComponent(String name, Integer value) {
		super(name,value);
		setId(ID);
	}
	
	/**
	 * @param name
	 */
	public IntegerComponent(String name) {
		this(name,null);
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return (Integer)super.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		super.setValue(value);
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}
}