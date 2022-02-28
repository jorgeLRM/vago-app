package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.model.Tail;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.service.TailService;

@Named
@ViewScoped
public class TailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Tail tail;
	
	private List<Tail> tails;
	
	private List<StandardProduction> productions;
	
	private StandardProduction production;
	
	@Inject
	private TailService tailService;

	@Inject
	private StandardProductionService productionService;
	
	public void openNew() {
		tail = new Tail();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				tail = tailService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	@Transactional
	public String save() {
		String page = "";
		try {
			tail.setStandardProduction(production);
			tailService.save(tail);
			production.setTotalVolume(production.getTotalVolume() + tail.getVolumeWater() + tail.getVolumeMezcal());
			production.nextStatus();
			productionService.update(production);
			addMessage("Operación exitosa", "Cola guardada exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/tails.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	@Transactional
	public void delete() {
		try {
			if (validToDelete(tail)) {
				StandardProduction tailProduction = tail.getStandardProduction();
				tailService.delete(tail);
				tailProduction.setTotalVolume(tailProduction.getTotalVolume() - tail.getVolumeWater() - tail.getVolumeMezcal());
				tailProduction.previousStatus();
				productionService.update(tailProduction);
				tails.remove(tail);
				showMessage("Operación exitosa", "La cola ha sido eliminada exitosamente", FacesMessage.SEVERITY_INFO);
				PrimeFaces.current().ajax().update(":form:dt-tails");
			} else {
				showMessage("Cuidado", "No se puede aliminar esta cola porque la producción a la que pertenece se encuentra en otra etapa.", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public boolean validToDelete(Tail t) {
		return t.getStandardProduction().getProductionStatus() == ProductionStatus.MIXTURE;
	}
	
	public void refreshTable() {
		tails = tailService.findAll();
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

	public List<Tail> getTails() {
		return tails;
	}

	public void setTails(List<Tail> tails) {
		this.tails = tails;
	}

	public List<StandardProduction> getProductions() {
		productions = productionService.findAllWithoutTail();
		return productions;
	}

	public void setProductions(List<StandardProduction> productions) {
		this.productions = productions;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
	}
}
