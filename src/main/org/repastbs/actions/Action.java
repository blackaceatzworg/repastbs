/**
 * File: Action.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.actions;

/**
 * Action represents user action, or any action user defines
 * @author �udov�t Hajzer
 *
 */
public interface Action {
	
	/**
	 * executes the action
	 * @param parameters 
	 * 
	 * @return result of action
	 */
	public abstract Object execute(Object parameters);
	
	/**
	 * @return name of this action
	 */
	public abstract String getName();
}
