/**
 * File: JavassistGeneratorTest.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.dynamic.JavassistGenerator;

/**
 * Test unit for Javasisst generator implementation
 * @author ¼udovít Hajzer
 *
 */
public class JavassistGeneratorTest {
	
	private DynamicGenerator generator;
	File classFile;
	String dir = "../test";
	
	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		generator = new JavassistGenerator(new Object(),"org.repastbs.dynamic.test.DynamicClassTest");
		classFile = new File(dir+"/org/repastbs/dynamic/test/DynamicClassTest.class");
		classFile.delete();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		generator = null;
		classFile.delete();
		deleteDir(new File(this.dir));
		System.gc();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void basicGenerationTest() throws Exception {
		Class c;
		try {
			//class should not exist
			c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
			fail("Should throw exception");
		} catch(ClassNotFoundException e) {
			//OK
		}
		//generate and check if it has been generated
		generateClassFile();
		c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		assertEquals("org.repastbs.dynamic.test.DynamicClassTest", c.getName());
	}

	/**
	 * @throws Exception 
	 */
	@Test
	public void addConstructorTest() throws Exception {
		//add empty constructor
		generator.addConstructor(null, null, "System.out.println(\"Constructor(void)\");");
		//try to add same constructor
		try {
			generator.addConstructor(null, null, "System.out.println(\"Test constructor1\");");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		//add constructor with params
		generator.addConstructor(new String[]{"param1","param2"}, new String[]{"double","int"}, 
				"System.out.println(\"Constructor(param1, param2): \"+param1+\", \"+param2);");
		
		//try to add constructor with wrong params
		try {
			generator.addConstructor(new String[]{"param1"}, null, 
					"System.out.println(\"Test constructor2\");");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		generateClassFile();
		//load class and check if constructors were added
		Class<?> c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		//try to invoke all created constructors
		assertTrue(c.isInstance(c.getConstructor((Class[])null).newInstance((Object[])null)));
		assertTrue(c.isInstance(c.getConstructor(double.class,int.class).newInstance(5.1,5)));
	}
	
	/**
	 * @throws Exception 
	 */
	@Test
	public void addMethodTest() throws Exception {
		//add empty method
		generator.addMethod("testMethod1", "void", null, null, "System.out.println(\"Method(void)\");");
		//try to add same method
		try {
			generator.addMethod("testMethod1", "void", null, null, "System.out.println(\"Method(void)\");");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		//add method with params
		generator.addMethod("testMethod2", "void", new String[]{"param1","param2"}, new String[]{"double","int"}, 
				"System.out.println(\"Method(param1, param2): \"+param1+\", \"+param2);");
		
		//try to add method with wrong params
		try {
			generator.addMethod("testMethod", "void", new String[]{"param1"}, null, "System.out.println(\"Method(void)\");");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		
		//add method with return type
		generator.addMethod("testMethod3", "int", new String[]{"param1","param2"}, new String[]{"int","int"}, 
				"System.out.println(\"Method(param1, param2): \"+param1+\", \"+param2);\n" +
				"return param1+param2;");
		
		//try to add method without return statement
		try {
			generator.addMethod("testMethod3", "int", new String[]{"param1"}, null, "System.out.println(\"Method(void)\");");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		generateClassFile();
		//load class and check if method were added
		Class<?> c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		Object holder = c.newInstance();
		//try to invoke all created methods
		c.getMethod("testMethod1", (Class[])null).invoke(holder, (Object[])null);
		c.getMethod("testMethod2", double.class,int.class).invoke(holder, 5.1, 5);
		assertEquals(10,c.getMethod("testMethod3", int.class,int.class).invoke(holder, 5, 5));
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void addFieldTest() throws Exception {
		//add field
		generator.addField("testField1", "int", null);
		generator.addField("testField1", "int", null);
		
		try {
			generator.addField("testFieldXX", "NOT_TO_BE_FOUND", null);
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		
		generator.addField("testString", "String", null);
		generator.addField("testStringWithDefault", "String", "test");
		
		try {
			generator.addGetterMethod("XXX");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		try {
			generator.addSetterMethod("XXX");
			fail("Should throw exception");
		} catch(DynamicException e) {
			//OK
		}
		
		generator.addGetterMethod("testField1");
		//add field with default value
		generator.addField("testField2", "int", "6");
		generator.addSetterMethod("testField2");
		generator.addGetterMethod("testField2");
		
		//add field with get/set methods
		generator.addField("testField3", "int", "6", true);
		generateClassFile();
		
		//load class and check if fields were added
		Class<?> c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		Object holder = c.newInstance();
		
		assertEquals(0,c.getMethod("getTestField1", (Class[])null).invoke(holder));
		assertEquals(6,c.getMethod("getTestField2", (Class[])null).invoke(holder));
		
		c.getMethod("setTestField3", int.class).invoke(holder,10);
		assertEquals(10,c.getMethod("getTestField3", (Class[])null).invoke(holder));
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void methodChangesTest() throws Exception {
		try {
			generator.setBody("methodChange", null, "changed--;");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		
		try {
			generator.setBody(null, new String[]{"int"}, "changed--;");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		generator.addField("changed", "int", "6", true);
		
		//change constructor
		generator.addConstructor(null, null, ";");
		generator.insertBefore(null, null, "changed--;");
		generator.setBody(null, null, "changed++;");
		generator.insertAfter(null, null, "changed--;");
		
		
		generator.addMethod("methodChange", null, null,null, ";");
		generateClassFile();
		Class<?> c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		Object holder = c.newInstance();

		//method should not change 'changed'
		c.getMethod("methodChange", (Class[])null).invoke(holder);
		assertEquals(6,c.getMethod("getChanged", (Class[])null).invoke(holder));
		
		//set body of the method to substract 1 from changed
		generator.setBody("methodChange", null, "changed--;");
		assertBodyChange(5);
		
		//insert code before method to substract 2 from changed
		generator.insertBefore("methodChange", null, "changed-=2;");
		assertBodyChange(3);
		
		//insert code after method to substract 3 from changed
		generator.insertAfter("methodChange", null, "changed-=3;");
		assertBodyChange(0);
		
		//method changes using method with params
		generator.addMethod("methodChange2", null, new String[]{"param"}, new String[]{"int"}, ";");
		
		generator.setBody("methodChange2", new String[]{"int"}, "changed -= $1;");
		generateClassFile();
		c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		holder = c.newInstance();
		c.getMethod("methodChange2",int.class).invoke(holder,6);
		assertEquals(0,c.getMethod("getChanged", (Class[])null).invoke(holder));
	}
	
	private void assertBodyChange(int value) throws Exception {
		generateClassFile();
		Class<?> c = loadClass("org.repastbs.dynamic.test.DynamicClassTest");
		Object holder = c.newInstance();
		//returned value should now be 0
		c.getMethod("methodChange",(Class[])null).invoke(holder);
		assertEquals(value,c.getMethod("getChanged", (Class[])null).invoke(holder));
	}	
	
	/**
	 * @throws Exception
	 */
	@Test
	public void importTest() throws Exception {
		try {
			generator.addField("test", "JFrame", null);
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		generator.addImport("javax.swing");
		generator.addField("test", "JFrame", null);
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void interfaceTest() throws Exception {
		try {
			generator.addInterface("xxxxx");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		generator.addImport("java.awt");
		generator.addField("button", "Button", "Test Button");
		generator.addConstructor(null, null, ";");
		try {
			generator.setBody(null, null, "button.addActionListener(this);");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		generator.addInterface("java.awt.event.ActionListener");
		generator.setBody(null, null, "button.addActionListener(this);");
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void newInstanceTest() throws Exception {
		try {
			generator.saveSourceCode("??");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		generator.saveSourceCode(dir);
		generator.getClass();
		generator.getNewInstance();
		try {
			generator.getNewInstance();
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		try {
			generator.saveToByteCode("??");
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
		
		generator = new JavassistGenerator(new Object());
		generateClassFile();
		generator.setClassName("org.repastbs.dynamic.test.AnotherClass");
		assertEquals("org.repastbs.dynamic.test.AnotherClass", generator.getClassName());
		generateClassFile();
		loadClass("org.repastbs.dynamic.test.AnotherClass");
		
		try {
			generator = new JavassistGenerator(null);
			fail("Should throw exception");
		} catch (Exception e) {
			//OK
		}
	}
	
	private void generateClassFile() throws DynamicException {
		generator.saveToByteCode(dir);
	}
	
	private Class loadClass(String name) throws Exception {
		URLClassLoader cl = new URLClassLoader(new URL[]{new File(dir).toURI().toURL()});
		return cl.loadClass(name);
	}
	
    /**
     * @param dir
     * @return true, if directory was deleted, false otherwise
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty, so delete it
        return dir.delete();
    }
}
