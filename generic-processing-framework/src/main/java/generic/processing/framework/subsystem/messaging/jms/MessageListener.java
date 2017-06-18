package generic.processing.framework.subsystem.messaging.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.Message;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Component;

import generic.processing.framework.serialisation.ISerialise;
import generic.processing.framework.service.chain.IServiceChain;
import generic.processing.framework.subsystem.messaging.IMessageProducer;
import generic.processing.framework.subsystem.messaging.IMessageRouter;
import generic.processing.framework.subsystem.messaging.MessageSenderTemplate;

/**
 * Class for handling the consumption of JMS messages from within the 
 * Generic Architecture Framework.
 * <br>
 * It also handles the executino of a defined ServiceChain that is configured
 * through the associated Spring configuration that MUST be supplied 
 * 
 * @author UP779462
 *
 */
@Component
public class MessageListener implements javax.jms.MessageListener {

	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(MessageListener.class);

	/**
	 * 
	 */
	private ISerialise deserialiser;

	/**
	 * 
	 */
	private IServiceChain serviceChain;
	
	/**
	 * 
	 */
	private IMessageRouter messageRouter;
	
	/**
	 * 
	 */
	private IMessageProducer messageProducer;

	/**
	 * Implementation of <code>MessageListener</code>.
	 */
	public void onMessage(Message message) {
		try {
			long start = System.currentTimeMillis();
			logger.debug("We are receiving a message...");
			Object businessInput = message;
			if (null != this.deserialiser) {
				businessInput = deserialiser.deserialise(businessInput);
			}
			Object businessOutput = businessInput;
			List<Object> responseList = null;			
			serviceChain.runChain(businessInput, responseList);
			logger.debug("About to get Routes...");
			List<String> routes = messageRouter.getRoutes(serviceChain, responseList);
			logger.info("routes to send message to : " + routes.size());
			if(routes.size() > 0) {
				logger.debug("sending message...");
				Iterator<String> iter = routes.iterator();
				while(iter.hasNext()) {
					MessageSenderTemplate mst = new MessageSenderTemplate();
					mst.setDestination(iter.next());
					mst.setMessage(businessOutput);
					messageProducer.sendMessage(mst);
				}
			}
			long end = System.currentTimeMillis();
			logger.info("Message processing for " + serviceChain + " completed in " + (end-start) + "ms");			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * getRoutes
	 */
	private List<String> getRoutes(IServiceChain serviceChain) {
		long start = System.currentTimeMillis();
		List<String> routes = new ArrayList<String>();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		String ruleFile = "rules/routes.drl";
		logger.debug("Loading file: " + ruleFile);
		kbuilder.add(ResourceFactory.newClassPathResource(ruleFile, MessageListener.class), ResourceType.DRL);
        if ( kbuilder.hasErrors() ) {
            System.out.println( kbuilder.getErrors().toString() );
            throw new RuntimeException( "Unable to compile \"HelloWorld.drl\"." );
        }		
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		logger.debug("setting globals...");
		ksession.setGlobal("list", new ArrayList<String>() );
/*		
        logger.debug("Set up debugging...");		
        ksession.addEventListener( new DebugAgendaEventListener() );
        ksession.addEventListener( new DebugWorkingMemoryEventListener() );
*/        
		logger.debug("inserting facts...");        
		ksession.insert(serviceChain);		
		logger.debug("firing them their rules...");
		ksession.fireAllRules();
		routes = (List<String>)ksession.getGlobal("list");
		ksession.dispose();
		logger.debug("routes : " + routes.size());
		long end = System.currentTimeMillis();
		Iterator<String> iter = routes.iterator();
		while(iter.hasNext()) {
			logger.debug("Route : " + iter.next());	
		}
		logger.debug("Routes determined in " + (end-start) + "ms");
		return routes;
	}

	/**
	 * 
	 * @param deserialiser
	 */
	public void setDeserialiser(ISerialise deserialiser) {
		this.deserialiser = deserialiser;
	}

	/**
	 * 
	 * @param serviceChain
	 */
	public void setServiceChain(IServiceChain serviceChain) {
		this.serviceChain = serviceChain;
	}
	
	/**
	 * 
	 * @param messageRouter
	 */
	public void setMessageRouter(IMessageRouter messageRouter) {
		this.messageRouter = messageRouter;
	}
	
	
	/**
	 * 
	 * @param jmsMessageProducer
	 */
	public void setMessageProducer(IMessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

}
