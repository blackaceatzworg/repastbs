/**
 * File: IntegerEditor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.repastbs.component.Component;
import org.repastbs.component.IntegerComponent;

/**
 * Editor used for editing Integer components
 * @author Ludovit Hajzer
 *
 */
public class IntegerEditor extends StringEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = 2593612224649361192L;
	
	/** */
	public IntegerEditor() {
		super("Integer editor",false);
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Ok") {
			if(valueField.getText().length()==0) {
				JOptionPane.showMessageDialog(this,"Enter value","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				new Integer(valueField.getText());
			} catch(Exception x) {
				JOptionPane.showMessageDialog(this,"Value must be integer","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		} 
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand()== "Cancel"  && objectComponent != null)
			setEditedValue(objectComponent);
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public IntegerComponent getEditedValue() {
		IntegerComponent integerComponent = (IntegerComponent)getObjectComponent();
		integerComponent.setValue(new Integer(valueField.getText()));
		return integerComponent;
	}
	
	/**
	 * @see org.repastbs.editors.Editor#setEditedValue(org.repastbs.component.Component)
	 */
	public void setEditedValue(Component component) {
		if(!(component instanceof IntegerComponent))
			throw new IllegalArgumentException("Edited value not supported by Integer Editor");
		setObjectComponent((IntegerComponent)component);
		valueField.setText(objectComponent.getValue().toString());
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		return IntegerComponent.class;
	}
}