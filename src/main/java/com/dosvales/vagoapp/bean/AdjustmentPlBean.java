package com.dosvales.vagoapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.AdjustmentPlantation;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.MovementTypePlantation;
import com.dosvales.vagoapp.model.Plantation;
import com.dosvales.vagoapp.service.AdjustmentPlantationService;
import com.dosvales.vagoapp.service.EstateService;
import com.dosvales.vagoapp.service.PlantationService;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;

@Named
@ViewScoped
public class AdjustmentPlBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AdjustmentPlantationService adjustmentService;

	@Inject
	private PlantationService plantationService;
	
	@Inject
	private EstateService estateService;

	private List<AdjustmentPlantation> adjustments;

	private List<Estate> estates;

	private List<Plantation> plantations;

	private AdjustmentPlantation adjustment;
	
	private Estate estate;
	
	public void openNew() {
		adjustment = new AdjustmentPlantation();
		estate = new Estate();
		estates = estateService.findAll();
		plantations = (estates.size() > 0) ? plantationService.findByEstate(estates.get(0)): new ArrayList<Plantation>();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				adjustment = adjustmentService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			page = save(adjustment);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public String save(AdjustmentPlantation a) {
		Plantation aux = adjustment.getPlantation();
		if (adjustment.getMovementType() == MovementTypePlantation.POSITIVE) {
			aux.setStock(aux.getStock() + adjustment.getQuantity());
		} else {
			if (adjustment.getQuantity() > aux.getStock()) {
				addMessage("Advertencia", "La cantidad de ajuste supera el stock actual del plantio", FacesMessage.SEVERITY_WARN);
				return null;
			} else {
				aux.setStock(aux.getStock() - adjustment.getQuantity());
			}
		}
		adjustmentService.save(adjustment);
		plantationService.update(aux);
		addMessage("Operacion exitosa", "Ajuste guardado correctamente", FacesMessage.SEVERITY_INFO);
		return "/protected/estates/adjustmentplantations.xhtml?faces-redirect=true";
	}

	public void refreshTable() {
		adjustments = adjustmentService.findAll();
	}

	public List<AdjustmentPlantation> getAdjustments() {
		return adjustments;
	}

	public List<Plantation> getPlantations() {
		plantations = plantationService.findByEstate(estate);
		return plantations;
	}

	public AdjustmentPlantation getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(AdjustmentPlantation adjustment) {
		this.adjustment = adjustment;
	}

	public List<Estate> getEstates() {
		return estates;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	
	public MovementTypePlantation[] getMovementTypesPlantation() {
		return MovementTypePlantation.values();
	}
}