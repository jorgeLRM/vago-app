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
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.model.AvailableTub;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.FormulationService;
import com.dosvales.vagoapp.service.PalenqueService;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.service.TubService;

@Named
@ViewScoped
public class FormulationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StandardProduction production;
	private Formulation formulation;
	private Palenque palenque;
	private List<Formulation> newFormulations;
	private List<Tub> tubsByPalenque;
	private List<AvailableTub> availableTubs;
	private List<Palenque> palenques;

	@Inject
	private StandardProductionService productionService;
	@Inject
	private FormulationService formulationService;
	@Inject
	private TubService tubService;
	@Inject
	private PalenqueService palenqueService;
	
	public void openNew(String idProduction) {
		try {
			production = productionService.findById(Long.valueOf(idProduction));
			newFormulations = new ArrayList<Formulation>();
			resetProduction();
		} catch (Exception ex) {}
	}
	
	public void load(String idProduction, String id) {
		if (idProduction != null && idProduction.length() > 0 
				&& id != null && id.length() > 0) {
			try {
				production = productionService.findWithAssociations(Long.valueOf(id));
				formulation = formulationService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public void onAddRow() {
		if (newFormulations.isEmpty()) {
			newFormulations.add(new Formulation());
		} else {
			Formulation f = newFormulations.get(newFormulations.size() - 1);
			if (isTheRowValid(f)) {
				newFormulations.add(new Formulation());
			} else {
				showMessage("Cuidado","Las fechas de su última formulación no tienen orden cronológico. "
						+ "La fecha de formulación debe ser mayor o igual que la de molienda y la fecha de destilación debe "
						+ "ser mayor que la de molienda.", FacesMessage.SEVERITY_WARN);
			}
		}
		PrimeFaces.current().ajax().update(":form:dt-newformulations");
	}
	
	public void onDeleteRow(int index) {
		if (newFormulations.size() > 1) {
			newFormulations.remove(index);
			PrimeFaces.current().ajax().update(":form:dt-newformulations");
		} else {
			showMessage("Cuidado", "Debe existir al menos una formulación", FacesMessage.SEVERITY_WARN);
		}
	}
	
	@Transactional
	public String saveList() {
		String redirect = "";
		if (newFormulations.isEmpty()) {
			showMessage("Cuidado", "Ingresa al menos una fila", FacesMessage.SEVERITY_WARN);
		} else {
			Formulation fAux = newFormulations.get(newFormulations.size() - 1);
			if (isTheRowValid(fAux)) {
				for (Formulation f : newFormulations) {
					Formulation found = formulationService.findByProductionTubAndGridingDate(production, f.getTub(), f.getGridingDate());
					if (found != null) {
						showMessage("Cuidado", "La formulación realizada en la tina: " + 
								found.getTub().getNumberTub() + "\ncon fecha de molienda: " +
								found.getGridingDate() + " ya esta registrada", FacesMessage.SEVERITY_WARN);
						return redirect;
					}
					f.setProduction(production);
				}
				
				try {
					formulationService.create(newFormulations);
					changeStatusProduction(production);
					addMessage("Operación exitosa", "Formulaciones guardadas exitosamente", FacesMessage.SEVERITY_INFO);
					redirect = "/protected/production/production-panel?faces-redirect=true&id="+production.getId();
				} catch (Exception ex) {
					showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
				}
			} else {
				showMessage("Cuidado", "Las fechas de su última formulacion no tienen orden cronológico. "
						+ "La fecha de formulación debe ser mayor o igual que la de molienda y la fecha de destilación debe "
						+ "ser mayor que la de molienda.", FacesMessage.SEVERITY_WARN);
			}
		}
		return redirect;
	}
	
	private boolean isTheRowValid(Formulation f) {
		return f.getDestilationDate().isAfter(f.getGridingDate()) &&
				(f.getFormulationDate().isAfter(f.getGridingDate()) || f.getGridingDate().equals(f.getFormulationDate()));
	}
	
	private void changeStatusProduction(StandardProduction p) {
		if (p.getProductionStatus() == ProductionStatus.PREPARATION) {
			p.setProductionStatus(ProductionStatus.FORMULATION);
			productionService.update(p);
		}
	}

	
	private void resetProduction() {
		this.palenque = production.getLotDetail().getPalenque();
		tubsByPalenque = tubService.findByPalenque(palenque);
		
		availableTubs = new ArrayList<AvailableTub>();
		tubsByPalenque.forEach(tub -> {
			availableTubs.add(new AvailableTub(tub, false));
		});
	}

	public void delete() {
		try {
			if (isValidToDelete(formulation)) {
				formulationService.delete(formulation);
				//formulations.remove(formulation);
				addMessage("Operación exitosa", "Formulación eliminada exitosamente", FacesMessage.SEVERITY_INFO);
				PrimeFaces.current().ajax().update(":form:dt-formulations");
			} else {
				showMessage("Cuidado", "Esta formulación no se puede eliminar porque la producción a la que pertenece se encuentra en otra etapa.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean isValidToDelete(Formulation f) {
		return f.getProduction().getProductionStatus() == ProductionStatus.FORMULATION;
	}
	
	public List<AvailableTub> getAvailableTubs() {
		upgradeAvailableTubs();
		return availableTubs;
	}
	
	public void upgradeAvailableTubs() {
		List<Tub> freeTubs = null;
		if (newFormulations.get(newFormulations.size() - 1).getGridingDate() != null)
			freeTubs = tubService.findTubsAvailableByDate(newFormulations.get(newFormulations.size() - 1).getGridingDate());
		
		for (AvailableTub tub: availableTubs)
			if (freeTubs != null && freeTubs.contains(tub.getTub()))
				tub.setAvailable(true);
			else
				tub.setAvailable(false);
		
		if (newFormulations.size() > 1 && freeTubs != null) {
			int x = newFormulations.size() -1;
			for (int y = 0; y < newFormulations.size() -1; y++) {
				if (newFormulations.get(x).getGridingDate().isAfter(newFormulations.get(y).getGridingDate().minusDays(1)) &&
						newFormulations.get(x).getGridingDate().isBefore(newFormulations.get(y).getDestilationDate())) {
					int index = tubsByPalenque.indexOf(newFormulations.get(y).getTub());						
					availableTubs.get(index).setAvailable(false);
				}	
			}
		}
	}
	
	public StandardProduction getProduction() {
		return production;
	}

	public Formulation getFormulation() {
		return formulation;
	}

	public void setFormulation(Formulation formulation) {
		this.formulation = formulation;
	}
	
	public List<Palenque> getPalenques() {
		palenques = palenqueService.findAllActive();
		return palenques;
	}
	
	public List<Formulation> getNewFormulations() {
		return newFormulations;
	}
}
