/**
 * File: NetworkLayout.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.repastbs.generated.NetworkLayoutProp;

/**
 * Common interface for all network layouts
 * @author Ludovit Hajzer
 *
 */
public interface NetworkLayout extends DisplayLayout {
	
	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	/**
	 * @return variable name for this display layout
	 */
	public String getVariableName();
	
	/**
	 * @return properties for this layout
	 */
	public NetworkLayoutProp getNetworkLayoutProp();
	
	/**
	 * @param networkLayoutProp
	 */
	public void setNetworkLayoutProp(NetworkLayoutProp networkLayoutProp);
}