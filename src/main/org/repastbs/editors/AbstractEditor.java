/**
 * File: AbstractEditor.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

/**
 * Abstract implementation of Editor interface, 
 * all concrete editors should extend this class
 * @author ¼udovít Hajzer
 *
 */
public abstract class AbstractEditor extends JComponent implements Editor {

	private List<EditorListener> listeners = new ArrayList<EditorListener>();
	
	protected GridBagConstraints gbc = new GridBagConstraints();
	protected GridBagLayout gridbag = new GridBagLayout();
	
	/**
	 * @see org.repastbs.editors.Editor#removeEditorListener(org.repastbs.editors.EditorListener)
	 */
	public void removeEditorListener(EditorListener l) {
		listeners.remove(l);
	}
	
	/**
	 * @see org.repastbs.editors.Editor#addEditorListener(org.repastbs.editors.EditorListener)
	 */
	public void addEditorListener(EditorListener l) {
		listeners.add(l);
	}

	/**
	 * @see org.repastbs.editors.Editor#fireValueChanged(org.repastbs.editors.EditorEvent)
	 */
	public void fireValueChanged(EditorEvent e) {
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			EditorListener listener = (EditorListener) iter.next();
			listener.valueChanged(e);
		}
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
	protected void add(int gx,int gy,int gw,int gh,int wx,int wy,
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
}
