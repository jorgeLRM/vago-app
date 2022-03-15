package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
@DiscriminatorValue(value = "StandardProduction")
@NamedEntityGraph(name = "graph.StandardProduction.allRelationships", 
attributeNodes = {
		  @NamedAttributeNode("formulations"),
		  @NamedAttributeNode("tail"),
		  @NamedAttributeNode("analyzes"),
		  @NamedAttributeNode("transfer"),
		  @NamedAttributeNode(value="lotDetail", subgraph="lotDetail-subgraph")
},
subgraphs= {
		@NamedSubgraph(
	      name = "lotDetail-subgraph",
	      attributeNodes = {
	        @NamedAttributeNode("cutting")
	      }
	    )
})
public class StandardProduction extends Production {

	private static final long serialVersionUID = 1L;
	
	private LocalDate startCoocking;

	private LocalDate endCoocking;

	private Double alcoholicGradeDist1 = 0.0;

	private Double alcoholicGradeDist2 = 0.0;

	private Double volumeDistillation2 = 0.0;
	
	@OneToMany(mappedBy = "standardProduction")
	private List<ReformulatedDetail> reformulatedDetails = new ArrayList<ReformulatedDetail>();
	
	@OneToOne(mappedBy="standardProduction")
	@JoinColumn(name = "idTail")
	private Tail tail;
	
	@OneToMany(mappedBy="production")
	private Set<Formulation> formulations = new HashSet<Formulation>();
	
	@PrePersist
	public void prePersist() {
		reset();
	}
	
	@PreUpdate
	public void preUpdate() {
		reset();
	}
	
	public void reset() {
		if (alcoholicGradeDist1 == null) {
			alcoholicGradeDist1 = 0.0;
		}
		if (alcoholicGradeDist2 == null) {
			alcoholicGradeDist2 = 0.0;
		}
		if (volumeDistillation2 == null) {
			volumeDistillation2 = 0.0;
		}
	}
	
	public LocalDate getStartCoocking() {
		return startCoocking;
	}

	public void setStartCoocking(LocalDate startCoocking) {
		this.startCoocking = startCoocking;
	}

	public LocalDate getEndCoocking() {
		return endCoocking;
	}

	public void setEndCoocking(LocalDate endCoocking) {
		this.endCoocking = endCoocking;
	}

	public Double getAlcoholicGradeDist1() {
		return alcoholicGradeDist1;
	}

	public void setAlcoholicGradeDist1(Double alcoholicGradeDist1) {
		this.alcoholicGradeDist1 = alcoholicGradeDist1;
	}

	public Double getAlcoholicGradeDist2() {
		return alcoholicGradeDist2;
	}

	public void setAlcoholicGradeDist2(Double alcoholicGradeDist2) {
		this.alcoholicGradeDist2 = alcoholicGradeDist2;
	}

	public Double getVolumeDistillation2() {
		return volumeDistillation2;
	}

	public void setVolumeDistillation2(Double volumeDistillation2) {
		this.volumeDistillation2 = volumeDistillation2;
	}

	public List<ReformulatedDetail> getReformulatedDetails() {
		return reformulatedDetails;
	}

	public void setReformulatedDetails(List<ReformulatedDetail> reformulatedDetails) {
		this.reformulatedDetails = reformulatedDetails;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

	public Set<Formulation> getFormulations() {
		return formulations;
	}

	public void setFormulations(Set<Formulation> formulations) {
		this.formulations = formulations;
	}

	@Override
	public void nextStatus() {
		/*if (productionStatus == ProductionStatus.PREPARATION) {
			productionStatus = ProductionStatus.FORMULATION;
		} else if (productionStatus == ProductionStatus.FORMULATION) {
			productionStatus = ProductionStatus.PRELIMINARYANALYSIS;
		} else if (productionStatus == ProductionStatus.PRELIMINARYANALYSIS) {
			productionStatus = ProductionStatus.MIXTURE;
		} else if (productionStatus == ProductionStatus.MIXTURE) {
			productionStatus = ProductionStatus.OFFICIALANALYSIS;
		}*/
	}

	@Override
	public void previousStatus() {
		if (productionStatus == ProductionStatus.OFFICIALANALYSIS) {
			productionStatus = ProductionStatus.MIXTURE;
		} else if (productionStatus == ProductionStatus.MIXTURE) {
			if (analyzes.size() == 3) {
				if (analyzes.get(1).isPositive()) {
					productionStatus = ProductionStatus.PRELIMINARYTAILPOSITIVE;
				} else {
					productionStatus = ProductionStatus.PRELIMINARYTAILNEGATIVE;
				}
			} else if (analyzes.size() == 2) {
				if (analyzes.get(0).isPositive()) {
					productionStatus = ProductionStatus.PRELIMINARYBODYPOSITIVE;
				} else {
					productionStatus = ProductionStatus.PRELIMINARYBODYNEGATIVE;
				}
			}
		} else if (productionStatus == ProductionStatus.PRELIMINARYTAILNEGATIVE ||
				productionStatus == ProductionStatus.PRELIMINARYTAILPOSITIVE) {
			if (analyzes.get(0).isPositive()) {
				productionStatus = ProductionStatus.PRELIMINARYBODYPOSITIVE;
			} else {
				productionStatus = ProductionStatus.PRELIMINARYBODYNEGATIVE;
			}
		}else if (productionStatus == ProductionStatus.PRELIMINARYBODYNEGATIVE ||
				productionStatus == ProductionStatus.PRELIMINARYBODYPOSITIVE) {
			productionStatus = ProductionStatus.FORMULATION;
		} else if (productionStatus == ProductionStatus.FORMULATION) {
			productionStatus = ProductionStatus.PREPARATION;
		}
	}
}
