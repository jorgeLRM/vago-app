package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mixture")
public class Mixture extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Double volume = 1.0;
	
	@ManyToOne
	@JoinColumn(name="idBaseProduction")
	private Production baseProduction;
	
	@ManyToOne
	@JoinColumn(name="idComplementaryProduction")
	private Production complementaryProduction;

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Production getBaseProduction() {
		return baseProduction;
	}

	public void setBaseProduction(Production baseProduction) {
		this.baseProduction = baseProduction;
	}

	public Production getComplementaryProduction() {
		return complementaryProduction;
	}

	public void setComplementaryProduction(Production complementaryProduction) {
		this.complementaryProduction = complementaryProduction;
	}
}
