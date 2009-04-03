/**
 * File: ComponentListener.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.EventListener;

/**
 * Component listener listens for editor events
 * @author Ludovit Hajzer
 *
 */
public interface ComponentListener extends EventListener {

	/**
	 * @param e
	 */
	public void componentChanged(ComponentEvent e);
}
