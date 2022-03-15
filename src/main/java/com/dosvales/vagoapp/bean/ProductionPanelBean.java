package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.model.TypeAnalysis;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.FormulationService;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class ProductionPanelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StandardProduction production;
	private List<CuttingDetail> cuttingDetails;
	private Formulation formulation;
	private List<Formulation> formulations;
	private List<Analysis> analyzes;
	private Analysis analysis;
	
	@Inject
	private StandardProductionService productionService;
	@Inject
	private CuttingDetailService cuttingDetailService;
	@Inject
	private FormulationService formulationService;
	@Inject
	private AnalysisService analysisService;
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(id));
				cuttingDetails = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
				formulations = formulationService.findAllByProduction(production);
				analyzes = analysisService.findAllByProduction(production);
			} catch (Exception ex) {
				ex.printStackTrace();
				production = null;
			}
		}
	}
	
	public double calculateVolumeOfFirstDistillation () {
		double result = 0;
		if (production != null) {
			for (Formulation formulation : formulations) {
				result += formulation.getOrdinary();
			}
		}
		return result;
	}
	
	public double calculateTotalVolumeFermentedWort() {
		double result = 0;
		if (production != null) {
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
	
	public String checkCooking() {
		String page = "";
		if (production.getEndCoocking() != null) {
			page = "/protected/production/new-formulation.xhtml?faces-redirect=true&idProduction="+production.getId();
		} else {
			showMessage("Cuidado", "Ingresa la fecha de la última cocción", FacesMessage.SEVERITY_WARN);
		}
		return page;
	}
	
	@Transactional
	public void deleteFormulation() {
		try {
			if (isValidToDeleteFormulation(formulation)) {
				formulationService.delete(formulation);
				formulations.remove(formulation);
				showMessage("Operación exitosa", "Formulación eliminada exitosamente", FacesMessage.SEVERITY_INFO);
				PrimeFaces.current().ajax().update(":form:tabview-production:dt-formulations");
			} else {
				showMessage("Cuidado", "Esta formulación no se puede eliminar porque la producción a la que pertenece se encuentra en otra etapa.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean isValidToDeleteFormulation(Formulation f) {
		return f.getProduction().getProductionStatus() == ProductionStatus.FORMULATION;
	}
	
	@Transactional
	public void deleteAnalysis() {
		try {
			if (isValidToDeleteAnalysis(analysis)) {
				production.previousStatus();
				analysisService.delete(analysis);
				productionService.update(production);
				analyzes.remove(analysis);
				showMessage("Operación exitosa", "Análisis eliminado exitosamente", FacesMessage.SEVERITY_INFO);
				PrimeFaces.current().ajax().update(":form:tabview-production:dt-analyzes");
			} else {
				showMessage("Cuidado", "Este análisis no se puede eliminar porque la producción a la que pertenece está en otra etapa.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean isValidToDeleteAnalysis(Analysis a) {
		if (a.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_BODY) {
			return a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYBODYNEGATIVE ||
					a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYBODYPOSITIVE;
		} else if (a.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_TAIL) {
			return a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYTAILNEGATIVE || 
					a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYTAILPOSITIVE;
		} else if (a.getTypeAnalysis() == TypeAnalysis.OFFICIAL) {
			return a.getProduction().getProductionStatus() == ProductionStatus.OFFICIALANALYSIS;
		}
		return false;
	}
	
	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
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

	public List<Formulation> getFormulations() {
		return formulations;
	}

	public List<Analysis> getAnalyzes() {
		return analyzes;
	}

	public void setAnalyzes(List<Analysis> analyzes) {
		this.analyzes = analyzes;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}
}
