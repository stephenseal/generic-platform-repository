/**
 * 
 */
package generic.processing.framework.data;

import java.util.List;

/**
 * To provide the blueprint for expanding beyond the 
 * basic DAO pattern implementation 
 * 
 * @author UP779462
 *
 */
public interface IGenericRepository {
	
	/**
	 * Interface method to allow a concrete implementation to retrieve a single entity 
	 * from a Data Store (perhaps RDBMS)
	 * 
	 * @param entityName
	 * @param keyId
	 * @return Object
	 */
	Object get(String entityName, int keyId);
	
	/**
	 * Interface method to allow a concrete implementation to 
	 * retrieve a list of items of a given entity type from a Data Store
	 * (perhaps RDBMS)
	 * 
	 * @param entityName
	 * @return
	 */
	List<Object> get(String entityName);
	
	/**
	 * Interface method to allow a concrete implementation to add an entity 
	 * into a Data Store (perhaps RDBMS)
	 * 
	 * @param object
	 */
  void add(Object object);
  
  /**
   * Interface method to allow a concrete implementation to logically
   * delete an entity from a Data Store (perhaps RDBMS)
   * 
   * @param object
   */
  void logicalDelete(Object object);
  
  /**
   * Interface method to allow a concrete implementation to update an entity
   * in a Data Store (perhaps a RDBMS)
   * 
   * @param object
   */
  void update(Object object);
  
  /**
   * Interface method to allow a concrete implementation to update an entity
   * in a Data Store (perhaps a RDBMS)
   * 
   * @param object
   */
  List<Object>query(String entityName, String whereName, int id);  

}
