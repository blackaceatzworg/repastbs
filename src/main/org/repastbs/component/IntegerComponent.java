/**
 * File: IntegerComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.repastbs.generated.IntegerComponentProp;

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
	
	private IntegerComponentProp integerComponentProp = new IntegerComponentProp();
	
	/**
	 * @param name
	 * @param value 
	 */
	public IntegerComponent(String name, Integer value) {
		super(name,value);
		this.integerComponentProp.setValue(value);
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
		return integerComponentProp.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		integerComponentProp.setValue(value);
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}

	/**
	 * @return the integerComponentProp
	 */
	public IntegerComponentProp getIntegerComponentProp() {
		return integerComponentProp;
	}

	/**
	 * @param integerComponentProp the integerComponentProp to set
	 */
	public void setIntegerComponentProp(IntegerComponentProp integerComponentProp) {
		this.integerComponentProp = integerComponentProp;
	}
}