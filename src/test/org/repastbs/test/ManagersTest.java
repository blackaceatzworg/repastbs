/**
 * File: ManagersTest.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.Panel;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.repastbs.RepastBS;
import org.repastbs.actions.Action;
import org.repastbs.actions.ActionManager;
import org.repastbs.actions.UnknownActionException;
import org.repastbs.component.Component;
import org.repastbs.component.ComponentManager;
import org.repastbs.editors.Editor;
import org.repastbs.editors.EditorEvent;
import org.repastbs.editors.EditorListener;
import org.repastbs.editors.EditorManager;
import org.repastbs.editors.NoEditor;


/**
 * Unit test to test managers funcionality
 * @author �udov�t Hajzer
 *
 */
@RunWith(JMock.class)
public class ManagersTest {
	
	private Mockery context = new JUnit4Mockery();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//no set ups
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
	public void actionManagerTest() throws Exception {
		ActionManager am = new ActionManager();
		try {
			am.executeAction("mockAction");
			fail("Should throw exception");
		} catch(UnknownActionException x) {
			//OK
		}
		assertFalse(am.isActionRegistered("mockAction"));
		
		//register mock action
		final Action mockAction = context.mock(Action.class);
		context.checking(new Expectations() {{
			one(mockAction).getName(); 
				will(returnValue("mockAction"));
			one(mockAction).execute(with(anything())); 
				will(returnValue("success"));
			one(mockAction).execute(with(any(String.class))); 
				will(returnValue("success2"));
		}});
		am.registerAction(mockAction);
		
		assertEquals("success",am.executeAction("mockAction"));
		assertEquals("success2",am.executeAction("mockAction","Test"));
		
		assertTrue(am.isActionRegistered("mockAction"));
		
		//test real action
		RepastBS repastBS = new RepastBS();
		try {
			repastBS.getActionManager().executeAction("XXXX");
			fail("Should throw exception");
		} catch(UnknownActionException x) {
			//OK
		}
		TestAction action = new TestAction();
		repastBS.getActionManager().registerAction(action);
		assertEquals("OK",repastBS.getActionManager().executeAction("Test"));
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void componentManagerTest() throws Exception {
		ComponentManager cm = new ComponentManager();
		
		assertNull(cm.getComponent("mockComponent1"));
		assertFalse(cm.isComponentRegistered("mockComponent1"));
		
		//create mock components
		final Component mockComponent1 = context.mock(Component.class);
		final Component mockComponent2 = context.mock(Component.class);
		final Component mockComponent3 = context.mock(Component.class);
		
		context.checking(new Expectations() {{
			allowing(mockComponent1).getId(); 
				will(returnValue("TEST"));
			allowing(mockComponent2).getId(); 
				will(returnValue("TEST"));
			allowing(mockComponent3).getId(); 
				will(returnValue("OTHER"));
		}});
		
		cm.registerComponent(mockComponent1);
		cm.registerComponent(mockComponent2);
		cm.registerComponent(mockComponent3);
		
		assertTrue(cm.isComponentRegistered(mockComponent1.getClass().getName()));
		assertEquals(3, cm.getComponentCount());
		assertEquals(2, cm.getComponentsById("TEST").size());
		assertEquals(1, cm.getComponentsById("OTHER").size());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void editorManagerTest() throws Exception {
		EditorManager em = new EditorManager();
		
		final Component editedValue = context.mock(Component.class);
		final EditorListener mockEditorListener = context.mock(EditorListener.class);
		final Editor mockEditor1 = context.mock(Editor.class);
		final Editor mockEditor2 = context.mock(Editor.class);
		
		NoEditor noEditor= (NoEditor)em.getEditor(String.class);
		final EditorEvent event = new EditorEvent(noEditor,editedValue);
		event.setType(EditorEvent.VALUE_CREATED);
		
		context.checking(new Expectations() {{
			one(mockEditor1).getEditorPanel(); 
				will(returnValue(new Panel()));
			one(mockEditor2).getEditorPanel(); 
				will(returnValue(new Panel()));
			one(mockEditorListener).valueChanged(event); 
		}});
		
		assertEquals(EditorEvent.VALUE_CREATED,event.getType());
		assertEquals(editedValue,event.getNewValue());
		
		noEditor.addEditorListener(mockEditorListener);
		noEditor.fireValueChanged(event);
		noEditor.removeEditorListener(mockEditorListener);
		
		assertNull(noEditor.getSupportedClass());
		assertFalse(em.isEditorRegistered("java.lang.String"));
		noEditor.setLabelText("test");
		assertEquals(noEditor.getEditorPanel(),em.getEditorPanel(String.class));
		noEditor.setEditedValue(editedValue);
		assertNull(noEditor.getEditedValue());
		assertEquals(0,em.getEditorCount());
		
		
		
		
		
		em.registerEditor(String.class,mockEditor1);
		em.registerEditor(Integer.class,mockEditor2);
		
		assertEquals(mockEditor1,em.getEditor(String.class));
		assertEquals(mockEditor2,em.getEditor(Integer.class));
		assertNotNull(em.getEditorPanel(String.class));
		assertNotNull(em.getEditorPanel(Integer.class));
		
		assertEquals(2,em.getEditorCount());
		assertEquals(2,em.getEditors().size());
		
		assertTrue(em.isEditorRegistered("java.lang.String"));
		assertTrue(em.isEditorRegistered("java.lang.Integer"));
	}
}