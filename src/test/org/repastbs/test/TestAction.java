/**
 * File: TestAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
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
