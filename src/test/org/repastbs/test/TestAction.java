/**
 * File: TestAction.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.test;

import org.repastbs.actions.AbstractAction;

/**
 * Simple action to test AbstractAction and ActionManager
 * @author �udov�t Hajzer
 *
 */
public class TestAction extends AbstractAction {

	/** */
	public TestAction() {
		super("Test");
	}

	/**
	 * @see org.repastbs.actions.Action#execute(java.lang.Object)
	 */
	public Object execute(Object parameters) {
		//DO NOTHING
		return "OK";
	}
}
