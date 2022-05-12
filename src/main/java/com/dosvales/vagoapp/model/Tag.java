package com.dosvales.vagoapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tag")
public class Tag extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private LocalDate dateMovement;

	@NotNull
	private Long minNumber;

	@NotNull
	private Long maxNumber;

	@Enumerated(EnumType.STRING)
	private TypeMovement typeMovement;

	@NotNull
	private String responsible;

	private String observations;

	@OneToOne(mappedBy = "tag")
	private ProductionOrderDetail productionOrderDetail;

	public LocalDate getDateMovement() {
		return dateMovement;
	}

	public void setDateMovement(LocalDate dateMovement) {
		this.dateMovement = dateMovement;
	}

	public Long getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Long minNumber) {
		this.minNumber = minNumber;
	}

	public Long getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Long maxNumber) {
		this.maxNumber = maxNumber;
	}

	public TypeMovement getTypeMovement() {
		return typeMovement;
	}

	public void setTypeMovement(TypeMovement typeMovement) {
		this.typeMovement = typeMovement;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public ProductionOrderDetail getProductionOrderDetail() {
		return productionOrderDetail;
	}

	public void setProductionOrderDetail(ProductionOrderDetail productionOrderDetail) {
		this.productionOrderDetail = productionOrderDetail;
	}
}