/**
 * File: AbstractComponent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.DynamicHolder;
import org.repastbs.editors.Editor;
import org.repastbs.generated.AbstractComponentProp;
import org.repastbs.model.Model;

/**
 * Simple implementation of Component interface, it implements  needed methods, so extending classes must not. Every Component should extend this class and override methods, they need to change.
 * @author  Ľudovít Hajzer
 */
public abstract class AbstractComponent extends DefaultMutableTreeNode implements Component {
	
	/** */
	private static final long serialVersionUID = 1392607270367788016L;
	
	private String id="ABSTRACT_COMPONENT";
	
	private AbstractComponentProp acp = new AbstractComponentProp();

	//private String description = "No description";
	private boolean editable = true;
	
	private boolean removable = true;
	private List<ComponentListener> listeners = new ArrayList<ComponentListener>();
	
	private Editor editor;
	
	/**
	 * Creates component with given name
	 * @param name
	 */
	public AbstractComponent(String name) {
		this(name,null);
	}
	
	/**
	 * Creates component with given name and component as its parent
	 * @param name
	 * @param parent 
	 */
	public AbstractComponent(String name, AbstractComponent parent) {
		this.acp.setName(name);
		setParent(parent);
	}

	/**
	 * @see org.repastbs.component.Component#removeComponentListener(org.repastbs.component.ComponentListener)
	 */
	public void removeComponentListener(ComponentListener l) {
		listeners.remove(l);
	}

	/**
	 * @see org.repastbs.component.Component#addComponentListener(org.repastbs.component.ComponentListener)
	 */
	public void addComponentListener(ComponentListener l) {
		listeners.add(l);
	}

	/**
	 * Returns name of this component
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return acp.getName();
	}

	/**
	 * Sets name of this component
	 * @param name  the name to set
	 * @uml.property  name="name"
	*/
	public void setName(String name) {
		this.acp.setName(name);
	}
	
	/**
	 * Add new component to this component as its child component
	 * This method updates TreeModel if there is any
	 * @param c
	 */
	public void add(Component c) {
		super.add(c);
		DefaultTreeModel treeModel = getModel().getTreeModel();
		if(treeModel!=null) {
			treeModel.insertNodeInto(c, this, children.size()-1);
			treeModel.nodeStructureChanged(this);
			treeModel.nodeStructureChanged(c);
		}
	}
	
	/**
	 * Removes child
	 * This method updates TreeModel if there is any
	 * @param c - child to be removed
	 */
	public void remove(Component c) {
		Component parent = c.getParent();
		super.remove(c);
		if(getModel().getTreeModel()!=null)
			getModel().getTreeModel().nodeChanged(parent);
	}
	
