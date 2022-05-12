package com.dosvales.vagoapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productionorder")
@NamedEntityGraph(name = "graph.ProductionOrder.orderDetails",
		attributeNodes = @NamedAttributeNode("orderDetails"))
public class ProductionOrder extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String numberOrder;

	@NotNull
	private LocalDate aplicationDate; 	// Fecha en que se genera la orden

	private LocalDate deliveryDate;		// Fecha en que debe estar completada la orden

	@Enumerated(EnumType.STRING)
	private Markets market;

	@Enumerated(EnumType.STRING)
	private ProductionOrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	private DictumReportStatus dictumStatus;

	@Enumerated(EnumType.STRING)
	private CertificateStatus certificateStatus;

	@Enumerated(EnumType.STRING)
	private InvoiceStatus invoiceStatus;

	private String observations;

	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private Customer customer;

	@OneToMany(mappedBy = "productionOrder", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<ProductionOrderDetail> orderDetails = new ArrayList<ProductionOrderDetail>();

	public String getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(String numberOrder) {
		this.numberOrder = numberOrder;
	}

	public LocalDate getAplicationDate() {
		return aplicationDate;
	}

	public void setAplicationDate(LocalDate aplicationDate) {
		this.aplicationDate = aplicationDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Markets getMarket() {
		return market;
	}

	public void setMarket(Markets market) {
		this.market = market;
	}

	public ProductionOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ProductionOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public DictumReportStatus getDictumStatus() {
		return dictumStatus;
	}

	public void setDictumStatus(DictumReportStatus dictumStatus) {
		this.dictumStatus = dictumStatus;
	}

	public CertificateStatus getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(CertificateStatus certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<ProductionOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<ProductionOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}