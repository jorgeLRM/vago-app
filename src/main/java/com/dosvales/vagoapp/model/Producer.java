package com.dosvales.vagoapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="producer")
@NamedEntityGraph(name = "graph.Producer.palenquesAndEstates", 
				attributeNodes = {
						@NamedAttributeNode("palenques"),
						@NamedAttributeNode("estates")
})
public class Producer extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_IMAGE = "no_image.png";
	
	@NotNull
	private String name;
	
	@NotNull
	private String abbreviation;
	
	@Enumerated(EnumType.STRING)
	private TypeProducer typeProducer;
	
	private String telephone;
	
	private String email;
	
	private String bank;
	
	private String interbankKey;
	
	private String document;
	
	private String photo = DEFAULT_IMAGE;
	
	@OneToMany(mappedBy="producer")
	private Set<Palenque> palenques = new HashSet<Palenque>();
	
	@OneToMany(mappedBy="producer")
	private Set<Estate> estates = new HashSet<Estate>();

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

	public TypeProducer getTypeProducer() {
		return typeProducer;
	}

	public void setTypeProducer(TypeProducer typeProducer) {
		this.typeProducer = typeProducer;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getInterbankKey() {
		return interbankKey;
	}

	public void setInterbankKey(String interbankKey) {
		this.interbankKey = interbankKey;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Set<Palenque> getPalenques() {
		return palenques;
	}

	public void setPalenques(Set<Palenque> palenques) {
		this.palenques = palenques;
	}

	public Set<Estate> getEstates() {
		return estates;
	}

	public void setEstates(Set<Estate> estates) {
		this.estates = estates;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
