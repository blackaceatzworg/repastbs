package org.repastbs.component.gis;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Agent;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.StringComponent;
import org.repastbs.component.VariablesComponent;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.AgentProp;
import org.repastbs.generated.GisAgentProp;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;

public class GisAgent extends AbstractComponent implements 
Agent, ComponentListener, DynamicChanger {

	/** */
	private static final long serialVersionUID = -1251324159052478587L;

	private GisAgentProp gisAgentProp = new GisAgentProp();

	private StringComponent agentName;
	private StringComponent groupName;
	private JavassistGenerator generator;

	/** */
	public GisAgent() {
		this("nodes");
	}

	/**
	 * @param name
	 */
	public GisAgent(String name) {
		super("GisAgent");
		setName("GisAgent");
		gisAgentProp.setAgentClass(getClass().getName());
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	@Override
	public void createNew() throws Exception {
		generator = new JavassistGenerator(new GisAgent());
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		
		groupName = new StringComponent("Group name","agentGroup");
		groupName.addComponentListener(this);
		add(groupName);
		gisAgentProp.setGroupName(groupName.getValue());
		
		agentName = new StringComponent("Agent name","GisAgent");
		agentName.addComponentListener(this);
		add(agentName);
		gisAgentProp.setName(agentName.getValue());
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		gisAgentProp.setActions(actions.getActionsProp());
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		gisAgentProp.setVariables(variables.getVariablesProp());
		
		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		gisAgentProp.setSchedule(schedule.getScheduleProp());
	}

	@Override
	public String getIterableClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSchedulableObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIterable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createFromXML(Node node) throws XMLSerializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToXML(ContentHandler handler)
			throws XMLSerializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFields() throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMethods() throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DynamicGenerator getGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void componentChanged(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFields(DynamicGenerator generator)
			throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMethods(DynamicGenerator generator)
			throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.repastbs.component.Agent#setAgentProp(org.repastbs.generated.AgentProp)
	 */
	@Override
	public void setAgentProp(AgentProp agentProp) throws Exception {
		this.gisAgentProp = (GisAgentProp)agentProp;
		generator = new JavassistGenerator(new GisAgent());
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		
		groupName = new StringComponent("Group name",gisAgentProp.getGroupName());
		add(groupName);
		
		agentName = new StringComponent("Agent name",gisAgentProp.getName());
		agentName.addComponentListener(this);
		add(agentName);

		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.setActionsProp(gisAgentProp.getActions());
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.setVariablesProp(gisAgentProp.getVariables());
		
		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.setScheduleProp(gisAgentProp.getSchedule());
	}

	/**
	 * @see org.repastbs.component.Agent#getAgentProp()
	 */
	@Override
	public AgentProp getAgentProp() {
		return gisAgentProp;
	}
}