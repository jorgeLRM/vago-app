package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="analysis")
public class Analysis extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private static final Double MIN_ALCOHOL = 35.0;
	@Transient
	private static final Double MAX_ALCOHOL = 55.0;
	@Transient
	private static final Double MIN_METHANOL = 30.0;
	@Transient
	private static final Double MAX_METHANOL = 300.0;
	@Transient
	private static final Double MIN_FURFURAL = 0.0;
	@Transient
	private static final Double MAX_FURFURAL = 5.0;
	
	private LocalDate dateOfIssue;
	
	private String fq;
	
	private Double alcohol;
	
	private Double methanol;
	
	private Double furfural;
	
	private Double volume;
	
	private String document;
	
	private AnalysisStatus analysisStatus;
	
	private String observations;
	
	@Enumerated(EnumType.STRING)
	private TypeAnalysis typeAnalysis;
	
	@ManyToOne
	@JoinColumn(name = "idProduction")
	private Production production;
	
	@OneToMany(mappedBy="analysis")
	private List<Parameter> parameters;
	
	public boolean isAlcoholAccepted() {
		return MIN_ALCOHOL <= this.alcohol && this.alcohol <= MAX_ALCOHOL;
	}
	
	public boolean isMethanolAccepted() {
		return MIN_METHANOL <= this.methanol && this.methanol <= MAX_METHANOL;
	}
	
	public boolean isFurfuralAccepted() {
		return MIN_FURFURAL <= this.furfural && this.furfural <= MAX_FURFURAL;
	}
	
	public String getFq() {
		return fq;
	}

	public void setFq(String fq) {
		this.fq = fq;
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

	public Double getFurfural() {
		return furfural;
	}

	public void setFurfural(Double furfural) {
		this.furfural = furfural;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public TypeAnalysis getTypeAnalysis() {
		return typeAnalysis;
	}

	public void setTypeAnalysis(TypeAnalysis typeAnalysis) {
		this.typeAnalysis = typeAnalysis;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public AnalysisStatus getAnalysisStatus() {
		return analysisStatus;
	}

	public void setAnalysisStatus(AnalysisStatus analysisStatus) {
		this.analysisStatus = analysisStatus;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(LocalDate dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
