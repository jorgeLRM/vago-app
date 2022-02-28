package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "photomaguey")
public class PhotoMaguey extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String name;
	
	@ManyToOne
	@JoinColumn(name="idMaguey")
	private Maguey maguey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Maguey getMaguey() {
		return maguey;
	}

	public void setMaguey(Maguey maguey) {
		this.maguey = maguey;
	}
}
