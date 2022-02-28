package com.dosvales.vagoapp.model;

public enum PaymentStatus {
	
	APPROBAL("Vo. Bo. Pago"),
	PASSED("Aprobado para pago"),
	PAID("Pagado"),
	INPROCESS("En proceso de pago"),
	PAIDBYLOT("Pagado por lote"),
	PENDING("Pendiente");
	
	private String type;
	
	private PaymentStatus(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
