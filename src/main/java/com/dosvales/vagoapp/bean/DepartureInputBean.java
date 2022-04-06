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

import com.dosvales.vagoapp.model.DepartureInput;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.service.DepartureInputService;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProviderInputService;

@Named
@ViewScoped
public class DepartureInputBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DepartureInput departureInput;

	private ProviderInput providerInput;

	private List<DepartureInput> departureInputs;

	private List<ProviderInput> providerInputs;

	private List<Input> inputs;

	@Inject
	private DepartureInputService departureService;

	@Inject
	private ProviderInputService providerInputService;

	@Inject
	private InputService inputService;

	public void openNew() {
		departureInput = new DepartureInput();
		providerInput = new ProviderInput();
	}

	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				departureInput = departureService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
	}

	public String save() {
		String page = "";
		try {
			departureInput.setProvider(providerInput.getProvider());
			departureInput.setTotalPrice(providerInput.getUnitPrice() * departureInput.getAmount());
			departureInput.getInput().setStock(departureInput.getInput().getStock() - departureInput.getAmount());

			inputService.update(departureInput.getInput());
			departureService.save(departureInput);
			addMessage("Operacion exitosa", "Salida de insumos guardada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/inputdeparture.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void refreshTable() {
		departureInputs = departureService.findAll();
	}

	public void refreshProviders() {
		if (departureInput.getInput() != null)
			providerInputs = providerInputService.findAllByInput(departureInput.getInput());
	}

	public void delete() {
		try {
			departureService.delete(departureInput);
			departureInputs.remove(departureInput);
			departureInput.getInput().setStock(departureInput.getInput().getStock() + departureInput.getAmount());
			inputService.update(departureInput.getInput());
			showMessage("Operación exitosa", "La salida de insumos fue eliminada correctamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-departures");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public DepartureInput getDepartureInput() {
		return departureInput;
	}

	public void setDepartureInput(DepartureInput departureInput) {
		this.departureInput = departureInput;
	}

	public ProviderInput getProviderInput() {
		return providerInput;
	}

	public void setProviderInput(ProviderInput providerInput) {
		this.providerInput = providerInput;
	}

	public List<DepartureInput> getDepartureInputs() {
		return departureInputs;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}

	public List<Input> getInputs() {
		inputs = inputService.findAllActive();
		return inputs;
	}
}