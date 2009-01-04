/**
 * File: ComponentListener.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.EventListener;

/**
 * Component listener listens for editor events
 * @author ¼udovít Hajzer
 *
 */
public interface ComponentListener extends EventListener {

	/**
	 * @param e
	 */
	public void componentChanged(ComponentEvent e);
}
