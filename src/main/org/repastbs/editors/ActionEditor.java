/**
 * File: ActionEditor.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.repastbs.RepastBS;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Component;
import org.repastbs.component.Variable;
import org.repastbs.component.VariablesComponent;
import org.repastbs.gui.SwingUtils;
import org.repastbs.gui.models.VariablesModel;


/**
 * Editor used for editing/creating Action components
 * @author  ¼udovít Hajzer
 */
public class ActionEditor extends AbstractEditor implements ActionListener, 
	ItemListener, Editor {

	private static final long serialVersionUID = -1064502739160467995L;
	
	private Action action;
	private boolean creator=false;
	
	private JTextField name= new JTextField();
	private JComboBox returnType = new JComboBox();	
	/** source code editor for actions */
	private JTextArea source = new JTextArea();
	/** imports */
	private JTextArea imports = new JTextArea();

	private JList variablesList;
	private JComboBox parameterType = new JComboBox();
	private JButton createParameter = new JButton("Add");
	private VariablesModel variablesModel = new VariablesModel();
	
	/**
	 * 
	 */
	public ActionEditor() {
		this(false);
	}
	
	/**
	 * @param creator 
	 */
	public ActionEditor(boolean creator) {
		this.creator=creator;
		setLayout(gridbag);
		variablesList = new JList(variablesModel);
		
		variablesList.setVisibleRowCount(-1);
		variablesList.setFixedCellWidth(-1);

		JPanel top = new JPanel();
		top.setLayout(gridbag);
		top.setBorder(BorderFactory.createTitledBorder("Action editor"));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1, 1, new JLabel("Name: ") ,top);
		add(1,0,1,1,20, 1, name ,top);
		add(0,1,1,1,1, 1, new JLabel("Return type: ") ,top);
		add(1,1,1,1,20, 1, returnType ,top);
		
		for(int i=0;i<RepastBS.returnTypes.size();i++) {
			returnType.addItem(RepastBS.returnTypes.get(i));
			parameterType.addItem(RepastBS.variableTypes.get(i).getName());
		}
		returnType.setEditable(true);
		parameterType.setEditable(true);
		add(0,0,3,1,100, 1, top ,this);
		if(!creator) {
			gbc.fill = GridBagConstraints.BOTH;
			add(0,1,1,1,30,30, SwingUtils
					.createTitledScrollComponent("Variables",variablesList), this);
			add(1,1,1,1,30,30, SwingUtils
					.createTitledScrollComponent("Java Imports",imports), this);
			
			JPanel params = new JPanel();
			params.setLayout(gridbag);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(0,0,1,1,1, 1, new JLabel("Type: ") ,params);
			add(1,0,1,1,100, 1, parameterType ,params);
			gbc.fill = GridBagConstraints.NONE;
			add(0,1,2,1,1, 1, createParameter ,params);
			gbc.fill = GridBagConstraints.BOTH;
			add(2,1,1,1,30,30, params, this);
			params.setBorder(BorderFactory.createTitledBorder("Add action parameters"));
			createParameter.addActionListener(this);
			
			add(0,2,3,1,100,100, SwingUtils
					.createTitledScrollComponent("Source",source) , this);
			
			
			/*gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.WEST;
			add(1,1,1,1,1,1, addImport, this);*/
		}
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,4,3,1,100,creator?100:1, SwingUtils
				.createOkCancelPanel(creator?"Create":"Ok", "Cancel",this), this);
		action = new Action("");
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Cancel" && action != null)
			setEditedValue(action);
		if(e.getSource() == createParameter) {
			if(parameterType.getSelectedItem()==null) {
				JOptionPane.showMessageDialog(this,"Select parameter type","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!action.isChangeSignature()) {
				JOptionPane.showMessageDialog(this,"Action can not be changed","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			action.addParameter(parameterType.getSelectedItem().toString());
			return;
		}
		
		if(e.getActionCommand() == "Ok" ||
			e.getActionCommand() == "Create") {
			if(name.getText().length()==0) {
				JOptionPane.showMessageDialog(this,"Name of action must be specified","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));		
		}
			
		if (e.getActionCommand() == "Create") {
			fireValueChanged(new EditorEvent(this,getEditedValue(),EditorEvent.VALUE_CREATED));		
		}
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
		if(creator && component instanceof ActionsComponent)
			return;
		if(!(component instanceof Action))
			throw new IllegalArgumentException("Edited value not supported by Actions Editor");
		action = (Action)component;
		name.setText(action.getName());
		returnType.setSelectedItem(action.getReturnType());
		source.setText(action.getSource());
		imports.setText(action.getImports());
		variablesModel.clear();
		Component parent = (Component)action.getParent();
		if(parent != null) {
			parent = (Component)parent.getParent();
			if(parent != null) {
				VariablesComponent variables = (VariablesComponent)parent.getChildById(VariablesComponent.ID);
				if(variables!=null) {
					Enumeration variablesEnum = variables.children();
					while(variablesEnum.hasMoreElements())
						variablesModel.addElement((Variable)variablesEnum.nextElement());
				}
			}
		}
		name.setEditable(action.isChangeSignature());
		returnType.setEnabled(action.isChangeSignature());
		parameterType.setEnabled(action.isChangeSignature());
		createParameter.setEnabled(action.isChangeSignature());
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public org.repastbs.component.Component getEditedValue() {
		if(creator)
			action=new Action();
		action.setName(name.getText());
		action.setSource(source.getText());
		action.setImports(imports.getText());
		if(returnType.getSelectedItem()!=null)
		action.setReturnType(returnType.getSelectedItem().toString());
		return action;
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	public Class getSupportedClass() {
		if(creator)
			return ActionsComponent.class;
		return Action.class;
	}
}