/**
 * File: ComponentInterface.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.component.interfaces;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Interface for implementing interfaces on dynamic classes
 * Every component interface is component, dynamic changer and xml serializable
 * 
 * @author �udov�t Hajzer
 *
 */
public interface ComponentInterface extends Component,DynamicChanger,XMLSerializable {

}
