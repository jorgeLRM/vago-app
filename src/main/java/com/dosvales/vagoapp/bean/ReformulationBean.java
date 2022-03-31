package com.dosvales.vagoapp.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FlowEvent;

import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.Mixture;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.LotDetailService;
import com.dosvales.vagoapp.service.MixtureService;
import com.dosvales.vagoapp.service.ProductionService;

@Named
@ViewScoped
public class ReformulationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Production production;
	private Production availableProduction;
	private Producer producer;
	private List<Production> productionsAvailables;
	private List<CuttingDetail> cuttingDetails;
	private boolean newLot;
	private Double volume = 1.0;
	private String firstPartLot;
	private String secondPartLot;
	private String lotPreview;
	private String magueyes = "";
	private String sequence;
	
	@Inject
	private ProductionService productionService;
	@Inject
	private MixtureService mixtureService;
	@Inject
	private CuttingDetailService cuttingDetailService;
	@Inject
	private LotDetailService lotService;

	public void load(String idProduction) {
		if (idProduction != null && idProduction.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(idProduction));
				productionsAvailables = productionService.findAllAvailable(production.getLotDetail().getPalenque().getProducer());
				producer = production.getLotDetail().getPalenque().getProducer();
				firstPartLot = "";
				secondPartLot = "";
				newLot = false;
			} catch (Exception ex) {
				production = null;
			}
		}
	}
	
	@Transactional
	public void save() {
		if (newLot) {
			Mixture mixture1 = new Mixture();
			Mixture mixture2 = new Mixture();
			
			Production newProduction = new Production();
		} else {
			Mixture mixture = new Mixture();
		}
	}
	
	/*public void onFlowProcess(FlowEvent event) {
		String step = event.getNewStep();
		if (step.equals("tabNewLot")) {
			if (!newLot) {
				magueyes = "";
				firstPartLot = "";
				secondPartLot = "";
				lotPreview = "";
			}
		}
	}*/
	
	public String onFlowProcess(FlowEvent event) {
		String step = event.getNewStep();
		if (step.equals("tabNewLot")) {
			if (newLot) {
				updateLot();
			} else {
				step = "confirm";
			}
		}
		//System.out.println(step);
		return step;
	}
	
	public void updateLot() {
		cuttingDetails = new ArrayList<CuttingDetail>();
		List<CuttingDetail> cd1 = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
		List<CuttingDetail> cd2 = cuttingDetailService.findAllByCutting(availableProduction.getLotDetail().getCutting());
		cuttingDetails.addAll(cd1);
		cuttingDetails.addAll(cd2);
		
		sequence = String.format("%02d", lotService.countAllByYearAndProducer(
				LocalDate.now().getYear(), 
				producer.getId()) + 1);
		createSecondPartLot(cuttingDetails);
		firstPartLot = producer.getAbbreviation()+"-"+sequence;
		secondPartLot+="-"+String.valueOf(availableProduction.getLotDetail().getRegistrationDate().getYear()).substring(2,4);
		buildLotPreview();
	}
	
	/*public void onLotChange() {
		if (newLot) {
			cuttingDetails = new ArrayList<CuttingDetail>();
			List<CuttingDetail> cd1 = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
			List<CuttingDetail> cd2 = cuttingDetailService.findAllByCutting(availableProduction.getLotDetail().getCutting());
			cuttingDetails.addAll(cd1);
			cuttingDetails.addAll(cd2);
			
			sequence = String.format("%02d", lotService.countAllByYearAndProducer(
					LocalDate.now().getYear(), 
					producer.getId()) + 1);
			createSecondPartLot(cuttingDetails);
			firstPartLot = producer.getAbbreviation()+"-"+sequence;
			secondPartLot+="-"+String.valueOf(availableProduction.getLotDetail().getRegistrationDate().getYear()).substring(2,4);
			buildLotPreview();
		} else {
			lotPreview = "";
			firstPartLot = "";
			secondPartLot = "";
		}
	}*/
	
	/*public void onAvailableProductionChange() {
		if (availableProduction != null) {
			cuttingDetails = new ArrayList<CuttingDetail>();
			List<CuttingDetail> cd1 = cuttingDetailService.findAllByCutting(production.getLotDetail().getCutting());
			List<CuttingDetail> cd2 = cuttingDetailService.findAllByCutting(availableProduction.getLotDetail().getCutting());
			cuttingDetails.addAll(cd1);
			cuttingDetails.addAll(cd2);
			
			if (newLot) {
				sequence = String.format("%02d", lotService.countAllByYearAndProducer(
						LocalDate.now().getYear(), 
						producer.getId()) + 1);
				createSecondPartLot(cuttingDetails);
				firstPartLot = producer.getAbbreviation();
				secondPartLot+="-"+String.valueOf(availableProduction.getLotDetail().getRegistrationDate().getYear()).substring(2,4);
			}
		} else {
			magueyes = "";
		}
	}*/
	
	private void createSecondPartLot(List<CuttingDetail> cds) {
		HashMap<Maguey, Integer> quantities = new HashMap<Maguey, Integer>();
		for (CuttingDetail cd: cds) {
			if (quantities.containsKey(cd.getPlantation().getMaguey())) {
				quantities.put(cd.getPlantation().getMaguey(), quantities.get(cd.getPlantation().getMaguey()) + cd.getNumberPinneapples());
			} else {
				quantities.put(cd.getPlantation().getMaguey(), cd.getNumberPinneapples());
			}
		}
		
		List<Entry<Maguey, Integer>> newQuantities = new ArrayList<>(quantities.entrySet());
		newQuantities.sort(Entry.comparingByValue());
		
		magueyes = "";
		secondPartLot = "";
		for (Entry<Maguey, Integer> emi : newQuantities) {
			magueyes += emi.getKey().getName();
			magueyes += ", ";
			secondPartLot += emi.getKey().getAbbreviation();
		}
		magueyes = magueyes.trim().substring(0, magueyes.lastIndexOf(','));
	}
	
	public void buildLotPreview() {
		lotPreview = firstPartLot + "-" +secondPartLot;
	}
	
	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public boolean isNewLot() {
		return newLot;
	}

	public void setNewLot(boolean newLot) {
		this.newLot = newLot;
	}

	public List<Production> getProductionsAvailables() {
		return productionsAvailables;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
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

	public String getLotPreview() {
		return lotPreview;
	}

	public void setLotPreview(String lotPreview) {
		this.lotPreview = lotPreview;
	}

	public String getMagueyes() {
		return magueyes;
	}

	public void setMagueyes(String magueyes) {
		this.magueyes = magueyes;
	}

	public Production getAvailableProduction() {
		return availableProduction;
	}

	public void setAvailableProduction(Production availableProduction) {
		this.availableProduction = availableProduction;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public String getSequence() {
		return sequence;
	}
}
