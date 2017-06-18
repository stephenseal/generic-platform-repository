/**
 * 
 */
package generic.processing.framework.serialisation;

import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

/**
 * Interface for handling any serialisation activity.
 * <br>
 * This includes both marshalling and unmarshalling of data
 * protocols used within the technical channels of this architecture
 * framework 
 * 
 * @author UP779462
 *
 */
public interface ISerialise {

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object deserialise(MapMessage obj) throws SerialisationException;
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object deserialise(ObjectMessage obj) throws SerialisationException;
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object deserialise(StreamMessage obj) throws SerialisationException;
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object deserialise(TextMessage obj) throws SerialisationException;
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object deserialise(Object obj) throws SerialisationException;	
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws SerialisationException
	 */
	Object serialise(Object obj) throws SerialisationException;	

}
