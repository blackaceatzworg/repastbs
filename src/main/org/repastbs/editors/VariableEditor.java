/**
 * File: VariableEditor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.repastbs.RepastBS;
import org.repastbs.component.Variable;
import org.repastbs.component.VariablesComponent;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing/creating Variable components
 * @author  �udov�t Hajzer
 */
public class VariableEditor extends AbstractEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = -7122908455998486858L;
	
	private Variable variable;
	private boolean creator;
	
	JTextField name = new JTextField();
	JComboBox type = new JComboBox();
	JTextField defaultValue = new JTextField();
	JCheckBox accessible = new JCheckBox();
	JCheckBox parameter = new JCheckBox();
	
	/**
	 */
	public VariableEditor() {
		this(false);
	}
	
	/**
	 * @param creator 
	 */
	public VariableEditor(boolean creator) {
		this.creator = creator;
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1,1,new JLabel("Name: ", JLabel.RIGHT),p);
		add(1,0,1,1,100,1,name,p);
		add(0,1,1,1,1,1,new JLabel("Type: ", JLabel.RIGHT),p);
		add(1,1,1,1,100,1,type,p);
		type.setEditable(true);
		for(int i=0;i<RepastBS.variableTypes.size();i++) {
        	type.addItem(RepastBS.variableTypes.get(i).getName());
        }
		add(0,2,1,1,1,1,new JLabel("Default value: ", JLabel.RIGHT),p);
		add(1,2,1,1,100,1,defaultValue,p);
		add(0,3,1,1,1,1,new JLabel("Accessible: ", JLabel.RIGHT),p);
		add(1,3,1,1,100,1,accessible,p);
		add(0,4,1,1,1,1,new JLabel("Parameter: ", JLabel.RIGHT),p);
		add(1,4,1,1,100,1,parameter,p);
		p.setBorder(BorderFactory.createTitledBorder(creator?"Variable creator":"Variable editor"));
		setLayout(gridbag);
		add(0,0,1,1,100,1,p,this);
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,1,1,1,100,100, SwingUtils
				.createOkCancelPanel(creator?"Create":"Ok", "Cancel", this), this);
		variable = new Variable("");
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditorPanel()
	 */
	public Container getEditorPanel() {
		return this;
	}

	/**
	 * @see org.repastbs.editors.Editor#setEditedValue(org.repastbs.component.Component)
	 */
	public void setEditedValue(org.repastbs.component.Component component) {
		if(creator && component instanceof VariablesComponent)
			return;
		if(!(component instanceof Variable))
			throw new IllegalArgumentException("Edited value not supported by Variable Editor");
		variable = (Variable)component;
		name.setText(variable.getName());
		type.setSelectedItem(variable.getVariableProp().getType());
		if(variable.getVariableProp().getDefaultValue()!=null)
			defaultValue.setText(variable.getVariableProp().getDefaultValue().toString());
		else
			defaultValue.setText("");
		accessible.setSelected(variable.getVariableProp().isAccessible());
		parameter.setSelected(variable.getVariableProp().isParameter());
		name.setEditable(variable.isRemovable());
		type.setEnabled(variable.isRemovable());
		accessible.setEnabled(variable.isRemovable());
		parameter.setEnabled(variable.isRemovable());
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Variable getEditedValue() {
		if(creator)
			variable=new Variable("");
		variable.setName(name.getText());
		variable.getVariableProp().setType((String)type.getSelectedItem());
		variable.getVariableProp().setDefaultValue(defaultValue.getText());
		variable.getVariableProp().setAccessible(accessible.isSelected());
		variable.getVariableProp().setParameter(parameter.isSelected());
		return variable;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Ok" ||
			e.getActionCommand() == "Create") {
			if(name.getText().length()==0) {
				JOptionPane.showMessageDialog(this,"Name of variable must be specified","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand() == "Create") {
			Variable newVariable = getEditedValue();
			fireValueChanged(new EditorEvent(this,newVariable,EditorEvent.VALUE_CREATED));
			((VariablesComponent)newVariable.getParent()).getVariablesProp().getVariable().add(
					newVariable.getVariableProp());
		}
		if (e.getActionCommand()== "Cancel" && variable != null)
			setEditedValue(variable);
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		if(creator)
			return VariablesComponent.class;
		return Variable.class;
	}
	
	
}
