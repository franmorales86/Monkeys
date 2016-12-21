package model;

import java.io.Serializable;
import java.util.List;

public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = -8909964107066077361L;

	private int id;

	private String name;

	private String surname;

	private String dni;

	private Byte[] image;

	private List<ProductDTO> products;

	public CustomerDTO() {
	}

	public CustomerDTO(int id, String name, String surname, String dni,
			Byte[] image, List<ProductDTO> products) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.image = image;
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}

}
