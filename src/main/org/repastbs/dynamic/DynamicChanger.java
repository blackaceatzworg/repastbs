/**
 * File: DynamicChanger.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Dynamic changer changes dynamic holder using holder's generator
 * @author ¼udovít Hajzer
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