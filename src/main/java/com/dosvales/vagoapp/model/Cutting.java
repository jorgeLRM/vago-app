package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cutting")
@NamedEntityGraph(name = "graph.Cutting.cuttingDetails", 
attributeNodes = @NamedAttributeNode("cuttingDetails"))
public class Cutting extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String lot;
	
	private String guideNumber;
	
	private String guideLink;
	
	private LocalDate cutoffDate;
	
	@OneToOne(cascade= {CascadeType.PERSIST})
	@JoinColumn(name="idLotDetail")
	private LotDetail lotDetail;
	
	@OneToMany(mappedBy="cutting", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<CuttingDetail> cuttingDetails;

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getGuideNumber() {
		return guideNumber;
	}

	public void setGuideNumber(String guideNumber) {
		this.guideNumber = guideNumber;
	}

	public String getGuideLink() {
		return guideLink;
	}

	public void setGuideLink(String guideLink) {
		this.guideLink = guideLink;
	}

	public LocalDate getCutoffDate() {
		return cutoffDate;
	}

	public void setCutoffDate(LocalDate cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	public LotDetail getLotDetail() {
		return lotDetail;
	}

	public void setLotDetail(LotDetail lotDetail) {
		this.lotDetail = lotDetail;
	}

	public List<CuttingDetail> getCuttingDetails() {
		return cuttingDetails;
	}

	public void setCuttingDetails(List<CuttingDetail> cuttingDetails) {
		this.cuttingDetails = cuttingDetails;
	}
}
