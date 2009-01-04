/**
 * File: FruchmanReingoldLayout.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display.layout;

import org.dom4j.Node;
import org.repastbs.component.IntegerComponent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Concrete implementation of network layout, 
 * represents Fruchman Reingold network layout
 * @author ¼udovít Hajzer
 *
 */
public class FruchmanReingoldLayout extends AbstractNetworkLayout {	
	
	/**  */
	private static final long serialVersionUID = -4818095084668473307L;

	/** */
	public static final String ID = "NETWORK_LAYOUT";
	
	private boolean animate;
	private boolean rescale;
	private int updateEveryN;
	
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
			SAXUtils.text(handler, "animate", ""+animate);
			SAXUtils.text(handler, "rescale", ""+rescale);
			SAXUtils.text(handler, "updateEveryN", ""+updateEveryN);
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
			animate = Boolean.parseBoolean((node.valueOf("margin/text()")));
			rescale = Boolean.parseBoolean((node.valueOf("rescale/text()")));
			updateEveryN = Integer.parseInt((node.valueOf("updateEveryN/text()")));
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
		animate = true;
		rescale = true;
		updateEveryN = 1;
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
		displayMethod.append(getVariableName()).append(".setAnimateTransitions(").append(animate).append(");\n");
		displayMethod.append(getVariableName()).append(".setRescaleLayout(").append(rescale).append(");\n");
		displayMethod.append(getVariableName()).append(".setUpdateEveryN(").append(updateEveryN).append(");\n");		
		try {
			generator.insertAfter("display", null, displayMethod.toString() );
		} catch(DynamicException e) {
			generator.addMethod("display",null,null, null, displayMethod.toString() );
		}
	}
}
