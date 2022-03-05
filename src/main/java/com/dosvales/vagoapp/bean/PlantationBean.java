package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.Plantation;
import com.dosvales.vagoapp.model.StockStatus;
import com.dosvales.vagoapp.service.EstateService;
import com.dosvales.vagoapp.service.MagueyService;
import com.dosvales.vagoapp.service.PlantationService;

@Named
@ViewScoped
public class PlantationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final LocalDate TODAY = LocalDate.now();
	
	@Inject
	private PlantationService plantationService;
	@Inject
	private EstateService estateService;
	@Inject
	private MagueyService magueyService;
	
	private int age;
	private LocalDate registrationDate;
	private Plantation plantation;
	private Estate estate;
	private List<Plantation> newPlantations;
	private List<Plantation> plantations;
	private List<Maguey> magueyes;
	private List<Estate> estates;
	
	public void openNew() {
		newPlantations = new ArrayList<Plantation>();
		setAge(0);
		setRegistrationDate(null);
		magueyes = magueyService.findAllActive();
		setEstates(estateService.findAllActive());
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				plantation = plantationService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public void onAddRow() {
		newPlantations.add(new Plantation());
		PrimeFaces.current().ajax().update(":form:dt-newplantations");
	}
	
	public void onDeleteRow(int index) {
		newPlantations.remove(index);
		PrimeFaces.current().ajax().update(":form:dt-newplantations");
	}
	
	public String tryToSave() {
		String page = "";
		if (newPlantations.isEmpty()) {
			showMessage("Cuidado", "Ingresa al menos un registro", FacesMessage.SEVERITY_WARN);
		} else {
			boolean areValid = arePlantationsValid(newPlantations);
			if (areValid) {
				page = save();
			}
		}
		return page;
	}
	
	public boolean arePlantationsValid(List<Plantation> futurePlantations) {
		int index = 0;
		for (Plantation np : futurePlantations) {
			if (!registrationDate.isAfter(np.getPlantingDate())) {
				showMessage("Cuidado", "Fila " + (index + 1) + ": La fecha de registro y la fecha de plantación no coinciden. La fecha de plantación no puede ser mayor a la fecha de registro.", FacesMessage.SEVERITY_WARN);
				return false;
			} else {
				for (int count = index + 1; count < futurePlantations.size(); count++) {
					if (np.getMaguey().getId() == futurePlantations.get(count).getMaguey().getId()
							&& np.getPlantingDate().isEqual(newPlantations.get(count).getPlantingDate())) {
						showMessage("Cuidado", "Fila " + (index + 1)
								+ ": Dos registros tienen la misma fecha de plantación y el mismo maguey", FacesMessage.SEVERITY_WARN);
						return false;
					}
				}
			}
			np.setEstate(estate);
			String lot = buildLot(np);
			np.setLot(lot);
			np.setStock(np.getInitialStock());
			np.setRegistrationDate(registrationDate);
			index++;
		}

		index = 0;
		for (Plantation np : futurePlantations) {
			Plantation found = plantationService.findByEstateMagueyAndPlantingDate(np);
			if (found != null) {
				showMessage("Cuidado", "Fila " + (index + 1)
						+ ": Ya se encuentra registrada una plantación para este predio con el mismo maguey y fecha de plantación", FacesMessage.SEVERITY_WARN);
				return false;
			}
			index++;
		}
		return true;
	}
	
	public String buildLot(Plantation p) {
		return "\"" + p.getEstate().getName() + "\"-" + p.getMaguey().getAbbreviation().toUpperCase() + "-"
				+ p.getPlantingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String save() {
		String page = "";
		try {
			plantationService.create(newPlantations);
			addMessage("Operación exitosa", "Plantaciones guardadas exitosamente", FacesMessage.SEVERITY_INFO);
			page =  "/protected/estates/plantations.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void delete() {
		try {
			checkHasAssociations(plantation);
			plantationService.delete(plantation);
			plantations.remove(plantation);
			showMessage("Operación exitosa", "La plantación ha sido eliminada exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-plantations");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Plantation checkHasAssociations(Plantation p) throws RelatedRecordException {
		Plantation found = plantationService.findWithCuttingDetail(p.getId());
		if (found.getCuttingDetails().isEmpty()) {
			return found;
		}
		throw new RelatedRecordException("La plantación no se puede eliminar porque ya se ha utilizado anteriormente.");
	}

	public LinkedHashMap<String, String> getStockStatusLegend() {
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		values.put(StockStatus.INSTOCK.getText(), "#82E0AA");
		values.put(StockStatus.LOWSTOCK.getText(), "#F7DC6F");
		values.put(StockStatus.OUTOFSTOCK.getText(), "#E59866");
		return values;
    }
	
	public LinkedHashMap<String, String> getPlantationStatusLegend() {
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		values.put("Listo para corte", "#82E0AA");
		values.put("Falta madurar", "#E59866");
		return values;
    }
	
	public void onDateSelect(SelectEvent<LocalDate> event) {
		setAge(getYearsFromPeriod(event.getObject(), LocalDate.now()));
	}

	public int getYearsFromPeriod(LocalDate date1, LocalDate date2) {
		Period period = Period.between(date1, date2);
		return period.getYears();
	}
	
	public boolean readyToCut(Plantation p) {
		return getYearsFromPeriod(p.getPlantingDate(), TODAY) >= p.getMaguey().getAgeOfMaturation();
	}
	
	public String getClassForStatus(Plantation p) {
		if (p.getStockStatus() == StockStatus.INSTOCK) {
			return "#82E0AA";
		} else if (p.getStockStatus() == StockStatus.LOWSTOCK) {
			return "#F7DC6F";
		} else {
			return "#E59866";
		}
	}
	
	public void refreshTable() {
		plantations = plantationService.findAll();
	}

	public List<Plantation> getPlantations() {
		return plantations;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	public Plantation getPlantation() {
		return plantation;
	}

	public void setPlantation(Plantation plantation) {
		this.plantation = plantation;
	}

	public List<Maguey> getMagueyes() {
		return magueyes;
	}

	public List<Estate> getEstates() {
		return estates;
	}

	public void setEstates(List<Estate> estates) {
		this.estates = estates;
	}

	public LocalDate getTODAY() {
		return TODAY;
	}

	public List<Plantation> getNewPlantations() {
		return newPlantations;
	}

	public void setNewPlantations(List<Plantation> newPlantations) {
		this.newPlantations = newPlantations;
	}
}
