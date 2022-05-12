package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.Tag;
import com.dosvales.vagoapp.model.TypeMovement;
import com.dosvales.vagoapp.service.TagService;

@Named
@ViewScoped
public class TagBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tag tag;

	private List<Tag> tags;

	private TypeMovement typeMovement = TypeMovement.INPUT;

	@Inject
	private TagService tagService;

	public void openNew() {
		tag = new Tag();
	}

	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				tag = tagService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
	}

	public String save() {
		String page = "";
		try {
			if (tag.getTypeMovement() == TypeMovement.OUTPUT) {
			//	hologram.setOrderDetail(orderDetail);
			}
			tagService.save(tag);
			addMessage("Operación exitosa", "Registro de marbetes guardado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/tags.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente más tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void refreshTable() {
		if (typeMovement == TypeMovement.INPUT)
			tags = tagService.findTagInput();
		else if (typeMovement == TypeMovement.OUTPUT)
			tags = tagService.findTagOutput();
		else
			tags = tagService.findAll();
	}

	public void delete() {
		
	}

	public TypeMovement[] getTypesMovement() {
		return TypeMovement.values();
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public TypeMovement getTypeMovement() {
		return typeMovement;
	}

	public void setTypeMovement(TypeMovement typeMovement) {
		this.typeMovement = typeMovement;
	}

	public Integer getExistence() {
		return tagService.existenceOfTags();
	}
}