package org.repastbs.component.gis;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
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
		Action move = actions.createAction("move", null,
				"space.putObjectAt(x, y, null);\n"+
				"space.putObjectAt($1, $2, this);\n"+
				"if (torus) {\n"+
				"$1 = ((Object2DTorus)space).xnorm($1);\n"+
				"$2 = ((Object2DTorus)space).ynorm($2);\n" +
				"}\n"+
				"x = $1;\n"+
				"y = $2;\n",  "void" +
				"world.getObjectAt(space.getCellCol(newX), space.getCellRow(newY)) != null);" +
				"world.putObjectAt(space.getCellCol(x), space.getCellRow(y), null)" +
				"world.putObjectAt(space.getCellCol(newX), space.getCellRow(newY), this)");
		move.addParameter("double");
		gisAgentProp.setActions(actions.getActionsProp());
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		variables.createVariable("space", "uchicago.src.sim.space.RasterSpace", null, true, false, false);
		variables.createVariable("world", "uchicago.src.sim.space.Object2DGrid", null, true, false, false);
		variables.createVariable("x", "double", null, true, false, false);
		variables.createVariable("y", "double", null, true, false, false);
		gisAgentProp.setVariables(variables.getVariablesProp());
		
		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		gisAgentProp.setSchedule(schedule.getScheduleProp());
	}

	@Override
	public String getIterableClass() {
		return getName();
	}

	@Override
	public String getSchedulableObjectName() {
		return groupName.getValue();
	}

	@Override
	public boolean isIterable() {
		return true;
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
		generator.init();
		generator.addImport("org.repastbs.model");
		generator.addField("model", getModel().getGenerator().getClassName(), null,true);
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		AbstractComponent.generateHolderFields(this);
		AbstractComponent.generateChangerFields(this,generator);
	}

	@Override
	public void generateMethods() throws DynamicException {
		AbstractComponent.generateHolderMethods(this);
		AbstractComponent.generateChangerMethods(this,generator);
		generator.saveToByteCode("output");
		generator.saveSourceCode("output");
	}

	@Override
	public DynamicGenerator getGenerator() {
		return generator;
	}

	@Override
	public void componentChanged(ComponentEvent e) {
		if(e.getSource()==agentName)
			setName(agentName.getValue());
	}

	@Override
	public void generateFields(DynamicGenerator generator)
			throws DynamicException {
		generator.addField(groupName.getValue(), "DefaultGroup", null,true);
		generator.addImport(getName());
	}

	@Override
	public void generateMethods(DynamicGenerator generator)
			throws DynamicException {
		StringBuffer initGroup = new StringBuffer();
		initGroup.append(groupName.getValue()).append(" = new DefaultGroup(Class.forName(\""+getName()+"\"), \"step\");");
		try {
			generator.insertBefore("setup", null, initGroup.toString());
		} catch (DynamicException x) {
			generator.addMethod("setup", null, null, null, initGroup.toString());
		}
		
		generator.addImport("java.util");
		StringBuffer initAgents = new StringBuffer();
		initAgents.append("Iterator iterator = ").append(groupName.getValue()).append(".iterator();");
		initAgents.append("while (iterator.hasNext()) {");
		initAgents.append("		").append(getName()).append(" agent = (")
			.append(getName()).append(") iterator.next();");
		initAgents.append("		agent.setModel(this);");
		initAgents.append("}");
		initAgents.append("initAgents();");
		initAgents.append("getAgentList().addAll(").append(groupName.getValue()).append(");");
		
		
		try {
			generator.insertAfter("begin", null, initAgents.toString() );

		} catch(DynamicException e) {
			generator.addMethod("begin",null,null, null, initAgents.toString() );
		}	
		
		try {
			generator.insertBefore("setup", null, "getAgentList().clear();" );
		} catch(DynamicException e) {
			generator.addMethod("setup",null,null, null, "getAgentList().clear();" );
		}
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