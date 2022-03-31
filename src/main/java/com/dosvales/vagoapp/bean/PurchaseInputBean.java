package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.model.PurchaseInput;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProviderInputService;
import com.dosvales.vagoapp.service.PurchaseInputService;

@Named
@ViewScoped
public class PurchaseInputBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PurchaseInput purchaseInput;
	private List<PurchaseInput> inputPurchases;
	private List<ProviderInput> providerInputs;
	private List<Input> inputs;
	private ProviderInput providerInput;
	
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
	
	public String save() {
		String page = "";
		try {
			purchaseInput.setProvider(providerInput.getProvider());
			purchaseInputService.save(purchaseInput);
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
}
