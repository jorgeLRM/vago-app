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

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.exception.DataFoundException;
import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.TypeProducer;
import com.dosvales.vagoapp.service.ProducerService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class ProducerBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProducerService producerService;
	
	private Producer producer;
	
	private List<Producer> producers;
	
	private EntityStatus status = EntityStatus.ACTIVE;
	
	public void openNew() {
		producer = new Producer();
	}

	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				producer = producerService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(producer);
			page = save(producer);
		} catch (DataFoundException | AppException ex) {
			addMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Producer checkAvailability(Producer producer) throws DataFoundException, AppException { 
		Producer found = producerService.findByNameOrAbbreviation(producer.getName(), producer.getAbbreviation());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre y/o abreviatura del productor que ingresaste ya se encuentra registrado pero está deshabilitado. Consulta los productores deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre y/o abreviatura del productor ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Producer p) {
		producerService.save(p);
		addMessage("Operación exitosa", "Productor guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/producers.xhtml?faces-redirect=true";
	}

	public String update() {
		String page = "";
		try {
			producerService.update(producer);
			addMessage("Operación exitosa", "Productor actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/estates/producers.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			checkHasAssociations(producer);
			producerService.delete(producer);
			producers.remove(producer);
			deleteFile(producer.getName());
			if (!producer.getPhoto().equals(Producer.DEFAULT_IMAGE)) {
				deleteFile(producer.getPhoto());
			}
			showMessage("Operación exitosa", "El productor ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-producers");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Producer checkHasAssociations(Producer p) throws RelatedRecordException {
		Producer found = producerService.findWithPalenquesAndEstates(p.getId());
		if (found.getPalenques().isEmpty() && found.getEstates().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("El productor no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo se ya no lo necesitas.");
	}
	
	public void deleteFile(String name) {
		if (name != null) {
			String documentPath = FilePath.PRODUCERS_PATH + File.separator + name;
			FacesUtil.deleteFile(documentPath);
		}
	}
	
	public void enabledDisabledProducer() {
		try {
			String operation = producer.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			producer = producerService.blockUnblockProducer(producer.getId());
			showMessage("Operación exitosa", "El productor ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			producers.remove(producer);
			PrimeFaces.current().ajax().update(":form:dt-producers");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			producers = producerService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			producers = producerService.findAllInactive();
		} else {
			producers = producerService.findAll();
		}
	}
	
	public void handleFileUploadDocument(FileUploadEvent event) {
		String name = event.getFile().getFileName();
		String newFile = FilePath.PRODUCERS_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			producer.setDocument(name);
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
			producer.setPhoto(name);
			showMessage("Imagen subida correctamente", "Imagen: " + name, FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public String getProducersPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.PRODUCER_DIRECTORY + File.separator + name;
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public TypeProducer[] getTypeProducers() {
		return TypeProducer.values();
	}
	
	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}
