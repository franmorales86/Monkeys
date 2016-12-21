package repositories;

import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controller.AppException;
import entities.User;

public class UsersRepository {

	/**
	 * Initialize Session to connect with the database
	 * @return
	 */
	private Session InitSession() {
		// Create session factory object
		SessionFactory sessionFactory = Database.getSessionFactory();
		// getting session object from session factory
		Session session = sessionFactory.openSession();
		// getting transaction object from session object
		session.beginTransaction();
		return session;
	}
	
	/**
	 * Close session and session factory with the connection of the database
	 * @param session
	 */
	private void CloseSession(Session session) {
		session.getTransaction().commit();
		session.getSessionFactory().close();
	}


    /**
     * 
     * @param token
     * @return
     * @throws AppException
     */
    public User getCustomerByToken(String token) throws AppException {
        User user = new User();
        Session session = InitSession();
		
        String queryString = "from User where Token = :token";
        Query query = session.createQuery(queryString);
        query.setString("token", token);
        
        try {
        	user = (User) query.uniqueResult();
        	if (user == null) {
        		throw new AppException("Access invalid", Response.Status.UNAUTHORIZED);	
        	}
        } catch (HibernateException exc) {
        	throw new AppException("Access invalid", Response.Status.UNAUTHORIZED);
        } finally {
        	CloseSession(session);
        }
        
        return user;
    }
}
