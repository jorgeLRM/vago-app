package com.dosvales.vagoapp.bean;

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
	
	@Inject
	private ProviderService providerService;
	
	public void openNew() {
		provider = new Provider();
	}
	
	public void handleFileUploadDocument(FileUploadEvent event) {
		String name = event.getFile().getFileName();
		String newFile = FilePath.PRODUCERS_PATH + File.separator + name;
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
		String newFile = FilePath.PRODUCERS_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			provider.setLogo(name);
			showMessage("Imagen subida correctamente", "Imagen: " + name, FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		providers = providerService.findAll();
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
}
