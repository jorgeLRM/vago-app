package com.dosvales.vagoapp.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 187633576728454059L;
	
	private String locality = "";

	private String municipality = "";
	
	private String state = "Oaxaca";
	
	@Basic
	public String getAddress() {
		return locality + ", " + municipality + ", " + state;
	}
	
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
