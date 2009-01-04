/**
 * File: DisplayLayout.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Common interface for all types of layouts
 * @author ¼udovít Hajzer
 *
 */
public interface DisplayLayout extends Component, DynamicChanger, XMLSerializable {
	
	/** */
	public static final String ID = "DISPLAY_LAYOUT";
}
