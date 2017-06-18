/**
 * 
 */
package generic.processing.framework.service.chain;

import java.util.List;

import generic.processing.framework.service.IService;

/*
 * Abstract parent class.  Exists to implement the actions of 
 * stopping the chain execution if an error has been generated
 * on the concrete chain implementation.
 * 
 * @author UP779462
 *
 */
public abstract class AbstractServiceChain implements IServiceChain {

	/**
	 * Used to stop execution between services in a chain
	 */
	protected boolean stopChainOnError;
	
	/**
	 * the name of the service chain
	 */
	protected String name;
	
	/**
	 * 
	 */
	protected List<IService> serviceChain;	

	/**
	 * 
	 * @param stopChainOnError
	 */
	public void setStopChainOnError(boolean stopChainOnError) {
		this.stopChainOnError = stopChainOnError;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getStopChainOnError() {
		return this.stopChainOnError;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the serviceChain
	 */
	public List<IService> getServiceChain() {
		return serviceChain;
	}

	/**
	 * @param serviceChain the serviceChain to set
	 */
	public void setServiceChain(List<IService> serviceChain) {
		this.serviceChain = serviceChain;
	}
	
	/**
	 * Used in various logging type needs
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
}
