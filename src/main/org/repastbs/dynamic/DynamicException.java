/**
 * File: DynamicException.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
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
