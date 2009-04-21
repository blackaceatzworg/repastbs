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
public class StringComponent extends AbstractComponent {
	
	/** */
	private static final long serialVersionUID = 8604534808303868199L;

	/** */
	public static final String ID = "STRING";
	
	private StringComponentProp stringComponentProp = new StringComponentProp();
	//private boolean large = false;
	//private String value;
	
	/**
	 * @param name
	 * @param value 
	 * @param large 
	 */
	public StringComponent(String name, String value, boolean large) {
		super(name);
		this.stringComponentProp.setValue(value);
		this.stringComponentProp.setLarge(large);
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
	 * @return value
	 */
	public String getValue() {
		return stringComponentProp.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.stringComponentProp.setValue(value);
		fireComponentChanged(new ComponentEvent(this,ComponentEvent.COMPONENT_CHANGED));
	}
	
	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		if(stringComponentProp.isLarge())
			return getName();
		return getName()+" = '"+getValue()+"'";
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	@Override
	public void createNew() throws Exception {
	}

	/**
	 * @return the stringComponentProp
	 */
	public StringComponentProp getStringComponentProp() {
		return stringComponentProp;
	}

	/**
	 * @param stringComponentProp the stringComponentProp to set
	 */
	public void setStringComponentProp(StringComponentProp stringComponentProp) {
		this.stringComponentProp = stringComponentProp;
	}
}