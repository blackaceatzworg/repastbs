/**
 * File: DynamicException.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Exception thrown when some dynamic operation didn't succeed
 * @author ¼udovít Hajzer
 *
 */
public class DynamicException extends Exception {

	/** */
	private static final long serialVersionUID = -2269155265617322644L;
	
	/**
	 * @param msg
	 */
	public DynamicException(String msg) {
		this(msg,null);
	}
	
	/**
	 * @param msg
	 * @param cause
	 */
	public DynamicException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
