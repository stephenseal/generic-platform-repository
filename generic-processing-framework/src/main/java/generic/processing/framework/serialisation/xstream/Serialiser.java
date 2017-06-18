/**
 * 
 */
package generic.processing.framework.serialisation.xstream;

import java.util.Iterator;
import java.util.Map;

import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import generic.processing.framework.serialisation.ISerialise;
import generic.processing.framework.serialisation.SerialisationException;

/**
 * This is an Object to XML and XML to Object serialiser using the XStream 
 * technology from ThoughWorks.
 * <br>
 * Its main use if for taking messages off of "the wire" and converting 
 * for processing OR for response to processing
 * 
 * @author UP779462
 *
 */
public class Serialiser implements ISerialise {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(Serialiser.class);
	
	/**
	 * Alias List for use in the serialisation/deserialisation
	 */
	private Map<String,Class> aliasMap;
	
	/**
	 * 
	 */
	public Object deserialise(MapMessage obj) throws SerialisationException {
		return null;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	public Object deserialise(ObjectMessage obj) throws SerialisationException {
		return null;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	public Object deserialise(StreamMessage obj) throws SerialisationException {
		return null;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	public Object deserialise(TextMessage obj) throws SerialisationException {
		try {
			SimpleMessageConverter messageConverter = new SimpleMessageConverter();
			XStream xstream = new XStream(new StaxDriver());
			if(null != aliasMap) {
				if(false == this.aliasMap.isEmpty()) {
					Iterator<String> iter = this.aliasMap.keySet().iterator();
					while(iter.hasNext()) {
						String aliasName = iter.next(); 
						Class clazz = this.aliasMap.get(aliasName);
						xstream.alias(aliasName, clazz);
					}
				}
			}
			String xmlString = (String)messageConverter.fromMessage((TextMessage)obj);
			logger.info("XML received : " + xmlString);
			return xstream.fromXML(xmlString);
		} catch(Exception e) {
			throw new SerialisationException(e);
		}

	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	public Object deserialise(Object obj) throws SerialisationException {
		try {
			if(obj instanceof TextMessage) {
				return deserialise((TextMessage)obj);
			} else if (obj instanceof MapMessage) {
				return deserialise((MapMessage)obj);
			} else if (obj instanceof ObjectMessage) {
				return deserialise((MapMessage)obj);
			} else if (obj instanceof StreamMessage) {
				return deserialise((StreamMessage)obj);
			}
		} catch(Exception e) {
			throw new SerialisationException(e);
		}
		
		/*
		 * If we get here ... Houston we have a problem!!
		 */
		return null;
	}
	
	/* (non-Javadoc)
	 * @see generic.processing.framework.serialisation.ISerialise#deserialise(java.lang.Object)
	 */
	public Object serialise(Object obj) throws SerialisationException {
		try {
			XStream xstream = new XStream(new StaxDriver());
			if(null != aliasMap) {
				if(false == this.aliasMap.isEmpty()) {
					Iterator<String> iter = this.aliasMap.keySet().iterator();
					while(iter.hasNext()) {
						String aliasName = iter.next(); 
						Class clazz = this.aliasMap.get(aliasName);
						xstream.alias(aliasName, clazz);
					}
				}
			}
			String xmlString = xstream.toXML(obj);
			logger.info("XML to send : " + xmlString);
			return xmlString;
		} catch(Exception e) {
			throw new SerialisationException(e);
		}
	}
	
	/**
	 * 
	 * @param aliasMap
	 */
	public void setAliasMap(Map<String,Class> aliasMap) {
		this.aliasMap = aliasMap;
	}

}
