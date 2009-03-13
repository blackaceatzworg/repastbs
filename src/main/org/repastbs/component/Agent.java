/**
 * File: Agent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import org.repastbs.dynamic.DynamicHolder;
import org.repastbs.xml.XMLSerializable;

/**
 * Every agent in Repast BS must implement this interface, 
 * every agent must be schedulable, component, xml serializable, 
 * and must be dynamic holder, to generate its class
 * @author Ľudovít Hajzer
 *
 */
public interface Agent extends Schedulable, Component, XMLSerializable, DynamicHolder {

	/** */
	public static final String ID = "AGENT";
}
