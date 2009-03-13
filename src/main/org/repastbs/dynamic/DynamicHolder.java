/**
 * File: DynamicHolder.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Holder represent object that generates class. 
 * It has associated generator, which is used to generate result class
 * @author Ľudovít Hajzer
 *
 */
public interface DynamicHolder {
	
	/**
	 * @return dynamic class associated with this component
	 */
	public DynamicGenerator getGenerator();
	
	/**
	 * generate class with associated generator
	 * @throws DynamicException 
	 */
	public void generateMethods() throws DynamicException;
	
	/**
	 * generate class with associated generator
	 * @throws DynamicException 
	 */
	public void generateFields() throws DynamicException;
}