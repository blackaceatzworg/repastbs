/**
 * File: NetworkDisplay.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display;

import java.util.List;
import org.dom4j.Node;
import org.repastbs.RepastBS;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Component;
import org.repastbs.component.IntegerComponent;
import org.repastbs.component.Schedulable;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.ScheduledAction;
import org.repastbs.component.display.layout.DisplayLayout;
import org.repastbs.component.display.layout.NetworkLayout;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializable;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Component which creates simple network display in result model
 * @author  Ľudovít Hajzer
 */
public class NetworkDisplay extends AbstractComponent implements DynamicChanger, XMLSerializable, Schedulable {

	/** */
	private static final long serialVersionUID = 3761529881265810264L;

	private IntegerComponent width;
	private IntegerComponent height;
	private NetworkLayout displayLayout;

	List<Component> supportedLayouts;// = new ArrayList<Component>();

	/** */
	public static final String ID = "NETWORK_DISPLAY";

	/** */
	public NetworkDisplay() {
		super("Network Display");
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() throws Exception {		
		if(width == null)
			width = new IntegerComponent("Width",400);
		add(this.width);

		if(height == null)
			height = new IntegerComponent("Height",400);
		add(this.height);

		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();

		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();


		Action a = actions.createAction("updateDisplay","","surface.updateDisplay();",null);
		a.setRemovable(false);
		a.setEditable(false);
		actions.setRemovable(false);
		actions.setEditable(false);

		schedule.setRemovable(false);
		schedule.setEditable(false);

		ScheduledAction scheduledAction=schedule.createScheduleAction("updateDisplay",a,ScheduledAction.EVERY_TICK,1,false);
		scheduledAction.setRemovable(false);

		buildSupportedLayouts();
		if(getSupportedLayouts().size()>0) {
			displayLayout = (NetworkLayout)getSupportedLayouts().get(0);
			add(displayLayout);
			displayLayout.createNew();
		}		
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		width = new IntegerComponent("Width",new Integer(node.valueOf("@width")));
		add(width);
		height = new IntegerComponent("Height",new Integer(node.valueOf("@height")));
		add(height);
		SAXUtils.createChildren(node, this);//(handler, this);

		displayLayout = (NetworkLayout)getChildById("NETWORK_LAYOUT");
		add(displayLayout);
		buildSupportedLayouts();
		for(int i=0;i<supportedLayouts.size();i++) {
			DisplayLayout curr = (DisplayLayout)supportedLayouts.get(i);
			if(curr.getClass().equals(displayLayout.getClass())) {
				supportedLayouts.set(i,displayLayout);
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
		atts.addAttribute("", "width", "width", "CDATA", width.getValue().toString());
		atts.addAttribute("", "height", "height", "CDATA", height.getValue().toString());
		try {
			SAXUtils.start(handler, "networkDisplay", atts);
			SAXUtils.serializeChildren(handler, this);
			SAXUtils.end(handler, "networkDisplay");
		} catch (SAXException e) {
			throw new XMLSerializationException("NetworkDisplay.writeToXML: "+e.getMessage(),e);
		}
	}

	/**
	 * @return  supported network types
	 * @uml.property  name="supportedLayouts"
	 */
	public List<Component> getSupportedLayouts() {
		return supportedLayouts;
	}

	private void buildSupportedLayouts() {
		supportedLayouts = RepastBS.getComponentManager().getComponentsById("NETWORK_LAYOUT");
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
		generator.addImport("uchicago.src.sim.network");
		generator.addImport("uchicago.src.sim.gui");
		if(displayLayout!=null)
			displayLayout.generateFields(generator);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		StringBuffer displayMethod = new StringBuffer();
		//not supporting different types of nodes yet
		displayMethod.append("Iterator iterator = getAgentList().iterator();");
		displayMethod.append("while (iterator.hasNext()) {");
		displayMethod.append("		DefaultDrawableNode currNode");
		displayMethod.append("		= (DefaultDrawableNode) iterator.next();");
		displayMethod.append("		OvalNetworkItem ovalnetworkitem");
		displayMethod.append("		= new OvalNetworkItem((double) 0, (double) 0);");
		displayMethod.append("		currNode.setDrawable(ovalnetworkitem);");
		displayMethod.append("}");
		try {
			generator.insertAfter("display", null, displayMethod.toString() );
		} catch(DynamicException e) {
			generator.addMethod("display",null,null, null, displayMethod.toString() );
		}

		//setup layout
		if(displayLayout!=null) {
			displayLayout.generateMethods(generator);

			displayMethod = new StringBuffer();
			displayMethod.append("Network2DDisplay network2ddisplay = new Network2DDisplay(")
			.append(displayLayout.getVariableName()).append(");");
			displayMethod.append("surface.addDisplayableProbeable(network2ddisplay, \"node\");");
			displayMethod.append("this.addSimEventListener(surface);");
			displayMethod.append(displayLayout.getVariableName()).append(".updateLayout();");
			displayMethod.append("surface.display();");
			generator.insertAfter("display", null, displayMethod.toString() );
		}

		StringBuffer setupMethod = new StringBuffer();
		setupMethod.append("if (surface != null)");
		setupMethod.append("    surface.dispose();");
		setupMethod.append("surface = new DisplaySurface(this, getName().concat(\" Display\"));");
		setupMethod.append("this.registerDisplaySurface(getName(), surface);");

		try {
			generator.insertBefore("setup", null, setupMethod.toString() );
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
	 * @return  the displayLayout
	 * @uml.property  name="displayLayout"
	 */
	public DisplayLayout getDisplayLayout() {
		return displayLayout;
	}

	/**
	 * @param layout  the displayLayout to set
	 * @uml.property  name="displayLayout"
	 */
	public void setDisplayLayout(NetworkLayout layout) {
		displayLayout.removeFromParent();
		displayLayout = layout;
		add(displayLayout);
		try {
			displayLayout.createNew();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}