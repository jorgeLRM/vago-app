package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.model.Transfer;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.service.TransferService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class TransferBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Transfer transfer;
	private StandardProduction production;
	
	@Inject
	private TransferService transferService;
	@Inject
	private StandardProductionService productionService;
	
	public void openNew(String idProduction) {
		if (idProduction != null && idProduction.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(idProduction));
				transfer = new Transfer();
			} catch (Exception ex) {}
		}
	}
	
	public String save() {
		String page = "";
		try {
			Transfer found = transferService.findByNumTransfer(transfer.getNumTransfer());
			if (found != null) {
				transferService.save(transfer);
				addMessage("Operación exitosa", "Traslado guardado exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/production-panel.xhtml?faces-redirect=true&id="+production.getId();
			} else {
				showMessage("Cuidado", "El número de transferencia ya está registrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void handleFileUpload(FileUploadEvent event) {
		String fileName = event.getFile().getFileName();
		String newFile = FilePath.TRANSFERS_PATH + File.separator + fileName;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			transfer.setDocument(fileName);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public String getTransferPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.TRANSFER_DIRECTORY + File.separator + name;
	}
	
	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
	}
}
