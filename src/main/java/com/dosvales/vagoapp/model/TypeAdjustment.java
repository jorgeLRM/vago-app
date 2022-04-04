package com.dosvales.vagoapp.model;

public enum TypeAdjustment {

	POSITIVE("Ajuste Positivo"),
	NEGATIVE("Ajuste Negativo");
	
	private String type;
	
	private TypeAdjustment(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}