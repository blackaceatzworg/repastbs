/**
 * File: ModelComponentsCellRenderer.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui.models;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.repastbs.RepastBS;

/**
 * Model components tree renderer
 * @author �udov�t Hajzer
 *
 */
public class ModelComponentsCellRenderer extends DefaultTreeCellRenderer {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7168489662445038588L;

	/**
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus)
	{
		Icon icon = RepastBS.getIcon(value.getClass().getSimpleName());
		setLeafIcon(icon);
		setOpenIcon(icon);
		setClosedIcon(icon);
		Component c = super.getTreeCellRendererComponent(
				tree, value,
				sel, expanded, leaf,
				row,
				hasFocus);
		return c;
	}
}