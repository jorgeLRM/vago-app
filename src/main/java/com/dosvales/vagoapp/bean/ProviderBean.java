package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.exception.DataFoundException;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Provider;
import com.dosvales.vagoapp.service.ProviderService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class ProviderBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Provider provider;
	private List<Provider> providers;
	private EntityStatus status = EntityStatus.ACTIVE;
	
	@Inject
	private ProviderService providerService;
	
	public void openNew() {
		provider = new Provider();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				provider = providerService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(provider);
			page = save(provider);
		} catch (DataFoundException | AppException ex) {
			addMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Provider checkAvailability(Provider provider) throws DataFoundException, AppException { 
		Provider found = providerService.findByName(provider.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre del proveedor que ingresaste ya se encuentra registrado pero está deshabilitado. Consulta los proveedores deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre del proveedor ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Provider p) {
		providerService.save(p);
		addMessage("Operación exitosa", "Proveedor guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/packing/providers.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			providerService.update(provider);
			addMessage("Operación exitosa", "Proveedor actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/providers.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void handleFileUploadDocument(FileUploadEvent event) {
		String name = event.getFile().getFileName();
		String newFile = FilePath.PROVIDERS_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			provider.setDocument(name);
			showMessage("Documento subido correctamente", "Documento: " + name, FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void handleFileUploadPhoto(FileUploadEvent event) {
		//String name = event.getFile().getFileName();
		String name = UUID.randomUUID().toString() + ".png";
		String newFile = FilePath.PROVIDERS_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			provider.setLogo(name);
			showMessage("Imagen subida correctamente", "Imagen: " + name, FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public String getProvidersPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.PROVIDER_DIRECTORY + File.separator + name;
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			providers = providerService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			providers = providerService.findAllInactive();
		} else {
			providers = providerService.findAll();
		}
	}

	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}
