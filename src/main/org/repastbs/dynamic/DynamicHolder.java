/**
 * File: DynamicHolder.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Holder represent object that generates class. 
 * It has associated generator, which is used to generate result class
 * @author �udov�t Hajzer
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