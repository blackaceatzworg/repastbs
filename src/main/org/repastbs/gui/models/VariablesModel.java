/**
 * File: VariablesModel.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.repastbs.component.Variable;

/**
 * Model used by variable editor
 * @author ¼udovít Hajzer
 *
 */
public class VariablesModel extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5355457717461453116L;
	
	private List<Variable> list = new ArrayList<Variable>();

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return list.get(index).getName();
	}
	
	/**
	 * @param index
	 * @param variable
	 */
	public void setElementAt(int index, Variable variable) {
		list.add(index, variable);
	}
	
	/**
	 * @param variable
	 */
	public void addElement(Variable variable) {
		list.add(variable);
	}

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		if(list == null)
			return 0;
		return list.size();
	}
	
	/**
	 * @param list
	 */
	public void setValues(List<Variable> list) {
		this.list = list;
		fireContentsChanged(this, 0, list.size());
	}
	
	/**
	 * 
	 */
	public void clear() {
		setValues(new LinkedList<Variable>());
	}
}
