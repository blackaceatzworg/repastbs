/**
 * File: NetworkAgentEditor.java
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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.repastbs.component.Component;
import org.repastbs.component.network.NetworkAgent;
import org.repastbs.component.network.type.NetworkType;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing NetworkAgent components
 * @author  ¼udovít Hajzer
 */
public class NetworkAgentEditor extends AbstractEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = -7247360427084314668L;
	
	protected NetworkAgent agent;
	
	protected JComboBox networkType = new JComboBox();
	protected JLabel valueLabel = new JLabel("Network type: ",JLabel.RIGHT);
	
	/**
	 */
	public NetworkAgentEditor () {
		setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1,1,valueLabel,p);
		add(1,0,1,1,100,1,networkType,p);
		p.setBorder(BorderFactory.createTitledBorder("Network Agent editor"));
		setLayout(gridbag);
		add(0,0,1,1,100,1,p,this);
		gbc.anchor = GridBagConstraints.NORTH;
		add(0,1,1,1,100,100, SwingUtils
				.createOkCancelPanel("Ok", "Cancel", this), this);
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		/*if(e.getActionCommand() == "Ok") {
			if(networkType.get.length()==0) {
				JOptionPane.showMessageDialog(this,"Enter value","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand()== "Cancel" && agent != null)
			setEditedValue(agent);
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Component getEditedValue() {
		if(networkType.getSelectedItem()!=null)
			agent.setNetworkType((NetworkType)networkType.getSelectedItem());
		return agent;
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
		if(!(component instanceof NetworkAgent))
			throw new IllegalArgumentException("Edited value not supported by Network Agent Editor");
		//valueField.setText(objectComponent.getValue());
		agent = (NetworkAgent)component;
		networkType.removeAllItems();
		List<Component> types = agent.getSupportedTypes();
		for (int i = 0; i < types.size(); i++)
			networkType.addItem(types.get(i));
		networkType.setSelectedItem(agent.getNetworkType());
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	public Class getSupportedClass() {
		return NetworkAgent.class;
	}
	
	/**
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		valueLabel.setText(newLabel);
	}
}