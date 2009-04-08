/**
 * File: ComponentInterface.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.interfaces;

import org.repastbs.component.Component;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.xml.XMLSerializable;

/**
 * Interface for implementing interfaces on dynamic classes
 * Every component interface is component, dynamic changer and xml serializable
 * 
 * @author Ludovit Hajzer
 *
 */
public interface ComponentInterface extends Component,DynamicChanger,XMLSerializable {

}
