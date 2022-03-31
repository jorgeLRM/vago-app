package com.dosvales.vagoapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.MixtureService;

public class ProductionSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private Production production;
	private List<Mixture> mixtures;
	private List<CuttingDetail> cuttingDetails;
	
	private HashMap<Maguey, Double> percentages;
	private HashMap<Maguey, Integer> quantities;
	private Integer total;
	
	private CuttingDetailService cuttingDetailService;
	private MixtureService mixtureService;
	
	public ProductionSummary(Production production, CuttingDetailService cuttingDetailService, MixtureService mixtureService) {
		this.production = production;
		this.cuttingDetailService = cuttingDetailService;
		this.mixtureService = mixtureService;
		createComposition();
		createQuantitiesAndPercentages(cuttingDetails);
	}
	
	public void createComposition() {
		cuttingDetails = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
		if (production.getTypeProduction() != TypeProduction.NORMAL) {
			mixtures = mixtureService.findAllByBaseProduction(production);
			for (Mixture m : mixtures) {
				List<CuttingDetail> cds = cuttingDetailService.findAllByCutting(m.getComplementaryProduction().getLotDetail().getCutting());
				cuttingDetails.addAll(cds);
			}
		}
	}
	
	public void createQuantitiesAndPercentages(List<CuttingDetail> cds) {
		quantities = new HashMap<Maguey, Integer>();
		total = 0;
		for (CuttingDetail cd: cds) {
			if (quantities.containsKey(cd.getPlantation().getMaguey())) {
				quantities.put(cd.getPlantation().getMaguey(), quantities.get(cd.getPlantation().getMaguey()) + cd.getNumberPinneapples());
			} else {
				quantities.put(cd.getPlantation().getMaguey(), cd.getNumberPinneapples());
			}
			total += cd.getNumberPinneapples();
		}
		
		percentages = new HashMap<Maguey, Double>();
		for (Maguey m : quantities.keySet()) {
			Double percentage = (double) Math.round(((quantities.get(m) * 100.0) / total));
			percentages.put(m, percentage);
		}
	}
	
	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public List<Mixture> getMixtures() {
		return mixtures;
	}

	public void setMixtures(List<Mixture> mixtures) {
		this.mixtures = mixtures;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}

	public void setCuttingDetails(List<CuttingDetail> cuttingDetails) {
		this.cuttingDetails = cuttingDetails;
	}

	public HashMap<Maguey, Double> getPercentages() {
		return percentages;
	}

	public void setPercentages(HashMap<Maguey, Double> percentages) {
		this.percentages = percentages;
	}

	public HashMap<Maguey, Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(HashMap<Maguey, Integer> quantities) {
		this.quantities = quantities;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
