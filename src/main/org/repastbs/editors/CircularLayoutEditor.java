/**
 * File: CircularLayoutEditor.java
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
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.repastbs.RepastBS;
import org.repastbs.component.Component;
import org.repastbs.component.display.layout.NetworkLayout;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing CircularLayout components
 * @author  Ludovit Hajzer
 */
public class CircularLayoutEditor extends AbstractEditor implements ActionListener {
	
	/** */
	private static final long serialVersionUID = -7247360427084314668L;
	
	protected NetworkLayout layout;
	
	protected JTextField margin = new JTextField();
	protected JLabel valueLabel = new JLabel("Margin: ",JLabel.RIGHT);
	
	/**
	 */
	public CircularLayoutEditor () {
		setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(0,0,1,1,1,1,valueLabel,p);
		add(1,0,1,1,100,1,margin,p);
		p.setBorder(BorderFactory.createTitledBorder("Circular Layout"));
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
		if (e.getActionCommand()== "Cancel" && layout != null)
			setEditedValue(layout);
		if (e.getActionCommand() == "Ok") {
			if(margin.getText().length()==0) {
				JOptionPane.showMessageDialog(this,"Enter value","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				new Integer(margin.getText());
			} catch(Exception x) {
				JOptionPane.showMessageDialog(this,"Margin must be integer value","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public Component getEditedValue() {
		//using reflection
		//layout.setMargin(new Integer(margin.getText()));//.setNetworkType((NetworkType)networkType.getSelectedItem());
		try {
			Method m = layout.getClass().getMethod("setMargin", Integer.class);
			m.invoke(layout, new Integer(margin.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return layout;
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
		if(!(component instanceof NetworkLayout))
			throw new IllegalArgumentException("Edited value not supported by Circular Layout Editor");
		layout = (NetworkLayout)component;
		try {
			Method m = layout.getClass().getMethod("getMargin", new Class[0]);
			margin.setText(m.invoke(layout, new Object[0]).toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("Edited value not supported by Circular Layout Editor",e);
		}
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		//CircularLayout.class;
		return RepastBS.getComponentManager()
			.getComponent("org.repastbs.component.display.layout.CircularLayout").getClass();
	}
	
	/**
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		valueLabel.setText(newLabel);
	}
}