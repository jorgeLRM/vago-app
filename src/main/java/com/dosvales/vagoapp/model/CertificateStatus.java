package com.dosvales.vagoapp.model;

public enum CertificateStatus {
	REQUIRED("Solicitados"),
	DRAFT("Borradores"),
	ORIGINAL("Originales");

	private String status;

	private CertificateStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}