	/**
	 * @return model of this component
	 */
	public Model getModel() {
		return (Model)getRoot();
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @see  org.repastbs.component.Component#isEditable()
	 * @uml.property  name="editable"
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Sets whether this component is editable by editor
	 * @param editable  the editable to set
	 * @uml.property  name="editable"
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @see org.repastbs.component.Component#getChildById(java.lang.String)
	 */
	public AbstractComponent getChildById(String id) {
		if(children == null)
			return null;
		for (int i = 0; i < children.size(); i++) {
			AbstractComponent curr = (AbstractComponent)getChildAt(i);
			if(curr.getId()!=null) {
				if(curr.getId().compareTo(id)==0)
					return curr;
			}
		}
		return null;
	}
	
	/**
	 * Find all children which have given id
	 * @param id
	 * @return component, or null if node with given id is not child of this component
	 */
	public List<Component> getChildsById(String id) {
		List<Component> toReturn = new ArrayList<Component>();
		if(children == null)
			return toReturn;
		for (int i = 0; i < children.size(); i++) {
			AbstractComponent curr = (AbstractComponent)getChildAt(i);
			if(curr.getId()!=null) {
				if(curr.getId().compareTo(id)==0)
					toReturn.add(curr);
			}
		}
		return toReturn;
	}
	
	/**
	 * @see org.repastbs.component.Component#getChildByName(java.lang.String)
	 */
	public AbstractComponent getChildByName(String name) {
		for (int i = 0; i < children.size(); i++) {
			AbstractComponent curr = (AbstractComponent)getChildAt(i);
			if(curr.getName()!=null) {
				if(curr.getName().compareTo(name)==0)
					return curr;
			}
		}
		return null;
	}

	/**
	 * Retunrs id of this component
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets id of this component
	 * @param  id
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id=id;
	}

	/**
	 * @see  org.repastbs.component.Component#getDescription()
	 * @uml.property  name="description"
	 */
	public String getDescription() {
		return "Description";//acp.getDescription();
	}

	/**
	 * Sets description of this component
	 * @param description  the description to set
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		//this.acp.setDescription(description);
	}
	
	/**
	 * @see  org.repastbs.component.Component#isRemovable()
	 * @uml.property  name="removable"
	 */
	public boolean isRemovable() {
		return removable;
	}
	
	/**
	 * Sets whether this component can be removed from its parent
	 * @param  removable
	 * @uml.property  name="removable"
	 */
	public void setRemovable(boolean removable) {
		this.removable = removable;
	}
	

	/**
	 * @see  org.repastbs.component.Component#getEditor()
	 * @uml.property  name="editor"
	 */
	public Editor getEditor() {
		return editor;
	}

	/**
	 * @see  org.repastbs.component.Component#setEditor(org.repastbs.editors.Editor)
	 * @uml.property  name="editor"
	 */
	public void setEditor(Editor editor) {
		this.editor = editor;
	}
	
	/**
	 * @see org.repastbs.component.Component#fireComponentChanged(org.repastbs.component.ComponentEvent)
	 */
	@SuppressWarnings("unchecked")
	public void fireComponentChanged(ComponentEvent e) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			ComponentListener listener = (ComponentListener) iter.next();
			listener.componentChanged(e);
		}
	}
	
	/**
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	public Component getParent() {
		return (Component)super.getParent();
	}
	
	/**
	 * Generate fields of all dynamic changer children
	 * @param parent
	 * @param generator
	 * @throws DynamicException
	 */
	public static void generateChangerFields(Component parent, DynamicGenerator generator) throws DynamicException {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Component c = (Component)parent.getChildAt(i);
			if(c instanceof DynamicChanger) {
				DynamicChanger changer = (DynamicChanger)c;
				changer.generateFields(generator);
			}
		}
	}
	
	/**
	 * Generate methods of all dynamic changer children
	 * @param parent
	 * @param generator
	 * @throws DynamicException
	 */
	public static void generateChangerMethods(Component parent, DynamicGenerator generator) throws DynamicException {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Component c = (Component)parent.getChildAt(i);
			if(c instanceof DynamicChanger) {
				DynamicChanger changer = (DynamicChanger)c;
				changer.generateMethods(generator);
			}
		}
	}
	
	/**
	 * Generate fields of dynamic classes that are children of parent component
	 * @param parent
	 * @throws DynamicException
	 */
	public static void generateHolderFields(Component parent) throws DynamicException {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Component c = (Component)parent.getChildAt(i);
			if(c instanceof DynamicHolder) {
				DynamicHolder holder = (DynamicHolder)c;
				holder.generateFields();
			}
		}
	}
	
	/**
	 * Generate methods of dynamic classes that are children of parent component
	 * @param parent
	 * @throws DynamicException
	 */
	public static void generateHolderMethods(Component parent) throws DynamicException {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Component c = (Component)parent.getChildAt(i);
			if(c instanceof DynamicHolder) {
				DynamicHolder holder = (DynamicHolder)c;
				holder.generateMethods();
			}
		}
	}
	
	/**
	 * @see org.repastbs.component.Component#removeChildProp()
	 */
	public void removeChildProp(Component comp) {
		//DO NOTHING
	}
}