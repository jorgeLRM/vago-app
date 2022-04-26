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

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.exception.DataFoundException;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.service.InputCategoryService;

@Named
@ViewScoped
public class InputCategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<InputCategory> categories;
	private InputCategory category;
	private EntityStatus status = EntityStatus.ACTIVE;
	
	@Inject
	private InputCategoryService categoryService;

	public void openNew() {
		category = new InputCategory();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				category = categoryService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(category);
			page = save(category);
		} catch (DataFoundException | AppException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public InputCategory checkAvailability(InputCategory ic) throws DataFoundException, AppException{
		InputCategory found = categoryService.findByNameAndNomenclature(ic.getName(), ic.getNomenclature());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				// Se cambio el mensaje
				throw new AppException("El nombre de la categoría que ingresaste ya se encuentra registrada pero está deshabilitada. Consulta las categorias deshabilitadas para volverla a habilitar.");
			} else {
				throw new DataFoundException("El nombre y/o nomenclatura de la categoría ya se encuentra registrada");
			}
		}
		return found;
	}
	
	public String save(InputCategory ic) {
		categoryService.save(ic);
		addMessage("Operación exitosa", "Categoría guardada exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/packing/inputcategories.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			categoryService.update(category);
			addMessage("Operación exitosa", "Categoría actualizada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/inputcategories.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void enabledDisabledCategory() {
		try {
			String operation = category.getStatus() == EntityStatus.ACTIVE ? "deshabilitada" : "habilitada" ;
			category = categoryService.blockUnblockAgave(category.getId());
			showMessage("Operación exitosa", "La categoría ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			categories.remove(category);
			PrimeFaces.current().ajax().update(":form:dt-categories");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			categories = categoryService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			categories = categoryService.findAllInactive();
		} else {
			categories = categoryService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public List<InputCategory> getCategories() {
		return categories;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public InputCategory getCategory() {
		return category;
	}

	public void setCategory(InputCategory category) {
		this.category = category;
	}
}
