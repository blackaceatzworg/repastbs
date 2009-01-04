/**
 * File: StringEditor.java
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
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.repastbs.component.Component;
import org.repastbs.component.ObjectComponent;
import org.repastbs.component.StringComponent;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing String components
 * @author  ¼udovít Hajzer
 */
public class StringEditor extends AbstractEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = -7247360427084314668L;
	
	protected ObjectComponent objectComponent;
	
	protected JTextComponent valueField = new JTextField();
	protected JLabel valueLabel = new JLabel("Value :",JLabel.RIGHT);
	
	private boolean textEditor;
	
	/**
	 */
	public StringEditor () {
		this("String editor",false);
	}
	
	/**
	 * @param textEditor
	 */
	public StringEditor (boolean textEditor) {
		this(textEditor?"Text Editor":"String Editor",textEditor);
	}
	
	/**
	 * @param title of the border
	 * @param textEditor 
	 */
	public StringEditor (String title, boolean textEditor) {
		this.textEditor = textEditor;
		setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		
		if(textEditor) {
			gbc.fill = GridBagConstraints.BOTH;
			add(1,0,1,1,100,100,SwingUtils.createScrollComponent(valueField=new JTextArea()),p);
		} else {
			add(0,0,1,1,1,1,valueLabel,p);
			add(1,0,1,1,100,1,valueField=new JTextField(),p);
		}
		p.setBorder(BorderFactory.createTitledBorder(title));
		setLayout(gridbag);
		add(0,0,1,1,100,textEditor?100:1,p,this);
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,1,1,1,100,textEditor?1:100, SwingUtils
				.createOkCancelPanel("Ok", "Cancel", this), this);
		objectComponent = new StringComponent("");
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Ok" && !textEditor ) {
			if(valueField.getText().length()==0) {
				JOptionPane.showMessageDialog(this,"Enter value","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand()== "Cancel" && objectComponent != null)
			setEditedValue(objectComponent);
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Component getEditedValue() {
		objectComponent.setValue(valueField.getText());
		return objectComponent;
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
	public void setEditedValue(Component component) {
		if(!(component instanceof StringComponent))
			throw new IllegalArgumentException("Edited value not supported by String Editor");
		objectComponent = (StringComponent)component;
		valueField.setText(objectComponent.getValue().toString());
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	public Class getSupportedClass() {
		return StringComponent.class;
	}
	
	/**
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		valueLabel.setText(newLabel);
	}

	/**
	 * @return  associated object component
	 * @uml.property  name="objectComponent"
	 */
	public ObjectComponent getObjectComponent() {
		return objectComponent;
	}

	/**
	 * Accesor for object component allows other editor types to change object component to any type, e.g. Integer component
	 * @param  objectComponent
	 * @uml.property  name="objectComponent"
	 */
	public void setObjectComponent(ObjectComponent objectComponent) {
		this.objectComponent = objectComponent;
	}
}