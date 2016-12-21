package repositories;

import java.util.ArrayList;
import java.util.List;

import model.CustomerConverter;
import model.CustomerDTO;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controller.AppException;
import entities.Customer;
import entities.Product;

public class CustomerRepository {

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
	 * Method that returns all customers in the database
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerDTO> GetAllCustomers() {
		Session session = InitSession();
		Query query = session.createQuery("from Customer");
		List<Customer> result = query.list();
		CloseSession(session);
		return CustomerConverter.fromEntityList(result);
	}
	
	/**
	 * Create a new customer
	 * @param customer
	 * @return
	 */
	public CustomerDTO addCustomer(CustomerDTO customer) {
		Session session = InitSession();
		Customer customerEntity = CustomerConverter.toEntity(customer);
        session.save(customerEntity);
        for (Product product : customerEntity.getProducts()) {
        	product.setCustomer(customerEntity);
        	session.save(product);			
		};
        CloseSession(session);        
		return CustomerConverter.fromEntity(customerEntity);
    }

	/**
	 * Delete a customer by Id
	 * @param customerId
	 * @throws AppException 
	 */
    public void deleteCustomer(int customerId) throws AppException {
    	Session session = InitSession();
    	try {
    		Customer customer = (Customer) session.load(Customer.class, new Integer(customerId));
    		for (Product product : customer.getProducts()) {
    			session.delete(product);
    		}
    		session.delete(customer);
    	} catch (HibernateException exc) {
    		throw new AppException("The customer with id " + customerId + " not exists");
    	} finally {
    		CloseSession(session);
    	}
    }

    /**
     * Update the information of one customer
     * @param customer
     * @return
     * @throws AppException 
     */
    public CustomerDTO updateCustomer(CustomerDTO customer) throws AppException {
    	Session session = InitSession();
    	Customer customerEntity = CustomerConverter.toEntity(customer);
    	
    	try {
    		session.update(customerEntity);
    		
    		for (Product product : customerEntity.getProducts()) {
    			product.setCustomer(customerEntity);
    			session.update(product);			
    		}
    		CloseSession(session);
    	} catch (HibernateException exc) {
    		throw new AppException("The customer with id " + customer.getId() + " not exists.");
    	}
    	
        return customer;
    }

    /**
     * Get the customer associated with the id parameter
     * @param customerId Id of the customer to obtain
     * @return
     * @throws AppException 
     */
    public CustomerDTO getCustomerById(int customerId) throws AppException {
        Customer customer = null;
        Session session = InitSession();

        String queryString = "from Customer where id = :id";
        Query query = session.createQuery(queryString);
        query.setInteger("id", customerId);
        
        try {
        	customer = (Customer) query.uniqueResult();
        	if (customer == null) {
        		throw new AppException("The customer with id " + customerId + " not exists.");	
        	}
        } catch (HibernateException exc) {
        	throw new AppException("The customer with id " + customerId + " not exists.");
        } finally {
        	CloseSession(session);
        }
        
        return CustomerConverter.fromEntity(customer);
    }
    
    /**
     * Get the customer associated with the name parameter
     * @param customerName Name of the customer to obtain
     * @return
     * @throws AppException 
     */
    @SuppressWarnings("unchecked")
	public List<CustomerDTO> getCustomerByName(String customerName) throws AppException {
        List<Customer> customers = new ArrayList<Customer>();
        Session session = InitSession();
		
        String queryString = "from Customer where Name = :name";
        Query query = session.createQuery(queryString);
        query.setString("name", customerName);
        
        try {
        	customers = (List<Customer>) query.list();
        	if ((customers == null) || (customers.isEmpty())) {
        		throw new AppException("The customer with name " + customerName + " not exists.");	
        	}
        } catch (HibernateException exc) {
        	throw new AppException("The customer with name " + customerName + " not exists.");
        } finally {
        	CloseSession(session);
        }
        
        return CustomerConverter.fromEntityList(customers);
    }
}
