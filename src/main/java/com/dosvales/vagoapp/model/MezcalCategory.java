package com.dosvales.vagoapp.model;

public enum MezcalCategory {

	MEZCAL("Mezcal"),
	CRAFT("Mezca artesanal"),
	ANCESTRAL("Mezcal ancestral");

	private String category;

	private MezcalCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}