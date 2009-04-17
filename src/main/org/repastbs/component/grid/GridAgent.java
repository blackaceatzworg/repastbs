/**
 * File: GridAgent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.grid;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Agent;
import org.repastbs.component.Component;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.StringComponent;
import org.repastbs.component.Variable;
import org.repastbs.component.VariablesComponent;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.AgentProp;
import org.repastbs.generated.GridAgentProp;
import org.repastbs.model.Model;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Class representing grid agent
 * @author  Ludovit Hajzer
 */
public class GridAgent extends AbstractComponent implements 
	Agent, ComponentListener, DynamicChanger {

	/** */
	private static final long serialVersionUID = 3761529881265810264L;
	
	private GridAgentProp gridAgentProp = new GridAgentProp();
	
	private StringComponent agentName;
	private StringComponent groupName;
	private Variable groupNumAgents;
	private JavassistGenerator generator;
	
	List<Component> supportedTypes = new ArrayList<Component>();

	/** */
	public static final String ID = "GRID_AGENT";

	/** */
	public GridAgent() {
		this("nodes");
	}

	/**
	 * @param groupName 
	 */
	public GridAgent(String groupName) {
		super("GridAgent");
		setName("GridAgent");
		gridAgentProp.setAgentClass(getClass().getName());
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		generator = new JavassistGenerator(new DefaultGridAgent());
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		//if(groupName == null)
		groupName = new StringComponent("Group name","agentGroup");
		groupName.addComponentListener(this);
		add(groupName);
		gridAgentProp.setGroupName(groupName.getValue());
		
		agentName = new StringComponent("Agent name","GridAgent");
		agentName.addComponentListener(this);
		add(agentName);
		gridAgentProp.setName(agentName.getValue());
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		actions.createAction("getVonNeumannNeighbors", "java.util",
				"return space.getVonNeumannNeighbors(x, y, false);", "java.util.Vector");
		actions.createAction("getVonNeumannNeighbors", "java.util",
				"return space.getVonNeumannNeighbors(x, y, $1, $1, false);",  "java.util.Vector")
				.addParameter("int");
		actions.createAction("getMooreNeighbors", "java.util",
				"return space.getMooreNeighbors(x, y, false);",  "java.util.Vector");
		actions.createAction("getMooreNeighbors", "java.util",
				"return space.getMooreNeighbors(x, y, $1, $1, false);",  "java.util.Vector")
				.addParameter("int");
		
		Action moveIfEmpty = actions.createAction("moveIfEmpty", null,
				"if (space.getObjectAt($1, $2) == null) {\n"+
				"	move($1, $2);\n"+
				"	return true;\n"+
				"} \n"+
				"return false;\n",  "boolean");
		moveIfEmpty.addParameter("int");
		moveIfEmpty.addParameter("int");
		
		Action move = actions.createAction("move", null,
				"space.putObjectAt(x, y, null);\n"+
				"space.putObjectAt($1, $2, this);\n"+
				"if (torus) {\n"+
				"$1 = ((Object2DTorus)space).xnorm($1);\n"+
				"$2 = ((Object2DTorus)space).ynorm($2);\n" +
				"}\n"+
				"x = $1;\n"+
				"y = $2;\n",  "void");
		move.addParameter("int");
		move.addParameter("int");
		gridAgentProp.setActions(actions.getActionsProp());
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		Variable space = variables.createVariable("space", "uchicago.src.sim.space.Object2DGrid", null, true, false, false);
		space.setEditable(false);
		variables.createVariable("x", "int", null, true, false, false);
		variables.createVariable("y", "int", null, true, false, false);
		variables.createVariable("torus", "boolean", "false", true, false, false);
		gridAgentProp.setVariables(variables.getVariablesProp());
		
		groupNumAgents = ((VariablesComponent)getModel().getChildById(VariablesComponent.ID))
			.createVariable(groupName.getValue()+"NumAgents", "int", "5000", true, true, false);
		//groupNumAgents.setEditable(false);
		
		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		gridAgentProp.setSchedule(schedule.getScheduleProp());
	}

	/**
	 * @see org.repastbs.component.Schedulable#getSchedulableObjectName()
	 */
	public String getSchedulableObjectName() {
		return groupName.getValue();
	}	

	/**
	 * @see org.repastbs.dynamic.DynamicHolder#generateFields()
	 */
	public void generateFields() throws DynamicException {
		generator.init();
		generator.addImport("org.repastbs.model");
		generator.addField("model", getModel().getGenerator().getClassName(), null,true);
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		generator.addConstructor(null, null, null);
		AbstractComponent.generateHolderFields(this);
		AbstractComponent.generateChangerFields(this,generator);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicHolder#generateMethods()
	 */
	public void generateMethods() throws DynamicException {
		AbstractComponent.generateHolderMethods(this);
		AbstractComponent.generateChangerMethods(this,generator);
		generator.saveToByteCode("output");
		generator.saveSourceCode("output");
	}

	/**
	 * @return  generator
	 * @uml.property  name="generator"
	 */
	public DynamicGenerator getGenerator() {
		return generator;
	}

	/**
	 * @see org.repastbs.component.Schedulable#getIterableClass()
	 */
	public String getIterableClass() {
		return getName();
	}

	/**
	 * @see org.repastbs.component.Schedulable#isIterable()
	 */
	public boolean isIterable() {
		return true;
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		try {
			generator = new JavassistGenerator(new DefaultGridAgent());
		} catch (DynamicException e) {
			throw new XMLSerializationException("NetworkAgent.createFromXML failed: "
					+e.getMessage(),e);
		}
		groupName = new StringComponent("Group name",node.valueOf("@group"));
		add(groupName);
		
		agentName = new StringComponent("Agent name",node.valueOf("@name"));
		setName(agentName.getValue());
		add(agentName);
		agentName.addComponentListener(this);
		
		SAXUtils.createChildren(node, this);
		
		groupNumAgents = ((VariablesComponent)((Model)getRoot())
				.getChildById(VariablesComponent.ID)).getChildByName(groupName.getValue()+"NumAgents");
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		atts.addAttribute("", "group", "group", "CDATA", gridAgentProp.getGroupName());
		atts.addAttribute("", "name", "name", "CDATA", gridAgentProp.getName());
		try {
			SAXUtils.start(handler, "agent", atts);
			SAXUtils.serializeChildren(handler,this);
			SAXUtils.end(handler, "agent");
		} catch (SAXException e) {
			throw new XMLSerializationException("AgentsComponent.createFromXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.component.ComponentListener#componentChanged(org.repastbs.component.ComponentEvent)
	 */
	public void componentChanged(ComponentEvent e) {
		if(e.getSource()==agentName)
			setName(agentName.getValue());
		if(e.getSource()==groupName)
			groupNumAgents.setName(groupName.getValue()+"NumAgents");
	}
	
	/**
	 * @return  group name value
	 * @uml.property  name="groupName"
	 */
	public String getGroupName() {
		return groupName.getValue();
	}
	
	/**
	 * @return  supported network types
	 * @uml.property  name="supportedTypes"
	 */
	public List<Component> getSupportedTypes() {
		return supportedTypes;
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField(groupName.getValue(), "DefaultGroup", null,true);
		generator.addImport(getName());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		StringBuffer initGroup = new StringBuffer();
		initGroup.append(groupName.getValue()).append(" = new DefaultGroup(Class.forName(\""+getName()+"\"), \"step\");");
		try {
			generator.insertBefore("setup", null, initGroup.toString());
		} catch (DynamicException x) {
			generator.addMethod("setup", null, null, null, initGroup.toString());
		}
		StringBuffer initAgents = new StringBuffer();
		initAgents.append("for(int i = 0; i<"+groupNumAgents.getName()+"; i++) {\n");
		initAgents.append("		"+agentName.getValue()+" agent = new "+agentName.getValue()+"();\n");
		initAgents.append("		"+groupName.getValue()+".add(agent);\n");
		initAgents.append("}");
		
		
		generator.addImport("java.util");

		initAgents.append("Iterator iterator = ").append(groupName.getValue()).append(".iterator();");
		initAgents.append("while (iterator.hasNext()) {");
		initAgents.append("		").append(getName()).append(" agent = (")
			.append(getName()).append(") iterator.next();");
		initAgents.append("		agent.setModel(this);");
		initAgents.append("		agent.setSpace(space);");
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
	 * @see org.repastbs.component.AbstractComponent#setName(java.lang.String)
	 */
	public void setName(String name) {
		super.setName(name);
		gridAgentProp.setName(name);
	}

	/**
	 * @see org.repastbs.component.Agent#setAgentProp(org.repastbs.generated.AgentProp)
	 */
	@Override
	public void setAgentProp(AgentProp agentProp) throws Exception {
		this.gridAgentProp = (GridAgentProp)agentProp;
		generator = new JavassistGenerator(new DefaultGridAgent());
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());

		groupName = new StringComponent("Group name",gridAgentProp.getGroupName());
		groupName.addComponentListener(this);
		add(groupName);
		
		agentName = new StringComponent("Agent name",gridAgentProp.getName());
		agentName.addComponentListener(this);
		add(agentName);

		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.setActionsProp(gridAgentProp.getActions());

		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.setVariablesProp(gridAgentProp.getVariables());

		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.setScheduleProp(gridAgentProp.getSchedule());
	}

	/**
	 * @see org.repastbs.component.Agent#getAgentProp()
	 */
	@Override
	public AgentProp getAgentProp() {
		return gridAgentProp;
	}
}