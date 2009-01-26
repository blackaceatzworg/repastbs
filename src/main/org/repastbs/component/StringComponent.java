/**
 * File: StringComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

/**
 * Simple object component holding String value
 * @author  �udov�t Hajzer
 */
public class StringComponent extends ObjectComponent {
	
	/** */
	private static final long serialVersionUID = 8604534808303868199L;

	/** */
	public static final String ID = "STRING";
	
	private boolean large = false;
	
	/**
	 * @param name
	 * @param value 
	 * @param large 
	 */
	public StringComponent(String name, String value, boolean large) {
		super(name,value);
		this.large = large;
		setId(ID);
	}
	
	/**
	 * @param name
	 * @param value 
	 */
	public StringComponent(String name, String value) {
		this(name,value,false);
	}
	
	/**
	 * @param name
	 * @param large 
	 */
	public StringComponent(String name,boolean large) {
		this(name,null,large);
	}
	
	/**
	 * @param name
	 */
	public StringComponent(String name) {
		this(name,null,false);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return (String)super.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		super.setValue(value);
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}
	
	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		if(large)
			return getName();
		return getName()+" = '"+getValue()+"'";
	}

	/**
	 * @return  whether this string component contains larg text
	 */
	public boolean isLarge() {
		return large;
	}

	/**
	 * @param  large
	 */
	public void setLarge(boolean large) {
		this.large = large;
	}
}