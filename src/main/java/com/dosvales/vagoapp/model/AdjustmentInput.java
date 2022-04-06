package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "adjustmentinput")
public class AdjustmentInput extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer amount;
	
	@NotNull
	private Double totalPrice;

	@NotNull
	private LocalDate dateOfAdjustment;

	@Enumerated(EnumType.STRING)
	private TypeAdjustment typeAdjustment;
	
	private String reason;
	
	private String observations;
	
	@ManyToOne
	@JoinColumn(name = "idInput")
	private Input input;
	
	@ManyToOne
	@JoinColumn(name = "idProvider")
	private Provider provider;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDate getDateOfAdjustment() {
		return dateOfAdjustment;
	}

	public void setDateOfAdjustment(LocalDate dateOfAdjustment) {
		this.dateOfAdjustment = dateOfAdjustment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public TypeAdjustment getTypeAdjustment() {
		return typeAdjustment;
	}

	public void setTypeAdjustment(TypeAdjustment typeAdjustment) {
		this.typeAdjustment = typeAdjustment;
	}
}