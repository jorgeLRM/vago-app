package com.dosvales.vagoapp.model;

public enum InvoiceStatus {
	REQUIRED("Solicitada"),
	VALIDATION("Validada"),
	STAMPED("Timbrada");

	private String status;

	private InvoiceStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}