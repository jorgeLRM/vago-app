package com.dosvales.vagoapp.model;

public enum ProductionStatus {
	
	PREPARATION("Corte, cocción, molienda", "yellow-row"),
	PREPARATIONREFORMULATION("Mezcla de producciones", "yellow-row"),
	FORMULATION("Formulación", "yellow-row"),
	PRELIMINARYPOSITIVE("Análisis preliminar", "green-row"),
	PRELIMINARYNEGATIVE("Análisis preliminar", "yellow-row"),
	MIXTURE("Homegenización/Mezcla", "yellow-row"),
	OFFICIALANALYSIS("Análisis oficial","yellow-row"),
	INBULK("A granel", "white-row"),
	REPROBATE("Reprobado", "red-row"),
	CANCELED("Cancelado", "red-row"),
	FORPALENQUEMIXING("Para mezcla en palenque", "white-row"),
	FOROFFICEMIXING("Para mezcla en oficina", "white-row");
	
	private String type;
	
	private String color;
	
	private ProductionStatus(String type, String color) {
		this.type = type;
		this.color = color;
	}
	
	public String getType() {
		return type;
	}
	
	public String getColor() {
		return color;
	}
	
}
