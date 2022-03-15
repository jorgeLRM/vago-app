package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.filter.ProductionFilter;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.PaymentStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.CuttingService;
import com.dosvales.vagoapp.service.ProducerService;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.util.ArrayUtil;

@Named("productionBean")
@ViewScoped
public class StandardProductionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Integer MIN_YEAR = 2021;
	private final Integer MAX_YEAR = 2070;
	
	private StandardProduction production;
	private String magueyes = "";
	private ProductionFilter filter;
	
	private List<StandardProduction> productions;
	private List<Cutting> cuttings;
	private List<Producer> producers;
	private List<Integer> years;
	
	private String firstPartLot;
	private String secondPartLot;
	private Double oldVolumeDistillation2 = 0.0;
	private String lotPreview;

	@Inject
	private StandardProductionService productionService;
	@Inject
	private CuttingService cuttingService;
	@Inject
	private ProducerService producerService;
	@Inject
	private CuttingDetailService cuttingDetailService;

	public void openNew() {
		production = new StandardProduction();
		production.setProductionStatus(ProductionStatus.PREPARATION);
		magueyes = "";
		setLotPreview("");
	}
	
	public void init() {
		filter = new ProductionFilter();
		years = ArrayUtil.createArray(MIN_YEAR, MAX_YEAR);
		producers = producerService.findAllMezcalProducers();
		refreshTable();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(id));
				oldVolumeDistillation2 = production.getVolumeDistillation2();
				magueyes = searchMagueyes(production.getLotDetail().getCutting());
			} catch (Exception ex) {}
		}
	}
	
	public String save() {
		String page = "";
		try {
			StandardProduction found = productionService.findByLot(lotPreview);
			if (found == null) {
				production.setLot(lotPreview);
				production.setTotalVolume(production.getVolumeDistillation2());
				productionService.save(production);
				addMessage("Operación exitosa", "Producción guardada exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/production/productions.xhtml?faces-redirect=true";
			} else {
				showMessage("Cuidado", "El lote que intentas ingresar ya está registrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public String update() {
		String page = "";
		try {
			production.setTotalVolume((production.getTotalVolume() - oldVolumeDistillation2) + production.getVolumeDistillation2());
			productionService.update(production);
			addMessage("Operación exitosa", "Producción actualizada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/productions.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void onCuttingLotChange() {
		if (production.getLotDetail().getCutting() != null) {
			String[] lot = production.getLotDetail().getCutting().getLot().split("-");
			firstPartLot = lot[0] + "-" + lot[1];
			secondPartLot = lot[2] + "-" + lot[3];
			buildLotPreview();
			magueyes = searchMagueyes(production.getLotDetail().getCutting());
		}else {
			firstPartLot = "";
			secondPartLot = "";
			magueyes = "";
		}
	}
	
	public void buildLotPreview() {
		setLotPreview(firstPartLot + "-" +secondPartLot);
	}
	
	public String searchMagueyes(Cutting c) {
		List<CuttingDetail> cuttingDetails = cuttingDetailService.findAllByCutting(c);
		cuttingDetails.sort((o1, o2)
				-> o1.getNumberPinneapples().compareTo(
						o2.getNumberPinneapples()));
		String cat = "";
		for (CuttingDetail cd : cuttingDetails) {
			cat += cd.getPlantation().getMaguey().getName();
			cat += ", ";
		}
		cat = cat.trim().substring(0, cat.lastIndexOf(','));
		return cat;
	}
	
	public void refreshTable() {
		productions = productionService.findAllByFilter(filter);
	}
	
	public PaymentStatus[] getPaymentStatus() {
		return PaymentStatus.values();
	}
	
	public List<Cutting> getCuttings() {
		cuttings = cuttingService.findAllAvailable();
		return cuttings;
	}
	
	public ProductionFilter getFilter() {
		return filter;
	}

	public void setFilter(ProductionFilter filter) {
		this.filter = filter;
	}

	public List<StandardProduction> getProductions() {
		return productions;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
	}

	public String getMagueyes() {
		return magueyes;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public List<Integer> getYears() {
		return years;
	}

	public String getLotPreview() {
		return lotPreview;
	}

	public void setLotPreview(String lotPreview) {
		this.lotPreview = lotPreview;
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

	public Double getOldVolumeDistillation2() {
		return oldVolumeDistillation2;
	}
}
