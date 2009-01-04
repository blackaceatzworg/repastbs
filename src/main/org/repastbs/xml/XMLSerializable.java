/**
 * File: XMLSerializable.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.xml;

import org.dom4j.Node;
import org.xml.sax.ContentHandler;

/**
 * XML Serializable represents clas that can be saved/loaded to XML file
 * @author ¼udovít Hajzer
 *
 */
public interface XMLSerializable {
	
	/**
	 * Write to xml
	 * @param handler
	 * @throws XMLSerializationException
	 */
	public void writeToXML(ContentHandler handler) throws XMLSerializationException;
	
	/**
	 * Load from xml
	 * @param node
	 * @throws XMLSerializationException
	 */
	public void createFromXML(Node node) throws XMLSerializationException;
}
