package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "rowcalculation")
public class RowCalculation extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Transient
	private Double percentage;

	private String name;

	private Integer volume = 0;

	private Double alcohol = 0d;

	private Double methanol = 0d;

	private Double higherAlcohols = 0d;

	private Double aldehydes = 0d;

	private Double furfurol = 0d;

	private Double plomo = 0d;

	private String observations;
	
	@ManyToOne
	@JoinColumn(name = "idCalculation")
	private Calculation calculation;

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(Double alcohol) {
		this.alcohol = alcohol;
	}

	public Double getMethanol() {
		return methanol;
	}

	public void setMethanol(Double methanol) {
		this.methanol = methanol;
	}

	public Double getHigherAlcohols() {
		return higherAlcohols;
	}

	public void setHigherAlcohols(Double higherAlcohols) {
		this.higherAlcohols = higherAlcohols;
	}

	public Double getAldehydes() {
		return aldehydes;
	}

	public void setAldehydes(Double aldehydes) {
		this.aldehydes = aldehydes;
	}

	public Double getFurfurol() {
		return furfurol;
	}

	public void setFurfurol(Double furfurol) {
		this.furfurol = furfurol;
	}

	public Double getPlomo() {
		return plomo;
	}

	public void setPlomo(Double plomo) {
		this.plomo = plomo;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Calculation getCalculation() {
		return calculation;
	}

	public void setCalculation(Calculation calculation) {
		this.calculation = calculation;
	}
}