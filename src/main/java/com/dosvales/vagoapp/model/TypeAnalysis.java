package com.dosvales.vagoapp.model;

public enum TypeAnalysis {
	
	PRELIMINARY_BODY("Preliminar cuerpo", 1),
	PRELIMINARY_TAIL("Preliminar cola", 2),
	OFFICIAL("Oficial", 3);
	
	private String type;
	private int order;
	
	private TypeAnalysis(String type, int order) {
		this.type = type;
		this.order = order;
	}
	
	public String getType() {
		return type;
	}
	
	public int getOrder() {
		return order;
	}
	
}
