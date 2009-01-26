/**
 * File: NetworkType.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.network.type;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Simple interface for network types, every network type must be component, 
 * dynamic changer and xml serializable
 * @author �udov�t Hajzer
 *
 */
public interface NetworkType extends Component, DynamicChanger, XMLSerializable {
	
	/** */
	public static final String ID = "NETWORK_TYPE";
}
