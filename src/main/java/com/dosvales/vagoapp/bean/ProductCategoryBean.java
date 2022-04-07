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
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductCategory;
import com.dosvales.vagoapp.service.ProducerService;
import com.dosvales.vagoapp.service.ProductCategoryService;

@Named
@ViewScoped
public class ProductCategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ProductCategory> categories;

	private List<Producer> producers;

	private ProductCategory category;

	private EntityStatus status = EntityStatus.ACTIVE;

	@Inject
	private ProducerService producerService;

	@Inject
	private ProductCategoryService categoryService;

	public void openNew() {
		category = new ProductCategory();
	}

	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				category = categoryService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
	}

	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(category);
			page = save(category);
		} catch (DataFoundException | AppException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde.", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public ProductCategory checkAvailability(ProductCategory pc) throws DataFoundException, AppException {
		ProductCategory found = categoryService.findByName(pc.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE)
				throw new AppException("El nombre de la categoría que ingresaste ya se encuentra registrada pero está deshabilitada. "
						+ "Consulta las categorias deshabilitadas para volverla a habilitar");
			else
				throw new DataFoundException("El nombre de la categoría ya se encuentra registrada");
					
		}
		return found;
	}
	
	public String save(ProductCategory pc) {
		categoryService.save(pc);
		addMessage("Operación exitosa", "Categoria guardada exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/packing/productcategories.xhtml?faces-redirect=true";
	}

	public String update() {
		String page = "";
		try {
			categoryService.update(category);
			addMessage("Operación exitosa", "Categoría actualizada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/productcategories.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void enabledDisabledCategory() {
		try {
			String operation = category.getStatus() == EntityStatus.ACTIVE ? "deshabilitada" : "habilitada";
			category = categoryService.blockUnblockCategory(category.getId());
			showMessage("Eperación exitosa", "La categoría ha sido " + operation + " exitosamente", FacesMessage.SEVERITY_INFO);
			categories.remove(category);
			PrimeFaces.current().ajax().update(":form:dt-categories");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (status == EntityStatus.ACTIVE)
			categories = categoryService.findAllActive();
		else if (status == EntityStatus.INACTIVE)
			categories = categoryService.findAllInactive();
		else
			categories = categoryService.findAll();
	}

	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public List<Producer> getProducers() {
		if (producers == null)
			producers = producerService.findAllActive();
		return producers;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}