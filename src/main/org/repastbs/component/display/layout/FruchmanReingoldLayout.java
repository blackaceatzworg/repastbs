/**
 * File: FruchmanReingoldLayout.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.dom4j.Node;
import org.repastbs.component.IntegerComponent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.FruchmanReingoldLayoutProp;
import org.repastbs.generated.NetworkLayoutProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Concrete implementation of network layout, 
 * represents Fruchman Reingold network layout
 * @author �udov�t Hajzer
 *
 */
public class FruchmanReingoldLayout extends AbstractNetworkLayout {	
	
	/**  */
	private static final long serialVersionUID = -4818095084668473307L;

	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	private FruchmanReingoldLayoutProp fruchmanReingoldLayoutProp = new FruchmanReingoldLayoutProp();
	
	/*private boolean animate;
	private boolean rescale;
	private int updateEveryN;*/
	
	/**
	 * Default empty constructor
	 */
	public FruchmanReingoldLayout() {
		super("Fruchman Reingold");
		setVariableName("fruchmanReingoldLayout");
		setId(ID);
	}
	
	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {			
			SAXUtils.start(handler, getVariableName(), atts);
			SAXUtils.text(handler, "animate", String.valueOf(fruchmanReingoldLayoutProp.isAnimate()));
			SAXUtils.text(handler, "rescale", String.valueOf(fruchmanReingoldLayoutProp.isRescale()));
			SAXUtils.text(handler, "updateEveryN", String.valueOf(fruchmanReingoldLayoutProp.getUpdateEveryN()));
			SAXUtils.end(handler, getVariableName());
		} catch (SAXException e) {
			throw new XMLSerializationException("FruchmanReingoldLayout.writeToXML: "+e.getMessage(),e);
		}
		
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		try {
			fruchmanReingoldLayoutProp.setAnimate(Boolean.parseBoolean((node.valueOf("margin/text()"))));
			fruchmanReingoldLayoutProp.setRescale(Boolean.parseBoolean((node.valueOf("rescale/text()"))));
			fruchmanReingoldLayoutProp.setUpdateEveryN(Integer.parseInt((node.valueOf("updateEveryN/text()"))));
		} catch (Exception e) {
			throw new XMLSerializationException("FruchmanReingoldLayout.createFromXML: "+e.getMessage(),e);
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
		fruchmanReingoldLayoutProp.setAnimate(true);
		fruchmanReingoldLayoutProp.setRescale(true);
		fruchmanReingoldLayoutProp.setUpdateEveryN(1);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addImport("uchicago.src.sim.gui");
		generator.addField(getVariableName(), "FruchGraphLayout", null);
		
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		StringBuffer displayMethod = new StringBuffer();
		IntegerComponent width = (IntegerComponent)getParent().getChildByName("Width");
		IntegerComponent height = (IntegerComponent)getParent().getChildByName("Height");
		displayMethod.append(getVariableName()).append(" = new FruchGraphLayout(getAgentList(), ")		
		.append(width!=null?width.getValue().toString():400).append(", ")
		.append(height!=null?height.getValue().toString():400).append(");\n");
		displayMethod.append(getVariableName()).append(".setAnimateTransitions(").append(fruchmanReingoldLayoutProp.isAnimate()).append(");\n");
		displayMethod.append(getVariableName()).append(".setRescaleLayout(").append(fruchmanReingoldLayoutProp.isRescale()).append(");\n");
		displayMethod.append(getVariableName()).append(".setUpdateEveryN(").append(fruchmanReingoldLayoutProp.getUpdateEveryN()).append(");\n");		
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
		return fruchmanReingoldLayoutProp;
	}

	/**
	 * @return the fruchmanReingoldLayoutProp
	 */
	public FruchmanReingoldLayoutProp getFruchmanReingoldLayoutProp() {
		return fruchmanReingoldLayoutProp;
	}

	/**
	 * @param fruchmanReingoldLayoutProp the fruchmanReingoldLayoutProp to set
	 */
	public void setFruchmanReingoldLayoutProp(
			FruchmanReingoldLayoutProp fruchmanReingoldLayoutProp) {
		this.fruchmanReingoldLayoutProp = fruchmanReingoldLayoutProp;
	}
}
