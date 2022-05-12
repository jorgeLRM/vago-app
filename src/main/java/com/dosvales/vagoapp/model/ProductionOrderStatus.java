package com.dosvales.vagoapp.model;

public enum ProductionOrderStatus {
	PLANNING("Planeación"),
	PRODUCTION("En producción"),
	FINISHED("Finalizada");
	
	private String status;

	private ProductionOrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}