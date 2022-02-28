package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cuttingdetail")
public class CuttingDetail extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer weight;
	
	private Integer numberPinneapples;
	
	@ManyToOne
	@JoinColumn(name="idPlantation")
	private Plantation plantation;
	
	@ManyToOne
	@JoinColumn(name="idCutting")
	private Cutting cutting;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getNumberPinneapples() {
		return numberPinneapples;
	}

	public void setNumberPinneapples(Integer numberPinneapples) {
		this.numberPinneapples = numberPinneapples;
	}

	public Plantation getPlantation() {
		return plantation;
	}

	public void setPlantation(Plantation plantation) {
		this.plantation = plantation;
	}

	public Cutting getCutting() {
		return cutting;
	}

	public void setCutting(Cutting cutting) {
		this.cutting = cutting;
	}
}
