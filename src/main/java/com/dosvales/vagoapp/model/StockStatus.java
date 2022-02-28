package com.dosvales.vagoapp.model;

public enum StockStatus {
	
	INSTOCK("Suficiente"),
	LOWSTOCK("Insuficiente"),
	OUTOFSTOCK("Agotado");
	
	private String text;
	
	private StockStatus(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
