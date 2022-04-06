package com.dosvales.vagoapp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "calculation")
@NamedEntityGraph(name="graph.Calculation.rowsCalculation",
		attributeNodes = @NamedAttributeNode("rowsCalculation"))
public class Calculation extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Timestamp time;

	@NotNull
	private Double alcoholicGradeDesired;

	@ManyToOne
	@JoinColumn(name="idProduction")
	private Production production;

	@OneToMany(mappedBy = "calculation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<RowCalculation> rowsCalculation = new ArrayList<RowCalculation>();

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Double getAlcoholicGradeDesired() {
		return alcoholicGradeDesired;
	}

	public void setAlcoholicGradeDesired(Double alcoholicGradeDesired) {
		this.alcoholicGradeDesired = alcoholicGradeDesired;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public List<RowCalculation> getRowsCalculation() {
		return rowsCalculation;
	}

	public void setRowsCalculation(List<RowCalculation> rowsCalculation) {
		this.rowsCalculation = rowsCalculation;
	}
}