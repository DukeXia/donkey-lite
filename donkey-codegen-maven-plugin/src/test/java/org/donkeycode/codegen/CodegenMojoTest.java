package org.donkeycode.codegen;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

/**
 * 测试
 *
 * @author nanfeng
 *
 */
public class CodegenMojoTest extends AbstractMojoTestCase {

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		//super.setUp();
	}

	/**
	 * @throws Exception
	 */
	public void testMojoGoal() throws Exception {
		File testPom = new File(getBasedir(), "src/test/resources/unit/basic-test/basic-test-plugin-config.xml");
		CodegenMojo mojo = (CodegenMojo) lookupMojo("code", testPom);
		assertNotNull(mojo);
	}

}