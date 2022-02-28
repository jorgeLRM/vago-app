package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tail")
public class Tail extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private LocalDate startRipening;
	
	private LocalDate endRipening;
	
	private Integer volumeWaterTail;
	
	private Double alcoholicGradeWaterTail;
	
	private Integer volumeWater;
	
	private Integer volumeMezcal;
	
	private Double alcoholicGradeMezcal;
	
	@OneToOne
	@JoinColumn(name="idStandardProduction")
	private StandardProduction standardProduction;
	
	public LocalDate getStartRipening() {
		return startRipening;
	}

	public void setStartRipening(LocalDate startRipening) {
		this.startRipening = startRipening;
	}

	public LocalDate getEndRipening() {
		return endRipening;
	}

	public void setEndRipening(LocalDate endRipening) {
		this.endRipening = endRipening;
	}

	public Integer getVolumeWaterTail() {
		return volumeWaterTail;
	}

	public void setVolumeWaterTail(Integer volumeWaterTail) {
		this.volumeWaterTail = volumeWaterTail;
	}

	public Double getAlcoholicGradeWaterTail() {
		return alcoholicGradeWaterTail;
	}

	public void setAlcoholicGradeWaterTail(Double alcoholicGradeWaterTail) {
		this.alcoholicGradeWaterTail = alcoholicGradeWaterTail;
	}

	public Integer getVolumeWater() {
		return volumeWater;
	}

	public void setVolumeWater(Integer volumeWater) {
		this.volumeWater = volumeWater;
	}

	public Integer getVolumeMezcal() {
		return volumeMezcal;
	}

	public void setVolumeMezcal(Integer volumeMezcal) {
		this.volumeMezcal = volumeMezcal;
	}

	public Double getAlcoholicGradeMezcal() {
		return alcoholicGradeMezcal;
	}

	public void setAlcoholicGradeMezcal(Double alcoholicGradeMezcal) {
		this.alcoholicGradeMezcal = alcoholicGradeMezcal;
	}

	public StandardProduction getStandardProduction() {
		return standardProduction;
	}

	public void setStandardProduction(StandardProduction standardProduction) {
		this.standardProduction = standardProduction;
	}
}
