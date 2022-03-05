package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="plantation")
@NamedEntityGraph(name = "graph.Plantation.cuttingDetails", 
attributeNodes = @NamedAttributeNode("cuttingDetails"))
public class Plantation extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Transient
	final int OPTIMAL_QUANTITY = 200;
	
	@NotNull
	private String lot;
	
	private Integer stock = 0;
	
	private Integer initialStock = 0;
	
	private LocalDate plantingDate;
	
	private LocalDate registrationDate;
	
	private boolean unknownSpecies;
	
	private String observations;
	
	@ManyToOne
	@JoinColumn(name = "idEstate")
	private Estate estate;
	
	@ManyToOne
	@JoinColumn(name = "idMaguey")
	private Maguey maguey;

	@OneToMany(mappedBy="plantation")
	private List<CuttingDetail> cuttingDetails = new ArrayList<CuttingDetail>();
	
	public StockStatus getStockStatus() {
		if (stock == 0) {
			return StockStatus.OUTOFSTOCK;
		} else if (stock > OPTIMAL_QUANTITY) {
			return StockStatus.INSTOCK;
		}
		return StockStatus.LOWSTOCK;
	}
	
	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getInitialStock() {
		return initialStock;
	}

	public void setInitialStock(Integer initialStock) {
		this.initialStock = initialStock;
	}

	public LocalDate getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(LocalDate plantingDate) {
		this.plantingDate = plantingDate;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isUnknownSpecies() {
		return unknownSpecies;
	}

	public void setUnknownSpecies(boolean unknownSpecies) {
		this.unknownSpecies = unknownSpecies;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	public Maguey getMaguey() {
		return maguey;
	}

	public void setMaguey(Maguey maguey) {
		this.maguey = maguey;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}

	public void setCuttingDetails(List<CuttingDetail> cuttingDetails) {
		this.cuttingDetails = cuttingDetails;
	}
}
