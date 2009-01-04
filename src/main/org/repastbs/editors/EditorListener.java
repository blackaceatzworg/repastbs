/**
 * File: EditorListener.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs.editors;

import java.util.EventListener;

/**
 * Listener for editor events
 * @author �udov�t Hajzer
 *
 */
public interface EditorListener extends EventListener {

	/**
	 * @param e
	 */
	public void valueChanged(EditorEvent e);
	
}
