package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class ProductionPanelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private StandardProductionService productionService;
	
	@Inject
	private CuttingDetailService cuttingDetailService;
	
	private StandardProduction production;
	
	private List<CuttingDetail> cuttingDetails;
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findWithAssociations(Long.valueOf(id));
				cuttingDetails = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public double calculateVolumeOfFirstDistillation () {
		double result = 0;
		if (production != null) {
			Set<Formulation> formulations = production.getFormulations();
			for (Formulation formulation : formulations) {
				result += formulation.getOrdinary();
			}
		}
		return result;
	}
	
	public double calculateTotalVolumeFermentedWort() {
		double result = 0;
		if (production != null) {
			Set<Formulation> formulations = production.getFormulations();
			for (Formulation formulation : formulations) {
				result += formulation.getVolumeFermentedWort();
			}
		}
		return result;
	}
	
	public double calculatePerformance() {
		double result = 0;
		if (production != null && production.getTotalVolume() > 0 && production.getAlcoholicGradeDist2() > 0) {
			double weight = calculateWeightOfMaguey();
			result = (weight /((production.getTotalVolume() * production.getAlcoholicGradeDist2()) / 50)) / 100;
		}
		return result;
	}
	
	private double calculateWeightOfMaguey() {
		double result = 0;
		if (cuttingDetails.size() > 0) {
			for (CuttingDetail cuttingDetail : cuttingDetails) {
				result += cuttingDetail.getWeight();
			}
		}
		return result;
	}
	
	public String cheangeInbulkStatus() {
		String page = "";
		if (production != null) {
			try {
				production.setProductionStatus(ProductionStatus.INBULK);
				productionService.update(production);
				addMessage("Operación exitosa", "La producción " + production.getLot() + " cambió su estado a granel.", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/productions.xhtml?faces-redirect=true";
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		}
		return page;
	}
	
	public String cheangeCanceledStatus() {
		String page = "";
		if (production != null) {
			try {
				production.setProductionStatus(ProductionStatus.CANCELED);
				productionService.update(production);
				addMessage("Operación exitosa", "La producción " + production.getLot() + " se ha cancelado.", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/productions.xhtml?faces-redirect=true";
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		}
		return page;
	}
	
	public String cheangeReprobateStatus() {
		String page = "";
		if (production != null) {
			try {
				production.setProductionStatus(ProductionStatus.REPROBATE);
				productionService.update(production);
				addMessage("Operación exitosa", "La producción " + production.getLot() + " se ha reprobado.", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/productions.xhtml?faces-redirect=true";
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		}
		return page;
	}
	
	public int getYears(LocalDate date) {
		Period period = Period.between(date, LocalDate.now());
		return period.getYears();
	}
	
	public String getCuttingsPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.CUTTING_DIRECTORY + File.separator + name;
	}
	
	public String getAnalysisPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.ANALYSIS_DIRECTORY + File.separator + name;
	}
	
	public String getTransferPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.TRANSFER_DIRECTORY + File.separator + name;
	}
	
	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
	}

	public Analysis getOfficialAnalysis() {
		Analysis analysis = null;
		if (production.getAnalyzes().size() == 2) {
			analysis = production.getAnalyzes().toArray(new Analysis[production.getAnalyzes().size()])[1];
		}
		return analysis;
	}

	public Analysis getPreliminaryAnalysis() {
		Analysis analysis = null;
		if (production.getAnalyzes().size() == 1 || production.getAnalyzes().size() == 2) {
			analysis = production.getAnalyzes().toArray(new Analysis[production.getAnalyzes().size()])[0];
		}
		return analysis;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}
}
