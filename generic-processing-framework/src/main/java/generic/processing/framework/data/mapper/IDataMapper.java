/**
 * 
 */
package generic.processing.framework.data.mapper;

/**
 * This provides the ability for the implementation of
 * any style of mapper
 * <br>
 * It is envisioned that this will take the role of
 * mapping between;
 * <ul>
 * 		<li>DTO to DAO mapping</li>
 * 		<li>DAO to DTO mapping</li>
 * </ul>
 * <br>
 * However, it could be any other type of mapping required 
 * implemented slightly differently but, coforming to this
 * interface blueprint
 * 
 * @author UP779462
 *
 */
public interface IDataMapper {
	
	/**
	 * Map from one object to another
	 * 
	 * @param mapFrom
	 * @return
	 * @throws DataMappingException
	 */
	Object mapObject(Object mapFrom) throws DataMappingException;

}
