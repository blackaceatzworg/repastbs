/**
 * File: SAXUtils.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.xml;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.repastbs.RepastBS;
import org.repastbs.component.Component;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Helper class which contains static method used for xml manipulation
 * @author ¼udovít Hajzer
 *
 */
public class SAXUtils {

	/**
	 * format used for writing to xml files
	 */
	public static final OutputFormat FORMAT = OutputFormat.createPrettyPrint();
	private static final Attributes EMPTY_ATTRIBUTES = new AttributesImpl();

	/**
	 * Write xml tag to handler, with specified attributes and value
	 * @param contentHandler
	 * @param element
	 * @param value
	 * @param atts
	 * @throws SAXException
	 */
	public static void text(ContentHandler contentHandler,
			String element, String value, Attributes atts) throws SAXException {
		contentHandler.startElement("", element, element, atts);
		char[] ch = value.toCharArray();
		contentHandler.characters(ch,0,ch.length);
		contentHandler.endElement("", element, element);
	}

	/**
	 * Write xml tag to handler, with specified value 
	 * @param contentHandler
	 * @param element
	 * @param value
	 * @throws SAXException
	 */
	public static void text(ContentHandler contentHandler,
			String element, String value) throws SAXException {
		SAXUtils.text(contentHandler, element,
				value, EMPTY_ATTRIBUTES);
	}

	/**
	 * Start xml tag, with given attributes
	 * @param contentHandler
	 * @param element
	 * @param atts 
	 * @throws SAXException
	 */
	public static void start(ContentHandler contentHandler,
			String element, Attributes atts) throws SAXException {
		contentHandler.startElement("",element, element, atts);
	}
	
	/**
	 * Start xml tag without any attributes
	 * @param contentHandler
	 * @param element
	 * @throws SAXException
	 */
	public static void start(ContentHandler contentHandler,
			String element) throws SAXException {
		SAXUtils.start(contentHandler, element, new AttributesImpl());
	}

	/**
	 * Close xml tag
	 * @param contentHandler
	 * @param element
	 * @throws SAXException
	 */
	public static void end(ContentHandler contentHandler,
			String element) throws SAXException {
		contentHandler.endElement("",element, element);
	}

	/**
	 * Reads all child nodes and tries to create components 
	 * and add them to parent component
	 * @param node
	 * @param parent
	 */
	public static void createChildren(Node node, Component parent) {
		for (Iterator iter = node.selectNodes("./*").iterator(); iter.hasNext();) {
			Node element = (Node) iter.next();
			try {
				String nodeValue = element.valueOf("@class");
				if(nodeValue == null || nodeValue.compareTo("")==0)
					continue;
				Component c = (Component)RepastBS.getComponentManager()
				.getComponent(nodeValue);
				if(c==null) {
					Class componentClass = Class.forName(nodeValue);
					c = (Component)componentClass.newInstance();
				}
				Method m = getCreateFromXMLMethod(c.getClass());//c.getClass().getDeclaredMethod("createFromXML", Node.class);
				parent.add(c);
				m.invoke(c,element);
			} catch (Exception e) {
				throw new XMLSerializationException("SAXUtils.createChildren: "+e.getMessage(),e);
			}	
		}
	}

	private static Method getCreateFromXMLMethod(Class<?> c) {
		try {
			Method m = c.getDeclaredMethod("createFromXML", Node.class);
			return m;
		} catch (Exception e) {
			return getCreateFromXMLMethod(c.getSuperclass());
		}
	}

	/**
	 * Reads all child nodes and tries to create components of specified class
	 * and add them to parent component
	 * @param node
	 * @param toCast class of newly added component
	 * @param parent
	 */
	public static void createChildren(Node node, Class toCast, Component parent) {
		for (Iterator iter = node.selectNodes("./*").iterator(); iter.hasNext();) {
			Node element = (Node) iter.next();
			try {
				Component c = (Component)toCast.newInstance();
				parent.add(c);
				((XMLSerializable)c).createFromXML(element);
			} catch (Exception e) {
				throw new XMLSerializationException("SAXUtils.createChildren: "+e.getMessage(), e);
			}	
		}
	}

	/**
	 * Serializes all XMLSerializable children
	 * @param handler
	 * @param parent
	 */
	public static void serializeChildren(ContentHandler handler, Component parent) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			Component c = (Component)parent.getChildAt(i);
			if(c instanceof XMLSerializable) {
				XMLSerializable serializable = (XMLSerializable)c;
				serializable.writeToXML(handler);
			}
		}
	}
}