package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.Tail;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.service.TailService;

@Named
@ViewScoped
public class TailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Tail tail;
	private Production production;
	
	@Inject
	private TailService tailService;
	@Inject
	private ProductionService productionService;
	
	public void openNew(String idProduction) {
		if (idProduction != null && idProduction.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(idProduction));
				if (production.getTail() == null) {
					tail = new Tail();
				} else {
					throw new Exception();
				}
			} catch (Exception ex) {
				production = null;
			}
		}
	}
	
	public void load(String idProduction, String id) {
		if (idProduction != null && idProduction.length() > 0 
				&& id != null && id.length() > 0) {
			try {
				production = productionService.findById(Long.valueOf(idProduction));
				tail = tailService.findById(Long.valueOf(id));
			} catch (Exception ex) {
				production = null;
			}
		}
	}
	
	@Transactional
	public String save() {
		String page = "";
		try {
			tail.setProduction(production);
			tailService.save(tail);
			production.setTotalVolume(production.getTotalVolume() + tail.getVolumeWater() + tail.getVolumeMezcal());
			production.setProductionStatus(ProductionStatus.MIXTURE);
			productionService.update(production);
			addMessage("Operación exitosa", "Cola guardada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/production-panel.xhtml?faces-redirect=true&id="+production.getId();
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Tail getTail() {
		return tail;
	}
	
	public void setTail(Tail tail) {
		this.tail = tail;
	}
	
	public Production getProduction() {
		return production;
	}
	
	public void setProduction(Production production) {
		this.production = production;
	}
}
