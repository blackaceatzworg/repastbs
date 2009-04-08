/**
 * File: GameAgentInterface.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.interfaces;

import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.GameAgentInterfaceProp;

/**
 * Implementation of component interface
 * This interfaces implements uchicago.src.sim.games.GameAgent 
 * interface in generated class
 * @author Ludovit Hajzer
 *
 */
public class GameAgentInterface extends AbstractComponentInterface {

	/** */
	private static final long serialVersionUID = 7541447410401404722L;
	
	private GameAgentInterfaceProp gaip = new GameAgentInterfaceProp();
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
	 * @return the gaip
	 */
	public GameAgentInterfaceProp getGaip() {
		return gaip;
	}

	/**
	 * @param gaip the gaip to set
	 */
	public void setGaip(GameAgentInterfaceProp gaip) {
		this.gaip = gaip;
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		// DO NOTHING
	}
}
