package com.dosvales.vagoapp.model;

import java.util.HashSet;
import java.util.Set;

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
@Table(name="palenque")
@NamedEntityGraph(name = "graph.Palenque.LotsAndTubs", 
attributeNodes = {
		@NamedAttributeNode("tubs"),
		@NamedAttributeNode("lots")
})
public class Palenque extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String name;
	
	@Embedded
	private Address address;
	
	private String observations;
	
	@ManyToOne
	@JoinColumn(name="idproducer")
	private Producer producer;

	@OneToMany(mappedBy="palenque")
	private Set<LotDetail> lots = new HashSet<LotDetail>();
	
	@OneToMany(mappedBy="palenque")
	private Set<Tub> tubs = new HashSet<Tub>();
	
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

	public Set<LotDetail> getLots() {
		return lots;
	}

	public void setLots(Set<LotDetail> lots) {
		this.lots = lots;
	}

	public Set<Tub> getTubs() {
		return tubs;
	}

	public void setTubs(Set<Tub> tubs) {
		this.tubs = tubs;
	}
}
