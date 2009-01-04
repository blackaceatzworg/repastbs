/**
 * File: SwingUtils.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;

/**
 * Helper class to provide som swing utility methods
 * @author ¼udovít Hajzer
 *
 */
public class SwingUtils {
	
	/**
	 * @param gx
	 * @param gy
	 * @param gw
	 * @param gh
	 * @param wx
	 * @param wy
	 * @param what
	 * @param where
	 * @param gbc
	 * @param gridbag
	 */
	public static void add(int gx,int gy,int gw,int gh,int wx,int wy,
			Component what,Container where,
			GridBagConstraints gbc, GridBagLayout gridbag)
	{
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
		gridbag.setConstraints(what, gbc);
		where.add(what);
	}
	
	/**
	 * @param title
	 * @param comp 
	 * @return jpanel
	 */
	public static JPanel createTitledScrollComponent(String title, Component comp) {
		JScrollPane scroll = new JScrollPane(comp);
		scroll.setWheelScrollingEnabled(true);
		scroll.setPreferredSize(new Dimension(1,1));
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setLayout(new BorderLayout());
		panel.add(scroll,BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * @param comp 
	 * @return jpanel
	 */
	public static JScrollPane createScrollComponent(Component comp) {
		JScrollPane scroll = new JScrollPane(comp);
		scroll.setWheelScrollingEnabled(true);
		return scroll;
	}
	
	/**
	 * @param label
	 * @param iconFileName
	 * @return createb JButton used in toolbar
	 */
	public static JButton createToolbarButton(String label, String iconFileName) {
		JButton button = new JButton(new ImageIcon(iconFileName));
		button.setFocusPainted(false);
		button.setMargin(new Insets(0,0,0,0));
		button.setToolTipText(label);
		button.setActionCommand(label);
		return button;
	}
	
	/**
	 * @param label
	 * @param icon
	 * @return createb JButton used in toolbar
	 */
	public static JButton createToolbarButton(String label, Icon icon) {
		JButton button = new JButton(icon);
		button.setFocusPainted(false);
		button.setMargin(new Insets(0,0,0,0));
		button.setToolTipText(label);
		button.setActionCommand(label);
		return button;
	}
	
	/**
	 * @param okText
	 * @param cancelText
	 * @param listener
	 * @return newly created JPanel with ok and cancel buttons
	 */
	public static JPanel createOkCancelPanel(String okText,String cancelText,ActionListener listener) {
		return createOkCancelPanel(okText,cancelText,listener,null);
	}
	
	/**
	 * @param okText
	 * @param cancelText
	 * @param listener
	 * @param root 
	 * @return newly created JPanel with ok and cancel buttons
	 */
	public static JPanel createOkCancelPanel(String okText,String 
			cancelText,ActionListener listener, JRootPane root) {
		JPanel panel = new JPanel();
		JButton ok = new JButton(okText);
		JButton cancel = new JButton(cancelText);
		panel.add(ok);
		panel.add(cancel);
		ok.addActionListener(listener);
		ok.setActionCommand(okText);
		if(root != null)
			root.setDefaultButton(ok);
		cancel.addActionListener(listener);
		cancel.setActionCommand(cancelText);
		return panel;
	}
	
	/**
	 * @param okText
	 * @param listener
	 * @return newly created JPanel with ok and cancel buttons
	 */
	public static JPanel createOkPanel(String okText,ActionListener listener) {
		return createOkPanel(okText, listener, null);
	}
	
	/**
	 * @param okText
	 * @param listener
	 * @param root 
	 * @return newly created JPanel with ok and cancel buttons
	 */
	public static JPanel createOkPanel(String okText,ActionListener listener, JRootPane root) {
		JPanel panel = new JPanel();
		JButton ok = new JButton(okText);
		panel.add(ok);
		ok.addActionListener(listener);
		ok.setActionCommand(okText);
		if(root != null)
			root.setDefaultButton(ok);
		return panel;
	}
	
	/**
	 * @param label
	 * @param labelFor
	 * @return createt JLabel
	 */
	public static JLabel createLabelFor(String label, Component labelFor) {
		JLabel labelComponent = new JLabel(label, JLabel.TRAILING);
		labelComponent.setLabelFor(labelFor);
		return labelComponent;
	}
}
