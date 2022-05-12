package com.dosvales.vagoapp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "productionorderdetail")
public class ProductionOrderDetail extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private Double volume;	// 100	Espadin
	
	private Integer numBotellasEnvasadas; // 100
	
	private Integer numBotellasDisponibles; // 0

	@Enumerated(EnumType.STRING)
	private OrderDetailStatus orderStatus;

	private String observations;

	@OneToOne
	@JoinColumn(name = "idTag")
	private Tag tag;

	@ManyToOne
	@JoinColumn(name = "idProductionOrder")
	private ProductionOrder productionOrder;

	@ManyToOne
	@JoinColumn(name = "idProduction")
	private Production production;

	@ManyToOne
	@JoinColumn(name = "idProduct")
	private Product product;

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public OrderDetailStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderDetailStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}