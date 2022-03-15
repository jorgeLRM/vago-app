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

import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.Assay;
import com.dosvales.vagoapp.model.Parameter;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.TypeAnalysis;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.AssayService;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;
import com.dosvales.vagoapp.util.ParameterForTable;

@Named
@ViewScoped
public class AnalysisBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AnalysisService analysisService;
	@Inject
	private ProductionService productionService;
	@Inject
	private AssayService assayService;

	private Analysis analysis;
	private Production production;
	private List<Assay> assays;
	private List<Parameter> parameters;
	private List<ParameterForTable> parametersForTable;
	
	public void openNew(String idProduction, String typeAnalysis) {
		if (idProduction != null && idProduction.length() > 0 
				&& typeAnalysis != null && typeAnalysis.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(idProduction));
				analysis = new Analysis();
				setTypeAnalysis(Integer.valueOf(typeAnalysis));
				createParametersForTable();
			} catch(Exception ex) {
				production = null;
			}
		}
	}
	
	public void load(String id, String idProduction) {
		if (id != null && id.length() > 0 
				&& idProduction != null && idProduction.length() > 0) {
			try {
				analysis = analysisService.findWithParameters(Long.valueOf(id));
				production = productionService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	@Transactional
	public String save() {
		String page = "";
		Analysis found = analysisService.findByFq(analysis.getFq());
		if (found != null) {
			showMessage("Cuidado", "Ya existe un análisis con el FQ que intentas ingresar.",FacesMessage.SEVERITY_WARN);
		} else {
			List<Parameter> parameters = createParameters();
			if (parameters.isEmpty()) {
				showMessage("Cuidado", "Ingresa por lo menos un ensayo.",FacesMessage.SEVERITY_WARN);
			} else {
				try {
					analysis.setParameters(parameters);
					analysis.setProduction(production);
					analysisService.save(analysis);
					changeStatusProduction();
					productionService.update(production);
					addMessage("Operación exitosa", "Análisis guardado exitosamente", FacesMessage.SEVERITY_INFO);
					page = "/protected/production/production-panel.xhtml?faces-redirect=true&id="+production.getId();
				} catch (Exception ex) {
					showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
				}
			}
		}
		return page;
	}
	
	public void changeStatusProduction() {
		if (analysis.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_BODY) {
			if (analysis.isPositive()) {
				production.setProductionStatus(ProductionStatus.PRELIMINARYBODYPOSITIVE);
			} else {
				production.setProductionStatus(ProductionStatus.PRELIMINARYBODYNEGATIVE);
			}
		} else if (analysis.getTypeAnalysis() == TypeAnalysis.PRELIMINARY_TAIL) {
			if (analysis.isPositive()) {
				production.setProductionStatus(ProductionStatus.PRELIMINARYTAILPOSITIVE);
			} else {
				production.setProductionStatus(ProductionStatus.PRELIMINARYTAILNEGATIVE);
			}
		} else if (analysis.getTypeAnalysis() == TypeAnalysis.OFFICIAL) {
			production.setProductionStatus(ProductionStatus.OFFICIALANALYSIS);
		}
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
	
	public void setTypeAnalysis(int typeAnalysis) throws AppException {
		switch (typeAnalysis) {
			case 1:
				analysis.setTypeAnalysis(TypeAnalysis.PRELIMINARY_BODY);
			break;
			case 2:
				analysis.setTypeAnalysis(TypeAnalysis.PRELIMINARY_TAIL);
			break;
			case 3:
				analysis.setTypeAnalysis(TypeAnalysis.OFFICIAL);
			break;
			default:
				throw new AppException("Type analysis not found.");
		}
	}
	
	private List<Parameter> createParameters() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (ParameterForTable pft : parametersForTable) {
			if (pft.isEditable()) {
				Parameter p = new Parameter();
				p.setAnalysis(analysis);
				p.setAssay(pft.getAssay());
				p.setResult(pft.getResult());
				ps.add(p);
			}
		}
		return ps;
	}
	
	public String getAnalysisPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.ANALYSIS_DIRECTORY + File.separator + name;
	}
	
	public TypeAnalysis[] getTypesAnalyzes() {
		return TypeAnalysis.values();
	}
	
	private void createParametersForTable() {
		assays = assayService.findAll();
		parametersForTable = new ArrayList<ParameterForTable>();
		for (Assay assay: assays) {
			ParameterForTable p = new ParameterForTable();
			p.setEditable(assay.isByDefault());
			p.setAnalysis(analysis);
			p.setAssay(assay);
			parametersForTable.add(p);
		}
	}
	
	public String getClassForResult(ParameterForTable pft) {
		String resultClass = "white-row";
		try {
			if (pft.isEditable() && pft.getResult() != null) {
				Double result = pft.getResult();
				if (result >= pft.getAssay().getAllowableMinimum() && 
						result <= pft.getAssay().getMaximumAllowable()) {
					resultClass="green-row";
				} else {
					resultClass="red-row";
				}
			}
		} catch (Exception ex) {}
		return resultClass;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<ParameterForTable> getParametersForTable() {
		return parametersForTable;
	}

	public void setParametersForTable(List<ParameterForTable> parametersForTable) {
		this.parametersForTable = parametersForTable;
	}
}
