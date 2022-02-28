package com.dosvales.vagoapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tub")
@NamedEntityGraph(name = "graph.Tub.formulations",
				attributeNodes = @NamedAttributeNode("formulations"))
public class Tub extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer numberTub;
	
	private Integer height1;
	
	private Integer height2;
	
	private Integer height3;
	
	private Integer height4;
	
	private Integer topDiameter1;
	
	private Integer topDiameter2;
	
	private Integer topDiameter3;
	
	private Integer topDiameter4;
	
	private Integer bottomDiameter1;
	
	private Integer bottomDiameter2;
	
	private Integer bottomDiameter3;
	
	private Integer bottomDiameter4;
	
	@Transient
	private Double averageHeight;
	
	@Transient
	private Double averageBottomDiameter;
	
	@Transient
	private Double averageTopDiameter;
	
	@Transient
	private Double topRadius;
	
	@Transient
	private Double bottomRadius;
	
	@Transient
	private Double volume;
	
	@Transient
	private Double factor;
	
	@ManyToOne
	@JoinColumn(name = "idPalenque")
	private Palenque palenque;
	
	@OneToMany(mappedBy="tub")
	private List<Formulation> formulations = new ArrayList<Formulation>();
	
	public Integer getNumberTub() {
		return numberTub;
	}

	public void setNumberTub(Integer numberTub) {
		this.numberTub = numberTub;
	}

	public Integer getHeight1() {
		return height1;
	}

	public void setHeight1(Integer height1) {
		this.height1 = height1;
	}

	public Integer getHeight2() {
		return height2;
	}

	public void setHeight2(Integer height2) {
		this.height2 = height2;
	}

	public Integer getHeight3() {
		return height3;
	}

	public void setHeight3(Integer height3) {
		this.height3 = height3;
	}

	public Integer getHeight4() {
		return height4;
	}

	public void setHeight4(Integer height4) {
		this.height4 = height4;
	}

	public Integer getTopDiameter1() {
		return topDiameter1;
	}

	public void setTopDiameter1(Integer topDiameter1) {
		this.topDiameter1 = topDiameter1;
	}

	public Integer getTopDiameter2() {
		return topDiameter2;
	}

	public void setTopDiameter2(Integer topDiameter2) {
		this.topDiameter2 = topDiameter2;
	}

	public Integer getTopDiameter3() {
		return topDiameter3;
	}

	public void setTopDiameter3(Integer topDiameter3) {
		this.topDiameter3 = topDiameter3;
	}

	public Integer getTopDiameter4() {
		return topDiameter4;
	}

	public void setTopDiameter4(Integer topDiameter4) {
		this.topDiameter4 = topDiameter4;
	}

	public Integer getBottomDiameter1() {
		return bottomDiameter1;
	}

	public void setBottomDiameter1(Integer bottomDiameter1) {
		this.bottomDiameter1 = bottomDiameter1;
	}

	public Integer getBottomDiameter2() {
		return bottomDiameter2;
	}

	public void setBottomDiameter2(Integer bottomDiameter2) {
		this.bottomDiameter2 = bottomDiameter2;
	}

	public Integer getBottomDiameter3() {
		return bottomDiameter3;
	}

	public void setBottomDiameter3(Integer bottomDiameter3) {
		this.bottomDiameter3 = bottomDiameter3;
	}

	public Integer getBottomDiameter4() {
		return bottomDiameter4;
	}

	public void setBottomDiameter4(Integer bottomDiameter4) {
		this.bottomDiameter4 = bottomDiameter4;
	}

	public Double getAverageHeight() {
		averageHeight = (double) ((height1 + height2 + height3 + height4) / 4);
		return averageHeight;
	}

	public Double getAverageBottomDiameter() {
		averageBottomDiameter = (double) ((bottomDiameter1.doubleValue() + bottomDiameter2.doubleValue() + 
				bottomDiameter3.doubleValue() + bottomDiameter4.doubleValue()) / 4);
		return averageBottomDiameter;
	}

	public Double getAverageTopDiameter() {
		averageTopDiameter = (double) ((topDiameter1.doubleValue() + topDiameter2.doubleValue() + 
				topDiameter3.doubleValue() + topDiameter4.doubleValue()) / 4);
		return averageTopDiameter;
	}

	public Double getTopRadius() {
		topRadius = getAverageTopDiameter() / 2;
		return topRadius;
	}

	public Double getBottomRadius() {
		bottomRadius = getAverageBottomDiameter() / 2;
		return bottomRadius;
	}

	public Double getVolume() {
		volume = ((getTopRadius() * topRadius) + (getBottomRadius() * bottomRadius) + (topRadius * bottomRadius))
				* Math.PI * getAverageHeight() / (3 * 1000);
		return volume;
	}

	public Double getFactor() {
		factor = getVolume() / averageHeight;
		return factor;
	}

	public Palenque getPalenque() {
		return palenque;
	}

	public void setPalenque(Palenque palenque) {
		this.palenque = palenque;
	}
	
	public Integer[] getHeights() {
		Integer[] heights = {height1, height2, height3, height4};
		return heights;
	}
	
	public Integer[] getTopDiameters() {
		Integer[] topDiameters = {topDiameter1, topDiameter2, topDiameter3, topDiameter4};
		return topDiameters;
	}
	
	public Integer[] getBottomDiameters() {
		Integer[] bottomDiameters = {bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4};
		return bottomDiameters;
	}

	public List<Formulation> getFormulations() {
		return formulations;
	}

	public void setFormulations(List<Formulation> formulations) {
		this.formulations = formulations;
	}
}
