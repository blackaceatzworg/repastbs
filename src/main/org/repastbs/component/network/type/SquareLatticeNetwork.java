/**
 * 
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
import org.repastbs.generated.NetworkTypeProp;
import org.repastbs.generated.SquareLatticeNetworkProp;
import org.repastbs.model.Model;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.repastbs.component.network.type.NetworkType;

/**
 * @author Ludovit Hajzer
 *
 */
public class SquareLatticeNetwork extends AbstractComponent implements NetworkType, ComponentListener {	

	/** */
	private static final long serialVersionUID = 4787760316023623923L;
	
	private SquareLatticeNetworkProp squareLatticeNetworkProp = new SquareLatticeNetworkProp();
	
	private Variable cols;
	private Variable connectRadius;
	private Variable rows;
	private Variable wrapAround;
	
	/**
	 * Default empty constructor
	 */
	public SquareLatticeNetwork() {
		super("Square Lattice");
		squareLatticeNetworkProp.setNetworkTypeClass(this.getClass().getName());
		setId(ID);
	}

	/**
	 * @return the cols
	 */
	public Variable getCols() {
		return cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(Variable cols) {
		this.cols = cols;
		squareLatticeNetworkProp.setColsVar(cols.getName());
	}

	/**
	 * @return the connectRadius
	 */
	public Variable getConnectRadius() {
		return connectRadius;
	}

	/**
	 * @param connectRadius the connectRadius to set
	 */
	public void setConnectRadius(Variable connectRadius) {
		this.connectRadius = connectRadius;
		squareLatticeNetworkProp.setConnectRadiusVar(connectRadius.getName());
	}

	/**
	 * @return the rows
	 */
	public Variable getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Variable rows) {
		this.rows = rows;
		squareLatticeNetworkProp.setRowsVar(rows.getName());
	}

	/**
	 * @return the wrapAround
	 */
	public Variable getWrapAround() {
		return wrapAround;
	}

	/**
	 * @param wrapAround the wrapAround to set
	 */
	public void setWrapAround(Variable wrapAround) {
		this.wrapAround = wrapAround;
		squareLatticeNetworkProp.setWrapAroundVar(wrapAround.getName());
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {
			SAXUtils.start(handler, "networkType", atts);
			SAXUtils.text(handler, "colsVar", ""+getCols().getName());
			SAXUtils.text(handler, "connectRadiusVar", ""+getConnectRadius().getName());
			SAXUtils.text(handler, "rowsVar", ""+getRows().getName());
			SAXUtils.text(handler, "wrapAroundVar", ""+getWrapAround().getName());			
			SAXUtils.end(handler, "networkType");
		} catch (SAXException e) {
			throw new XMLSerializationException("SquareLatticeNetwork.writeToXML: "+e.getMessage(),e);
		}
		
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		System.out.println("Creating from xml");
		this.setName("Square Lattice");
		try {
			cols = initVariable(node.valueOf("colsVar/text()"));
			connectRadius = initVariable(node.valueOf("connectRadiusVar/text()"));
			rows = initVariable(node.valueOf("rowsVar/text()"));
			wrapAround = initVariable(node.valueOf("wrapAroundVar/text()"));
		} catch (Exception e) {
			throw new XMLSerializationException("SquareLatticeNetwork.createFromXML: "+e.getMessage(),e);
		}
	}
	
	private Variable initVariable(String varName) {
		Variable v = ((VariablesComponent)((Model)getRoot())
				.getChildById(VariablesComponent.ID)).getChildByName(varName);
		v.setRemovable(false);
		return v;
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
		this.setName("Square Lattice");
		VariablesComponent v = (VariablesComponent)((Model)getRoot())
			.getChildById(VariablesComponent.ID);
		StringComponent group = (StringComponent)getParent().getChildByName("Group name");
		if(group!=null)
			group.addComponentListener(this);
		cols = v.createVariable(group!=null?group.getValue()+"Cols":"cols",
				"int","10",true,true, false);
		squareLatticeNetworkProp.setColsVar(cols.getName());
		connectRadius = v.createVariable(group!=null?group.getValue()+"ConnectRadius":"connectRadius",
				"int","1",true,true, false);
		squareLatticeNetworkProp.setConnectRadiusVar(connectRadius.getName());
		rows = v.createVariable(group!=null?group.getValue()+"Rows":"rows",
				"int","5",true,true, false);
		squareLatticeNetworkProp.setRowsVar(rows.getName());
		wrapAround = v.createVariable(group!=null?group.getValue()+"WrapAround":"wrapAround",
				"boolean","false",true,true, false);
		squareLatticeNetworkProp.setWrapAroundVar(wrapAround.getName());
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#removeFromParent()
	 */
	public void removeFromParent() {
		VariablesComponent v = (VariablesComponent)cols.getParent();
		v.removeChildProp(connectRadius);
		v.remove(connectRadius);
		v.removeChildProp(rows);
		v.remove(rows);
		v.removeChildProp(wrapAround);
		v.remove(wrapAround);
		v.removeChildProp(cols);
		v.remove(cols);
		super.removeFromParent();
	}

	/**
	 * @see org.repastbs.component.ComponentListener#componentChanged(org.repastbs.component.ComponentEvent)
	 */
	public void componentChanged(ComponentEvent e) {
		cols.setName(((StringComponent)e.getSource()).getValue()+"Cols");
		squareLatticeNetworkProp.setColsVar(cols.getName());
		connectRadius.setName(((StringComponent)e.getSource()).getValue()+"ConnectRadius");
		squareLatticeNetworkProp.setConnectRadiusVar(connectRadius.getName());
		rows.setName(((StringComponent)e.getSource()).getValue()+"Rows");
		squareLatticeNetworkProp.setRowsVar(rows.getName());
		wrapAround.setName(((StringComponent)e.getSource()).getValue()+"WrapAround");
		squareLatticeNetworkProp.setWrapAroundVar(wrapAround.getName());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		//DO NOTHING
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		//this component ignores passed generator, instead uses model's generator
		generator = getModel().getGenerator();
		StringBuffer createNetwork = new StringBuffer();
		createNetwork.append("java.util.List list");
		createNetwork.append(" = (uchicago.src.sim.network.NetworkFactory).createSquareLatticeNetwork(");
		createNetwork.append(getCols()).append(", ");
		createNetwork.append(getRows()).append(", ");
		createNetwork.append(getWrapAround()).append(", ");
		createNetwork.append(getConnectRadius()).append(", ");
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
	 * @see org.repastbs.component.network.type.NetworkType#getNetworkTypeProp()
	 */
	@Override
	public NetworkTypeProp getNetworkTypeProp() {
		return squareLatticeNetworkProp;
	}

	/**
	 * @see org.repastbs.component.network.type.NetworkType#setNetworkTypeProp(org.repastbs.generated.NetworkTypeProp)
	 */
	@Override
	public void setNetworkTypeProp(NetworkTypeProp networkTypeProp) {
		this.squareLatticeNetworkProp = (SquareLatticeNetworkProp)networkTypeProp;
		this.cols = Variable.findVariable(this,this.squareLatticeNetworkProp.getColsVar());
		this.connectRadius = Variable.findVariable(this,this.squareLatticeNetworkProp.getConnectRadiusVar());
		this.rows = Variable.findVariable(this,this.squareLatticeNetworkProp.getRowsVar());
		this.wrapAround = Variable.findVariable(this,this.squareLatticeNetworkProp.getWrapAroundVar());
	}
}