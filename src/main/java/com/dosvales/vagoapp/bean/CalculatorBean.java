package com.dosvales.vagoapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.dosvales.vagoapp.model.RowCalculator;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.StandardProductionService;

@Named
@ViewScoped
public class CalculatorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private StandardProductionService productionService;
	
	private StandardProduction production;
	
	private List<StandardProduction> productions;
	
	private List<RowCalculator> rows;
	
	private RowCalculator row;
	
	private double alcoholicGradeMezcal;

	private double alcoholicGradeDesired;
	
	private double volumeProduction;
	
	private double volumeMezcal;
	
	private double volumeWater;
	
	@PostConstruct
	public void init() {
		productions = productionService.findAll();
		rows = new ArrayList<RowCalculator>();
		row = new RowCalculator();
	}
	
	public double getAlcoholicGradeMezcal() {
		return alcoholicGradeMezcal;
	}

	public void setAlcoholicGradeMezcal(double alcoholicGradeMezcal) {
		this.alcoholicGradeMezcal = alcoholicGradeMezcal;
	}

	public double getAlcoholicGradeDesired() {
		return alcoholicGradeDesired;
	}

	public void setAlcoholicGradeDesired(double alcoholicGradeDesired) {
		this.alcoholicGradeDesired = alcoholicGradeDesired;
	}

	public double getVolumeProduction() {
		return volumeProduction;
	}

	public void setVolumeProduction(double volumeProduction) {
		this.volumeProduction = volumeProduction;
	}

	public double getVolumeMezcal() {
		volumeMezcal = volumeWater/4;
		return volumeMezcal;
	}

	public double getVolumeWater() {
		volumeWater = volumeProduction * (alcoholicGradeMezcal/alcoholicGradeDesired) - volumeProduction;
		return volumeWater;
	}
	
	public RowCalculator getRow() {
		Double resultPercentage = 0d;
		Integer totalVolume = 0;
		Double resultAlcohol = 0d;
		Double resultMethanol = 0d;
		Double resultHigherAlcohols = 0d;
		Double resultAldehydes = 0d;
		Double resultFurfurol = 0d;
		Double resultLead = 0d;
		
		for (RowCalculator row: rows) {
			resultPercentage += row.getPercentage();
			totalVolume += row.getVolume();
			resultAlcohol += row.getVolume() * row.getAlcohol();
			resultMethanol += row.getVolume() * row.getMethanol();
			resultHigherAlcohols += row.getVolume() * row.getHigherAlcohols();
			resultAldehydes += row.getVolume() * row.getAldehydes();
			resultFurfurol += row.getVolume() * row.getFurfurol();
			resultLead += row.getVolume() * row.getLead();
		}
		
		row.setPercentage(resultPercentage);
		row.setVolume(totalVolume);
		row.setAlcohol(resultAlcohol/totalVolume);
		row.setMethanol(resultMethanol/totalVolume);
		row.setHigherAlcohols(resultHigherAlcohols/totalVolume);
		row.setAldehydes(resultAldehydes/totalVolume);
		row.setFurfurol(resultFurfurol/totalVolume);
		row.setLead(resultLead/totalVolume);
		return row;
	}

	public List<RowCalculator> getRows() {
		if (rows != null && !rows.isEmpty()) {
			Double totalVolume = 0d;
			for(int x = 0; x < rows.size(); x++)
				totalVolume += rows.get(x).getVolume();
			
			for(int x = 0; x < rows.size(); x++)
				rows.get(x).setPercentage(Math.round(((rows.get(x).getVolume()/totalVolume) * 100) * 100.0)/100.0);
			
			rows.get(0).setName(production.getLot() + " Cuerpo");
			rows.get(1).setName(production.getLot() + " Cola");
			rows.get(2).setName("Agua");
			
			if (rows.get(0).getAlcohol() == 0 && rows.get(0).getVolume() == 0) {
				rows.get(0).setVolume((int) volumeProduction);
				rows.get(0).setAlcohol(alcoholicGradeMezcal);
			}
		}
		return rows;
	}

	public StandardProduction getProduction() {
		return production;
	}

	public void setProduction(StandardProduction production) {
		this.production = production;
		
		volumeProduction = production.getVolumeDistillation2();
		alcoholicGradeMezcal = production.getAlcoholicGradeDist2();
		alcoholicGradeDesired = 50;
		
		rows = new ArrayList<RowCalculator>();
		for (int x = 0; x < 7; x++)
			rows.add(new RowCalculator());
	}

	public List<StandardProduction> getProductions() {
		return productions;
	}
}