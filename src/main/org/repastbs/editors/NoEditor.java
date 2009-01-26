/**
 * File: NoEditor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;

import javax.swing.JLabel;

import org.repastbs.component.Component;

/**
 * No editor is returned for every component that has no associated editor 
 * @author �udov�t Hajzer
 *
 */
public class NoEditor extends AbstractEditor {

	/** */
	private static final long serialVersionUID = 1035318012229402356L;
	
	private JLabel label = new JLabel("No editor specified for this component",JLabel.CENTER);
	/**
	 * @see org.repastbs.editors.Editor#getEditorPanel()
	 */
	public Container getEditorPanel() {
		return label;
	}

	/**
	 * @see org.repastbs.editors.Editor#setEditedValue(org.repastbs.component.Component)
	 */
	public void setEditedValue(Component component) {
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Component getEditedValue() {
		return null;
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	public Class getSupportedClass() {
		return null;
	}
	
	/**
	 * Sets the label text
	 * @param text
	 */
	public void setLabelText(String text) {
		label.setText(text);
	}
}
