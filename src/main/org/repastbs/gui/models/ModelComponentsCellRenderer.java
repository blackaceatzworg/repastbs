/**
 * File: ModelComponentsCellRenderer.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
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
 * @author ¼udovít Hajzer
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