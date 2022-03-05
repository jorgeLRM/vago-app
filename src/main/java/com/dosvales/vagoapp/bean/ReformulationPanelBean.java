package com.dosvales.vagoapp.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.ReformulatedProduction;
import com.dosvales.vagoapp.service.ReformulatedProductionService;

@Named
@ViewScoped
public class ReformulationPanelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReformulatedProductionService reformulationService;
	
	private ReformulatedProduction reformulation;
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				reformulation = reformulationService.findWithAssociations(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}

	public ReformulatedProduction getReformulation() {
		return reformulation;
	}

	public void setReformulation(ReformulatedProduction reformulation) {
		this.reformulation = reformulation;
	}
	
}
