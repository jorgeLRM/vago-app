package com.dosvales.vagoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value="ReformulatedProduction")
@NamedEntityGraph(name = "graph.ReformulatedProduction.reformulatedDetails", 
attributeNodes = @NamedAttributeNode("reformulatedDetails"))
public class ReformulatedProduction extends Production {
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="reformulatedProduction", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ReformulatedDetail> reformulatedDetails = new ArrayList<ReformulatedDetail>();

	public List<ReformulatedDetail> getReformulatedDetails() {
		return reformulatedDetails;
	}

	public void setReformulatedDetails(List<ReformulatedDetail> reformulatedDetails) {
		this.reformulatedDetails = reformulatedDetails;
	}

	@Override
	public void nextStatus() {
		/*if (productionStatus == ProductionStatus.PREPARATIONREFORMULATION) {
			productionStatus = ProductionStatus.PRELIMINARYANALYSIS;
		} else if (productionStatus == ProductionStatus.PRELIMINARYANALYSIS) {
			productionStatus = ProductionStatus.OFFICIALANALYSIS;
		}*/
	}

	@Override
	public void previousStatus() {
		/*if (productionStatus == ProductionStatus.OFFICIALANALYSIS) {
			productionStatus = ProductionStatus.PRELIMINARYANALYSIS;
		} else if (productionStatus == ProductionStatus.PRELIMINARYANALYSIS) {
			productionStatus = ProductionStatus.PREPARATIONREFORMULATION;
		}*/
	}
}
