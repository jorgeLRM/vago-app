package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="transfer")
public class Transfer extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String numTransfer;
	
	private String document;
	
	private LocalDate transferDate;
	
	private String destination;
	
	@OneToOne
	@JoinColumn(name="idProduction")
	private Production production;

	public String getNumTransfer() {
		return numTransfer;
	}

	public void setNumTransfer(String numTransfer) {
		this.numTransfer = numTransfer;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}
}
