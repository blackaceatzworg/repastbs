/**
 * File: XMLSerializable.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.xml;

import org.dom4j.Node;
import org.xml.sax.ContentHandler;

/**
 * XML Serializable represents clas that can be saved/loaded to XML file
 * @author �udov�t Hajzer
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
