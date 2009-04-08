/**
 * File: Editor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;
import org.repastbs.component.Component;

/**
 * Editor interface, which all editors for components must implement
 * @author  Ludovit Hajzer
 */
public interface Editor {
	
	/**
	 * Gsts supported class for this editor
	 * @return type of object this editor edits
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass();
	
	/**
	 * Gets editor panel associated with this editor
	 * @return panel associated with this editor
	 */
	public Container getEditorPanel();
	
	/**
	 * Sets value this editor edits
	 * @param component
	 * @uml.property  name="editedValue"
	 */
	public void setEditedValue(Component component);
	
	/**
	 * Gets edited value
	 * @return edited value
	 */
	public Component getEditedValue();
	
	/**
	 * Registers editor listener
	 * @param l
	 */
	public void addEditorListener(EditorListener l);
	
	/**
	 * Removes registered editor listener
	 * @param l
	 */
	public void removeEditorListener(EditorListener l);
	
	/**
	 * Notifies all registered editor listeners
	 * @param e
	 */
	public void fireValueChanged(EditorEvent e);
}
