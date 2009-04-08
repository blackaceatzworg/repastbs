/**
 * File: ScheduleEditor.java
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Component;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.ScheduledAction;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing Schedule components
 * @author  Ludovit Hajzer
 */
public class ScheduleEditor extends AbstractEditor implements ActionListener, ItemListener {
	
	/** */
	private static final long serialVersionUID = -7122908455998486858L;

	private boolean creator;
	
	private ScheduledAction scheduledAction;

	JComboBox actions = new JComboBox();
	JComboBox execution = new JComboBox();
	JTextField tick = new JTextField("1");
	JCheckBox executeLast = new JCheckBox("Execute last");
	
	private String executionNames[] = {"Every tick", 
				"At a single tick", 
				"At interval", 
				"At end", 
				"At pause"};
	
	/**
	 * 
	 */
	public ScheduleEditor() {
		this(false);
	}
	
	/**
	 * @param creator 
	 */
	public ScheduleEditor(boolean creator) {
		this.creator = creator;
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1,1,new JLabel("Actions: ", JLabel.RIGHT),p);
		add(1,0,1,1,100,1,actions,p);
		add(0,1,1,1,1,1,new JLabel("Execution: ", JLabel.RIGHT),p);
		for (int i = 0; i < executionNames.length; i++)
			execution.addItem(executionNames[i]);
		add(1,1,1,1,100,1,execution,p);
		
		add(0,2,1,1,1,1,new JLabel("Tick: ", JLabel.RIGHT),p);
		add(1,2,1,1,100,1,tick,p);
		add(1,3,1,1,100,1,executeLast,p);
		p.setBorder(BorderFactory.createTitledBorder((creator?"Create":"Edit")+" action execution"));
		setLayout(gridbag);
		add(0,0,1,1,100,1,p,this);
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,1,1,1,100,100, SwingUtils
				.createOkCancelPanel(creator?"Create":"Ok", "Cancel", this), this);
		execution.addItemListener(this);
		execution.setSelectedIndex(0);
		executeLast.setEnabled(false);
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
	@SuppressWarnings("unchecked")
	public void setEditedValue(org.repastbs.component.Component component) {
		this.actions.removeAllItems();
		Component parent = (Component)component.getParent();
		if(parent != null) {
			if(!creator)
				parent = (Component)parent.getParent();
			ActionsComponent actions = (ActionsComponent)parent.getChildById(ActionsComponent.ID);
			if(actions!=null) {
				Enumeration actionsEnum = actions.getActions();
				while(actionsEnum.hasMoreElements())
					this.actions.addItem(actionsEnum.nextElement());
			}
		}
		if(creator && component instanceof ScheduleComponent)
			return;
		if(!(component instanceof ScheduledAction))
			throw new IllegalArgumentException("Edited value not supported by Schedule editor");
		scheduledAction = (ScheduledAction)component;
		actions.setSelectedItem(scheduledAction.getAction());
		execution.setSelectedIndex(scheduledAction.getScheduledActionProp().getExecution());
		tick.setText(""+scheduledAction.getScheduledActionProp().getTick());
		executeLast.setSelected(scheduledAction.getScheduledActionProp().isExecuteLast());
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public org.repastbs.component.Component getEditedValue() {
		if(creator)
			scheduledAction = new ScheduledAction();
		scheduledAction.setAction((Action)actions.getSelectedItem());
		scheduledAction.getScheduledActionProp().setAction(scheduledAction.getAction().getName());
		scheduledAction.getScheduledActionProp().setExecution(execution.getSelectedIndex());
		scheduledAction.getScheduledActionProp().setTick(Integer.parseInt(tick.getText()));
		scheduledAction.getScheduledActionProp().setExecuteLast(executeLast.isSelected());
		return scheduledAction;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Ok" ||
			e.getActionCommand() == "Create") {
			if(actions.getSelectedItem()==null) {
				JOptionPane.showMessageDialog(this,"Select action to schedule",
						"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));		
		}
		if (e.getActionCommand() == "Create") {
			ScheduledAction newSchedule = (ScheduledAction)getEditedValue();
			fireValueChanged(new EditorEvent(this,newSchedule,EditorEvent.VALUE_CREATED));
			((ScheduleComponent)newSchedule.getParent()).getScheduleProp().getScheduledAction().add(newSchedule.getScheduledActionProp());
		}
		if (e.getActionCommand()== "Cancel" && scheduledAction != null)
			setEditedValue(scheduledAction);
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		if(creator)
			return ScheduleComponent.class;
		return ScheduledAction.class;
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		int index = execution.getSelectedIndex();
		executeLast.setEnabled(index == ScheduledAction.AT_A_SINGLE_TICK ||
				index == ScheduledAction.AT_INTERVAL );
		if(!(index == ScheduledAction.AT_A_SINGLE_TICK ||
				index == ScheduledAction.AT_INTERVAL))
			executeLast.setSelected(false);
	}
}
