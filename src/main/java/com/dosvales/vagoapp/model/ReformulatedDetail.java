package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReformulatedDetail extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private Integer volume;
	
	@ManyToOne
	@JoinColumn(name="idStandardProduction")
	private StandardProduction standardProduction;
	
	@ManyToOne
	@JoinColumn(name="idReformulatedProduction")
	private ReformulatedProduction reformulatedProduction;

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public StandardProduction getStandardProduction() {
		return standardProduction;
	}

	public void setStandardProduction(StandardProduction standardProduction) {
		this.standardProduction = standardProduction;
	}

	public ReformulatedProduction getReformulatedProduction() {
		return reformulatedProduction;
	}

	public void setReformulatedProduction(ReformulatedProduction reformulatedProduction) {
		this.reformulatedProduction = reformulatedProduction;
	}
}
