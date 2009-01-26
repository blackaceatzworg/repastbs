/**
 * File: ComponentManager.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Component manager manages all available components registered in Repast BS
 * @author  �udov�t Hajzer
 */
public class ComponentManager {
	
	/**
	 * @uml.property  name="components"
	 * @uml.associationEnd  qualifier="key:java.lang.Object org.repastbs.component.Component"
	 */
	private Map<String, Component> components = new HashMap<String, Component>();
	
	/**
	 * Register new component in repast BS
	 * 
	 * @param component
	 */
	public void registerComponent(Component component) {
		components.put(component.getClass().getName(), component);
	}
	
	/**
	 * Checks wheter component of given class is registered
	 * @param className
	 * @return true if component is registered, false otherwise
	 */
	public boolean isComponentRegistered(String className) {
		return components.get(className) != null;
	}
	
	/**
	 * Gets component of give class name
	 * @param className
	 * @return registered component by id
	 */
	public Component getComponent(String className) {
		return components.get(className);
	}
	
	/**
	 * Gets count of registered component
	 * @return count of registered components
	 */
	public int getComponentCount() {
		return components.size();
	}
	
	/**
	 * Gets collection of all registered components
	 * @return  registered component
	 * @uml.property  name="components"
	 */
	public Collection<Component> getComponents() {
		return components.values();
	}
	
	/**
	 * Get all components with specified id
	 * @param id
	 * @return list of components with given id
	 */
	public List<Component> getComponentsById(String id) {
		List<Component> list = new ArrayList<Component>();
		for (Iterator iter = getComponents().iterator(); iter.hasNext();) {
			Component element = (Component) iter.next();
			if(element.getId().compareTo(id)==0)
				list.add(element);
		}
		return list;
	}
}
