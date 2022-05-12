package com.dosvales.vagoapp.model;

public enum DictumReportStatus {
	REQUIRED("Solicitado"),
	VERIFIED("Verificado");

	private String status;

	private DictumReportStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}