/**
 * File: UnlinkedNetwork.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.network.type;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.component.StringComponent;
import org.repastbs.component.Variable;
import org.repastbs.component.VariablesComponent;
import org.repastbs.component.network.NetworkAgent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.UnlinkedNetworkProp;
import org.repastbs.model.Model;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Implementation of Unlinked network type, used in network agent
 * @author  Ludovit Hajzer
 */
public class UnlinkedNetwork extends AbstractComponent implements NetworkType, ComponentListener {	

	/** */
	private static final long serialVersionUID = -2907826650030007648L;
	
	private UnlinkedNetworkProp networkTypeProp = new UnlinkedNetworkProp();
	
	private Variable nodeCount;
	
	/**
	 * Default empty constructor
	 */
	public UnlinkedNetwork() {
		this("Unlinked network",10);
	}
	
	/**
	 * Constructor with name
	 * @param name 
	 * @param nodeCount 
	 */
	public UnlinkedNetwork(String name, int nodeCount) {
		super(name);
		setId(ID);
	}

	/**
	 * @return  the nodeCount
	 * @uml.property  name="nodeCount"
	 */
	public Variable getNodeCount() {
		return nodeCount;
	}

	/**
	 * @param nodeCount  the nodeCount to set
	 * @uml.property  name="nodeCount"
	 */
	public void setNodeCount(Variable nodeCount) {
		this.nodeCount = nodeCount;
		networkTypeProp.setCountVar(nodeCount.getName());
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {
			SAXUtils.start(handler, "networkType", atts);
			SAXUtils.text(handler, "countVar", ""+getNodeCount().getName());
			SAXUtils.end(handler, "networkType");
		} catch (SAXException e) {
			throw new XMLSerializationException("UnlinkedNetwork.writeToXML: "+e.getMessage(),e);
		}
		
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setName("Unlinked network");
		try {
			nodeCount = ((VariablesComponent)((Model)getRoot())
					.getChildById(VariablesComponent.ID)).getChildByName(
							node.valueOf("countVar/text()"));
			nodeCount.setRemovable(false);
		} catch (Exception e) {
			throw new XMLSerializationException("UnlinkedNetwork.createFromXML: "+e.getMessage(),e);
		}
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return false;
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {
		this.setName("Unlinked network");
		VariablesComponent v = (VariablesComponent)((Model)getRoot())
			.getChildById(VariablesComponent.ID);
		StringComponent group = (StringComponent)getParent().getChildByName("Group name");
		if(group!=null)
			group.addComponentListener(this);
		nodeCount = v.createVariable(group!=null?group.getValue()+"Count":"count",
				"int","40",true,true, false);
		networkTypeProp.setCountVar(nodeCount.getName());
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#removeFromParent()
	 */
	public void removeFromParent() {
		//Model m = (Model)getRoot();
		VariablesComponent v = (VariablesComponent)nodeCount.getParent();
		v.remove(nodeCount);
		super.removeFromParent();
		/*((DefaultTreeModel)m.getTreeModel()).nodeChanged(this);
		((DefaultTreeModel)m.getTreeModel()).nodeStructureChanged(v);*/
	}

	/**
	 * @see org.repastbs.component.ComponentListener#componentChanged(org.repastbs.component.ComponentEvent)
	 */
	public void componentChanged(ComponentEvent e) {
		nodeCount.setName(((StringComponent)e.getSource()).getValue()+"Count");
		networkTypeProp.setCountVar(nodeCount.getName());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//this component ignores passed generator, instead uses model's generator
		generator = getModel().getGenerator();
		StringBuffer createNetwork = new StringBuffer();
		createNetwork.append("java.util.List list");
		createNetwork.append(" = (uchicago.src.sim.network.NetworkFactory).createUnlinkedNetwork(");
		createNetwork.append(getNodeCount()).append(", ");
		NetworkAgent agent = (NetworkAgent)getParent();
		createNetwork.append("Class.forName(\"");
		createNetwork.append(agent.getGenerator().getClassName()).append("\"));\n");
		createNetwork.append(agent.getGroupName()+".addAll(list);");
		try {
			generator.insertBefore("begin", null, createNetwork.toString() );
		} catch(DynamicException e) {
			generator.addMethod("begin",null,null, null, createNetwork.toString() );
		}
	}

	/**
	 * @return the networkProp
	 */
	public UnlinkedNetworkProp getNetworkTypeProp() {
		return networkTypeProp;
	}

	/**
	 * @param networkProp the networkProp to set
	 */
	public void setNetworkTypeProp(UnlinkedNetworkProp networkProp) {
		this.networkTypeProp = networkProp;
	}	
}
