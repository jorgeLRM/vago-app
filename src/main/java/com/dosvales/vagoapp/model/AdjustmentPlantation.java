package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "adjustmentplantation")
public class AdjustmentPlantation extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer quantity;

	@NotNull
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private MovementTypePlantation movementType;

	@ManyToOne
	@JoinColumn(name = "idPlantation")
	private Plantation plantation;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public MovementTypePlantation getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementTypePlantation movementType) {
		this.movementType = movementType;
	}

	public Plantation getPlantation() {
		return plantation;
	}

	public void setPlantation(Plantation plantation) {
		this.plantation = plantation;
	}
}