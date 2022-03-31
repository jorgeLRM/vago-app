package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="analysis")
@NamedEntityGraph(name = "graph.Analysis.parameters", 
attributeNodes = @NamedAttributeNode("parameters"))
public class Analysis extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private LocalDate dateOfIssue;
	
	private String fq;
	
	private Double volume;
	
	private String document;
	
	private String observations;
	
	@Enumerated(EnumType.STRING)
	private TypeAnalysis typeAnalysis;
	
	@ManyToOne
	@JoinColumn(name = "idProduction")
	private Production production;
	
	@OneToMany(mappedBy="analysis", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Parameter> parameters;
	
	public boolean isPositive() {
		boolean result = true;
		for (Parameter parameter: parameters) {
			if (parameter.getResult() < parameter.getAssay().getAllowableMinimum() 
					|| parameter.getResult() > parameter.getAssay().getMaximumAllowable()) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public String getFq() {
		return fq;
	}

	public void setFq(String fq) {
		this.fq = fq;
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
