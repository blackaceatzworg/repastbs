/**
 * File: NetworkAgent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.network;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;
import org.repastbs.RepastBS;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Agent;
import org.repastbs.component.Component;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.StringComponent;
import org.repastbs.component.VariablesComponent;
import org.repastbs.component.interfaces.GameAgentInterface;
import org.repastbs.component.network.type.NetworkType;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import uchicago.src.sim.network.DefaultDrawableNode;

/**
 * Implementation of network agent
 * @author  �udov�t Hajzer
 */
public class NetworkAgent extends AbstractComponent implements 
	Agent, ComponentListener, DynamicChanger {

	/** */
	private static final long serialVersionUID = 3761529881265810264L;
	
	private StringComponent agentName;
	private StringComponent groupName;
	private NetworkType networkType;

	private JavassistGenerator generator;
	
	List<Component> supportedTypes = new ArrayList<Component>();

	/** */
	public static final String ID = "NETWORK_AGENT";

	/** */
	public NetworkAgent() {
		this("nodes");
	}

	/**
	 * @param groupName 
	 */
	public NetworkAgent(String groupName) {
		super("NetworkAgent");
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		generator = new JavassistGenerator(new DefaultDrawableNode());
		generator.addImport("uchicago.src.sim.engine");
		generator.setClassName(getName());
		//if(groupName == null)
		groupName = new StringComponent("Group name","nodes");
		add(groupName);
		
		agentName = new StringComponent("Agent name","NetworkAgent");
		agentName.addComponentListener(this);
		add(agentName);
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		
		
		VariablesComponent variables = new VariablesComponent();
		add(variables);
		variables.createNew();
		
		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		
		buildSupportedTypes();
		if(getSupportedTypes().size()>0) {
			networkType = (NetworkType)getSupportedTypes().get(0);
			add(networkType);
			networkType.createNew();
		}
		
		GameAgentInterface behavior = new GameAgentInterface();
		add(behavior);
		behavior.createNew();
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
			generator = new JavassistGenerator(new DefaultDrawableNode());
		} catch (DynamicException e) {
			throw new XMLSerializationException("NetworkAgent.createFromXML failed: "
					+e.getMessage(),e);
		}
		groupName = new StringComponent("Group name",node.valueOf("@group"));
		add(groupName);
		
		agentName = new StringComponent("Agent name",node.valueOf("@name"));
		add(agentName);
		agentName.addComponentListener(this);
		
		SAXUtils.createChildren(node, this);
		
		networkType = (NetworkType)getChildById(NetworkType.ID);
		add(networkType);
		buildSupportedTypes();
		for(int i=0;i<supportedTypes.size();i++) {
			NetworkType curr = (NetworkType)supportedTypes.get(i);
			if(curr.getClass().equals(networkType.getClass())) {
				supportedTypes.set(i,networkType);
				break;
			}	
		}
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		atts.addAttribute("", "group", "group", "CDATA", groupName.getValue());
		atts.addAttribute("", "name", "name", "CDATA", agentName.getValue());
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
	
	private void buildSupportedTypes() {
		supportedTypes = RepastBS.getComponentManager().getComponentsById("NETWORK_TYPE");
	}
	
	/**
	 * sets current network type to new type
	 * @param type  new network type
	 * @uml.property  name="networkType"
	 */
	public void setNetworkType(NetworkType type) {
		networkType.removeFromParent();
		networkType = type;
		add(networkType);
		try {
			networkType.createNew();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return  current network type
	 * @uml.property  name="networkType"
	 */
	public NetworkType getNetworkType() {
		return networkType;
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
}