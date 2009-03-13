/**
 * File: JavassistGenerator.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.dynamic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import jode.decompiler.Decompiler;

/**
 * Implementation of dynamic generator, it uses Javassist and Jode
 * @author  Ľudovít Hajzer
 */
public class JavassistGenerator implements DynamicGenerator {

	/** replace body */
	public static final int CHANGE_BODY = 0;
	/** add code before body */
	public static final int INSERT_BEFORE = 1;
	/** add code after body*/
	public static final int INSERT_AFTER = 2;

	private CtClass dynamicClass;
	private Object parentObject;
	private ClassPool cp = ClassPool.getDefault();
	private String packageName="";

	/**
	 * Constructor for JavassistGenerator
	 * @param parentObject - object, whose class will be parent of generated class
	 * @throws DynamicException 
	 */
	public JavassistGenerator(Object parentObject) throws DynamicException {
		this(parentObject,parentObject.getClass().getPackage().getName()+
				".Dynamic"+parentObject.getClass().getSimpleName());
	}

	/**
	 * Constructor for JavassistGenerator
	 * @param parentObject - object, whose class will be parent of generated class
	 * @param packageName - package name of generated class
	 * @throws DynamicException 
	 */
	public JavassistGenerator(Object parentObject, String packageName) throws DynamicException {
		this.parentObject = parentObject;
		this.packageName = packageName;
		init();
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#init()
	 */
	public void init() throws DynamicException {
		dynamicClass = cp.makeClass(packageName);
		CtClass parent;
		try {
			parent = cp.get(parentObject.getClass().getName());
			dynamicClass.setSuperclass(parent);			
		} catch (Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}

	/**
	 * @see  org.repastbs.dynamic.DynamicGenerator#getDynamicClass()
	 * @uml.property  name="dynamicClass"
	 */
	public Class getDynamicClass() throws DynamicException {
		try {
			Class c = dynamicClass.toClass();
			dynamicClass.defrost();
			return c;
		} catch (Exception e) {
			throw new DynamicException("Get Dynamic failed: "+e.getMessage(),e);
		}

	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#getNewInstance()
	 */
	public Object getNewInstance() throws DynamicException {
		try {
			Object o=getDynamicClass().newInstance();
			dynamicClass.defrost();
			return o;
		} catch (Exception e) {
			throw new DynamicException("New instance failed: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#saveSourceCode(java.lang.String)
	 */
	public void saveSourceCode(String directory) throws DynamicException {
		try {
			saveToByteCode(directory);
			String packageName = "";
			if(dynamicClass.getPackageName()!=null)
				packageName = dynamicClass.getPackageName().replace(".","/");
			File f = new File(directory+"/"+packageName
					+"/"+dynamicClass.getSimpleName()+".java");
			f.createNewFile();
			Writer out = new OutputStreamWriter(new FileOutputStream(f));
			Decompiler decompiler = new Decompiler();
			decompiler.setClassPath(directory);
			decompiler.decompile(dynamicClass.getName(), out, null);
			out.close();
		} catch (Exception e) {
			throw new DynamicException("saveSourceCode failed: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#saveToByteCode(java.lang.String)
	 */
	public void saveToByteCode(String directory) throws DynamicException {
		try {
			dynamicClass.stopPruning(true);
			dynamicClass.writeFile(directory);
			dynamicClass.defrost();
		} catch (Exception e) {
			throw new DynamicException("saveToByteCode failed: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addField(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addField(String name, String type, String defaultValue) throws DynamicException {
		addField(name,type,defaultValue,false);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addField(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void addField(String name, String type, String defaultValue, boolean addGetSet) 
	throws DynamicException {
		//if field exist, return
		try {
			dynamicClass.getField(name);
			return;
		} catch(NotFoundException x) {
			//DO NOTHING
		}
		StringBuffer fieldDefinition = new StringBuffer()
		.append("private ")
		.append(type)
		.append(' ')
		.append(name);

		if(defaultValue!=null && defaultValue.compareTo("")!=0) {
			CtClass typeClass = null;
			try {
				typeClass = cp.get(type);
			} catch (NotFoundException e) {
				//OK
			}
			if(typeClass!=null && typeClass.isPrimitive())
				fieldDefinition.append(" = (").append(type).append(")").append(defaultValue);
			else {
				fieldDefinition.append(" = new ")
				.append(type)
				.append("(\"").append(defaultValue).append("\")");
			}
		}
		fieldDefinition.append(";");
		System.out.println("Creating field in "+dynamicClass.getSimpleName()+": "+fieldDefinition.toString());
		try {
			dynamicClass.addField(CtField.make(fieldDefinition.toString(), dynamicClass));
		} catch (Exception e) {
			throw new DynamicException("Add Field failed: "+e.getMessage(),e);
		}
		if(addGetSet) {
			addGetterMethod(name);
			addSetterMethod(name);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addConstructor(java.lang.String[], java.lang.String[], java.lang.String)
	 */
	public void addConstructor(String[] params, String[] paramTypes, String body) throws DynamicException {
		addMethod(null,null,params,paramTypes,body);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addMethod(java.lang.String, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String)
	 */
	public void addMethod(String name, String returnType, String[] params, 
			String[] paramTypes, String body) throws DynamicException {
		if(params==null)
			params=new String[0];
		if(paramTypes==null)
			paramTypes=new String[0];
		if(params.length != paramTypes.length)
			throw new DynamicException("Length of parameter names is not coresponding with parameter types");
		StringBuffer methodString = new StringBuffer()
		.append("public ")
		.append(name==null?"":returnType==null?"void ":returnType+" ")
		.append(name==null?dynamicClass.getSimpleName():name)
		.append("(");
		for (int i = 0; i < paramTypes.length; i++) {
			methodString.append(paramTypes[i])
			.append(' ').append(params[i]);
			if(i != params.length-1)
				methodString.append(", ");
		}
		methodString.append(") { ")
		.append(body==null?";":body)
		.append(" }");
		System.out.println("Creating method in "+dynamicClass.getSimpleName()+": "+methodString);
		try {
			if(name == null) {
				CtConstructor ctConstructor = CtNewConstructor.make(
						methodString.toString(),dynamicClass);
				dynamicClass.addConstructor(ctConstructor);				
			}
			else {
				CtMethod method = CtNewMethod.make(methodString.toString(),
						dynamicClass);
				dynamicClass.addMethod(method);
			}
		} catch (Exception e) {
			throw new DynamicException("Add method "+name+" failed: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addGetterMethod(java.lang.String)
	 */
	public void addGetterMethod(String fieldName) throws DynamicException {
		try {
			CtField field=dynamicClass.getField(fieldName);
			StringBuffer methodName = new StringBuffer("get"+fieldName);
			methodName.setCharAt(3, Character.toUpperCase(methodName.charAt(3)));
			this.addMethod(methodName.toString(),field.getType().getName(), null, null, "return "+fieldName+";");
		} catch (NotFoundException e) {
			throw new DynamicException("Add Getter method failed for field "+fieldName+": "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addSetterMethod(java.lang.String)
	 */
	public void addSetterMethod(String fieldName) throws DynamicException {
		try {
			CtField field=dynamicClass.getField(fieldName);
			StringBuffer methodName = new StringBuffer("set"+fieldName);
			methodName.setCharAt(3, Character.toUpperCase(methodName.charAt(3)));
			this.addMethod(methodName.toString(),null, new String[]{fieldName}, 
					new String[]{field.getType().getName()}, 
					"this."+fieldName+" = "+fieldName+";");
		} catch (NotFoundException e) {
			throw new DynamicException("Add Setter method failed for field "+fieldName+": "+e.getMessage(),e);
		}
	}

	/**
	 * @param name
	 * @param paramTypes
	 * @param body
	 * @param type
	 * @throws DynamicException
	 */
	private void changeBody(String name, String[] paramTypes, 
			String body, int type) throws DynamicException {
		if(paramTypes==null)
			paramTypes=new String[0];
		try {
			CtClass parameters[] = new CtClass[paramTypes.length];
			for (int i = 0; i < paramTypes.length; i++) {
				parameters[i] = cp.get(paramTypes[i]);
			}
			if(name == null) {
				CtConstructor ctConstructor = dynamicClass.getDeclaredConstructor(parameters);
				switch(type) {
					case CHANGE_BODY: ctConstructor.setBody("{ "+body+" }"); break;
					case INSERT_BEFORE: ctConstructor.insertBefore("{ "+body+" }"); break;
					case INSERT_AFTER: ctConstructor.insertAfter("{ "+body+" }"); break;
				}
			}
			else {
				CtMethod method = dynamicClass.getDeclaredMethod(name, parameters);
				switch(type) {
					case CHANGE_BODY: method.setBody("{ "+body+" }"); break;
					case INSERT_BEFORE: method.insertBefore("{ "+body+" }"); break;
					case INSERT_AFTER: method.insertAfter("{ "+body+" }"); break;
				}

			}
		} catch (Exception e) {
			throw new DynamicException("Change Body failed: "+e.getMessage(),e);
		}
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#insertAfter(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void insertAfter(String methodName, String[] paramTypes, String code) throws DynamicException {
		changeBody(methodName,paramTypes,code,JavassistGenerator.INSERT_AFTER);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#insertBefore(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void insertBefore(String methodName, String[] paramTypes, String code) throws DynamicException {
		changeBody(methodName,paramTypes,code,JavassistGenerator.INSERT_BEFORE);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#setBody(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void setBody(String methodName, String[] paramTypes, String body) throws DynamicException {
		changeBody(methodName,paramTypes,body,JavassistGenerator.CHANGE_BODY);
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		dynamicClass.replaceClassName(dynamicClass.getSimpleName(), className);
		dynamicClass.setName(className);
		packageName = className;
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#getClassName()
	 */
	public String getClassName() throws DynamicException {
		return dynamicClass.getName();
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addImport(java.lang.String)
	 */
	public void addImport(String imp) {
		cp.importPackage(imp);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicGenerator#addInterface(java.lang.String)
	 */
	public void addInterface(String name) throws DynamicException {
		try {
			dynamicClass.addInterface(cp.get(name));
		} catch (NotFoundException e) {
			throw new DynamicException("Interface "+name+" not found", e);
		}
	}
}