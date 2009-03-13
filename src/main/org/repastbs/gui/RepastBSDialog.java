/**
 * File: RepastBSDialog.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Class to implement some basic gridbag layout capabilities
 * @author Ľudovít Hajzer
 *
 */
public class RepastBSDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5336618467093835172L;
	
	protected GridBagConstraints gbc = new GridBagConstraints();
	protected GridBagLayout gridbag = new GridBagLayout();
	protected Container cp;
	
	/**
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public RepastBSDialog(JFrame parent,String title,boolean modal) {
		super(parent,title,modal);
		this.cp = getContentPane();
		cp.setLayout(gridbag);
	}
	
	/**
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public RepastBSDialog(JDialog parent,String title,boolean modal) {
		super(parent,title,modal);
	}
	
	/**
	 * @param gx
	 * @param gy
	 * @param gw
	 * @param gh
	 * @param wx
	 * @param wy
	 * @param what
	 * @param where
	 */
	public void add(int gx,int gy,int gw,int gh,int wx,int wy,
			Component what,Container where)
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
	 * 
	 */
	public void centerScreen() {
		setLocationRelativeTo(null);
	}
	
	/**
	 * Centers this dialog to center of its parent
	 */
	public void centerParent () {
		int x;
		int y;
		Container myParent = getParent();
		Point topLeft = myParent.getLocationOnScreen();
		Dimension parentSize = myParent.getSize();

		Dimension mySize = getSize();

		if (parentSize.width > mySize.width) 
			x = ((parentSize.width - mySize.width)/2) + topLeft.x;
		else 
			x = topLeft.x;

		if (parentSize.height > mySize.height) 
			y = ((parentSize.height - mySize.height)/2) + topLeft.y;
		else 
			y = topLeft.y;

		setLocation (x, y);
	}  
}
