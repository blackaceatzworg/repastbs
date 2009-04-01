/**
 * File: GridModel.java
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
 * @author Ľudovít Hajzer
 *
 */
public class GridModel extends AbstractModel {

	/** */
	private static final long serialVersionUID = 6147610689058660846L;
	
	private GridModelProp gridModelProp;
	
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
		setDescription("Grid type model in which agents operate on a two dimensional matrix grid or torus.");
		setGenerator(new JavassistGenerator(new DefaultModel()));
	}
	

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		super.createNew("GridModel","Grid model");
		gridModelProp = new GridModelProp();
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
		
		GridSpace gridSpace = new GridSpace();
		add(gridSpace);
		gridSpace.createNew();
		gridModelProp.setGridSpace(gridSpace.getGridSpaceProp());
		
		GridDisplay display = new GridDisplay();
		add(display);
		display.createNew();
		gridModelProp.setGridDisplay(display.getGridDisplayProp());
		
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
}