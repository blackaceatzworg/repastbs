/**
 * File: EditorListener.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
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
