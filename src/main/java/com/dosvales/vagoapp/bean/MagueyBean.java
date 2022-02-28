package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
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
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;
import com.dosvales.vagoapp.service.AgaveService;
import com.dosvales.vagoapp.service.MagueyService;
import com.dosvales.vagoapp.service.PhotoMagueyService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class MagueyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MagueyService magueyService;
	
	@Inject
	private AgaveService agaveService;
	
	@Inject
	private PhotoMagueyService photoMagueyService;
	
	private List<Maguey> magueyes;
	
	private List<Agave> agaves;
	
	private Maguey maguey;
	
	private EntityStatus status = EntityStatus.ACTIVE;
	
	public void openNew() {
		maguey = new Maguey();
		agaves = agaveService.findAllActive();
	}

	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				maguey = magueyService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(maguey);
			page = save(maguey);
		} catch (DataFoundException | AppException ex) {
			addMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Maguey checkAvailability(Maguey m) throws DataFoundException, AppException{
		Maguey found = magueyService.findByNameOrAbbreviation(m.getName(), m.getAbbreviation());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre y/o abreviación del maguey que intentas ingresar ya se encuentra registrado pero está deshabilitado. Consulta los magueyes deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre y/o abreviación del maguey ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Maguey m) {
		magueyService.save(m);
		addMessage("Operación exitosa", "Maguey guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/magueyes.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			magueyService.update(maguey);
			addMessage("Operación exitosa", "Maguey actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/estates/magueyes.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			checkHasAssociations(maguey);
			List<PhotoMaguey> photos = photoMagueyService.findByMaguey(maguey);
			magueyService.delete(maguey);
			magueyes.remove(maguey);
			deleteFiles(photos);
			showMessage("Operación exitosa", "El maguey ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-magueyes");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Maguey checkHasAssociations(Maguey m) throws RelatedRecordException {
		Maguey found = magueyService.findWithPlantations(m.getId());
		if (found.getPlantations().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("El maguey no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo si ya no lo necesitas.");
	}
	
	public void deleteFiles(List<PhotoMaguey> photos) {
		for (PhotoMaguey pm: photos) {
			String photoPath = FilePath.MAGUEYES_PATH + File.separator + pm.getName(); 
			FacesUtil.deleteFile(photoPath);
		}
	}
	
	public void enabledDisabledMaguey() {
		try {
			String operation = maguey.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			maguey = magueyService.blockUnblockMaguey(maguey.getId());
			showMessage("Operación exitosa", "El agave ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			magueyes.remove(maguey);
			PrimeFaces.current().ajax().update(":form:dt-magueyes");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			magueyes = magueyService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			magueyes = magueyService.findAllInactive();
		} else {
			magueyes = magueyService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public List<Maguey> getMagueyes() {
		return magueyes;
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

	public Maguey getMaguey() {
		return maguey;
	}

	public void setMaguey(Maguey maguey) {
		this.maguey = maguey;
	}
}
