package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="input")
public class Input extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String name;
	
	private String specifications;
	
	private Integer stock = 0;
	
	private Integer minimumStock;
	
	private Integer maximumStock;
	
	private String unit;
	
	@ManyToOne
	@JoinColumn(name="idCategory")
	private InputCategory category;
	
	@OneToMany(mappedBy="input", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ProviderInput> providerInputs;
	
	public StockStatus getStockStatus() {
		StockStatus stockStatus = null;
		if (stock > maximumStock) {
			stockStatus = StockStatus.INSTOCK;
		} else if (stock >= minimumStock && stock <= maximumStock) {
			stockStatus = StockStatus.LOWSTOCK;
		} else {
			stockStatus = StockStatus.OUTOFSTOCK;
		}
		return stockStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(Integer minimumStock) {
		this.minimumStock = minimumStock;
	}

	public Integer getMaximumStock() {
		return maximumStock;
	}

	public void setMaximumStock(Integer maximumStock) {
		this.maximumStock = maximumStock;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public InputCategory getCategory() {
		return category;
	}

	public void setCategory(InputCategory category) {
		this.category = category;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}

	public void setProviderInputs(List<ProviderInput> providerInputs) {
		this.providerInputs = providerInputs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
}
