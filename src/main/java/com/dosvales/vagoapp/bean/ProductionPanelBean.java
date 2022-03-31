package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.ProductionSummary;
import com.dosvales.vagoapp.model.Tail;
import com.dosvales.vagoapp.model.Transfer;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.FormulationService;
import com.dosvales.vagoapp.service.MixtureService;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.service.TailService;
import com.dosvales.vagoapp.service.TransferService;
import com.dosvales.vagoapp.util.ColorBank;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class ProductionPanelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Production production;
	private List<CuttingDetail> cuttingDetails;
	private ProductionSummary productionSummary;
	private DonutChartModel donutModel;
	
	private Formulation formulation;
	private List<Formulation> formulations;

	private List<Analysis> analyzes;
	private Analysis analysis;
	
	private Tail tail;
	private Transfer transfer;
	
	@Inject
	private ProductionService productionService;
	@Inject
	private CuttingDetailService cuttingDetailService;
	@Inject
	private MixtureService mixtureService;
	@Inject
	private FormulationService formulationService;
	@Inject
	private AnalysisService analysisService;
	@Inject
	private TailService tailService;
	@Inject
	private TransferService transferService;
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(id));
				cuttingDetails = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
				formulations = formulationService.findAllByProduction(production);
				analyzes = analysisService.findAllByProduction(production);
				productionSummary = new ProductionSummary(production, cuttingDetailService, mixtureService);
				createDonutModel();
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
	
	public void createDonutModel() {
		donutModel = new DonutChartModel();
		ChartData data = new ChartData();
		
		DonutChartDataSet dataSet = new DonutChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<String>();
		List<String> bgColors = new ArrayList<>();
		ColorBank.resetPosition();
		Iterator<Entry<Maguey, Double>> it = productionSummary.getPercentages().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Maguey, Double> entry = (Map.Entry<Maguey, Double>)it.next();
			labels.add(entry.getKey().getName());
			values.add(entry.getValue());
			String color = ColorBank.getColor();
			bgColors.add("rgb("+color+")");
		}
		dataSet.setData(values);
		dataSet.setBackgroundColor(bgColors);
		data.addChartDataSet(dataSet);
		data.setLabels(labels);
		
		donutModel.setData(data);
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
	
	public void deleteFormulation() {
		try {
			formulationService.delete(formulation);
			formulations.remove(formulation);
			showMessage("Operación exitosa", "Formulación eliminada exitosamente", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			ex.printStackTrace();
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	@Transactional
	public void deleteAnalysis() {
		try {
			analysisService.delete(analysis);
			showMessage("Operación exitosa", "Análisis eliminado exitosamente", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	@Transactional
	public void deleteTail() {
		try {
			tail = production.getTail();
			if (tail != null) {
				double newVolume = production.getTotalVolume() - (tail.getVolumeWater() + tail.getVolumeMezcal());
				tailService.delete(tail);
				production.setTotalVolume(newVolume);
				productionService.update(production);
				production.setTail(null);
			} else {
				showMessage("Cuidado", "No existe ninguna cola que eliminar.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	@Transactional
	public void deleteTransfer() {
		try {
			transfer = production.getTransfer();
			if (transfer != null) {
				transferService.delete(transfer);
				production.setTransfer(null);
			} else {
				showMessage("Cuidado", "No existe ningun traslado que eliminar.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void reprobate() {
		try {
			production.setProductionStatus(ProductionStatus.REPROBATE);
			productionService.update(production);
			showMessage("Operación exitosa", "La producción fue reprobada", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
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

	public Production getProduction() {
		return production;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}

	public ProductionSummary getProductionSummary() {
		return productionSummary;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
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

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public List<Analysis> getAnalyzes() {
		return analyzes;
	}

	public void setAnalyzes(List<Analysis> analyzes) {
		this.analyzes = analyzes;
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
}
