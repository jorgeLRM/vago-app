package com.dosvales.vagoapp.model;

import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="formulation")
public class Formulation extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private LocalDate gridingDate;
	
	private LocalDate formulationDate;
	
	private LocalDate destilationDate;
	
	private Integer firstVacuum;
	
	private Integer secondVaccum;
	
	private Integer ordinary;
	
	private Integer initialBrix;
	
	private Integer ultimateBrix;
	
	@Transient
	private Double volumeJuiceBagasse;
	
	@Transient
	private Double volumeWater;
	
	@Transient
	private Double volumeFermentedWort;
	
	@Transient
	private Integer fermentationDays;
	
	@ManyToOne
	@JoinColumn (name = "idTub")
	private Tub tub;
	
	@ManyToOne
	@JoinColumn (name = "idProduction")
	private Production production;
	
	public LocalDate getGridingDate() {
		return gridingDate;
	}

	public void setGridingDate(LocalDate gridingDate) {
		this.gridingDate = gridingDate;
	}

	public LocalDate getFormulationDate() {
		return formulationDate;
	}

	public void setFormulationDate(LocalDate formulationDate) {
		this.formulationDate = formulationDate;
	}

	public LocalDate getDestilationDate() {
		return destilationDate;
	}

	public void setDestilationDate(LocalDate destilationDate) {
		this.destilationDate = destilationDate;
	}

	public Integer getFirstVacuum() {
		return firstVacuum;
	}

	public void setFirstVacuum(Integer firstVacuum) {
		this.firstVacuum = firstVacuum;
	}

	public Integer getSecondVaccum() {
		return secondVaccum;
	}

	public void setSecondVaccum(Integer secondVaccum) {
		this.secondVaccum = secondVaccum;
	}

	public Integer getOrdinary() {
		return ordinary;
	}

	public void setOrdinary(Integer ordinary) {
		this.ordinary = ordinary;
	}
	
	public Integer getInitialBrix() {
		return initialBrix;
	}

	public void setInitialBrix(Integer initialBrix) {
		this.initialBrix = initialBrix;
	}

	public Integer getUltimateBrix() {
		return ultimateBrix;
	}

	public void setUltimateBrix(Integer ultimateBrix) {
		this.ultimateBrix = ultimateBrix;
	}

	public Tub getTub() {
		return tub;
	}

	public void setTub(Tub tub) {
		this.tub = tub;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Double getVolumeJuiceBagasse() {
		volumeJuiceBagasse = (tub.getAverageHeight() - firstVacuum) * tub.getFactor();
		return volumeJuiceBagasse;
	}

	public Double getVolumeWater() {
		volumeWater = getVolumeFermentedWort() - getVolumeJuiceBagasse();
		return volumeWater;
	}

	public Double getVolumeFermentedWort() {
		volumeFermentedWort = (tub.getAverageHeight() - secondVaccum) * tub.getFactor();
		return volumeFermentedWort;
	}

	public Integer getFermentationDays() {
		if (gridingDate != null && destilationDate != null)
			fermentationDays = Math.toIntExact(Duration.between(gridingDate.atStartOfDay(), destilationDate.atStartOfDay()).toDays());
		return fermentationDays;
	}
}
