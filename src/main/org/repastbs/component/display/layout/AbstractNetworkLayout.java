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
 * @author  Ludovit Hajzer
 */
@SuppressWarnings("serial")
public abstract class AbstractNetworkLayout extends AbstractComponent implements NetworkLayout {

	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	/**
	 * @param name
	 */
	public AbstractNetworkLayout(String name) {
		super(name);
	}

}
