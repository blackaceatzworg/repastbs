/**
 * File: GridDisplay.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.ColorComponent;
import org.repastbs.component.Schedulable;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.ScheduledAction;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.GridDisplayProp;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Component which creates simple grid display in result model
 * @author  Ludovit Hajzer
 */
public class GridDisplay extends AbstractComponent implements DynamicChanger, XMLSerializable, Schedulable {

	/** */
	private static final long serialVersionUID = 3761529881265810264L;
	
	private GridDisplayProp gridDisplayProp = new GridDisplayProp();
	
	/** */
	public static final String ID = "GRID_DISPLAY";

	private ColorComponent backGroundColor;;

	/** */
	public GridDisplay() {
		super("Grid Display");
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {		
		backGroundColor = new ColorComponent();
		add(backGroundColor);
		gridDisplayProp.setColor(null);
		gridDisplayProp.setColor(backGroundColor.getColorComponentProp());
		
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		gridDisplayProp.setActions(actions.getActionsProp());

		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		gridDisplayProp.setSchedule(schedule.getScheduleProp());

		Action a = actions.createAction("updateDisplay","","surface.updateDisplay();",null);
		a.setRemovable(false);
		a.setEditable(false);
		actions.setRemovable(false);
		actions.setEditable(false);

		schedule.setRemovable(false);
		schedule.setEditable(false);

		ScheduledAction scheduledAction=schedule.createScheduleAction("updateDisplay",a,ScheduledAction.EVERY_TICK,1,false);
		scheduledAction.setRemovable(false);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		SAXUtils.createChildren(node, this);
		backGroundColor = (ColorComponent)getChildById(ColorComponent.ID);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		try {
			SAXUtils.start(handler, "gridDisplay", atts);
			SAXUtils.serializeChildren(handler, this);
			SAXUtils.end(handler, "gridDisplay");
		} catch (SAXException e) {
			throw new XMLSerializationException("GridDisplay.writeToXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.component.Schedulable#getIterableClass()
	 */
	public String getIterableClass() {
		return null;
	}

	/**
	 * @see org.repastbs.component.Schedulable#getSchedulableObjectName()
	 */
	public String getSchedulableObjectName() {
		return "surface";
	}

	/**
	 * @see org.repastbs.component.Schedulable#isIterable()
	 */
	public boolean isIterable() {
		return false;
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		generator.addField("surface", "uchicago.src.sim.gui.DisplaySurface", null);

		generator.addImport("java.util");
		generator.addImport("java.awt");
		generator.addImport("uchicago.src.sim.network");
		generator.addImport("uchicago.src.sim.gui");
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		StringBuffer displayMethod = new StringBuffer();
		
		displayMethod.append("Object2DDisplay object2ddisplay = new Object2DDisplay(space);");
		displayMethod.append("object2ddisplay.setObjectList(getAgentList());");
		displayMethod.append("surface.addDisplayableProbeable(object2ddisplay, \"agentList\");");
		displayMethod.append("Color color = new Color(")
			.append(backGroundColor.getColor().getRed()).append(", ")
			.append(backGroundColor.getColor().getGreen()).append(", ")
			.append(backGroundColor.getColor().getBlue()).append(");");
		displayMethod.append("surface.setBackground(color);");
		displayMethod.append("this.addSimEventListener(surface);");
		displayMethod.append("surface.display();");
		try {
			generator.insertAfter("display", null, displayMethod.toString() );
		} catch(DynamicException e) {
			generator.addMethod("display",null,null, null, displayMethod.toString() );
		}
		
		StringBuffer setupMethod = new StringBuffer();
		setupMethod.append("if (surface != null)");
		setupMethod.append("    surface.dispose();");
		setupMethod.append("surface = new DisplaySurface(this, getName().concat(\" Display\"));");
		setupMethod.append("this.registerDisplaySurface(getName(), surface);");

		try {
			generator.insertAfter("setup", null, setupMethod.toString() );
		} catch(DynamicException e) {
			generator.addMethod("setup",null,null, null, setupMethod.toString() );
		}
		
		try {
			generator.insertAfter("begin", null, "display();");
		} catch (DynamicException x) {
			generator.addMethod("begin", null, null, null, "display();");
		}
	}

	/**
	 * @return the gridDisplayProp
	 */
	public GridDisplayProp getGridDisplayProp() {
		return gridDisplayProp;
	}

	/**
	 * @param gridDisplayProp the gridDisplayProp to set
	 */
	public void setGridDisplayProp(GridDisplayProp gridDisplayProp) {
		this.gridDisplayProp = gridDisplayProp;
	}
}