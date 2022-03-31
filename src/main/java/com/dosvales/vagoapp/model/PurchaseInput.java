package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="purchaseinput")
public class PurchaseInput extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer amount;
	
	private Integer totalPrice;
	
	private String invoice;
	
	private LocalDate dateOfAdmission;
	
	private String observations;
	
	private Double costPerParcel;
	
	@ManyToOne
	@JoinColumn(name="idProvider")
	private Provider provider;
	
	@ManyToOne
	@JoinColumn(name="idInput")
	private Input input;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public LocalDate getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(LocalDate dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Double getCostPerParcel() {
		return costPerParcel;
	}

	public void setCostPerParcel(Double costPerParcel) {
		this.costPerParcel = costPerParcel;
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
