package com.dosvales.vagoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="maguey")
@NamedEntityGraphs({
	@NamedEntityGraph(name="graph.Maguey.photos",
					attributeNodes = @NamedAttributeNode("photos")),
	@NamedEntityGraph(name = "graph.Maguey.plantations", 
					attributeNodes = @NamedAttributeNode("plantations")),
})
public class Maguey extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String name;
	
	@NotNull
	private String abbreviation;
	
	private Integer ageOfMaturation;
	
	private String observations;
	
	@ManyToOne
	@JoinColumn(name="idAgave")
	private Agave agave;
	
	@OneToMany(mappedBy="maguey", cascade = CascadeType.REMOVE)
	private List<PhotoMaguey> photos = new ArrayList<PhotoMaguey>();

	@OneToMany(mappedBy="maguey")
	private List<Plantation> plantations = new ArrayList<Plantation>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Integer getAgeOfMaturation() {
		return ageOfMaturation;
	}

	public void setAgeOfMaturation(Integer ageOfMaturation) {
		this.ageOfMaturation = ageOfMaturation;
	}

	public Agave getAgave() {
		return agave;
	}

	public void setAgave(Agave agave) {
		this.agave = agave;
	}

	public List<PhotoMaguey> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoMaguey> photos) {
		this.photos = photos;
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
