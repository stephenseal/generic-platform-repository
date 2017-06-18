/**
 * 
 */
package generic.processing.framework.data;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Generic DAO object to get data from a Data Store (perhaps a RDBMS)
 * 
 * @author UP779462
 *
 */
@Component
public interface IGenericDAO {
	
	/**
	 * 
	 * @param entityName
	 * @param id
	 * @return
	 */
	public Object get(String entityName, int id);

	/**
	 * 
	 * @param entityName
	 * @return
	 */
	public List<Object> getAll(String entityName);	

	/**
	 * 
	 * @param o
	 */
	public void add(Object o);
	
	/**
	 * 
	 * @param o
	 */
	public void update(Object o);
	
	/**
	 * 
	 * @param o
	 */
	public void delete(Object o);
	
	/**
	 * 
	 * @param o
	 */
	public List<Object> query(String entityName, String whereName, int id);	

}
