/**
 * File: UnknownActionException.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

/**
 * Thrown when action with given name has been not found
 * @author ¼udovít Hajzer
 *
 */
public class UnknownActionException extends RuntimeException {
	
	/**	 */
	private static final long serialVersionUID = 2320093920968683952L;

	/**
	 * @param unknownAction
	 */
	public UnknownActionException(String unknownAction) {
		super("Action '"+unknownAction+ "' has not been registered");
	}
}
