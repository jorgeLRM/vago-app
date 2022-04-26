package com.dosvales.vagoapp.model;

public enum InputUnit {

	MILLILITER("Mililitros"),
	BOTTLES("Botellas"),
	PIECE("Pieza"),
	PACKAGE("Paquete"),
	BAG("Bolsa");

	private String text;
	
	private InputUnit(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}