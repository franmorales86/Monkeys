package model;

import java.util.ArrayList;
import java.util.List;

import entities.Customer;

public class CustomerConverter {

	/**
	 * Convert an entity in a DTO object
	 * @param customer
	 * @return
	 */
	public static CustomerDTO fromEntity (Customer customer) {
		
		return new CustomerDTO(customer.getId(), 
				customer.getName(), 
				customer.getSurname(), 
				customer.getDni(), 
				customer.getImage(), 
				ProductConverter.fromEntityList(customer.getProducts()));
	}
	
	/**
	 * Convert a list of entities in a list of DTO
	 * @param customers
	 * @return
	 */
	public static List<CustomerDTO> fromEntityList(List<Customer> customers) {
		List<CustomerDTO> result = new ArrayList<CustomerDTO>();
		for (Customer customer : customers) {
			result.add(fromEntity(customer));
		}
		return result;
	}
	
	/**
	 * Convert a DTO into entity
	 * @param customer
	 * @return
	 */
	public static Customer toEntity(CustomerDTO customer) {
		Customer result = new Customer(customer.getId(),
				customer.getName(),
				customer.getSurname(),
				customer.getDni(),
				customer.getImage(),
				null);
		
		result.setProducts(ProductConverter.toEntityList(customer.getProducts(), result));
		return result;
	}
}
