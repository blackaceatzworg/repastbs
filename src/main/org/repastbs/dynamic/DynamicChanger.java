/**
 * File: DynamicChanger.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Dynamic changer changes dynamic holder using holder's generator
 * @author �udov�t Hajzer
 *
 */
public interface DynamicChanger {
	
	/**
	 * Generate fields, and empty or abstract methods
	 * @param generator
	 * @throws DynamicException 
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException;
	
	/**
	 * Generate method bodies
	 * @param generator
	 * @throws DynamicException
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException;
}