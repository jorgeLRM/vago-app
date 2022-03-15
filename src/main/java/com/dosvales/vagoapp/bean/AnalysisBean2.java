package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.TypeAnalysis;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class AnalysisBean2 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AnalysisService analysisService;

	@Inject
	private ProductionService productionService;
	
	private List<Analysis> analyzes;
	
	private List<Production> productions;
	
	private Analysis analysis;
	
	private Production production;
	
	private String status = "ALL";
	
	public void openNew() {
		analysis = new Analysis();
		analysis.setTypeAnalysis(TypeAnalysis.PRELIMINARY_BODY);
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				analysis = analysisService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public void tryToSave() {
		Analysis found = analysisService.findByFq(analysis.getFq());
		if (found != null) {
			showMessage("Cuidado", "Ya existe un análisis con el FQ que intentas ingresar.",FacesMessage.SEVERITY_WARN);
		} else {
			
		}
	}
	
	@Transactional
	public String save() {
		String page = "";
		Analysis found = analysisService.findByFq(analysis.getFq());
		if (found != null) {
			showMessage("Cuidado", "Ya existe un análisis con el FQ que intentas ingresar.",FacesMessage.SEVERITY_WARN);
		} else {
			try {
				updateStatus(analysis);
				production.nextStatus();
				analysis.setProduction(production);
				analysisService.save(analysis);
				productionService.update(production);
				addMessage("Operación exitosa", "Análisis guardado exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/analysis.xhtml?faces-redirect=true";
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		}
		return page;
	}
	
	public void updateStatus(Analysis a) {
		/*if (isAnalysisPositive(a)) {
			a.setAnalysisStatus(AnalysisStatus.POSITIVE);
		} else {
			a.setAnalysisStatus(AnalysisStatus.NEGATIVE);
		}*/
	}
	
	public boolean isAnalysisPositive(Analysis a) {
		/*return a.isAlcoholAccepted() && 
				a.isFurfuralAccepted() &&
				a.isMethanolAccepted();*/
		return false;
	}
	
	public String update() {
		String page = "";
		try {
			analysisService.save(analysis);
			analyzes.remove(analysis);
			addMessage("Operación exitosa", "Análisis actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/analysis.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	@Transactional
	public void delete() {
		try {
			if (isValidToDelete(analysis)) {
				production = analysis.getProduction();
				production.previousStatus();
				analysisService.delete(analysis);
				productionService.update(production);
				analyzes.remove(analysis);
				PrimeFaces.current().ajax().update(":form:dt-analysis");
				showMessage("Operación exitosa", "Análisis actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			} else {
				showMessage("Cuidado", "Este análisis no se puede eliminar porque la producción a la que pertenece está en otra etapa.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean isValidToDelete(Analysis a) {
		/*if (a.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_BODY || 
				a.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_TAIL) {
			return a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYPOSITIVE ||
					a.getProduction().getProductionStatus() == ProductionStatus.PRELIMINARYNEGATIVE;
		} else {
			return a.getProduction().getProductionStatus() == ProductionStatus.OFFICIALANALYSIS;
		}*/
		return false;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		String fileName = event.getFile().getFileName();
		String newFile = FilePath.ANALYSIS_PATH + File.separator + fileName;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			analysis.setDocument(fileName);
		} catch (Exception ex) {
			
		}
	}
	
	public void refreshTable() {
		if (status.equals("PRELIMINARY TAIL")) {
			analyzes = analysisService.findAllPreliminaryTailAnalysis();
		} else if (status.equals("PRELIMINARY BODY")) {
			analyzes = analysisService.findAllPreliminaryBodyAnalysis();
		} else if (status.equals("OFFICIAL")) {
			analyzes = analysisService.findAllOfficialAnalysis();
		} else {
			analyzes = analysisService.findAll();
		}
	}
	
	public List<Production> getProductions() {
		if (analysis.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_BODY) {
			productions = productionService.findAllWithoutPreliminaryBodyAnalysis();
		} else if (analysis.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_TAIL) {
			productions = productionService.findAllWithoutPreliminaryTailAnalysis();
		} else if (analysis.getTypeAnalysis() == TypeAnalysis.OFFICIAL) {
			productions = productionService.findAllWithoutOfficialAnalysis();
		} else {
			productions = new ArrayList<Production>();
		}
		return productions;
	}
	
	public String getAnalysisPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.ANALYSIS_DIRECTORY + File.separator + name;
	}
	
	public TypeAnalysis[] getTypesAnalyzes() {
		return TypeAnalysis.values();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProductions(List<Production> productions) {
		this.productions = productions;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}
}
