/**
 * File: ComponentInterface.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
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
 * @author ¼udovít Hajzer
 *
 */
public interface ComponentInterface extends Component,DynamicChanger,XMLSerializable {

}
