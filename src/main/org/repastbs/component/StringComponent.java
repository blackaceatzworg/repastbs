/**
 * File: StringComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.repastbs.generated.StringComponentProp;

/**
 * Simple object component holding String value
 * @author  Ľudovít Hajzer
 */
public class StringComponent extends ObjectComponent {
	
	/** */
	private static final long serialVersionUID = 8604534808303868199L;

	/** */
	public static final String ID = "STRING";
	
	private StringComponentProp scp = new StringComponentProp();
	//private boolean large = false;
	//private String value;
	
	/**
	 * @param name
	 * @param value 
	 * @param large 
	 */
	public StringComponent(String name, String value, boolean large) {
		super(name);
		this.scp.setValue(value);
		this.scp.setLarge(large);
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

	public String getValue() {
		return scp.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.scp.setValue(value);
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}
	
	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		if(scp.isLarge())
			return getName();
		return getName()+" = '"+getValue()+"'";
	}
}