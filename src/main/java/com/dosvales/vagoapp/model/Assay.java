package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="assay")
public class Assay extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Double allowableMinimum;
	
	private Double maximumAllowable;
	
	private String unit;
	
	private boolean byDefault;
	
	@OneToMany(mappedBy="assay")
	private List<Parameter> parameters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAllowableMinimum() {
		return allowableMinimum;
	}

	public void setAllowableMinimum(Double allowableMinimum) {
		this.allowableMinimum = allowableMinimum;
	}

	public Double getMaximumAllowable() {
		return maximumAllowable;
	}

	public void setMaximumAllowable(Double maximumAllowable) {
		this.maximumAllowable = maximumAllowable;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public boolean isByDefault() {
		return byDefault;
	}

	public void setByDefault(boolean byDefault) {
		this.byDefault = byDefault;
	}
}
