/**
 * File: NetworkModelTest.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.repastbs.RepastBS;
import org.repastbs.model.Model;
import org.repastbs.model.NetworkModel;
import org.repastbs.xml.SAXUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;


/**
 * Network model template unit test
 * @author �udov�t Hajzer
 *
 */
@RunWith(JMock.class)
public class NetworkModelTest {
	
	private Mockery context = new JUnit4Mockery();
	private Model model;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		model = new NetworkModel();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.gc();
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void basicTest() throws Exception {
		//test when RepastBS is not initialized, so no components are registered
		model.createNew();
		model.generateFields();
		model.generateMethods();
		
		
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void basicTestWithRepastBS() throws Exception {
		//	init RepastBS and run same methods
		RepastBS repast = new RepastBS();
		repast.getActionManager();
		model.createNew();
		model.generateFields();
		model.generateMethods();
		model.setTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
		assertNotNull(model.getDescription());
		assertFalse(model.isRemovable());
		assertFalse(model.isIterable());
		assertNull(model.getIterableClass());
		assertTrue(model.isEditable());
		assertEquals("this",model.getSchedulableObjectName());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void xmlSerializationTest() throws Exception {
		//to cover 100%
		SAXUtils utils = new SAXUtils();
		utils.getClass();
		
		RepastBS repast = new RepastBS();
		repast.getActionManager();
		model.createNew();
		
		final ContentHandler handler = context.mock(ContentHandler.class);
		context.checking(new Expectations() {{
			allowing (handler).startElement(
					with(any(String.class)), 
					with(any(String.class)), 
					with(any(String.class)), 
					with(any(Attributes.class)));
			allowing (handler).characters(
					with(any(char[].class)), 
					with(any(int.class)), 
					with(any(int.class)));
			allowing (handler).endElement(
					with(any(String.class)), 
					with(any(String.class)), 
					with(any(String.class)));	
		}});
		model.writeToXML(handler);
		
		testRead("../Resources/test/NetworkModel.xml");
		testRead("../Resources/test/StagHunt.xml");
		try {
			testRead("../Resources/test/NetworkModelError.xml");
			fail("Should throw exception");
		} catch(Exception e) {
			//ok
		}
	}
	
	private void testRead(String fileName) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		document.normalize();
		Node root = document.selectSingleNode("//model");
		Class modelClass = Class.forName(root.valueOf("@class"));
		model = (Model)modelClass.newInstance();
		model.createFromXML(root);
		model.generateFields();
		model.generateMethods();
	}
}