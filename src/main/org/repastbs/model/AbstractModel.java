/**
 * File: AbstractModel.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.model;

import javax.swing.tree.DefaultTreeModel;
import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.component.MasterSchedule;
import org.repastbs.component.StringComponent;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.editors.StringEditor;
import org.repastbs.xml.SAXUtils;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Abstract implementation of Model interface,  all model templates should extend this class to implement specific model templates
 * @author  �udov�t Hajzer
 */
public abstract class AbstractModel extends AbstractComponent implements Model, ComponentListener {

	private DynamicGenerator generator;
	private DefaultTreeModel treeModel;

	protected StringComponent modelName;
	protected StringComponent displayName;
	protected StringComponent descriptionComp;
	
	/** */
	public static final String ID = "MODEL";
	
	/**
	 * @param name 
	 * @throws DynamicException 
	 */
	public AbstractModel(String name) throws DynamicException {
		super(name);
	}

	/**
	 * @return  the generator
	 * @uml.property  name="generator"
	 */
	public DynamicGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator  the generator to set
	 * @uml.property  name="generator"
	 */
	public void setGenerator(DynamicGenerator generator) {
		this.generator = generator;
	}

	/**
	 * @see org.repastbs.component.Schedulable#getSchedulableObjectName()
	 */
	public String getSchedulableObjectName() {
		return "this";
	}

	/**
	 * @see org.repastbs.component.Schedulable#getIterableClass()
	 */
	public String getIterableClass() {
		return null;
	}

	/**
	 * @see org.repastbs.component.Schedulable#isIterable()
	 */
	public boolean isIterable() {
		return false;
	}

	/**
	 * @see org.repastbs.component.Component#isRemovable()
	 */
	public boolean isRemovable() {
		return false;
	}

	/**
	 * @see  org.repastbs.model.Model#getTreeModel()
	 * @uml.property  name="treeModel"
	 */
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	/**
	 * @see  org.repastbs.model.Model#setTreeModel(javax.swing.tree.DefaultTreeModel)
	 * @uml.property  name="treeModel"
	 */
	public void setTreeModel(DefaultTreeModel treeModel) {
		this.treeModel = treeModel;
	}

	/**
	 * @see org.repastbs.component.ComponentListener#componentChanged(org.repastbs.component.ComponentEvent)
	 */
	public void componentChanged(ComponentEvent e) {
		if(e.getSource()==displayName)
			setName(displayName.getValue());
	}
	
	/**
	 * @param modelNameStr 
	 * @param displayNameStr 
	 * @throws Exception 
	 */
	public void createNew(String modelNameStr, String displayNameStr) throws Exception {
		modelName = new StringComponent("Model name",modelNameStr);
		add(modelName);
		modelName.createNew();
		
		displayName = new StringComponent("Display name",displayNameStr);
		add(displayName);
		displayName.createNew();
		
		descriptionComp = new StringComponent("Description","",true);
		add(descriptionComp);
		descriptionComp.createNew();
		descriptionComp.setEditor(new StringEditor(true));
	}
	
	/**
	 * @see org.repastbs.xml.XMLSerializable#createFromXML(org.dom4j.Node)
	 */
	public void createFromXML(Node node) throws XMLSerializationException {
		modelName = new StringComponent("Model name","NetworkModel");
		modelName.setValue(node.valueOf("@name"));
		setName(modelName.getValue());
		add(modelName);
		
		displayName = new StringComponent("Display name","Network model");
		displayName.setValue(node.valueOf("@displayName"));
		add(displayName);
		
		descriptionComp = new StringComponent("Description","",true);
		add(descriptionComp);
		descriptionComp.createNew();
		descriptionComp.setValue(node.valueOf("description/text()"));
		descriptionComp.setEditor(new StringEditor(true));
		
		//try to initialize all children nodes from xml
		SAXUtils.createChildren(node, this);
		
		//finally add master schedule
		MasterSchedule masterSchedule = new MasterSchedule();
		add(masterSchedule);
	}

	/**
	 * @see org.repastbs.xml.XMLSerializable#writeToXML(org.xml.sax.ContentHandler)
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException {
		
	    try {
	    	AttributesImpl atts = new AttributesImpl();
	    	atts.addAttribute("", "class", "class", "CDATA", getClass().getName());
		    atts.addAttribute("", "displayName", "displayName", "CDATA", displayName.getValue());
		    atts.addAttribute("", "name", "name", "CDATA", modelName.getValue());
		    
	    	SAXUtils.start(handler, "model", atts);
	    	SAXUtils.text(handler,"description", descriptionComp.getValue());

	    	SAXUtils.serializeChildren(handler, this);
	    	SAXUtils.end(handler, "model");
		} catch (SAXException e) {
			throw new XMLSerializationException("NetworkModel.writeToXML: "+e.getMessage());
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicHolder#generateFields()
	 */
	public void generateFields() throws DynamicException {
		generator.init();
		generator.setClassName(modelName.getValue());
		generator.addImport(getClass().getPackage().getName());
		
		generator.addImport("uchicago.src.sim.engine");
		AbstractComponent.generateHolderFields(this);
	    AbstractComponent.generateChangerFields(this,generator);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicHolder#generateMethods()
	 */
	public void generateMethods() throws DynamicException {
		try {
			getGenerator().insertAfter(null, null, "setName(\""+getName()+"\");");
		} catch (DynamicException x) {
			getGenerator().addConstructor(null, null, "setName(\""+getName()+"\");");
		}
		StringBuffer mainMethod = new StringBuffer();
		mainMethod.append("SimInit siminit = new SimInit();");
	    mainMethod.append(getGenerator().getClassName()+" model = new "+getGenerator().getClassName()+"();");
	    mainMethod.append("siminit.loadModel(model, \"\", false);");
	    mainMethod.append("}");
	    getGenerator().addMethod("main", "static void", new String[]{"args"}, 
	    		new String[]{"String[]"}, mainMethod.toString());
	    AbstractComponent.generateHolderMethods(this);
	    AbstractComponent.generateChangerMethods(this,generator);
	}
}