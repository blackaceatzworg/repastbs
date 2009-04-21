/**
 * File: GridModel.java
 * Program: Repast BS
 * Author:  Ludovit Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: Laszlo Gulyas, Ph.D.
 */
package org.repastbs.model;

import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.AgentsComponent;
import org.repastbs.component.MasterSchedule;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.Variable;
import org.repastbs.component.VariablesComponent;
import org.repastbs.component.display.GridDisplay;
import org.repastbs.component.grid.GridAgent;
import org.repastbs.component.grid.GridSpace;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.ModelProp;
import org.repastbs.generated.GridModelProp;

/**
 * Grid model template
 * @author Ludovit Hajzer
 *
 */
public class GridModel extends AbstractModel {
	
	/** */
	private static final long serialVersionUID = -8643103595595877830L;
	private GridModelProp gridModelProp = new GridModelProp();
	
	/**
	 * @throws DynamicException 
	 */
	public GridModel() throws DynamicException {
		this("Model name");
	}
	
	/**
	 * @param modelName
	 * @throws DynamicException 
	 */
	public GridModel(String modelName) throws DynamicException {
		super("Grid model");
		setGenerator(new JavassistGenerator(new DefaultModel()));
	}
	

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		super.createNew("GridModel","Grid model");
		gridModelProp.getDescription().setValue("Grid type model in which agents operate on a two dimensional matrix grid or torus.");	
		gridModelProp.setModelClass(this.getClass().getName());

		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		gridModelProp.setVariables(variables.getVariablesProp());
		Variable space = variables.createVariable("space", "uchicago.src.sim.space.Object2DGrid", null, false, false, false);
		space.setEditable(false);
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		Action initAgents = actions.createAction("initAgents");
		initAgents.setRemovable(false);
		initAgents.setChangeSignature(false);
		gridModelProp.setActions(actions.getActionsProp());
				
		ScheduleComponent scheduleComponent = new ScheduleComponent();
		add(scheduleComponent);
		gridModelProp.setSchedule(scheduleComponent.getScheduleProp());
		
		AgentsComponent agents = new AgentsComponent();
		GridAgent agent = new GridAgent("group");
		add(agents);
		agents.addAgent(agent);
		agent.createNew();
		gridModelProp.setAgents(agents.getAgentsProp());
		
		GridSpace gridSpace = new GridSpace();
		add(gridSpace);
		gridSpace.createNew();
		gridModelProp.setGridSpace(gridSpace.getGridSpaceProp());
		
		GridDisplay gridDisplay = new GridDisplay();
		add(gridDisplay);
		gridDisplay.createNew();
		gridModelProp.setGridDisplay(gridDisplay.getGridDisplayProp());
		
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
	 * @param gridModelProp the gridModelProp to set
	 */
	public void setGridModelProp(GridModelProp gridModelProp) {
		this.gridModelProp = gridModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#getModelProp()
	 */
	public ModelProp getModelProp() {
		return gridModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#setModelProp(org.repastbs.generated.ModelProp)
	 */
	public void setModelProp(ModelProp modelProp) throws Exception {
		GridModelProp props = (GridModelProp)modelProp;
		gridModelProp = props;
		super.setModelProp(gridModelProp);
		displayName.addComponentListener(this);

		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		variables.setVariablesProp(gridModelProp.getVariables());

		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		actions.setActionsProp(gridModelProp.getActions());

		ScheduleComponent scheduleComponent = new ScheduleComponent();
		add(scheduleComponent);
		scheduleComponent.setScheduleProp(gridModelProp.getSchedule());

		AgentsComponent agents = new AgentsComponent();
		add(agents);
		agents.setAgentsProp(gridModelProp.getAgents());

		GridSpace gridSpace = new GridSpace();
		add(gridSpace);
		//gridSpace.createNew();
		gridSpace.setGridSpaceProp(gridModelProp.getGridSpace());

		GridDisplay gridDisplay = new GridDisplay();
		add(gridDisplay);
		gridDisplay.createNew();
		gridDisplay.setGridDisplayProp(gridModelProp.getGridDisplay());

		MasterSchedule masterSchedule = new MasterSchedule();
		add(masterSchedule);
	}
}