/**
 * File: Schedulable.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

/**
 * All components, which allow Schedule component as its child
 * must implement this interface
 * @author Ľudovít Hajzer
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
