package com.dosvales.vagoapp.model;

public enum AnalysisStatus {
	
	NEGATIVE("Negativo"),
	POSITIVE("Positivo");
	
	private String text;
	
	private AnalysisStatus(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
