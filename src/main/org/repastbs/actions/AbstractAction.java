/**
 * File: AbstractAction.java
* Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

/**
 * Simple abstract action, which implements NetworkDisplay interface
 * @author  Ľudovít Hajzer
 */
public abstract class AbstractAction implements Action {

	private String name;
	
	/**
	 * @param name
	 */
	public AbstractAction(String name) {
		this.name = name;
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
}
