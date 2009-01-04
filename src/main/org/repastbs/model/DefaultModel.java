/**
 * File: DefaultModel.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.model;

import java.util.ArrayList;
import java.util.List;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimModelImpl;

/**
 * Simple basic, no funcionality Sim Model for Repast,  this class should be extended by dynamically created models
 * @author  ¼udovít Hajzer
 */
public class DefaultModel extends SimModelImpl {

	private String name;
	
	protected Schedule schedule;
	
	protected String initParam[] = new String[0];
	
	private List agentList = new ArrayList();
	
	/**
	 * @see uchicago.src.sim.engine.SimModel#begin()
	 */
	public void begin() {
	}

	/**
	 * @see  uchicago.src.sim.engine.SimModel#getInitParam()
	 * @uml.property  name="initParam"
	 */
	public String[] getInitParam() {
		return initParam;
	}

	/**
	 * @see  uchicago.src.sim.engine.SimModel#getName()
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param  name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see  uchicago.src.sim.engine.SimModel#getSchedule()
	 * @uml.property  name="schedule"
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * @see uchicago.src.sim.engine.SimModel#setup()
	 */
	public void setup() {
	}

	/**
	 * @return  agent list
	 * @uml.property  name="agentList"
	 */
	public List getAgentList() {
		return agentList;
	}

	/**
	 * Sets the agent list
	 * @param  agentList
	 * @uml.property  name="agentList"
	 */
	public void setAgentList(List agentList) {
		this.agentList = agentList;
	}
}
