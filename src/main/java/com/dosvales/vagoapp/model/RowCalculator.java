package com.dosvales.vagoapp.model;

public class RowCalculator {
	private Double percentage = 0d;
	private String name;
	private Integer volume = 0;
	private Double alcohol = 0d;
	private Double methanol = 0d;
	private Double higherAlcohols = 0d;
	private Double aldehydes =0d;
	private Double furfurol =0d;
	private Double lead = 0d;
	private String observations;
	
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
	public Double getLead() {
		return lead;
	}
	public void setLead(Double lead) {
		this.lead = lead;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
}