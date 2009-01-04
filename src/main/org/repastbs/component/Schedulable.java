/**
 * File: Schedulable.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.component;

/**
 * All components, which allow Schedule component as its child
 * must implement this interface
 * @author �udov�t Hajzer
 *
 */
public interface Schedulable {
	
	/**
	 * @return schedulable object name
	 */
	public String getSchedulableObjectName();
	
	/**
	 * @return true if generated object is iterable, when calling
	 * scheduled actions they should be called using Iterator 
	 * and casting to appropriate class
	 */
	public boolean isIterable();
	
	/**
	 * @return iterable class name
	 */
	public String getIterableClass();
}
