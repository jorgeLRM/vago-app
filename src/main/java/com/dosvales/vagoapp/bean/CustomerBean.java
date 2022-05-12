package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.dosvales.vagoapp.exception.AppException;
import com.dosvales.vagoapp.exception.DataFoundException;
import com.dosvales.vagoapp.exception.RelatedRecordException;
import com.dosvales.vagoapp.model.Address;
import com.dosvales.vagoapp.model.Customer;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.service.CustomerService;

@Named
@ViewScoped
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customer;
	private List<Customer> customers;
	private EntityStatus status = EntityStatus.ACTIVE;
	
	@Inject
	private CustomerService customerService;
	
	public void openNew() {
		customer = new Customer();
		customer.setAddress(new Address());
	}
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				customer = customerService.findById(Long.valueOf(id));
			} catch (Exception ex) {}
		}
	}
	
	public String tryToSave() {
		String page = "";
		try {
			checkAvailability(customer);
			page = save(customer);
		} catch (DataFoundException | AppException ex) {
			showMessage("Cuidado", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}
	
	public Customer checkAvailability(Customer c) throws DataFoundException, AppException{
		Customer found = customerService.findByName(c.getName());
		if (found != null) {
			if (found.getStatus() == EntityStatus.INACTIVE) {
				throw new AppException("El nombre del cliente que ingresaste ya se encuentra registrado pero está deshabilitado. Consulta los clientes deshabilitados para volverlo a habilitar.");
			} else {
				throw new DataFoundException("El nombre del cliente ya se encuentra registrado");
			}
		}
		return found;
	}
	
	public String save(Customer c) {
		customerService.save(c);
		addMessage("Operación exitosa", "Cliente guardado exitosamente", FacesMessage.SEVERITY_INFO);
		return "/protected/packing/customers.xhtml?faces-redirect=true";
	}

	public void delete() {
		try {
			checkHasAssociations(customer);
			customerService.delete(customer);
			customers.remove(customer);
			showMessage("Operación exitosa", "El cliente ha sido eliminado correctamente", FacesMessage.SEVERITY_INFO);
			PrimeFaces.current().ajax().update(":form:dt-customers");
		} catch (RelatedRecordException ex) {
			showMessage("¡Cuidado!", ex.getMessage(), FacesMessage.SEVERITY_WARN);
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}

	public Customer checkHasAssociations(Customer c) throws RelatedRecordException {
		Customer found = customerService.findWithProductionOrders(c.getId());
		if (found.getProductionOrders().isEmpty())
			return found;
		throw new RelatedRecordException("El cliente no se puede eliminar porque ha sido utilizado anteriormente. Intente deshabilitarlo si ya no lo necesita");
	}

	public String update() {
		String page = "";
		try {
			customerService.update(customer);
			addMessage("Operación exitosa", "Cliente actualizado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/customers.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void enabledDisabledCustomer() {
		try {
			String operation = customer.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado";
			customer = customerService.blockUnblockCustomer(customer.getId());
			showMessage("Operación exitosa", "El cliente ha sido " + operation + " exitosamente", FacesMessage.SEVERITY_INFO);
			customers.remove(customer);
			PrimeFaces.current().ajax().update(":form:dt-customers");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_INFO);
		}
	}
	
	public void refreshTable() {
		if (status == EntityStatus.ACTIVE) {
			customers = customerService.findAllActive();
		} else if (status == EntityStatus.INACTIVE){
			customers = customerService.findAllInactive();
		} else {
			customers = customerService.findAll();
		}
	}
	
	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}
}
