/**
 * File: AbstractNetworkLayout.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.repastbs.component.AbstractComponent;

/**
 * Abstract implementation of NetworkLayout interface,  all network layouts should extend this class
 * @author  �udov�t Hajzer
 */
public abstract class AbstractNetworkLayout extends AbstractComponent implements NetworkLayout {

	private String variableName;
	
	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	/**
	 * @param name
	 */
	public AbstractNetworkLayout(String name) {
		super(name);
	}
	
	/**
	 * @see  org.repastbs.component.display.layout.NetworkLayout#getVariableName()
	 * @uml.property  name="variableName"
	 */
	public String getVariableName() {
		return variableName;
	}
	
	/**
	 * Sets variable name of this network layout component
	 * @param  variableName
	 * @uml.property  name="variableName"
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
