package com.dosvales.vagoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="estate")
@NamedEntityGraph(name = "graph.Estate.plantations", 
				attributeNodes = @NamedAttributeNode("plantations"))	
public class Estate extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String name;
	
	@Embedded
	private Address address;
	
	@NotNull
	private Integer crmnumber;
	
	private String observations;
	
	@ManyToOne
	@JoinColumn(name="idProducer")
	private Producer producer;
	
	@OneToMany(mappedBy="estate")
	private List<Plantation> plantations = new ArrayList<Plantation>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getCrmnumber() {
		return crmnumber;
	}

	public void setCrmnumber(Integer crmnumber) {
		this.crmnumber = crmnumber;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<Plantation> getPlantations() {
		return plantations;
	}

	public void setPlantations(List<Plantation> plantations) {
		this.plantations = plantations;
	}
}
