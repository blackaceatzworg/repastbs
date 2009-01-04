/**
 * File: NetworkDisplayEditor.java
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
import org.repastbs.component.display.NetworkDisplay;
import org.repastbs.component.display.layout.NetworkLayout;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing NetworkDisplay components
 * @author  ¼udovít Hajzer
 */
public class NetworkDisplayEditor extends AbstractEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = -7247360427084314668L;
	
	protected NetworkDisplay display;
	
	protected JComboBox layout = new JComboBox();
	protected JComboBox nodeShape = new JComboBox();
	
	/**
	 */
	public NetworkDisplayEditor () {
		setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1,1,new JLabel("Network layout: ",JLabel.RIGHT),p);
		add(1,0,1,1,100,1,layout,p);
		//add(1,0,1,1,100,1,nodeShape,p);
		p.setBorder(BorderFactory.createTitledBorder("Network Display Editor"));
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
		if (e.getActionCommand() == "Ok") {
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand()== "Cancel" && display != null)
			setEditedValue(display);
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Component getEditedValue() {
		display.setDisplayLayout((NetworkLayout)layout.getSelectedItem());
		return display;
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
		if(!(component instanceof NetworkDisplay))
			throw new IllegalArgumentException("Edited value not supported by Network Display Editor");
		display = (NetworkDisplay)component;
		layout.removeAllItems();
		List<Component> types = display.getSupportedLayouts();
		for (int i = 0; i < types.size(); i++)
			layout.addItem(types.get(i));
		layout.setSelectedItem(display.getDisplayLayout());
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	public Class getSupportedClass() {
		return NetworkDisplay.class;
	}
}