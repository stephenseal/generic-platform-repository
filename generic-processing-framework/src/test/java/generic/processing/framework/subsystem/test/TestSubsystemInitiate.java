/**
 * 
 */
package generic.processing.framework.subsystem.test;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import generic.processing.framework.subsystem.ISubsystem;

/**
 * @author Steve
 *
 */
public class TestSubsystemInitiate {

	/**
	 * 
	 */
	ApplicationContext applicationContext;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		applicationContext =new ClassPathXmlApplicationContext("applicationContextTest.xml");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		applicationContext = null;
	}

	@Test
	public void test() {
		ISubsystem requiredSubsybtem = null;
		try {		
		    Map<String, ISubsystem> subsystemMap = applicationContext.getBeansOfType(ISubsystem.class);
	        for (ISubsystem subsystem : subsystemMap.values()) {
	        	requiredSubsybtem = subsystem;
	            subsystem.initiate();
	        }		
		} catch(Exception e) {
			
		}
        assertTrue((null != requiredSubsybtem));
	}

}
