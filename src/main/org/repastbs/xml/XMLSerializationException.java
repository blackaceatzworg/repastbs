/**
 * File: XMLSerializationException.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.xml;

/**
 * Exception thrown during XML serialization processes
 * @author �udov�t Hajzer
 *
 */
public class XMLSerializationException extends RuntimeException {

	/** */
	private static final long serialVersionUID = -1739652021874009552L;
	
	/**
	 * @param msg
	 */
	public XMLSerializationException(String msg) {
		this(msg,null);
	}
	
	/**
	 * @param msg
	 * @param cause
	 */
	public XMLSerializationException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
