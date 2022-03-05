package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.LotDetail;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.PaymentStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.ReformulatedDetail;
import com.dosvales.vagoapp.model.ReformulatedProduction;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.LotDetailService;
import com.dosvales.vagoapp.service.ReformulatedProductionService;
import com.dosvales.vagoapp.service.StandardProductionService;

@Named("reformulationBean")
@ViewScoped
public class ReformulatedProductionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReformulatedProductionService reformulationService;
	
	@Inject
	private StandardProductionService productionService;
	
	@Inject
	private CuttingDetailService cuttingDetailService;
	
	@Inject
	private LotDetailService lotDetailService;
	
	private String firstPartLot;
	
	private String secondPartLot;
	
	private Integer sequence;
	
	private Palenque palenque;
	
	private Double volumeFailedProduction;
	
	private Double volumeProductionAvailable;
	
	private StandardProduction failedProduction;
	
	private StandardProduction productionAvailable;
	
	private ReformulatedProduction reformulation;
	
	private List<StandardProduction> failedProductions;
	
	private List<StandardProduction> productionsAvailable;
	
	private List<ReformulatedProduction> reformulations;
	
	public void openNew() {
		reformulation = new ReformulatedProduction();
		reformulation.setProductionStatus(ProductionStatus.PREPARATIONREFORMULATION);
		firstPartLot = "";
		secondPartLot = "";
	}
	
	public String save() {
		String page = "";
		try {
			reformulation.setLot(firstPartLot + "-" + secondPartLot);
			ReformulatedProduction found = reformulationService.findByLot(reformulation.getLot());
			if (found == null) {
				ReformulatedDetail failedReformulatedDetail = new ReformulatedDetail();
				failedReformulatedDetail.setStandardProduction(failedProduction);
				failedReformulatedDetail.setVolume(volumeFailedProduction);
				failedReformulatedDetail.setReformulatedProduction(reformulation);
				
				ReformulatedDetail reformulatedDetailAvailable = new ReformulatedDetail();
				reformulatedDetailAvailable.setStandardProduction(productionAvailable);
				reformulatedDetailAvailable.setVolume(volumeProductionAvailable);
				reformulatedDetailAvailable.setReformulatedProduction(reformulation);
				
				List<ReformulatedDetail> reformulatedDetails = new ArrayList<ReformulatedDetail>();
				reformulatedDetails.add(reformulatedDetailAvailable);
				reformulatedDetails.add(failedReformulatedDetail);
				
				LotDetail lotDetail = new LotDetail();
				lotDetail.setSequence(sequence);
				lotDetail.setPalenque(palenque);
				lotDetail.setRegistrationDate(LocalDate.now());
				lotDetailService.save(lotDetail);
				
				reformulation.setLotDetail(lotDetail);
				reformulation.setTotalVolume(volumeFailedProduction + volumeProductionAvailable);
				reformulation.setReformulatedDetails(reformulatedDetails);
				reformulationService.save(reformulation);
				
				addMessage("Operación exitosa", "Reformulación guardada exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/reformulations.xhtml?faces-redirect=true";
			} else {
				showMessage("Cuidado", "El lote que intentas ingresar ya está registrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void onFailedProductionChange() {
		if (failedProduction != null) {
			palenque = failedProduction.getLotDetail().getPalenque();
			Producer producer = palenque.getProducer();
			productionsAvailable = productionService.findAllAvailable(producer);
			sequence = lotDetailService.countAllByYearAndProducer(LocalDate.now().getYear(), producer.getId());
			firstPartLot = producer.getAbbreviation() + "-" + String.format("%02d", sequence+1);
			//secondPartLot = failedProduction.getLotDetail().getCutting().getLot().split("-")[2];
		} else {
			productionsAvailable = new ArrayList<StandardProduction>();
			firstPartLot = "";
			secondPartLot = "";
		}
	}
	
	public void onProductionAvailableChange() {
		if (productionAvailable != null && failedProduction != null) {
			List<CuttingDetail> cuttingDetails1 = cuttingDetailService.findAllByCutting(productionAvailable.getLotDetail().getCutting());
			List<CuttingDetail> cuttingDetails2 = cuttingDetailService.findAllByCutting(failedProduction.getLotDetail().getCutting());
			List<CuttingDetail> cuttingDetails = Stream.concat(cuttingDetails1.stream(), cuttingDetails2.stream()).collect(Collectors.toList());
			secondPartLot = "";
			cuttingDetails.sort((o1, o2)
					-> o1.getNumberPinneapples().compareTo(
							o2.getNumberPinneapples()));
			cuttingDetails.forEach(cd -> {
				secondPartLot += cd.getPlantation().getMaguey().getAbbreviation();
			});
			secondPartLot += "-" + String.valueOf(LocalDate.now()).substring(2,4);
		}
	}
	
	public void refreshTable() {
		reformulations = reformulationService.findAll();
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

	public StandardProduction getFailedProduction() {
		return failedProduction;
	}

	public void setFailedProduction(StandardProduction failedProduction) {
		this.failedProduction = failedProduction;
	}

	public List<ReformulatedProduction> getReformulations() {
		return reformulations;
	}

	public String getSecondPartLot() {
		return secondPartLot;
	}

	public void setSecondPartLot(String secondPartLot) {
		this.secondPartLot = secondPartLot;
	}

	public ReformulatedProduction getReformulation() {
		return reformulation;
	}

	public void setReformulation(ReformulatedProduction reformulatedProduction) {
		this.reformulation = reformulatedProduction;
	}

	public StandardProduction getProductionAvailable() {
		return productionAvailable;
	}

	public List<StandardProduction> getFailedProductions() {
		failedProductions = productionService.findAllFails();
		return failedProductions;
	}

	public List<StandardProduction> getProductionsAvailable() {
		return productionsAvailable;
	}
	
	public Double getVolumeFailedProduction() {
		return volumeFailedProduction;
	}

	public void setVolumeFailedProduction(Double volumeFailedProduction) {
		this.volumeFailedProduction = volumeFailedProduction;
	}

	public Double getVolumeProductionAvailable() {
		return volumeProductionAvailable;
	}

	public void setVolumeProductionAvailable(Double volumeProductionAvailable) {
		this.volumeProductionAvailable = volumeProductionAvailable;
	}

	public void setProductionAvailable(StandardProduction productionAvailable) {
		this.productionAvailable = productionAvailable;
	}
}
