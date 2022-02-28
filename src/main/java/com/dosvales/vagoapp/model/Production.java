package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="production")
@DiscriminatorColumn(name="typeProduction", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Production extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String lot;
	
	private Double totalVolume = 0.0;
	
	private LocalDate admissionDate;
	
	private String observations;
	
	private String location;
	
	private Integer wastage;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Enumerated(EnumType.STRING)
	protected ProductionStatus productionStatus;
	
	@OneToOne
	@JoinColumn(name="idLotDetail")
	private LotDetail lotDetail;
	
	@OneToOne(mappedBy="production")
	private Transfer transfer;
	
	@OneToMany(mappedBy="production")
	private Set<Analysis> analyzes = new HashSet<Analysis>();
	
	public abstract void nextStatus();
	
	public abstract void previousStatus();
	
	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getWastage() {
		return wastage;
	}

	public void setWastage(Integer wastage) {
		this.wastage = wastage;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LotDetail getLotDetail() {
		return lotDetail;
	}

	public void setLotDetail(LotDetail lotDetail) {
		this.lotDetail = lotDetail;
	}

	public ProductionStatus getProductionStatus() {
		return productionStatus;
	}

	public void setProductionStatus(ProductionStatus productionStatus) {
		this.productionStatus = productionStatus;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public Set<Analysis> getAnalyzes() {
		return analyzes;
	}

	public void setAnalyzes(Set<Analysis> analyzes) {
		this.analyzes = analyzes;
	}
}
