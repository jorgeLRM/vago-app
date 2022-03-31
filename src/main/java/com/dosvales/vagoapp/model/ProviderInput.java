package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="providerinput")
public class ProviderInput extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer deliveryTime;
	
	private Double unitPrice;
	
	@ManyToOne
	@JoinColumn(name="idProvider")
	private Provider provider;
	
	@ManyToOne
	@JoinColumn(name="idInput")
	private Input input;

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}
}
