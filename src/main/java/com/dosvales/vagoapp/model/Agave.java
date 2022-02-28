package com.dosvales.vagoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="agave")
@NamedEntityGraph(name = "graph.Agave.magueyes", 
	attributeNodes = @NamedAttributeNode("magueyes"))
public class Agave extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
    private String name;
	
	private String description;
	
	@OneToMany(mappedBy="agave")
	private List<Maguey> magueyes = new ArrayList<Maguey>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Maguey> getMagueyes() {
		return magueyes;
	}

	public void setMagueyes(List<Maguey> magueyes) {
		this.magueyes = magueyes;
	}
}
