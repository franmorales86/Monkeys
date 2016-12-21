package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.CustomerDTO;
import oauth.OAuthHelper;
import repositories.CustomerRepository;

/**
 * Controller to manage all request associated with the customers
 * @author "fjmorales"
 *
 */
@Path("/customer")
public class CustomerController {

	private CustomerRepository customerRepository = new CustomerRepository();
	
	private OAuthHelper oAuthHelper = new OAuthHelper();
	

	/**
	 * Method to obtain the list of current customers
	 * @return
	 * @throws AppException 
	 * @throws Exception 
	 */
	@GET
	@Produces("application/json")
	public List<CustomerDTO> courses(@Context HttpServletRequest request) throws AppException {
		oAuthHelper.Authenticate(request);
		
        List<CustomerDTO> courseList = new ArrayList<CustomerDTO>();
		courseList = customerRepository.GetAllCustomers();
		return courseList;
	}

	/**
	 * Method to obtain a customer with a specific id
	 * @param id
	 * @return
	 * @throws AppException
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDTO getCustomerById(@Context HttpServletRequest request, @PathParam("id") int id) throws AppException {
		oAuthHelper.Authenticate(request);
		
		CustomerDTO result = customerRepository.getCustomerById(id);
		return result;
	}

	/**
	 * Method to obtain a list of customers with a specific name
	 * @param name
	 * @return
	 * @throws AppException
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> getCustomerByName(@Context HttpServletRequest request, @PathParam("name") String name) throws AppException {
		oAuthHelper.Authenticate(request);
		
		List<CustomerDTO> result = customerRepository.getCustomerByName(name);
		return result;
	}
	
	/**
	 * Method to create a new customer
	 * @param customer
	 * @return
	 * @throws AppException 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDTO createCustomer(@Context HttpServletRequest request, CustomerDTO customer) throws AppException {
		oAuthHelper.Authenticate(request);
		
		return customerRepository.addCustomer(customer);
	}
	
	/**
	 * Method to update the information of a customer
	 * @param customer
	 * @return
	 * @throws AppException
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDTO updateCustomer(@Context HttpServletRequest request, CustomerDTO customer) throws AppException {
		oAuthHelper.Authenticate(request);
		
		return customerRepository.updateCustomer(customer);
	}
	
	/**
	 * Method to delete a customer by Id
	 * @param id
	 * @throws AppException
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCustomer(@Context HttpServletRequest request, @PathParam("id") int id) throws AppException {
		oAuthHelper.Authenticate(request);
		
		customerRepository.deleteCustomer(id);
	}
}
