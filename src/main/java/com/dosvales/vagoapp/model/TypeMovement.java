package com.dosvales.vagoapp.model;

public enum TypeMovement {
	INPUT("Entrada", "instock"),
	OUTPUT("Salida", "outofstock");

	private String movement;
	private String badge;

	private TypeMovement(String movement, String badge) {
		this.movement = movement;
		this.badge = badge;
	}

	public String getMovement() {
		return movement;
	}

	public String getBadge() {
		return badge;
	}
}