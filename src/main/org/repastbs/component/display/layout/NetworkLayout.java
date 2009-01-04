/**
 * File: NetworkLayout.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

/**
 * Common interface for all network layouts
 * @author ¼udovít Hajzer
 *
 */
public interface NetworkLayout extends DisplayLayout {
	
	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	/**
	 * @return variable name for this display layout
	 */
	public String getVariableName();
}
