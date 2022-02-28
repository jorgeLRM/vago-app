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

import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.PalenqueService;
import com.dosvales.vagoapp.service.TubService;

@Named
@ViewScoped
public class TubBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tub tub;
	
	private List<Tub> tubs;
	
	private List<Palenque> palenques;
	
	private List<Integer> availableNumbers;
	
	@Inject
	private TubService tubService;
	
	@Inject
	private PalenqueService palenqueService;
	
	private EntityStatus status = EntityStatus.ACTIVE;
	
	public void openNew() {
		tub = new Tub();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				tub = tubService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String redirect = "";
		if (tub.getId() == null) {
			try {
				redirect = save();
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		}
		return redirect;
	}
	
	public String save() {
		tubService.save(tub);
		addMessage("Operación exitosa", "Tina guardada exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/production/tubs.xhtml?faces-redirect=true";
	}
	
	public void delete() {
		try {
			checkHasAssociations(tub);
			tubService.delete(tub);
			tubs.remove(tub);
			showMessage("Operación exitosa", "La tina ha sido eliminada exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-tubs");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Tub checkHasAssociations(Tub t) throws RelatedRecordException {
		Tub found = tubService.findWithFormulations(t.getId());
		if (found.getFormulations().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("La tina no se puede eliminar porque ya se ha utilizado anteriormente. Intenta deshabilitarlo se ya no lo necesitas.");
	}
	
	public void enabledDisabledTub() {
		try {
			String operation = tub.getStatus() == EntityStatus.ACTIVE ? "deshabilitada" : "habilitada" ;
			tub = tubService.blockUnblockTub(tub.getId());
			showMessage("Operación exitosa", "La tina ha sido "+operation+" exitosamente", FacesMessage.SEVERITY_INFO);
			tubs.remove(tub);
			PrimeFaces.current().ajax().update(":form:dt-tubs");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			tubs = tubService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			tubs = tubService.findAllInactive();
		} else {
			tubs = tubService.findAll();
		}
	}
	
	public void onPalenqueChange() {
		if (tub.getPalenque() != null) {
			availableNumbers = findAvailableNumbers(tub.getPalenque());
		} else {
			tub = new Tub();
			availableNumbers = new ArrayList<>();
		}
	}
	
	private List<Integer> findAvailableNumbers(Palenque p) {
		List<Integer> numbers = new ArrayList<Integer>();
		List<Tub> tubsByPalenque = tubService.findByPalenque(tub.getPalenque());
		int total = tubsByPalenque.isEmpty() ? 5 : tubsByPalenque.get(0).getNumberTub() + 5;
		for (int index = 1; index <= total; index++) {
			numbers.add(index);
		}
		tubsByPalenque.forEach(t -> {
			numbers.remove(t.getNumberTub());
		});
		return numbers;
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public Tub getTub() {
		return tub;
	}

	public void setTub(Tub tub) {
		this.tub = tub;
	}

	public List<Tub> getTubs() {
		return tubs;
	}

	public void setTubs(List<Tub> tubs) {
		this.tubs = tubs;
	}

	public List<Palenque> getPalenques() {
		palenques = palenqueService.findAll();
		return palenques;
	}

	public void setPalenques(List<Palenque> palenques) {
		this.palenques = palenques;
	}

	public List<Integer> getAvailableNumbers() {
		return availableNumbers;
	}

	public void setAvailableNumbers(List<Integer> availableNumbers) {
		this.availableNumbers = availableNumbers;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}
