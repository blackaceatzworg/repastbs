/**
 * File: DynamicGenerator.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.dynamic;

/**
 * Interface representig dynamic generator of class,  it has methods to easily generate classes
 * @author  Ludovit Hajzer
 */
public interface DynamicGenerator {
	
	/**
	 * sets class name of generated class
	 * @param className  
	 * @throws DynamicException
	 * @uml.property  name="className"
	 */
	public void setClassName(String className) throws DynamicException;
	
	/**
	 * @return  class name of this dynamic class
	 * @throws DynamicException
	 * @uml.property  name="className"
	 */
	public String getClassName() throws DynamicException;
	
	/**
	 * @return dynamically created object
	 * @throws DynamicException 
	 */
	public Object getNewInstance() throws DynamicException;
	
	/**
	 * @return dynamically created class
	 * @throws DynamicException 
	 */
	@SuppressWarnings("unchecked")
	public Class getDynamicClass() throws DynamicException;
	
	/**
	 * Saves generated class to bytecode .class file
	 * @param directory output directory
	 * @throws DynamicException 
	 */
	public void saveToByteCode(String directory) throws DynamicException;
	
	/**
	 * Saves generated source code to .java file
	 * @param directory output directory
	 * @throws DynamicException 
	 */
	public void saveSourceCode(String directory) throws DynamicException;
	
	/**
	 * initializes this generator (reinitializes)
	 * @throws DynamicException
	 */
	public void init() throws DynamicException;
	
	/**
	 * Adds new field
	 * @param name
	 * @param type 
	 * @param defaultValue 
	 * @throws DynamicException 
	 */
	public void addField(String name, String type, String defaultValue) 
		throws DynamicException;
	
	/**
	 * Adds new field
	 * @param name
	 * @param type 
	 * @param defaultValue 
	 * @param addGetSet - if true, add also get/set method for created field
	 * @throws DynamicException 
	 */
	public void addField(String name, String type, String defaultValue, boolean addGetSet) 
		throws DynamicException;
	
	/**
	 * Adds get method for field
	 * @param fieldName
	 * @throws DynamicException 
	 */
	public void addGetterMethod(String fieldName) 
		throws DynamicException;
	
	/**
	 * Adds set method for field
	 * @param fieldName
	 * @throws DynamicException 
	 */
	public void addSetterMethod(String fieldName) 
		throws DynamicException;
	
	/**
	 * Adds method
	 * @param name
	 * @param returnType 
	 * @param paramNames 
	 * @param paramTypes 
	 * @param body
	 * @throws DynamicException 
	 */
	public void addMethod(String name, String returnType, 
			String []paramNames, String []paramTypes, String body) 
		throws DynamicException;
	
	/**
	 * Adds constructor
	 * @param paramNames 
	 * @param paramTypes 
	 * @param body
	 * @throws DynamicException 
	 */
	public void addConstructor(String []paramNames, String []paramTypes, 
			String body) 
		throws DynamicException;
	
	/**
	 * Replaces body of given method which has given parameters
	 * @param methodName
	 * @param paramTypes 
	 * @param body
	 * @throws DynamicException 
	 */
	public void setBody(String methodName, String []paramTypes, String body) 
		throws DynamicException;
	
	/**
	 * Insert code before all other code to method
	 * @param methodName 
	 * @param paramTypes 
	 * @param code
	 * @throws DynamicException 
	 */
	public void insertBefore(String methodName, String []paramTypes, String code) 
		throws DynamicException;
	
	/**
	 * Insert code after all other code to method
	 * @param methodName 
	 * @param paramTypes 
	 * @param code
	 * @throws DynamicException 
	 */
	public void insertAfter(String methodName, String []paramTypes, String code) 
		throws DynamicException;
	
	
	/**
	 * @param imp - the import to add to dynamic class
	 */
	public void addImport(String imp);
	
	/**
	 * Add specified interface to this dynamically created class
	 * @param name - name of interface
	 * @throws DynamicException 
	 */
	public void addInterface(String name) throws DynamicException;
}
