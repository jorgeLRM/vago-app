package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productcategory")
@NamedEntityGraph(name = "graph.ProductCategory.products",
		attributeNodes = @NamedAttributeNode("products"))
public class ProductCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	private String description;

	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	@ManyToOne
	@JoinColumn(name = "idProducer")
	private Producer producer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}
}