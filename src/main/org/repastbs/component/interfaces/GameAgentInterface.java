/**
 * File: GameAgentInterface.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.component.interfaces;

import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;

/**
 * Implementation of component interface
 * This interfaces implements uchicago.src.sim.games.GameAgent 
 * interface in generated class
 * @author �udov�t Hajzer
 *
 */
public class GameAgentInterface extends AbstractComponentInterface {

	/** */
	private static final long serialVersionUID = 7541447410401404722L;
	
	/** */
	public static final String ID = "GAME_AGENT";

	/**
	 * default constructor
	 */
	public GameAgentInterface() {
		super("Game Agent");
		setId(ID);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField("strategy", "int", null, true);
		generator.addField("payoff", "float", null, true);
		generator.addInterface("uchicago.src.sim.games.GameAgent");
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		// DO NOTHING
	}
}
