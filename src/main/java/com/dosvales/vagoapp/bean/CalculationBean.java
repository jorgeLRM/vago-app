package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.RowCalculation;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.CalculationService;
import com.dosvales.vagoapp.service.StandardProductionService;

@Named
@ViewScoped
public class CalculationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CalculationService calculationService;
	
	@Inject
	private StandardProductionService productionService;
	
	private List<Calculation> calculations;
	
	private List<RowCalculation> rows;
	
	private List<StandardProduction> productions;
	
	private Calculation calculation;
	
	private StandardProduction production;
	
	private double volumeTail;
	
	private double volumeWater;
	
	private RowCalculation row;
	
	public void openNew() {
		calculation = new Calculation();
		rows = new ArrayList<RowCalculation>();
		productions = productionService.findAll();
		row = new RowCalculation();
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				calculation = calculationService.findWithRows(Long.valueOf(id));
				rows = calculation.getRowsCalculation();
				production = calculation.getProduction();
				row = new RowCalculation();
			} catch (Exception ex) {}
	}
	
	public void onAddRow() {
		rows.add(new RowCalculation());
		rows.get(rows.size() - 1).setCalculation(calculation);
	}
	
	public void onDeleteRow(int index) {
		if (rows.size() > 1) {
			rows.remove(index);
			PrimeFaces.current().ajax().update(":calculatorForm:dt-newrowcalculations");
		} else {
			showMessage("Cuidado", "Debe existir al menos una formulación", FacesMessage.SEVERITY_WARN);
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			calculation.setTime(new Timestamp(System.currentTimeMillis()));
			calculation.setRowsCalculation(rows);
			calculation.setProduction(production);
			calculationService.save(calculation);
			addMessage("Operación exitosa", "Cálculo guardado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/production/calculations.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public void delete() {
		try {
			calculationService.delete(calculation);
			calculations.remove(calculation);
			showMessage("Operación exitosa", "El cálculo fue eliminado correctamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-calculations");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intentelo más tarde.", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void refreshTable() {
		if (productions == null) {
			productions = productionService.findAll();
			if (production == null && productions.size() > 0) {
				production = productions.get(0);
			}
		}
		calculations = calculationService.findByProduction(production);
	}

	public List<Calculation> getCalculations() {
		return calculations;
	}

	public List<RowCalculation> getRows() {
		if (rows != null && rows.size() > 0) {
			if (rows.get(0).getAlcohol() == 0 && rows.get(0).getVolume() == 0) {
				rows.get(0).setName(production.getLot() + " Cuerpo");
				rows.get(0).setVolume(production.getVolumeDistillation2().intValue());
				rows.get(0).setAlcohol(production.getAlcoholicGradeDist2());
			}
			
			Double totalVolume = 0d;
			for(int x = 0; x < rows.size(); x++)
				totalVolume += rows.get(x).getVolume();
			
			for(int x = 0; x < rows.size(); x++)
				rows.get(x).setPercentage(Math.round(((rows.get(x).getVolume()/totalVolume) * 100) * 100.0)/100.0);
		}
		return rows;
	}

	public List<StandardProduction> getProductions() {
		return productions;
	}

	public Calculation getCalculation() {
		return calculation;
	}

	public void setCalculation(Calculation calculation) {
		this.calculation = calculation;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
		if (calculation != null)
			calculation.setAlcoholicGradeDesired(50d);
	}

	public double getVolumeTail() {
		if (production != null)
			volumeTail = volumeWater/4;
		else
			volumeTail = 0d;
		return volumeTail;
	}

	public double getVolumeWater() {
		if (production != null)
			volumeWater = production.getVolumeDistillation2() * (production.getAlcoholicGradeDist2()/calculation.getAlcoholicGradeDesired()) - production.getVolumeDistillation2();
		else
			volumeWater = 0d;
		return volumeWater;
	}

	public RowCalculation getRow() {
		Double resultPercentage = 0d;
		Integer totalVolume = 0;
		Double resultAlcohol = 0d;
		Double resultMethanol = 0d;
		Double resultHigherAlcohols = 0d;
		Double resultAldehydes = 0d;
		Double resultFurfurol = 0d;
		Double resultLead = 0d;
		
		for (RowCalculation row: rows) {
			resultPercentage += row.getPercentage();
			totalVolume += row.getVolume();
			resultAlcohol += row.getVolume() * row.getAlcohol();
			resultMethanol += row.getVolume() * row.getMethanol();
			resultHigherAlcohols += row.getVolume() * row.getHigherAlcohols();
			resultAldehydes += row.getVolume() * row.getAldehydes();
			resultFurfurol += row.getVolume() * row.getFurfurol();
			resultLead += row.getVolume() * row.getPlomo();
		}
		
		row.setPercentage(resultPercentage);
		row.setVolume(totalVolume);
		row.setAlcohol(resultAlcohol/totalVolume);
		row.setMethanol(resultMethanol/totalVolume);
		row.setHigherAlcohols(resultHigherAlcohols/totalVolume);
		row.setAldehydes(resultAldehydes/totalVolume);
		row.setFurfurol(resultFurfurol/totalVolume);
		row.setPlomo(resultLead/totalVolume);
		return row;
	}
	
	public String getClassForValue(String attribute) {
		switch (attribute) {
		case "alcohol":
			if (row.getAlcohol().isNaN())
				return "initial";
			else if (row.getAlcohol() >= 35 && row.getAlcohol() <= 55)
				return "#82E0AA";
			else
				return "#F47340";
		case "methanol":
			if (row.getMethanol().isNaN())
				return "initial";
			else if (row.getMethanol() >= 30 && row.getMethanol() <= 300)
				return "#82E0AA";
			else
				return "#E59866";
		case "higherAlcohols":
			if (row.getHigherAlcohols().isNaN())
				return "initial";
			else if (row.getHigherAlcohols() >= 100 && row.getHigherAlcohols() <= 500)
				return "#82E0AA";
			else
				return "#E59866";
		case "aldehydes":
			if (row.getAldehydes().isNaN())
				return "initial";
			else if (row.getAldehydes() >= 0 && row.getAldehydes() <= 40)
				return "#82E0AA";
			else
				return "#E59866";
		case "furfurol":
			if (row.getFurfurol().isNaN())
				return "initial";
			else if (row.getFurfurol() >= 0 && row.getFurfurol() <= 5)
				return "#82E0AA";
			else
				return "#E59866";
		case "plomo":
			if (row.getPlomo().isNaN())
				return "initial";
			else if (row.getPlomo() >= 0 && row.getPlomo() <= 0.5)
				return "#82E0AA";
			else
				return "#E59866";
		default:
			return "initial";
		}
	}
}