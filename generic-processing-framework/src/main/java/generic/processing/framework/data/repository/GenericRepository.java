/**
 * 
 */
package generic.processing.framework.data.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import generic.processing.framework.data.IGenericDAO;
import generic.processing.framework.data.IGenericRepository;


/**
 * Concrete implementation of a repository to retrieve 
 * data from a Data Store (perhaps a RDBMS)
 * 
 * @author UP779462
 *
 */
@Component
public class GenericRepository implements IGenericRepository {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(GenericRepository.class);
	
	/**
	 * the DAO implementation
	 */
	@Autowired	
	private IGenericDAO dao;

	/**
	 * 
	 */
	public GenericRepository() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#get(java.lang.String, int)
	 */
	public Object get(String entityName, int keyId) {
		logger.info("GenericRepository : GET : " + entityName  + "[" + keyId + "]");
		return dao.get(entityName, keyId);
	}	

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#get(java.lang.String)
	 */
	public List<Object> get(String entityName) {
		return dao.getAll(entityName);
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#add(java.lang.Object)
	 */
	public void add(Object object) {
		logger.info("GenericRepository : ADD : [" + object+ "]");		
		dao.add(object);
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#logicalDelete(java.lang.Object)
	 */
	public void logicalDelete(Object object) {
		logger.info("GenericRepository : DELETE : [" + object+ "]");		
		dao.delete(object);
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#update(java.lang.Object)
	 */
	public void update(Object object) {
		logger.info("GenericRepository : UPDATE : [" + object+ "]");		
		dao.update(object);
	}
	
	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericRepository#update(java.lang.Object)
	 */
	public List<Object> query(String entityName, String whereName, int id) {
		logger.info("GenericRepository : QUERY : [" + entityName  + "] WHERE : [" + whereName + "] ID : [" + id + "]");
		return dao.query(entityName, whereName, id);
	}	
	
	/**
	 * 
	 * @param dao
	 */
	public void setGenericDAO(IGenericDAO dao) {
		this.dao = dao;
	}
	
}
