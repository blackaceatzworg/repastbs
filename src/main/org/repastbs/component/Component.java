/**
 * File: Component.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import javax.swing.tree.MutableTreeNode;
import org.repastbs.editors.Editor;


/**
 * Core interface, represents single component,  every module in Repast BS should implement this interface
 * @author  �udov�t Hajzer
 */
public interface Component extends MutableTreeNode {

	/**
	 * Gets name of this component
	 * @return  name of this component
	 * @uml.property  name="name"
	 */
	public String getName();
	
	/**
	 * Sets name of this component
	 * @param name  - new name
	 * @uml.property  name="name"
	 */
	public void setName(String name);
	
	/**
	 * Add new child to this component
	 * @param c
	 */
	public void add(Component c);
	
	/**
	 * Returns whether this component is editable by editor
	 * @return true if this component is editable, false otherwise
	 */
	public boolean isEditable();

	/**
	 * Find child which has given id
	 * @param id
	 * @return component, or null if node with given id is not child of this component
	 */
	public Component getChildById(String id);
	
	/**
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public Component getParent();
	
	/**
	 * Find child which has given name
	 * @param name
	 * @return component, or null if node with given id is not child of this component
	 */
	public Component getChildByName(String name);

	/**
	 * @return id of this component
	 */
	public String getId();
	
	/**
	 * create and initialize self
	 * @throws Exception any initialization exception throwed
	 */
	public void createNew() throws Exception;
	
	/**
	 * Returns description of this component
	 * @return the description of this component
	 */
	public String getDescription();
	
	/**
	 * @return whether this component can be removed from its parent
	 */
	public boolean isRemovable();
	
	/**
	 * Adds component listener to this component
	 * @param l the listener to add
	 */
	public void addComponentListener(ComponentListener l);
	
	/**
	 * Removes component listener from this component
	 * @param l the listener to remove
	 */
	public void removeComponentListener(ComponentListener l);
	
	/**
	 * Notifies all registered component listeners
	 * @param e component event
	 */
	public void fireComponentChanged(ComponentEvent e);
	
	/**
	 * Sets editor associated with this component
	 * @param e  - editor
	 * @uml.property  name="editor"
	 */
	public void setEditor(Editor e);
	
	/**
	 * Gets associated editor
	 * @return associated editor
	 */
	public Editor getEditor();
}