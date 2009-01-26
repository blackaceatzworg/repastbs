/**
 * File: AllTests.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.test;

/**
 * Class that runs all tests
 * @author �udov�t Hajzer
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
