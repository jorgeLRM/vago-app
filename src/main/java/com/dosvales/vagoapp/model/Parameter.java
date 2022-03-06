package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="parameter")
public class Parameter extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Double result;
	
	@ManyToOne
	@JoinColumn(name="idAnalysis")
	private Analysis analysis;
	
	@ManyToOne
	@JoinColumn(name="idAssay")
	private Assay assay;

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public Assay getAssay() {
		return assay;
	}

	public void setAssay(Assay assay) {
		this.assay = assay;
	}
}
