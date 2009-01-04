/**
 * File: DisplayLayout.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Common interface for all types of layouts
 * @author �udov�t Hajzer
 *
 */
public interface DisplayLayout extends Component, DynamicChanger, XMLSerializable {
	
	/** */
	public static final String ID = "DISPLAY_LAYOUT";
}
