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
import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.Address;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.service.EstateService;
import com.dosvales.vagoapp.service.ProducerService;

@Named
@ViewScoped
public class EstateBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstateService estateService;
	
	@Inject
	private ProducerService producerService;
	
	private Estate estate;
	
	private List<Producer> producers;
	
	private List<Estate> estates;
	
	private EntityStatus status = EntityStatus.ACTIVE;

	public void openNew() {
		estate = new Estate();
		estate.setAddress(new Address());
		producers = producerService.findAllAgaveProducers();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				estate = estateService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(estate);
			page = save(estate);
		} catch (DataFoundException | AppException ex) {
			addMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Estate checkAvailability(Estate e) throws DataFoundException, AppException{
		Estate found = estateService.findByName(e.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre del predio que intentas ingresar ya se encuentra registrado pero está deshabilitado. Consulta los predios deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre del predio ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Estate e) {
		estateService.save(e);
		addMessage("Operación exitosa", "Predio guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/estates.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			estateService.update(estate);
			addMessage("Operación exitosa", "Predio actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/estates/estates.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			checkHasAssociations(estate);
			estateService.delete(estate);
			estates.remove(estate);
			showMessage("Operación exitosa", "El predio ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-estates");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Estate checkHasAssociations(Estate e) throws RelatedRecordException {
		Estate found = estateService.findWithPlantations(e.getId());
		if (found.getPlantations().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("El predio no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo si ya no lo necesitas.");
	}
	
	public void enabledDisabledEstate() {
		try {
			String operation = estate.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			estate = estateService.blockUnblockEstate(estate.getId());
			showMessage("Operación exitosa", "El predio ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			estates.remove(estate);
			PrimeFaces.current().ajax().update(":form:dt-estates");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			estates = estateService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			estates = estateService.findAllInactive();
		} else {
			estates = estateService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public List<Estate> getEstates() {
		return estates;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
}
