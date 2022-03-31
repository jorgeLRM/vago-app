package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="production")
public class Production extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String lot;
	
	private Double totalVolume = 0.0;
	
	private Double stock = 0.0;
	
	private LocalDate admissionDate;
	
	private String observations;
	
	private String location;
	
	private Double wastage = 0.0;
	
	private LocalDate startCoocking;

	private LocalDate endCoocking;

	private Double alcoholicGradeDist1 = 0.0;

	private Double alcoholicGradeDist2 = 0.0;

	private Double volumeDistillation2 = 0.0;
	
	@Enumerated(EnumType.STRING)
	private TypeProduction typeProduction = TypeProduction.NORMAL;
	
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
	protected List<Analysis> analyzes = new ArrayList<Analysis>();
	
	@OneToOne(mappedBy="production")
	@JoinColumn(name = "idTail")
	private Tail tail;
	
	@OneToMany(mappedBy="production")
	private Set<Formulation> formulations = new HashSet<Formulation>();
	
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

	public Double getWastage() {
		return wastage;
	}

	public void setWastage(Double wastage) {
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

	public List<Analysis> getAnalyzes() {
		return analyzes;
	}

	public void setAnalyzes(List<Analysis> analyzes) {
		this.analyzes = analyzes;
	}

	public LocalDate getStartCoocking() {
		return startCoocking;
	}

	public void setStartCoocking(LocalDate startCoocking) {
		this.startCoocking = startCoocking;
	}

	public LocalDate getEndCoocking() {
		return endCoocking;
	}

	public void setEndCoocking(LocalDate endCoocking) {
		this.endCoocking = endCoocking;
	}

	public Double getAlcoholicGradeDist1() {
		return alcoholicGradeDist1;
	}

	public void setAlcoholicGradeDist1(Double alcoholicGradeDist1) {
		this.alcoholicGradeDist1 = alcoholicGradeDist1;
	}

	public Double getAlcoholicGradeDist2() {
		return alcoholicGradeDist2;
	}

	public void setAlcoholicGradeDist2(Double alcoholicGradeDist2) {
		this.alcoholicGradeDist2 = alcoholicGradeDist2;
	}

	public Double getVolumeDistillation2() {
		return volumeDistillation2;
	}

	public void setVolumeDistillation2(Double volumeDistillation2) {
		this.volumeDistillation2 = volumeDistillation2;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

	public Set<Formulation> getFormulations() {
		return formulations;
	}

	public void setFormulations(Set<Formulation> formulations) {
		this.formulations = formulations;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public TypeProduction getTypeProduction() {
		return typeProduction;
	}

	public void setTypeProduction(TypeProduction typeProduction) {
		this.typeProduction = typeProduction;
	}
}
