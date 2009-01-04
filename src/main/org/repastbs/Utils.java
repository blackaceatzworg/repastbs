/**
 * File: Utils.java
 * Program: Repast BS
 * Author:  �udov�t Hajzer
 * Master�S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: L�szl� Guly�s, Ph.D.
 */
package org.repastbs;

import java.io.File;

/**
 * Utils class contains helper methods
 * @author Ľudovít Hajzer
 *
 */
public class Utils {
	
    /** XML file extension*/
    public final static String xml = "xml";

    /**
     * Get the extension of a file.
     * @param f - file of which to get extension
     * @return file extension
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    /**
     * Method for getting class of primitive types
     * @param className
     * @return class based on class name
     * @throws ClassNotFoundException
     */
    public static Class getClass(String className) throws ClassNotFoundException {
    	if(className.compareTo("boolean")==0)
			return boolean.class;
    	else if(className.compareTo("char")==0)
			return char.class;
    	else if(className.compareTo("char")==0)
			return char.class;
    	else if(className.compareTo("double")==0)
			return double.class;
    	else if(className.compareTo("float")==0)
			return float.class;
    	else if(className.compareTo("int")==0)
			return int.class;
    	else if(className.compareTo("long")==0)
			return long.class;
    	else if(className.compareTo("short")==0)
			return short.class;
    	else if(className.compareTo("void")==0)
			return void.class;
    	else
    		return Class.forName(className);
    }
}