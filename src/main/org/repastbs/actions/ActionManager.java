/**
 * File: ActionManager.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

import java.util.HashMap;
import java.util.Map;


/**
 * Actions are managed by action manager, 
 * which provides methods to execute actions
 * @author ¼udovít Hajzer
 *
 */
public class ActionManager {
	
	/**
	 * @uml.property  name="actions"
	 * @uml.associationEnd  qualifier="key:java.lang.Object org.repastbs.actions.Action"
	 */
	private Map<String, Action> actions = new HashMap<String, Action>();
	
	/**
	 * registers new action in repast
	 * 
	 * @param action
	 */
	public void registerAction(Action action) {
		actions.put(action.getName(), action);
	}
	
	/**
	 * executes registered action
	 * 
	 * @param action
	 * @param parameters 
	 * @return action result
	 * @throws UnknownActionException if action has not been registered
	 */
	public Object executeAction(String action, Object parameters) throws UnknownActionException {
		Action a = actions.get(action);
		if(a == null)
			throw new UnknownActionException(action);
		System.out.println("Executing action "+action+" wth param: "+parameters);
		return a.execute(parameters);
	}
	
	/**
	 * executes registered action
	 * 
	 * @param action
	 * @return action result
	 * @throws UnknownActionException if action has not been registered
	 */
	public Object executeAction(String action) throws UnknownActionException {
		return executeAction(action, null);
	}
	
	/**
	 * @param action
	 * @return true if action is registered, false otherwise
	 */
	public boolean isActionRegistered(String action) {
		return actions.get(action) != null;
	}
}
