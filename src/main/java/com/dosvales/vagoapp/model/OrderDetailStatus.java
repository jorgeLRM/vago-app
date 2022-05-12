package com.dosvales.vagoapp.model;

public enum OrderDetailStatus {
	PENDING("Pendiente/Sin iniciar"),
	PACKAGNIG("Envasando"),
	BLOCKED("Bloqueada por falta de insumos"),
	FINISHED("Finalizada");

	private String status;

	private OrderDetailStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}