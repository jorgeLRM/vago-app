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

import com.dosvales.vagoapp.model.AdjustmentInput;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.model.TypeAdjustment;
import com.dosvales.vagoapp.service.AdjustmentInputService;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProviderInputService;

@Named
@ViewScoped
public class AdjustmentInputBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AdjustmentInput adjustmentInput;
	
	private List<AdjustmentInput> adjustmentInputs;

	private List<ProviderInput> providerInputs;
	
	private List<Input> inputs;

	private ProviderInput providerInput;

	private TypeAdjustment typeAdjustment;
	
	private Double totalCost;
	
	@Inject
	private AdjustmentInputService adjustmentService;
	
	@Inject
	private InputService inputService;

	@Inject
	private ProviderInputService providerInputService;

	public void openNew() {
		adjustmentInput = new AdjustmentInput();
		providerInput = new ProviderInput();
	}

	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				adjustmentInput = adjustmentService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
	}

	public String save() {
		String page = "";
		try {
			adjustmentInput.setProvider(providerInput.getProvider());
			if (adjustmentInput.getTypeAdjustment() == TypeAdjustment.POSITIVE) {
				adjustmentInput.setTotalPrice(providerInput.getUnitPrice() * adjustmentInput.getAmount());
				adjustmentInput.getInput().setStock(adjustmentInput.getInput().getStock() + adjustmentInput.getAmount());
			} else {
				adjustmentInput.setTotalPrice((providerInput.getUnitPrice() * adjustmentInput.getAmount()) * -1);
				adjustmentInput.getInput().setStock(adjustmentInput.getInput().getStock() - adjustmentInput.getAmount());
			}
			adjustmentService.save(adjustmentInput);
			inputService.update(adjustmentInput.getInput());
			addMessage("Operacion exitosa", "Ajuste de insumos guardado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/inputadjustments.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void refreshTable() {
		if (typeAdjustment == TypeAdjustment.POSITIVE) {
			adjustmentInputs = adjustmentService.findAllByTypeAdjustment(TypeAdjustment.POSITIVE);
		} else if (typeAdjustment == TypeAdjustment.NEGATIVE){
			adjustmentInputs = adjustmentService.findAllByTypeAdjustment(TypeAdjustment.NEGATIVE);
		} else {
			adjustmentInputs = adjustmentService.findAll();
		}
	}

	public void refreshProviders() {
		if (adjustmentInput.getInput() != null)
			providerInputs = providerInputService.findAllByInput(adjustmentInput.getInput());
	}

	public void delete() {
		try {
			adjustmentService.delete(adjustmentInput);
			adjustmentInputs.remove(adjustmentInput);
			if (adjustmentInput.getTypeAdjustment() == TypeAdjustment.POSITIVE)
				adjustmentInput.getInput().setStock(adjustmentInput.getInput().getStock() - adjustmentInput.getAmount());
			else
				adjustmentInput.getInput().setStock(adjustmentInput.getInput().getStock() + adjustmentInput.getAmount());
			inputService.update(adjustmentInput.getInput());
			showMessage("Operación exitosa", "El ajuste de insumos ha sido eliminado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-adjustments");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public AdjustmentInput getAdjustmentInput() {
		return adjustmentInput;
	}

	public void setAdjustmentInput(AdjustmentInput adjustmentInput) {
		this.adjustmentInput = adjustmentInput;
	}

	public List<AdjustmentInput> getAdjustmentInputs() {
		return adjustmentInputs;
	}

	public List<Input> getInputs() {
		inputs = inputService.findAllActive();
		return inputs;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}
	
	public TypeAdjustment[] getTypesAdjustment() {
		return TypeAdjustment.values();
	}

	public TypeAdjustment getTypeAdjustment() {
		return typeAdjustment;
	}

	public void setTypeAdjustment(TypeAdjustment typeAdjustment) {
		this.typeAdjustment = typeAdjustment;
	}

	public ProviderInput getProviderInput() {
		return providerInput;
	}
	
	public void setProviderInput(ProviderInput providerInput) {
		this.providerInput = providerInput;
	}

	public Double getTotalCost() {
		totalCost = 0d;
		if (adjustmentInput != null && providerInput != null) {
			if (adjustmentInput.getTypeAdjustment() == TypeAdjustment.POSITIVE)
				totalCost = adjustmentInput.getAmount() * providerInput.getUnitPrice();
			else if (adjustmentInput.getTypeAdjustment() == TypeAdjustment.NEGATIVE)
				totalCost = (-1) * (adjustmentInput.getAmount() * providerInput.getUnitPrice());
		}
		return totalCost;
	}
}