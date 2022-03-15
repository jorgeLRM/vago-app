package com.dosvales.vagoapp.filter;

import java.io.Serializable;
import java.time.LocalDate;

import com.dosvales.vagoapp.model.Producer;

public class ProductionFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer year;
	
	private Producer producer;

	public ProductionFilter() {
		year = LocalDate.now().getYear();
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}
}
