
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
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.service.PalenqueService;
import com.dosvales.vagoapp.service.ProducerService;

@Named
@ViewScoped
public class PalenqueBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PalenqueService palenqueService;
	
	@Inject
	private ProducerService producerService;
	
	private Palenque palenque;
	
	private List<Palenque> palenques;
	
	private List<Producer> producers;
	
	private EntityStatus status = EntityStatus.ACTIVE;
	
	public void openNew() {
		palenque = new Palenque();
		palenque.setAddress(new Address());
		producers = producerService.findAllMezcalProducers();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				palenque = palenqueService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(palenque);
			page = save(palenque);
		} catch (DataFoundException | AppException ex) {
			addMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Palenque checkAvailability(Palenque p) throws DataFoundException, AppException{
		Palenque found = palenqueService.findByName(p.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre del palenque que intentas ingresar ya se encuentra registrado pero está deshabilitado. Consulta los palenques deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre del palenque ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Palenque p) {
		palenqueService.save(p);
		addMessage("Operación exitosa", "Palenque guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/palenques.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			palenqueService.update(palenque);
			addMessage("Operación exitosa", "Palenque actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/estates/palenques.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			checkHasAssociations(palenque);
			palenqueService.delete(palenque);
			palenques.remove(palenque);
			showMessage("Operación exitosa", "El palenque ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-palenques");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Palenque checkHasAssociations(Palenque p) throws RelatedRecordException {
		Palenque found = palenqueService.findWithLotsAndTubs(p.getId());
		if (found.getLots().isEmpty() && found.getTubs().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("El palenque no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo si ya no lo necesitas.");
	}
	
	public void enabledDisabledPalenque() {
		try {
			String operation = palenque.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			palenque = palenqueService.blockUnblockPalenque(palenque.getId());
			showMessage("Operación exitosa", "El palenque ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			palenques.remove(palenque);
			PrimeFaces.current().ajax().update(":form:dt-palenques");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			palenques = palenqueService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			palenques = palenqueService.findAllInactive();
		} else {
			palenques = palenqueService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}


	public Palenque getPalenque() {
		return palenque;
	}

	public void setPalenque(Palenque palenque) {
		this.palenque = palenque;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public List<Palenque> getPalenques() {
		return palenques;
	}

	public List<Producer> getProducers() {
		return producers;
	}
}
