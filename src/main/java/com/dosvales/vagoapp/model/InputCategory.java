package com.dosvales.vagoapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "inputcategory")
@NamedEntityGraph(name = "graph.InputCategory.inputs", 
attributeNodes = @NamedAttributeNode("inputs"))
public class InputCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String description;
	
	@OneToMany(mappedBy="category")
	private List<Input> inputs;

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

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}
}
