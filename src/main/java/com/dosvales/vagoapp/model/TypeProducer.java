package com.dosvales.vagoapp.model;

public enum TypeProducer {
	
	MEZCAL_PRODUCER("Productor de mezcal"),
	AGAVE_PRODUCER("Productor de agave"),
	BOTH("Productor de mezcal y agave");
	
	private String type;
	
	private TypeProducer(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
