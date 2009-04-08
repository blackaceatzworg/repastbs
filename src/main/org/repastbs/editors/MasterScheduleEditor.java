/**
 * File: MasterScheduleEditor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import org.repastbs.RepastBS;
import org.repastbs.component.Component;
import org.repastbs.component.MasterSchedule;
import org.repastbs.component.ScheduledAction;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing Master Schedule components
 * @author  Ludovit Hajzer
 */
public class MasterScheduleEditor extends AbstractEditor implements ActionListener, TreeSelectionListener {

	/** */
	private static final long serialVersionUID = -7122908455998486858L;

	MasterSchedule schedule;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");


	JTree scheduleTree = new JTree();
	DefaultTreeModel treeModel;

	DefaultListModel actionsModel = new DefaultListModel();
	JList actions = new JList(actionsModel);

	DefaultListModel lastActionsModel = new DefaultListModel();
	JList lastActions = new JList(lastActionsModel);

	JButton moveDown = SwingUtils.createToolbarButton("Move Down", RepastBS.getIcon("MoveDown"));
	JButton moveUp = SwingUtils.createToolbarButton("Move Up", RepastBS.getIcon("MoveUp"));

	DefaultMutableTreeNode nodes[] = new DefaultMutableTreeNode[5];
	private int actualExecution = -1;
	private int actualTick = -1;

	private String executionNames[] = {"Every tick", 
			"At a single tick", 
			"At interval", 
			"At end", 
	"At pause"};


	/**
	 */
	public MasterScheduleEditor() {
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.BOTH;

		treeModel = new DefaultTreeModel(root);

		scheduleTree.setRootVisible(false);
		for (int i = 0; i < executionNames.length; i++) {
			nodes[i] = new DefaultMutableTreeNode(executionNames[i]);
			root.add(nodes[i]);
			treeModel.insertNodeInto(nodes[i], root, i);
		}
		scheduleTree.setModel(treeModel);
		scheduleTree.scrollPathToVisible(new TreePath(nodes[0].getPath()));
		scheduleTree.addTreeSelectionListener(this);
		add(0,0,1,3,100,100,SwingUtils.createScrollComponent(scheduleTree),p);
		add(1,0,1,1,100,50,SwingUtils.createTitledScrollComponent("Actions",actions),p);
		add(1,1,1,1,100,50,SwingUtils.createTitledScrollComponent("Last actions",lastActions),p);
		JPanel controls = new JPanel();
		controls.setLayout(new FlowLayout());

		controls.add(moveUp);
		controls.add(moveDown);
		moveUp.addActionListener(this);
		moveDown.addActionListener(this);
		add(1,2,1,1,100,1,controls,p);

		setLayout(gridbag);
		gbc.fill = GridBagConstraints.BOTH;
		add(0,0,1,1,100,100,p,this);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,1,1,1,100,1, SwingUtils
				.createOkPanel("Ok", this), this);
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
		if(!(component instanceof MasterSchedule))
			throw new IllegalArgumentException("Edited value not supported by Master Schedule editor");
		schedule = (MasterSchedule)component;
		actionsModel.clear();
		lastActionsModel.clear();
		Component parent = (Component)component.getParent();
		//parent should be Model instance, search for ScheduleComponent(s)
		if(parent != null) {
			schedule.clear();
			schedule.findSchedules(parent);
		}

		for (int i = 0; i < nodes.length; i++) {
			nodes[i].removeAllChildren();
			treeModel.nodeStructureChanged(nodes[i]);
			List<Integer> ticks = schedule.getTicks(i);
			Collections.sort(ticks);
			for (int j=0;j<ticks.size();j++) {
				Integer tick = ticks.get(j);
				DefaultMutableTreeNode tickNode = new DefaultMutableTreeNode(tick);
				treeModel.insertNodeInto(tickNode,nodes[i], nodes[i].getChildCount());
				nodes[i].add(tickNode);
				treeModel.nodeStructureChanged(nodes[i]);
			}
		}
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public org.repastbs.component.Component getEditedValue() {		
		return schedule;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));		
		}
		if(e.getSource()==moveUp || e.getSource()==moveDown) {
			if(actualExecution!=-1 && actualTick!=-1 ) {
				if(actions.getSelectedIndex()!=-1) {
					int index = actions.getSelectedIndex();
					if(e.getSource()==moveUp)
						schedule.moveActionUp(actualExecution, actualTick,false, index);
					else
						schedule.moveActionDown(actualExecution, actualTick,false, index);
					refreshModels();
					if(e.getSource()==moveUp)
						actions.setSelectedIndex(index-1);
					else
						actions.setSelectedIndex(index+1);
				}
				if(lastActions.getSelectedIndex()!=-1) {
					int index = lastActions.getSelectedIndex();
					if(e.getSource()==moveUp)
						schedule.moveActionUp(actualExecution, actualTick,true, index);
					else
						schedule.moveActionDown(actualExecution, actualTick,true, index);
					refreshModels();
					if(e.getSource()==moveUp)
						lastActions.setSelectedIndex(index-1);
					else
						lastActions.setSelectedIndex(index+1);
				}
			}
		}
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		return MasterSchedule.class;
	}

	/**
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		TreePath p = e.getPath();
		//if concrete tick is selected, refresh actions list
		if(p.getPathCount()>2) {
			int executionType = ((MutableTreeNode)p.getPathComponent(0))
			.getIndex(((MutableTreeNode)p.getPathComponent(1)));
			int tick = Integer.parseInt(((MutableTreeNode)p.getLastPathComponent()).toString());

			actualExecution = executionType;
			actualTick = tick;
			refreshModels();
		}
		else {
			actionsModel.clear();
			lastActionsModel.clear();
		}
	}

	private void refreshModels() {
		actionsModel.clear();
		lastActionsModel.clear();
		List<ScheduledAction> scheduled = schedule
		.getActionsScheduledForTick(actualExecution, actualTick, false);
		for(int i=0;i<scheduled.size();i++) {
			ScheduledAction a = scheduled.get(i);
			Component parent = (Component) a.getParent().getParent();
			actionsModel.addElement(parent.getName()+": "+a.getAction()+"()");
		}
		scheduled = schedule
		.getActionsScheduledForTick(actualExecution, actualTick, true);
		for(int i=0;i<scheduled.size();i++) {
			ScheduledAction a = scheduled.get(i);
			Component parent = (Component) a.getParent().getParent();
			lastActionsModel.addElement(parent.getName()+": "+a.getAction()+"()");
		}
	}
}
