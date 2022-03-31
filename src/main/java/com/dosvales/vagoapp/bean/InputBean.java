package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.exception.DataFoundException;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.model.Provider;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.service.InputCategoryService;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProviderInputService;
import com.dosvales.vagoapp.service.ProviderService;

@Named
@ViewScoped
public class InputBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Input input;
	private List<Input> inputs;
	private List<InputCategory> categories;
	private List<ProviderInput> providerInputs;
	private List<Provider> providers;
	private EntityStatus status = EntityStatus.ACTIVE;
	
	@Inject
	private InputService inputService;
	@Inject
	private InputCategoryService categoryService;
	@Inject
	private ProviderService providerService;
	@Inject
	private ProviderInputService providerInputService;

	public void openNew() {
		input = new Input();
		providerInputs = new ArrayList<ProviderInput>();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				input = inputService.findById(Long.valueOf(id));
				providerInputs = providerInputService.findAllByInput(input);
			} catch (Exception ex) {
				input = null;
			}
		}
	}

	public void onAddRow() {
		ProviderInput pi = new ProviderInput();
		pi.setInput(input);
		providerInputs.add(pi);
		PrimeFaces.current().ajax().update(":form:dt-newproviderinputs");
	}
	
	public void onDeleteRow(int index) {
		providerInputs.remove(index);
		PrimeFaces.current().ajax().update(":form:dt-newproviderinputs");
	}
	
	public String tryToSave() {
		String page = "";
		try {
			if (!providerInputs.isEmpty()) {
				checkAvailability(input);
				page = save(input);
			} else {
				showMessage("Cuidado", "Ingresa al menos un proveedor.", FacesMessage.SEVERITY_WARN);
			}
		} catch (DataFoundException | AppException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Input checkAvailability(Input i) throws DataFoundException, AppException{
		Input found = inputService.findByNameOrCode(i.getName(), i.getCode());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre y/o código del insumo que ingresaste ya se encuentra registrado pero está deshabilitado.");
			} else {
				throw new DataFoundException("El nombre y/o código del insumo ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Input i) {
		i.setProviderInputs(providerInputs);
		inputService.save(i);
		addMessage("Operación exitosa", "Insumo guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/packing/inputs.xhtml?faces-redirect=true";
	}
	
	public String update() {
		String page = "";
		try {
			inputService.update(input);
			addMessage("Operación exitosa", "Insumo actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/inputs.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void enabledDisabledInput() {
		try {
			String operation = input.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado" ;
			input = inputService.blockUnblockInput(input.getId());
			showMessage("Operación exitosa", "El insumo ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			inputs.remove(input);
			PrimeFaces.current().ajax().update(":form:dt-inputs");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			inputs = inputService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			inputs = inputService.findAllInactive();
		} else {
			inputs = inputService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public List<InputCategory> getCategories() {
		categories = categoryService.findAllActive();
		return categories;
	}

	public List<ProviderInput> getProviderInputs() {
		return providerInputs;
	}

	public void setProviderInputs(List<ProviderInput> providerInputs) {
		this.providerInputs = providerInputs;
	}

	public List<Provider> getProviders() {
		providers = providerService.findAllActive();
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
}
