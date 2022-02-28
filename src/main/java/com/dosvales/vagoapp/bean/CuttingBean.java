package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.LotDetail;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Plantation;
import com.dosvales.vagoapp.service.CuttingService;
import com.dosvales.vagoapp.service.EstateService;
import com.dosvales.vagoapp.service.LotDetailService;
import com.dosvales.vagoapp.service.PalenqueService;
import com.dosvales.vagoapp.service.PlantationService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class CuttingBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CuttingService cuttingService;
	
	@Inject
	private PalenqueService palenqueService;
	
	@Inject
	private EstateService estateService;
	
	@Inject
	private PlantationService plantationService;

	@Inject
	private LotDetailService lotService;
	
	private Integer quantityTotal;
	
	private Integer weightTotal;
	
	private Cutting cutting;
	
	private Estate estate;
	
	private DualListModel<Plantation> dualListPlantation;
	
	private List<Cutting> cuttings;
	
	private List<Palenque> palenques;
	
	private List<Estate> estates;
	
	private List<CuttingDetail> cuttingDetailWrapper;
	
	private String status = "ACEPTADOS";
	
	public void openNew() {
		cutting = new Cutting();
		cutting.setLotDetail(new LotDetail());
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				cutting = cuttingService.findWithCuttingDetail(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String onFlowProcess(FlowEvent event) {
		String step = event.getNewStep();
		if (step.equals("cdMagueyes")) {
			if (cutting.getGuideLink() == null) {
				step = event.getOldStep();
				showMessage("Cuidado", "Agrega la guía de corte.", FacesMessage.SEVERITY_WARN);
			} else {
				if (!exists(cutting)) {
					dualListPlantation = createListModel(estate);
				} else {
					step = event.getOldStep();
					showMessage("Cuidado", "Ya se encuentra un registro con el mismo número de guía.", FacesMessage.SEVERITY_WARN);
				}
			}
		}
		if (step.equals("cdDetail")) {
			cuttingDetailWrapper = new ArrayList<CuttingDetail>();
			for (Plantation pt : dualListPlantation.getTarget()) {
				CuttingDetail cd = new CuttingDetail();
				cd.setCutting(cutting);
				cd.setPlantation(pt);
				cd.setNumberPinneapples(1);
				cd.setWeight(1);
				cuttingDetailWrapper.add(cd);
			}
		}
		return step;
	}
	
	private boolean exists(Cutting c) {
		Cutting found = cuttingService.findByGuideNumber(c.getGuideNumber());
		return found != null;
	}
	
	public String confirm() {
		String redirect = "";
		try {
			String lot = buildLot();
			cutting.setLot(lot);
			cutting.setCuttingDetails(cuttingDetailWrapper);
			cutting.getLotDetail().setRegistrationDate(LocalDate.now());
			cuttingService.save(cutting);
			addMessage("Operación exitosa", "Corte guardado exitosamente", FacesMessage.SEVERITY_INFO);
			redirect = "/protected/estates/cuttings.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return redirect;
	}
	
	private String buildLot() throws Exception {
		if (cutting != null && cuttingDetailWrapper != null && estate != null) {
			Palenque palenque = cutting.getLotDetail().getPalenque();
			
			String producerAbbreviation = palenque.getProducer().getAbbreviation();
			String sequence = String.format("%02d", lotService.countAllByYearAndProducer(
					LocalDate.now().getYear(), 
					palenque.getProducer().getId()) + 1);
			cutting.getLotDetail().setSequence(Integer.valueOf(sequence));
			String abbreviations = "";
			
			cuttingDetailWrapper.sort((o1, o2)
					-> o1.getWeight().compareTo(
							o2.getWeight()));
			
			for (CuttingDetail cd : cuttingDetailWrapper) {
				abbreviations += cd.getPlantation().getMaguey().getAbbreviation();
			}
			
			String lot = producerAbbreviation + "-" + sequence + "-" + abbreviations + "-" + 
						String.valueOf(LocalDate.now()).substring(2,4);
			return lot;
		} else {
			throw new Exception("Lot was not created.");
		}
	}
	
	public DualListModel<Plantation> createListModel(Estate e) {
		List<Plantation> source = plantationService.findAllReadyForCuttingByEstate(e.getId());
		return new DualListModel<Plantation>(source, new ArrayList<Plantation>());
	}
	
	public void tryToCancel() {
		try {
			checkHasAssociations(cutting);
			cuttingService.cancel(cutting);
			cuttings.remove(cutting);
			showMessage("Operación exitosa", "El predio ha sido cancelado exitosamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-cuttings");
		} catch (RelatedRecordException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public Cutting checkHasAssociations(Cutting e) throws RelatedRecordException {
		Cutting found = cuttingService.findWithProduction(e.getId());
		if (found.getLotDetail().getProduction() == null) {
			return found;
		}
		throw new RelatedRecordException("El corte no se puede cancelar porque ya se ha utilizado anteriormente.");
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		String fileName = event.getFile().getFileName();
		String newFile = FilePath.CUTTINGS_PATH + File.separator + fileName;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			cutting.setGuideLink(fileName);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public String update() {
		String page = "";
		if (cutting != null) {
			try {
				int lastIndex = cuttings.lastIndexOf(cutting);
				cutting = cuttingService.update(cutting);
				cuttings.set(lastIndex, cutting);
				addMessage("Operación exitosa", "Corte actualizado exitosamente", FacesMessage.SEVERITY_INFO);
				page = "/protected/estates/cuttings.xhtml?faces-redirect=true";
			} catch (Exception ex) {
				showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
			}
		} else {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public String getCuttingsPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.CUTTING_DIRECTORY + File.separator + name;
	}
	
	public int getYears(LocalDate date) {
		Period period = Period.between(date, LocalDate.now());
		return period.getYears();
	}

	public Integer getQuantityTotal(List<CuttingDetail> cds) {
		quantityTotal = cds.stream().mapToInt(CuttingDetail::getNumberPinneapples).sum();
		return quantityTotal;
	}
	
	public Integer getWeightTotal(List<CuttingDetail> cds) {
		weightTotal = cds.stream().mapToInt(CuttingDetail::getWeight).sum();
		return weightTotal;
	}
	
	public void refreshTable() {
		if (status.equals("ACEPTADOS")) {
			cuttings = cuttingService.findAllActive();
		} else if (status.equals("CANCELADOS")) {
			cuttings = cuttingService.findAllInactive();
		} else {
			cuttings = cuttingService.findAll();
		}
	}
	
	public void setWeightTotal(Integer weightTotal) {
		this.weightTotal = weightTotal;
	}

	public Cutting getCutting() {
		return cutting;
	}

	public void setCutting(Cutting cutting) {
		this.cutting = cutting;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	public DualListModel<Plantation> getDualListPlantation() {
		return dualListPlantation;
	}

	public void setDualListPlantation(DualListModel<Plantation> dualListPlantation) {
		this.dualListPlantation = dualListPlantation;
	}

	public List<CuttingDetail> getCuttingDetailWrapper() {
		return cuttingDetailWrapper;
	}

	public void setCuttingDetailWrapper(List<CuttingDetail> cuttingDetailWrapper) {
		this.cuttingDetailWrapper = cuttingDetailWrapper;
	}

	public List<Cutting> getCuttings() {
		return cuttings;
	}

	public List<Palenque> getPalenques() {
		palenques = palenqueService.findAll();
		return palenques;
	}

	public List<Estate> getEstates() {
		estates = estateService.findAll();
		return estates;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
