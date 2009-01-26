/**
 * File: Action.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
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
