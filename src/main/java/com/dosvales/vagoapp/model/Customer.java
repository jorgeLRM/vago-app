package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customer")
@NamedEntityGraph(name = "graph.Customer.productionOrders",
		attributeNodes = @NamedAttributeNode("productionOrders"))
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String telephone;
	
	private String email;
	
	private String observations;
	
	@Embedded
	private Address address;

	@OneToMany(mappedBy = "customer")
	private List<ProductionOrder> productionOrders;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<ProductionOrder> getProductionOrders() {
		return productionOrders;
	}

	public void setProductionOrders(List<ProductionOrder> productionOrders) {
		this.productionOrders = productionOrders;
	}	
}