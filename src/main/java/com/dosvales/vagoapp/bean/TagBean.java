package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.exception.RelatedRecordException;
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
			tagService.save(tag);
			addMessage("Operación exitosa", "Registro de marbetes guardado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/tags.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente más tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public String update() {
		String page = "";
		try {
			tagService.update(tag);
			addMessage("Operación exitosa", "Registro de marbetes actualizado correctamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/tags.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void delete() {
		try {
			if (tag.getProductionOrderDetail() == null) {
				tagService.delete(checkLastTag(tag));
				tags.remove(tag);
				showMessage("Operación exitosa", "El registro de marbetes ha sido eliminado correctamente.", FacesMessage.SEVERITY_INFO);
				PrimeFaces.current().ajax().update(":form:dt-tags");
			} else {
				showMessage("Cuidado", "El registro no puede ser eliminado porque esta asociado con una orden de producción", FacesMessage.SEVERITY_WARN);
			}
		} catch(RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		}catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public Tag checkLastTag(Tag tag) throws RelatedRecordException {
		if (tag.getTypeMovement() == TypeMovement.INPUT)
			if (tag.getMaxNumber().intValue() == tagService.findLastConsecutiveInputs())
				return tag;
			else
				throw new RelatedRecordException("No puede eliminar el registro ya que no es la ultima entrada de marbetes.");
		else if (tag.getTypeMovement() == TypeMovement.OUTPUT)
			if (tag.getMaxNumber().intValue() == tagService.findLastConsecutiveOutputs())
				return tag;
			else
				throw new RelatedRecordException("No puede eliminar el registro ya que no es la ultima salida de marbetes");
		else
			return null;
	}
	
	public void refreshTable() {
		if (typeMovement == TypeMovement.INPUT)
			tags = tagService.findTagInput();
		else if (typeMovement == TypeMovement.OUTPUT)
			tags = tagService.findTagOutput();
		else
			tags = tagService.findAll();
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

	public Integer getLastFolio() {
		if (tag.getTypeMovement() == TypeMovement.OUTPUT)
			tag.setMinNumber(tagService.findLastConsecutiveOutputs().longValue() + 1);
		else
			tag.setMinNumber(tagService.findLastConsecutiveInputs().longValue() + 1);
		return tag.getMinNumber().intValue();
	}
}