/**
 * 
 */
package generic.processing.framework.data.jpa;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import generic.processing.framework.data.IGenericDAO;

/**
 * @author UP779462
 *
 */
@Component
public class GenericDAO implements IGenericDAO {
	
	/**
	 * The Logger...
	 */
	static Logger logger = Logger.getLogger(GenericDAO.class);

	/**
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;	

	/**
	 * 
	 */
	public GenericDAO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#get(java.lang.String, int)
	 */
	public Object get(String entityName, int id) {
		try {
			logger.info("GenericDAO : GET : " + entityName + "[" + id + "]");
			Session session = this.sessionFactory.openSession();
			logger.info("Session : " + session);
			Transaction tx = session.beginTransaction();
			logger.info("Transaction : " + tx);
			Object o = session.get(entityName, id);
			logger.info("Object : " + o);
			tx.commit();
			session.close();		
			return o;
		} catch(Exception e) {
			logger.info("Exception : " + e.getMessage());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#getAll(java.lang.String)
	 */
	public List<Object> getAll(String entityName) {
		logger.info("GenericDAO : LISTALL : " + entityName + "]");		
		Session session = this.sessionFactory.openSession();
		List<Object> objectList = session.createQuery("from " + entityName).list();
		session.close();
		return objectList;
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#add(java.lang.Object)
	 */
	public void add(Object o) {
		logger.info("GenericDAO : ADD : " + o + "]");		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(o);
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#update(java.lang.Object)
	 */
	public void update(Object o) {
		logger.info("GenericDAO : UPDATE : " + o + "]");		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(o);
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#delete(java.lang.Object)
	 */
	public void delete(Object o) {
		logger.info("GenericDAO : DELETE : " + o + "]");		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(o);
		tx.commit();
		session.close();
	}
	
	/* (non-Javadoc)
	 * @see generic.processing.framework.data.IGenericDAO#delete(java.lang.Object)
	 */
	public List<Object> query(String entityName, String whereName, int id) {
		logger.info("GenericDAO : QUERY : [" + entityName + "] WHERE : [" + whereName + "] ID : [" + id + "]" );		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Object> objectList = session.createQuery("from " + entityName + " " + "where " + whereName + " = " + id).list();
		tx.commit();
		session.close();
		return objectList;
	}	

}
