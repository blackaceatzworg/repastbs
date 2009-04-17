/**
 * File: GridSpace.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.grid;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.IntegerComponent;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.GridSpaceProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Component which adds grid space in which grid agents live
 * @author  Ľudovít Hajzer
 */
public class GridSpace extends AbstractComponent implements XMLSerializable, DynamicChanger {

	/** */
	private static final long serialVersionUID = 8179308108473886316L;

	private GridSpaceProp gridSpaceProp = new GridSpaceProp();

	private IntegerComponent spaceWidth;
	private IntegerComponent spaceHeight;

	/** */
	public GridSpace() {
		super("Grid space");
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {
		spaceHeight = new IntegerComponent("Space Height",100);
		//spaceHeight.setValue(new Integer(100));
		gridSpaceProp.setHeight(spaceHeight.getIntegerComponentProp());
		spaceWidth = new IntegerComponent("Space Width",100);
		//spaceWidth.setValue(new Integer(100));
		gridSpaceProp.setWidth(spaceWidth.getIntegerComponentProp());
		add(spaceHeight);
		add(spaceWidth);
		ActionsComponent actions = (ActionsComponent)getModel().getChildById(ActionsComponent.ID);
		Action addToSpace = actions.createAction("randomAddToSpace");
		addToSpace.setChangeSignature(false);
		addToSpace.addParameter("org.repastbs.component.grid.DefaultGridAgent");
		addToSpace.addParameter("uchicago.src.sim.space.Object2DGrid");
		StringBuffer addToSpaceSource = new StringBuffer();
		addToSpaceSource.append("int width = $2.getSizeX() - 1;\n");
		addToSpaceSource.append("int height = $2.getSizeY() - 1;\n");
		addToSpaceSource.append("int x, y;\n");
		addToSpaceSource.append("do {\n");
		addToSpaceSource.append("x = uchicago.src.sim.util.Random.uniform.nextIntFromTo(0, width);\n");
		addToSpaceSource.append("y = uchicago.src.sim.util.Random.uniform.nextIntFromTo(0, height);\n");
		addToSpaceSource.append("} while ($2.getObjectAt(x, y) != null);\n");
		addToSpaceSource.append("$1.setX(x);\n");
		addToSpaceSource.append("$1.setY(y);\n");
		addToSpaceSource.append("$2.putObjectAt(x, y, $1);\n");
		addToSpace.getActionProp().setSource(addToSpaceSource.toString());
		addToSpaceSource = new StringBuffer();
		addToSpaceSource.append("uchicago.src.sim.gui\n");
		addToSpaceSource.append("uchicago.src.sim.space\n");
		addToSpaceSource.append("uchicago.src.sim.util\n");
		addToSpaceSource.append("org.repastbs.component.grid\n");
		addToSpace.getActionProp().setImports(addToSpaceSource.toString());
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		spaceHeight = new IntegerComponent("Space Height");
		spaceHeight.setValue(new Integer(100));
		spaceWidth = new IntegerComponent("Space Width");
		spaceWidth.setValue(new Integer(100));
		spaceHeight.setValue(new Integer(node.valueOf("@height")));
		spaceWidth.setValue(new Integer(node.valueOf("@width")));
		add(spaceHeight);
		add(spaceWidth);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		try {
			AttributesImpl atts = new AttributesImpl();
			atts.addAttribute("", "class", "class", "CDATA",getClass().getName());
			atts.addAttribute("", "width", "width", "CDATA", spaceWidth.getValue().toString());
			atts.addAttribute("", "height", "height", "CDATA", spaceHeight.getValue().toString());			    
			SAXUtils.start(handler, "gridSpace", atts);
			SAXUtils.end(handler, "gridSpace");
		} catch (SAXException e) {
			throw new XMLSerializationException("NetworkModel.writeToXML: "+e.getMessage());
		}
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
		String buildMethod = "space = new Object2DGrid("
			+spaceWidth.getValue().toString()+", "+
			spaceHeight.getValue().toString()+");";
		try {
			generator.insertBefore("begin", null, buildMethod );
		} catch (DynamicException x) {
			generator.addMethod("begin", null, null, null, buildMethod);
		}
	}

	/**
	 * @return the gridSpaceProp
	 */
	public GridSpaceProp getGridSpaceProp() {
		return gridSpaceProp;
	}

	/**
	 * @param gridSpaceProp the gridSpaceProp to set
	 */
	public void setGridSpaceProp(GridSpaceProp gridSpaceProp) {
		this.gridSpaceProp = gridSpaceProp;
		removeAllChildren();
		IntegerComponent spaceHeight = new IntegerComponent("Space Height",gridSpaceProp.getHeight());
		add(spaceHeight);
		IntegerComponent spaceWidth = new IntegerComponent("Space Width",gridSpaceProp.getWidth());
		add(spaceWidth);
	}
}