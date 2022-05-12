package com.dosvales.vagoapp.model;

public enum Markets {
	UNKNOWN("Sin destino"),
	LOCAL("Local"),
	NATIONAL("Nacional"),
	INTERNATIONAL("Internacional");

	private String text;

	private Markets(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}