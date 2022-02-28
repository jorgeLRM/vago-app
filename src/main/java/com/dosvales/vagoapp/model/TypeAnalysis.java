package com.dosvales.vagoapp.model;

public enum TypeAnalysis {
	
	PRELIMINARY("Preliminar"),
	OFFICIAL("Oficial");
	
	private String type;
	
	private TypeAnalysis(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
