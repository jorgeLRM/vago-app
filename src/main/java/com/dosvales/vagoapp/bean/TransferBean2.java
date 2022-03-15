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
import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.Transfer;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.service.TransferService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class TransferBean2 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Transfer transfer;
	
	private List<Transfer> transfers;
	
	private List<Production> productions;
	
	@Inject
	private TransferService transferService;
	
	@Inject
	private ProductionService productionService;
	
	public void openNew() {
		transfer = new Transfer();
	}

	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				transfer = transferService.findById(Long.valueOf(id));
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
				page = "/protected/production/transfers.xhtml?faces-redirect=true";
			} else {
				showMessage("Cuidado", "El número de transferencia ya está registrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public String update() {
		String page = "";
		try {
			transferService.update(transfer);
			addMessage("Operación exitosa", "Traslado actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/transfers.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			transferService.delete(transfer);
			transfers.remove(transfer);
			showMessage("Operación exitosa", "Traslado eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-transfers");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
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

	public void refreshTable() {
		transfers = transferService.findAll();
	}
	
	public List<Production> getProductions() {
		productions = productionService.findAllWithoutTransfer();
		return productions;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public List<Transfer> getTransfers() {
		return transfers;
	}
}
