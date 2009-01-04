/**
 * File: IntegerComponent.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

/**
 * Simple object component, holding Integer as its value
 * @author ¼udovít Hajzer
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