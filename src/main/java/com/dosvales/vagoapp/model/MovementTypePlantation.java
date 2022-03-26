package com.dosvales.vagoapp.model;

public enum MovementTypePlantation {

	POSITIVE("Ajuste Positivo"),
	NEGATIVE("Ajuste Negativo");
	
	private String type;
	
	private MovementTypePlantation(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}