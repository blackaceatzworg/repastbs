/**
 * File: Action.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

/**
 * Action represents user action, or any action user defines
 * @author ¼udovít Hajzer
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
