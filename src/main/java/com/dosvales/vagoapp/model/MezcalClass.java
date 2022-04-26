package com.dosvales.vagoapp.model;

public enum MezcalClass {

	YOUNG("Joven"),
	MATURED("Madurado en vidrio"),
	RESTFUL("Reposado"),
	OLD("Añejo"),
	DOOMED("Abocado con"),
	DISTILLED("Destilado con");

	private String text;

	private MezcalClass (String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}