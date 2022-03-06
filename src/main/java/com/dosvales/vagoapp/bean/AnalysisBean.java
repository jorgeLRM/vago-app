package com.dosvales.vagoapp.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.TypeAnalysis;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class AnalysisBean implements Serializable {

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
	
	public String save() {
		String page = "";
		return page;
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
