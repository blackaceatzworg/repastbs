/**
 * File: Model.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.model;

import javax.swing.tree.DefaultTreeModel;
import org.repastbs.component.Component;
import org.repastbs.component.Schedulable;
import org.repastbs.dynamic.DynamicHolder;
import org.repastbs.generated.ModelProp;
import org.repastbs.xml.XMLSerializable;

/**
 * Model interface represents repast model,  it is component, dynamic holder, xml serializable and schedulable
 * @author  Ľudovít Hajzer
 */
public interface Model extends Component, DynamicHolder, XMLSerializable, Schedulable {
	
	/**
	 * Sets model's tree model
	 * @param  model
	 * @uml.property  name="treeModel"
	 */
	public void setTreeModel(DefaultTreeModel model);
	
	/**
	 * Gets model's tree model
	 * @return  tree model associated with this model
	 * @uml.property  name="treeModel"
	 */
	public DefaultTreeModel getTreeModel();
	
	/**
	 * @return model prop
	 */
	public ModelProp getModelProp();

	/**
	 * @param modelProp
	 * @throws Exception 
	 */
	public void setModelProp(ModelProp modelProp) throws Exception;
}
