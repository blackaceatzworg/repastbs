/**
 * File: RandomDensityNetwork.java
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
import org.repastbs.generated.RandomDensityNetworkProp;
import org.repastbs.model.Model;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Implementation of Random density network type, used in network agent
 * @author  �udov�t Hajzer
 */
public class RandomDensityNetwork extends AbstractComponent implements NetworkType, ComponentListener {	

	/** */
	private static final long serialVersionUID = 1857953851235595976L;
	
	private RandomDensityNetworkProp networkTypeProp = new RandomDensityNetworkProp();
	
	private Variable size;
	private Variable density;
	private Variable allowLoops;
	private Variable symmetric;
	
	/**
	 * Default empty constructor
	 */
	public RandomDensityNetwork() {
		this("Random density network");
	}
	
	/**
	 * Constructor with name
	 * @param name
	 */
	public RandomDensityNetwork(String name) {
		super(name);
		setId(ID);
	}

	/**
	 * @return  the allowLoops
	 * @uml.property  name="allowLoops"
	 */
	public Variable getAllowLoops() {
		return allowLoops;
	}

	/**
	 * @param allowLoops  the allowLoops to set
	 * @uml.property  name="allowLoops"
	 */
	public void setAllowLoops(Variable allowLoops) {
		this.allowLoops = allowLoops;
		networkTypeProp.setAllowLoopsVar(allowLoops.getName());
	}

	/**
	 * @return  the density
	 * @uml.property  name="density"
	 */
	public Variable getDensity() {
		return density;
	}

	/**
	 * @param density  the density to set
	 * @uml.property  name="density"
	 */
	public void setDensity(Variable density) {
		this.density = density;
		networkTypeProp.setDensityVar(density.getName());
	}

	/**
	 * @return  the size
	 * @uml.property  name="size"
	 */
	public Variable getSize() {
		return size;
	}

	/**
	 * @param size  the size to set
	 * @uml.property  name="size"
	 */
	public void setSize(Variable size) {
		this.size = size;
		networkTypeProp.setSizeVar(size.getName());
	}

	/**
	 * @return  the symmetric
	 * @uml.property  name="symmetric"
	 */
	public Variable getSymmetric() {
		return symmetric;
	}

