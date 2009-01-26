/**
 * File: DynamicChanger.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
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