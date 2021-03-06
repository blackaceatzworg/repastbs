/**
 * File: IconLabelCellRenderer.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui.models;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * GUI model for tree in main frame
 * @author �udov�t Hajzer
 *
 */
public class IconLabelCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

	/** */
	private static final long serialVersionUID = 650793853118179324L;

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus)
	{
		JLabel retuuurn = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
		retuuurn.setIcon(((JLabel)value).getIcon());
		retuuurn.setText(((JLabel)value).getText());
		return retuuurn;
	}
}