package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.PaymentStatus;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.CuttingService;
import com.dosvales.vagoapp.service.StandardProductionService;

@Named()
@ViewScoped
public class StandardProductionBean2 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private StandardProductionService productionService;
	
	@Inject
	private CuttingService cuttingService;
	
	private String magueyes = "";
	
	private Double oldVolumeDistillation2 = 0.0;
	
	private String firstPartLot;
	
	private String secondPartLot;
	
	private StandardProduction production;
	
	private List<StandardProduction> productions;
	
	private List<Cutting> cuttings;
	
	private String status = "IN PROCESS";
	
	public void openNew() {
		production = new StandardProduction();
		production.setProductionStatus(ProductionStatus.PREPARATION);
		magueyes = "";
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(id));
				oldVolumeDistillation2 = production.getVolumeDistillation2();
				magueyes = production.getLotDetail().getCutting().getLot().split("-")[2];
			} catch (Exception ex) {}
		}
	}
	
	public void loadPanel(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findWithAssociations(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String save() {
		String page = "";
		try {
			production.setLot(firstPartLot + "-" + secondPartLot);
			StandardProduction found = productionService.findByLot(production.getLot());
			if (found == null) {
				production.setTotalVolume(production.getVolumeDistillation2());
				productionService.save(production);
				addMessage("Operación exitosa", "Producción guardada exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/productions.xhtml?faces-redirect=true";
			} else {
				showMessage("Cuidado", "El lote que intentas ingresar ya está registrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public String update() {
		String page = "";
		try {
			production.setTotalVolume((production.getTotalVolume() - oldVolumeDistillation2) + production.getVolumeDistillation2());
			productionService.update(production);
			addMessage("Operación exitosa", "Producción actualizada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/productions.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void onCuttingLotChange() {
		if (production.getLotDetail().getCutting() != null) {
			String[] lot = production.getLotDetail().getCutting().getLot().split("-");
			firstPartLot = lot[0] + "-" + lot[1];
			secondPartLot = lot[2] + "-" + lot[3];
			magueyes = lot[2];
		}else {
			firstPartLot = "";
			secondPartLot = "";
			magueyes = "";
		}
	}
	
	public void refreshTable() {
		if (status.equals("IN PROCESS")) {
			productions = productionService.findAllInProcess();
		} else if (status.equals("TERMINATED")) {
			productions = productionService.findAllTerminated();
		}else if (status.equals("CANCELED")) {
			productions = productionService.findAllCanceled();
		} else {
			productions = productionService.findAll();
		}
	}
	
	public PaymentStatus[] getPaymentStatus() {
		return PaymentStatus.values();
	}
	
	public String getFirstPartLot() {
		return firstPartLot;
	}

	public void setFirstPartLot(String firstPartLot) {
		this.firstPartLot = firstPartLot;
	}

	public String getSecondPartLot() {
		return secondPartLot;
	}

	public void setSecondPartLot(String secondPartLot) {
		this.secondPartLot = secondPartLot;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
	}

	public List<StandardProduction> getProductions() {
		return productions;
	}

	public List<Cutting> getCuttings() {
		cuttings = cuttingService.findAllAvailable();
		return cuttings;
	}

	public String getMagueyes() {
		return magueyes;
	}

	public void setMagueyes(String magueyes) {
		this.magueyes = magueyes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
