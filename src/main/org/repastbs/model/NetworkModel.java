/**
 * File: NetworkModel.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.model;

import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.AgentsComponent;
import org.repastbs.component.MasterSchedule;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.VariablesComponent;
import org.repastbs.component.display.NetworkDisplay;
import org.repastbs.component.network.NetworkAgent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.ModelProp;
import org.repastbs.generated.NetworkModelProp;

/**
 * Network model template
 * @author Ľudovít Hajzer
 *
 */
public class NetworkModel extends AbstractModel {

	/** */
	private static final long serialVersionUID = 6147610689058660846L;
	
	private NetworkModelProp networkModelProp;
	
	/**
	 * @throws DynamicException 
	 */
	public NetworkModel() throws DynamicException {
		this("Model name");
	}
	
	/**
	 * @param modelName
	 * @throws DynamicException 
	 */
	public NetworkModel(String modelName) throws DynamicException {
		super("Network model");
		setDescription("Network type model in which agents are nodes" +
				" in a network. Agents can be created " +
				"in a variety of network topologies.");
		setGenerator(new JavassistGenerator(new DefaultModel()));
	}
	

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		super.createNew("NetworkModel","Network model");
		networkModelProp = new NetworkModelProp();
		displayName.addComponentListener(this);
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		networkModelProp.setVariables(variables.getVariablesProp());
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		Action a = actions.createAction("initAgents","void");
		a.setRemovable(false);
		networkModelProp.setActions(actions.getActionsProp());
		
		ScheduleComponent scheduleComponent = new ScheduleComponent();
		add(scheduleComponent);
		networkModelProp.setSchedule(scheduleComponent.getScheduleProp());
		
		AgentsComponent agents = new AgentsComponent();
		NetworkAgent agent = new NetworkAgent("group");
		add(agents);
		agents.addAgent(agent);
		agent.createNew();
		
		MasterSchedule masterSchedule = new MasterSchedule();
		add(masterSchedule);
		masterSchedule.createNew();
		
		NetworkDisplay networkDisplay = new NetworkDisplay();
		add(networkDisplay);
		networkDisplay.createNew();
		networkModelProp.setNetworkDisplay(networkDisplay.getNetworkDisplayProp());
	}
	
	/**
	 * @see org.repastbs.component.Component#isEditable()
	 */
	public boolean isEditable() {
		return true;
	}

	/**
	 * @param networkModelProp the networkModelProp to set
	 */
	public void setNetworkModelProp(NetworkModelProp networkModelProp) {
		this.networkModelProp = networkModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#getModelProp()
	 */
	public ModelProp getModelProp() {
		return networkModelProp;
	}
	
	
}