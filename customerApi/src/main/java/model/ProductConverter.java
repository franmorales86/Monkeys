package model;

import java.util.ArrayList;
import java.util.List;

import entities.Customer;
import entities.Product;

public class ProductConverter {

	/**
	 * Convert a entity in a DTO
	 * @param product
	 * @return
	 */
	public static ProductDTO fromEntity (Product product) {
		return new ProductDTO(product.getId(), 
				product.getName(), 
				product.getDescription(),
				product.getPrice());
	}
	
	/**
	 * Convert a list of entities in a list of DTO
	 * @param products
	 * @return
	 */
	public static List<ProductDTO> fromEntityList(List<Product> products) {
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		for (Product product : products) {
			result.add(fromEntity(product));
		}
		return result;
	}
	
	/**
	 * Convert a DTO to entity
	 * @param product
	 * @param customer
	 * @return
	 */
	public static Product toEntity(ProductDTO product, Customer customer) {
		return new Product(product.getId(), 
				product.getName(), 
				product.getDescription(), 
				product.getPrice(), 
				customer);
	}
	
	/**
	 * Convert a list of DTO into a list of entities
	 * @param products
	 * @param customer
	 * @return
	 */
	public static List<Product> toEntityList(List<ProductDTO> products, Customer customer) {
		List<Product> result = new ArrayList<Product>();
		for (ProductDTO product : products) {
			result.add(toEntity(product, customer));
		}
		return result;
	}
}
