/**
 * File: DynamicException.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Exception thrown when some dynamic operation didn't succeed
 * @author �udov�t Hajzer
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
