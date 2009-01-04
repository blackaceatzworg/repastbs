/**
 * File: AllTests.java
 * Program: Repast BS
 * Author:  ¼udovít Hajzer
 * Master’S Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: MSc. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */package org.repastbs.test;

/**
 * Class that runs all tests
 * @author ¼udovít Hajzer
 *
 */
public class AllTests {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main(new String[]{
				"org.repastbs.test.JavassistGeneratorTest",
				"org.repastbs.test.ManagersTest",
				"org.repastbs.test.models.GridModelTest",
				"org.repastbs.test.models.NetworkModelTest"
		});
	}
}
