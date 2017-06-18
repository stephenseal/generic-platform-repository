/**
 * 
 */
package generic.processing.framework.subsystem.http.rest.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initialises the web environment
 * 
 * @author UP779462
 *
 */
public class GenericInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 
	 */
	public GenericInitializer() {
		System.out.println("Constructing GenericInitializer...");
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
		/* TAKEN OUT FOR NOW
        return new Class[] { 
            	HibernateConfiguration.class };
          */
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
        return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
        return new String[] { "/" };
	}

}
