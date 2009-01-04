/**
 * File: XMLSerializationException.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.xml;

/**
 * Exception thrown during XML serialization processes
 * @author ¼udovít Hajzer
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
