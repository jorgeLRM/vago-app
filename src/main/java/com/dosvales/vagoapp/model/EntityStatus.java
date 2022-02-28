package com.dosvales.vagoapp.model;

public enum EntityStatus {
	
	ACTIVE("instock","Activo"),
	INACTIVE("outofstock","Inactivo");
	
	private String badge;
	private String text;
	
	private EntityStatus(String badge, String text) {
		this.badge = badge;
		this.text = text;
	}
	
	public String getBadge() {
		return badge;
	}
	
	public String getText() {
		return text;
	}
}
