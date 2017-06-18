/**
 * 
 */
package generic.processing.framework.subsystem.http.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This is the generic configuation for this 
 * framework
 * 
 * @author UP779462
 *
 */
@Configuration
@ImportResource("classpath:applicationSubsystem-http.xml")
@EnableWebMvc
@ComponentScan(basePackages = "generic.processing.framework.subsystem.http")
public class GenericConfiguration {

	/**
	 * 
	 */
	public GenericConfiguration() {
		System.out.println("We are initialising the GenericConfiguration stuff...");
	}
}
