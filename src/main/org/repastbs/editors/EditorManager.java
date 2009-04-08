/**
 * File: EditorManager.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Container;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Editor manager manages editors registered in Repast BS
 * @author  Ludovit Hajzer
 */
public class EditorManager {
	
	/**
	 * @uml.property  name="editors"
	 * @uml.associationEnd  qualifier="key:java.lang.Object org.repastbs.editors.Editor"
	 */
	private Map<String, Editor> editors = new HashMap<String, Editor>();
	
	private Editor noEditor;
	
	/**
	 * 
	 */
	public EditorManager() {
		noEditor = new NoEditor();
	}
	
	/**
	 * registers new editor in repast
	 * @param editorFor 
	 * @param editor 
	 */
	@SuppressWarnings("unchecked")
	public void registerEditor(Class editorFor, Editor editor) {
		editors.put(editorFor.getName(), editor);
		System.out.println("Registering editor: "+editor.getClass().toString()
			+" for "+editorFor.getName());
	}
	
	/**
	 * Gets editor's panel
	 * @param editorFor
	 * @return editors container
	 */
	@SuppressWarnings("unchecked")
	public Container getEditorPanel(Class editorFor) {
		Editor e = editors.get(editorFor.getName());
		if(e == null)
			return noEditor.getEditorPanel();
		return e.getEditorPanel();
	}
	
	/**
	 * Returns registered editor for given class
	 * @param editorFor
	 * @return editor
	 */
	@SuppressWarnings("unchecked")
	public Editor getEditor(Class editorFor) {
		Editor e = editors.get(editorFor.getName());
		if(e == null)
			return noEditor;
		return e;
	}
	
	/**
	 * @param editor name
	 * @return true if editor is registered, false otherwise
	 */
	public boolean isEditorRegistered(String editor) {
		return editors.get(editor) != null;
	}
	
	/**
	 * @return count of registered editors
	 */
	public int getEditorCount() {
		return editors.size();
	}
	
	/**
	 * @return  registered editors
	 * @uml.property  name="editors"
	 */
	public Collection<Editor> getEditors() {
		return editors.values();
	}
}
