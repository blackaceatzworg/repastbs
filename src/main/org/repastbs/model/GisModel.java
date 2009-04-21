/**
 * File: GisModel.java
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
import org.repastbs.component.display.GisDisplay;
import org.repastbs.component.gis.GisAgent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.GisModelProp;
import org.repastbs.generated.ModelProp;

public class GisModel extends AbstractModel {
	
	/** */
	private static final long serialVersionUID = -1752626690061068002L;
	private GisModelProp gisModelProp = new GisModelProp();

	/**
	 * @throws DynamicException
	 */
	public GisModel() throws DynamicException {
		this("Model name");
	}

	/**
	 * @param modelName
	 * @throws DynamicException
	 */
	public GisModel(String modelName) throws DynamicException {
		super("Gis model");
		setGenerator(new JavassistGenerator(new DefaultModel()));
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	@Override
	public void createNew() throws Exception {
		super.createNew("GisModel","Gis model");
		gisModelProp.getDescription().setValue("Gis is geographic information system type model");
		gisModelProp.setModelClass(this.getClass().getName());
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		gisModelProp.setVariables(variables.getVariablesProp());
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		Action initAgents = actions.createAction("initAgents");
		Action writeAgents = actions.createAction("writeAgents");
		initAgents.setRemovable(false);
		writeAgents.setRemovable(false);
		gisModelProp.setActions(actions.getActionsProp());
				
		ScheduleComponent scheduleComponent = new ScheduleComponent();
		add(scheduleComponent);
		gisModelProp.setSchedule(scheduleComponent.getScheduleProp());
		
		AgentsComponent agents = new AgentsComponent();
		GisAgent agent = new GisAgent("group");
		add(agents);
		agents.addAgent(agent);
		agent.createNew();
		gisModelProp.setAgents(agents.getAgentsProp());

		GisDisplay gisDisplay = new GisDisplay();
		add(gisDisplay);
		gisDisplay.createNew();
		gisModelProp.setGisDisplay(gisDisplay.getGisDisplayProp());

		MasterSchedule masterSchedule = new MasterSchedule();
		add(masterSchedule);
		masterSchedule.createNew();
	}

	/**
	 * @see org.repastbs.component.Component#isEditable()
	 */
	public boolean isEditable() {
		return true;
	}

	/**
	 * @param gisModelProp the gisModelProp to set
	 */
	public void setGisModelProp(GisModelProp gisModelProp) {
		this.gisModelProp = gisModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#getModelProp()
	 */
	@Override
	public ModelProp getModelProp() {
		return gisModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#setModelProp(org.repastbs.generated.ModelProp)
	 */
	@Override
	public void setModelProp(ModelProp modelProp) throws Exception {
		GisModelProp props = (GisModelProp)modelProp;
		gisModelProp = props;
		super.setModelProp(gisModelProp);
		displayName.addComponentListener(this);

		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		variables.setVariablesProp(gisModelProp.getVariables());

		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		actions.setActionsProp(gisModelProp.getActions());

		ScheduleComponent scheduleComponent = new ScheduleComponent();
		add(scheduleComponent);
		scheduleComponent.setScheduleProp(gisModelProp.getSchedule());

		AgentsComponent agents = new AgentsComponent();
		add(agents);
		agents.setAgentsProp(gisModelProp.getAgents());

		GisDisplay gisDisplay = new GisDisplay();
		add(gisDisplay);
		gisDisplay.createNew();
		gisDisplay.setGisDisplayProp(gisModelProp.getGisDisplay());

		MasterSchedule masterSchedule = new MasterSchedule();
		add(masterSchedule);
	}	
}
