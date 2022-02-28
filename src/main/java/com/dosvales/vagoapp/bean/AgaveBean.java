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
import com.dosvales.vagoapp.model.Agave;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.service.AgaveService;

@Named
@ViewScoped
public class AgaveBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgaveService agaveService;

	private List<Agave> agaves;

	private Agave agave;
	
	private EntityStatus status = EntityStatus.ACTIVE;
	
	public void openNew() {
		agave = new Agave();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				agave = agaveService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}

	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(agave);
			page = save(agave);
		} catch (DataFoundException | AppException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Agave checkAvailability(Agave a) throws DataFoundException, AppException{
		Agave found = agaveService.findByName(a.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre del agave que ingresaste ya se encuentra registrado pero está deshabilitado. Consulta los agaves deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre del agave ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Agave a) {
		agaveService.save(a);
		addMessage("Operación exitosa", "Agave guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/agaves.xhtml?faces-redirect=true";
	}

	public String update() {
		String page = "";
		try {
			agaveService.update(agave);
			addMessage("Operación exitosa", "Agave actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/estates/agaves.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void delete() {
		try {
			checkHasAssociations(agave);
			agaveService.delete(agave);
			agaves.remove(agave);
			showMessage("Operación exitosa", "El agave ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-agaves");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Agave checkHasAssociations(Agave a) throws RelatedRecordException {
		Agave found = agaveService.findWithMagueyes(a.getId());
		if (found.getMagueyes().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("El agave no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo se ya no lo necesitas.");
	}
	
	public void enabledDisabledAgave() {
		try {
			String operation = agave.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			agave = agaveService.blockUnblockAgave(agave.getId());
			showMessage("Operación exitosa", "El agave ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			agaves.remove(agave);
			PrimeFaces.current().ajax().update(":form:dt-agaves");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			agaves = agaveService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			agaves = agaveService.findAllInactive();
		} else {
			agaves = agaveService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}

	public Agave getAgave() {
		return agave;
	}

	public void setAgave(Agave agave) {
		this.agave = agave;
	}
	
	public List<Agave> getAgaves() {
		return agaves;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}
