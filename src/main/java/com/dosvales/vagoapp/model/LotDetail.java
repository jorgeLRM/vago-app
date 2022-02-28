package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lotdetail")
public class LotDetail extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer sequence;
	
	private LocalDate registrationDate;
	
	@ManyToOne
	@JoinColumn(name="idPalenque")
	private Palenque palenque;
	
	@OneToOne(mappedBy="lotDetail")
	private Cutting cutting;
	
	@OneToOne(mappedBy="lotDetail")
	private Production production;

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Palenque getPalenque() {
		return palenque;
	}

	public void setPalenque(Palenque palenque) {
		this.palenque = palenque;
	}

	public Cutting getCutting() {
		return cutting;
	}

	public void setCutting(Cutting cutting) {
		this.cutting = cutting;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}
}
