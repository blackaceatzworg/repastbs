/**
 * File: NetworkType.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.network.type;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Simple interface for network types, every network type must be component, 
 * dynamic changer and xml serializable
 * @author ¼udovít Hajzer
 *
 */
public interface NetworkType extends Component, DynamicChanger, XMLSerializable {
	
	/** */
	public static final String ID = "NETWORK_TYPE";
}
