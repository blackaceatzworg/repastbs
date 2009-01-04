/**
 * File: EditorListener.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.util.EventListener;

/**
 * Listener for editor events
 * @author ¼udovít Hajzer
 *
 */
public interface EditorListener extends EventListener {

	/**
	 * @param e
	 */
	public void valueChanged(EditorEvent e);
	
}