	/**
	 * @param symmetric  the symmetric to set
	 * @uml.property  name="symmetric"
	 */
	public void setSymmetric(Variable symmetric) {
		this.symmetric = symmetric;
		networkTypeProp.setSymmetricVar(symmetric.getName());
	}
	
	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {			
			SAXUtils.start(handler, "networkType", atts);
			SAXUtils.text(handler, "sizeVar", ""+size.getName());
			SAXUtils.text(handler, "densityVar", ""+density.getName());
			SAXUtils.text(handler, "allowLoopsVar", ""+allowLoops.getName());
			SAXUtils.text(handler, "symmetricVar", ""+symmetric.getName());
			SAXUtils.end(handler, "networkType");
		} catch (SAXException e) {
			throw new XMLSerializationException("CircularLayout.writeToXML: "+e.getMessage(),e);
		}
		
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		this.setName("Random density network");
		try {
			size = findVariable(node.valueOf("sizeVar/text()"));
			size.setRemovable(false);
			density = findVariable(node.valueOf("densityVar/text()"));
			density.setRemovable(false);
			allowLoops = findVariable(node.valueOf("allowLoopsVar/text()"));
			allowLoops.setRemovable(false);
			symmetric = findVariable(node.valueOf("symmetricVar/text()"));
			symmetric.setRemovable(false);
			StringComponent group = (StringComponent)getParent().getChildByName("Group name");
			if(group!=null)
				group.addComponentListener(this);
		} catch (Exception e) {
			throw new XMLSerializationException("CircularLayout.createFromXML: "+e.getMessage(),e);
		}
	}
	
	private Variable findVariable(String name) {
		return ((VariablesComponent)((Model)getRoot())
				.getChildById(VariablesComponent.ID)).getChildByName(name);
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
		this.setName("Random density network");
		VariablesComponent v = (VariablesComponent)((Model)getParent().getParent().getParent())
			.getChildById(VariablesComponent.ID);
		StringComponent group = (StringComponent)getParent().getChildByName("Group name");
		if(group!=null)
			group.addComponentListener(this);
		size=v.createVariable(group!=null?group.getValue()+"Size":"size",
			"int","40",true,true, false);
		networkTypeProp.setSizeVar(size.getName());
		density=v.createVariable(group!=null?group.getValue()+"Density":"density",
				"double","0.3",true,true, false);
		networkTypeProp.setDensityVar(density.getName());
		allowLoops=v.createVariable(group!=null?group.getValue()+"AllowLoops":"allowLoops",
				"boolean","false",true,true, false);
		networkTypeProp.setAllowLoopsVar(allowLoops.getName());
		symmetric=v.createVariable(group!=null?group.getValue()+"Symmetric":"symmetric",
				"boolean","false",true,true, false);
		networkTypeProp.setSymmetricVar(symmetric.getName());
	}

	/**
	 * @see org.repastbs.component.ComponentListener#componentChanged(org.repastbs.component.ComponentEvent)
	 */
	public void componentChanged(ComponentEvent e) {
		size.setName(((StringComponent)e.getSource()).getValue()+"Size");
		networkTypeProp.setSizeVar(size.getName());
		density.setName(((StringComponent)e.getSource()).getValue()+"Density");
		networkTypeProp.setDensityVar(density.getName());
		allowLoops.setName(((StringComponent)e.getSource()).getValue()+"AllowLoops");
		networkTypeProp.setAllowLoopsVar(allowLoops.getName());
		symmetric.setName(((StringComponent)e.getSource()).getValue()+"Symmetric");
		networkTypeProp.setSymmetricVar(symmetric.getName());
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#removeFromParent()
	 */
	public void removeFromParent() {
		//Model m = (Model)getRoot();
		VariablesComponent v = (VariablesComponent)size.getParent();
		v.removeChildProp(density);
		v.remove(density);
		v.removeChildProp(allowLoops);
		v.remove(allowLoops);
		v.removeChildProp(symmetric);
		v.remove(symmetric);
		v.removeChildProp(size);
		v.remove(size);
		super.removeFromParent();
		/*((DefaultTreeModel)m.getTreeModel()).nodeStructureChanged(v);
		((DefaultTreeModel)m.getTreeModel()).nodeChanged(this);*/
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
		//this component ignores passed generator, instead use model's generator
		generator = getModel().getGenerator();
		StringBuffer createNetwork = new StringBuffer();
		createNetwork.append("java.util.List list");
		createNetwork.append("= (uchicago.src.sim.network.NetworkFactory).createRandomDensityNetwork(");
		
		createNetwork.append(size).append(", ");
	    createNetwork.append(density).append(", ");
	    createNetwork.append(allowLoops).append(", ");
	    createNetwork.append(symmetric).append(", ");

		NetworkAgent agent = (NetworkAgent)getParent();
		createNetwork.append("Class.forName(\"");
		createNetwork.append(agent.getGenerator().getClassName()).append("\"), ");
		createNetwork.append("Class.forName(\"uchicago.src.sim.network.DefaultDrawableEdge\"));\n");
		createNetwork.append(agent.getGroupName()+".addAll(list);");

		try {
			generator.insertBefore("begin", null, createNetwork.toString() );
		} catch(DynamicException e) {
			generator.addMethod("begin",null,null, null, createNetwork.toString() );
		}
	}

	/**
	 * @return the networkTypeProp
	 */
	public RandomDensityNetworkProp getNetworkTypeProp() {
		return networkTypeProp;
	}

	/**
	 * @param networkTypeProp the networkTypeProp to set
	 */
	public void setNetworkTypeProp(RandomDensityNetworkProp networkTypeProp) {
		this.networkTypeProp = networkTypeProp;
	}
}
