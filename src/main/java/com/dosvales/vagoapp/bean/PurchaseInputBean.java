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

import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.model.PurchaseInput;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProviderInputService;
import com.dosvales.vagoapp.service.PurchaseInputService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class PurchaseInputBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PurchaseInput purchaseInput;
	private List<PurchaseInput> inputPurchases;
	private List<ProviderInput> providerInputs;
	private List<Input> inputs;
	private ProviderInput providerInput;
	
	private Boolean flagInvoice = false;
	
	@Inject
	private PurchaseInputService purchaseInputService;
	@Inject
	private InputService inputService;
	@Inject
	private ProviderInputService providerInputService;
	
	public void openNew() {
		purchaseInput = new PurchaseInput();
		providerInput = new ProviderInput();
	}

	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				purchaseInput = purchaseInputService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}

	public String save() {
		String page = "";
		try {
			purchaseInput.setProvider(providerInput.getProvider());
			if (flagInvoice)
				purchaseInput.setInvoice("NO HAY FACTURA");
			// Agregue el calculo del costo total
			purchaseInput.setTotalPrice((providerInput.getUnitPrice() * purchaseInput.getAmount()) + purchaseInput.getCostPerParcel());
			purchaseInputService.save(purchaseInput);
			//Actualizar las existencias
			purchaseInput.getInput().setStock(purchaseInput.getInput().getStock() + purchaseInput.getAmount());
			inputService.update(purchaseInput.getInput());
			addMessage("Operación exitosa", "Entrada de insumo guardada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/inputpurchases.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void refreshTable() {
		inputPurchases = purchaseInputService.findAll();
	}
	
	public void refreshProviders() {
		if (purchaseInput.getInput() != null) {
			providerInputs = providerInputService.findAllByInput(purchaseInput.getInput());
		}
	}
	
	public void handleFileUploadDocument(FileUploadEvent event) {
		String name = event.getFile().getFileName();
		String newFile = FilePath.PURCHASEINPUT_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			purchaseInput.setInvoice(name);
			showMessage("Documento subido correctamente", "Documento: " + name, FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void delete() {
		try {
			purchaseInputService.delete(purchaseInput);
			inputPurchases.remove(purchaseInput);
			//Actualizar las existencias
			purchaseInput.getInput().setStock(purchaseInput.getInput().getStock() - purchaseInput.getAmount());
			inputService.update(purchaseInput.getInput());
			showMessage("Operación exitosa", "El ingreso de insumos ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-purchases");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	
	public String getPurchaseInvoicePath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.PURCHASEINPUT_DIRECTORY + File.separator + name;
	}
	
	public PurchaseInput getPurchaseInput() {
		return purchaseInput;
	}
	
	public void setPurchaseInput(PurchaseInput purchaseInput) {
		this.purchaseInput = purchaseInput;
	}
	
	public List<PurchaseInput> getInputPurchases() {
		return inputPurchases;
	}
	
	public void setInputPurchases(List<PurchaseInput> inputPurchases) {
		this.inputPurchases = inputPurchases;
	}

	public List<Input> getInputs() {
		inputs = inputService.findAllActive();
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}

	public void setProviderInputs(List<ProviderInput> providerInputs) {
		this.providerInputs = providerInputs;
	}

	public ProviderInput getProviderInput() {
		return providerInput;
	}

	public void setProviderInput(ProviderInput providerInput) {
		this.providerInput = providerInput;
	}

	public Boolean getFlagInvoice() {
		return flagInvoice;
	}

	public void setFlagInvoice(Boolean flagInvoice) {
		this.flagInvoice = flagInvoice;
	}
}