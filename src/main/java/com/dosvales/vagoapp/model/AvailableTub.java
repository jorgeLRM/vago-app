package com.dosvales.vagoapp.model;

public class AvailableTub {
	
	private Tub tub;
	
	private boolean available;

	public AvailableTub(Tub tub, boolean available) {
		this.tub = tub;
		this.available = available;
	}
	
	public Tub getTub() {
		return tub;
	}

	public void setTub(Tub tub) {
		this.tub = tub;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
