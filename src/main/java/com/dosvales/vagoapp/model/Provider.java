package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="provider")
@NamedEntityGraph(name = "graph.Provider.providerInputs", 
attributeNodes = @NamedAttributeNode("providerInputs"))
public class Provider extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_IMAGE = "no_image.png";
	
	private String name;
	
	private String telephone;
	
	private String bank;
	
	private String interbankKey;
	
	private String document;
	
	private String email;
	
	private String logo = DEFAULT_IMAGE;
	
	@OneToMany(mappedBy="provider")
	private List<ProviderInput> providerInputs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}

	public void setProviderInputs(List<ProviderInput> providerInputs) {
		this.providerInputs = providerInputs;
	}
}
