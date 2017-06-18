/**
 * 
 */
package generic.processing.framework.subsystem.http.tomcat;


import java.io.File;
import java.net.URISyntaxException;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import generic.processing.framework.subsystem.ISubsystem;
import generic.processing.framework.subsystem.SubsystemException;
import generic.processing.framework.subsystem.http.rest.configuration.GenericConfiguration;

/**
 * This is the sub system that bootstraps the Tomcat Servlet 
 * Container Engine and all that this entails to enable HTTP 
 * connectivity to occur.
 * <br>
 * It is envisioned that both REST and SOAP traffic can be 
 * handled through this capability.
 * <br>
 * The process would be to create a WAR file as today but, to 
 * then "tell" this process about it so that it can load it up  
 * 
 * @author UP779462
 * 
 */
@ComponentScan(basePackages = "generic.processing.framework.subsystem.http")
public class HttpHandler implements ISubsystem {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(HttpHandler.class);
	
	/**
	 * Spring Context Scan path
	 */
	private String contextScanPath = "generic.processing.framework.subsystem.http";
	
	/**
	 * Port to run this TOMCAT instance on
	 */
	private int port = 8080;	// Default port value
	
	/**
	 * Default Constructor
	 */
	public HttpHandler() {
		// TO DO : Nothing much I do not think...
	}

	/**
	 * Creates the Embedded Tomcat Server ...
	 * <p>
	 * and uses the incoming port to create 
	 * the business of being in business 
	 * 
	 * @param port
	 */
	public HttpHandler(int port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @param applicationContextFile
	 * @param port
	 */
	public HttpHandler(String contextScanPath, int port) {
		this.contextScanPath = contextScanPath;
		this.port = port;
	}
	
	/**
	 * The initiation of the Tomcat HTTP subsystem.  This will load the annoated Spring MVC 4
	 * classes that will enable REST communication to oc
	 * 
	 * @see generic.processing.framework.subsystem.ISubsystem#initiate()
	 */
	public void initiate() throws SubsystemException {
		logger.info("Initiating the HTTP WebContainer Subsystem...");
		try{
			Tomcat tomcat = new Tomcat();
			tomcat.setPort(this.port);
			File base = new File("");
			logger.info(base.getAbsolutePath());
			Context rootCtx = tomcat.addContext("", base.getAbsolutePath());
			AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
			ctx.scan(this.contextScanPath);
			ctx.register(GenericConfiguration.class);
			DispatcherServlet dispatcher = new DispatcherServlet(ctx);
			Tomcat.addServlet(rootCtx, "SpringMVC", dispatcher);
			rootCtx.addServletMapping("/*", "SpringMVC");
			logger.info("Configured Dispatcher Serlvet...");
			Tomcat.initWebappDefaults(rootCtx);
			logger.info("Configured WebApp defaults...");
			logger.info("starting tomcat...");
			tomcat.start();
			logger.info("started tomcat!");
			tomcat.getServer().await();
		} catch(Exception e) {
			throw new SubsystemException(e);
		}
	}
	
	/**
	 * Overrides the default port of 8080 if needed 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @param contextScanPath
	 */
	public void setContextScanPath(String contextScanPath) {
		this.contextScanPath = contextScanPath;
	}
	
	
}
