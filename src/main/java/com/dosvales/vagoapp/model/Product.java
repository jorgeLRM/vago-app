package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private Double salePrice;

	@NotNull
	private Double productionCost;

	@ManyToOne
	@JoinColumn(name = "idCategory")
	private ProductCategory category;

	@OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ProductInput> productInputs;
	
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

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getProductionCost() {
		return productionCost;
	}

	public void setProductionCost(Double productionCost) {
		this.productionCost = productionCost;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public List<ProductInput> getProductInputs() {
		return productInputs;
	}

	public void setProductInputs(List<ProductInput> productInputs) {
		this.productInputs = productInputs;
	}	
}