package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@NamedEntityGraph(name = "graph.Product.productInputs",
		attributeNodes = @NamedAttributeNode("productInputs"))
public class Product extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private Double salePrice;

	@NotNull
	private Double productionCost;

	@NotNull
	private String trademark;

	@NotNull
	private Integer capacity;

	@Enumerated(EnumType.STRING)
	private MezcalCategory mezcalCategory;

	@Enumerated(EnumType.STRING)
	private MezcalClass mezcalClass;

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

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public MezcalCategory getMezcalCategory() {
		return mezcalCategory;
	}

	public void setMezcalCategory(MezcalCategory mezcalCategory) {
		this.mezcalCategory = mezcalCategory;
	}

	public MezcalClass getMezcalClass() {
		return mezcalClass;
	}

	public void setMezcalClass(MezcalClass mezcalClass) {
		this.mezcalClass = mezcalClass;
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