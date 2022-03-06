package com.dosvales.vagoapp.model;

public enum TypeAnalysis {
	
	PRELIMINARY_BODY("Preliminar cuerpo"),
	PRELIMINARY_TAIL("Preliminar cola"),
	OFFICIAL("Oficial");
	
	private String type;
	
	private TypeAnalysis(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
