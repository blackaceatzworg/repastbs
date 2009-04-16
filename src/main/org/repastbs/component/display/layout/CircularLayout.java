/**
 * 
 */
package org.repastbs.component.display.layout;

import org.dom4j.Node;
import org.repastbs.component.IntegerComponent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.CircularLayoutProp;
import org.repastbs.generated.NetworkLayoutProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Ludovit Hajzer
 *
 */
public class CircularLayout extends AbstractNetworkLayout {	

	/** */
	private static final long serialVersionUID = -582249863756142310L;
	
	private CircularLayoutProp circularLayoutProp = new CircularLayoutProp();

	/**
	 * Default empty constructor
	 */
	public CircularLayout() {
		super("Circular Layout");
		circularLayoutProp.setVariableName("circularLayout");
		circularLayoutProp.setNetworkLayoutClass(this.getClass().getName());
		setId(ID);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {			
			SAXUtils.start(handler, "circularLayout", atts);
			SAXUtils.text(handler, "margin", circularLayoutProp.getMargin().toString());
			SAXUtils.end(handler, "circularLayout");
		} catch (SAXException e) {
			throw new XMLSerializationException("CircularLayout.writeToXML: "+e.getMessage(),e);
		}

	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		try {
			circularLayoutProp.setMargin(new Integer(Integer.parseInt((node.valueOf("margin/text()")))));
		} catch (Exception e) {
			throw new XMLSerializationException("CircularLayout.createFromXML: "+e.getMessage(),e);
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
		circularLayoutProp.setMargin(new Integer(6));
	}

	/**
	 * @return margin
	 */
	public Integer getMargin() {
		return circularLayoutProp.getMargin();
	}

	/**
	 * @param margin
	 */
	public void setMargin(Integer margin) {
		circularLayoutProp.setMargin(margin);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addImport("uchicago.src.sim.gui");
		generator.addField(getVariableName(), "CircularGraphLayout", null);

	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		StringBuffer displayMethod = new StringBuffer();
		IntegerComponent width = (IntegerComponent)getParent().getChildByName("Width");
		IntegerComponent height = (IntegerComponent)getParent().getChildByName("Height");
		displayMethod.append(getVariableName()).append(" = new CircularGraphLayout(getAgentList(), ")
		.append(width!=null?width.getValue().toString():400).append(", ")
		.append(height!=null?height.getValue().toString():400).append(");");
		displayMethod.append(getVariableName()).append(".setPad(6);");
		try {
			generator.insertAfter("display", null, displayMethod.toString() );
		} catch(DynamicException e) {
			generator.addMethod("display",null,null, null, displayMethod.toString() );
		}
	}

	/**
	 * @see org.repastbs.component.display.layout.NetworkLayout#getNetworkLayoutProp()
	 */
	public NetworkLayoutProp getNetworkLayoutProp() {
		return circularLayoutProp;
	}

	/**
	 * @see org.repastbs.component.display.layout.NetworkLayout#setNetworkLayoutProp(org.repastbs.generated.NetworkLayoutProp)
	 */
	@Override
	public void setNetworkLayoutProp(NetworkLayoutProp networkLayoutProp) {
		this.circularLayoutProp = (CircularLayoutProp)networkLayoutProp;
	}

	/**
	 * @see org.repastbs.component.display.layout.NetworkLayout#getVariableName()
	 */
	@Override
	public String getVariableName() {
		return this.circularLayoutProp.getVariableName();
	}
}