package com.dosvales.vagoapp.util;

import java.io.Serializable;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.Assay;

public class ParameterForTable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean editable;
	
	private Double result;
	
	private Analysis analysis;
	
	private Assay assay;

	public boolean isAccepted() {
		boolean accepted = false;
		if (result != null) {
			accepted = (result >= assay.getAllowableMinimum() && result <= assay.getMaximumAllowable());
		}
		return accepted;
	}
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

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
