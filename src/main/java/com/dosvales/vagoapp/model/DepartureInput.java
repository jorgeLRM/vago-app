package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "departureinput")
public class DepartureInput extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer amount;

	@NotNull
	private Double totalPrice;

	@NotNull
	private String note;

	@NotNull
	private LocalDate dateOfDeparture;
	
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
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
}