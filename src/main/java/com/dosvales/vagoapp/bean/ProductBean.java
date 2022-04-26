package com.dosvales.vagoapp.bean;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.MezcalCategory;
import com.dosvales.vagoapp.model.MezcalClass;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.model.ProductCategory;
import com.dosvales.vagoapp.model.ProductInput;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.ProductCategoryService;
import com.dosvales.vagoapp.service.ProductInputService;
import com.dosvales.vagoapp.service.ProductService;

@Named
@ViewScoped
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Product product;

	private DualListModel<Input> dualListInput;

	private List<Product> products;

	private List<Input> inputs;

	private List<ProductCategory> categories;

	private List<ProductInput> productInputWrapper;

	private EntityStatus status = EntityStatus.ACTIVE;

	@Inject
	private ProductService productService;

	@Inject
	private InputService inputService;

	@Inject
	private ProductCategoryService categoryService;

	@Inject
	private ProductInputService productInputService;

	public void openNew() {
		product = new Product();
	}

	public void load(String id) {
		if (id != null && id.length() > 0)
			try {
				product = productService.findWithInputs(Long.valueOf(id));
			} catch (Exception ex) {}
	}

	public String onFlowProcess(FlowEvent event) {
		String step = event.getNewStep();
		if (step.equals("cdInputs")) {
			if (!exists(product)) {
				if (product.getSalePrice() <= product.getProductionCost()) {
					step = event.getOldStep();
					showMessage("Cuidado", "El precio de venta debe ser mayor al costo de produccón", FacesMessage.SEVERITY_WARN);
				} else
					dualListInput = createListModel();
			} else {
				step = event.getOldStep();
				showMessage("Cuidado", "Ya se encuentra registrado un producto con el mismo nombre", FacesMessage.SEVERITY_WARN);
			}
		} else if (step.equals("cdQuantity")) {
			productInputWrapper = new ArrayList<ProductInput>();
			for (Input in : dualListInput.getTarget()) {
				ProductInput pi = new ProductInput();
				pi.setProduct(product);
				pi.setInput(in);
				pi.setQuantity(1);
				productInputWrapper.add(pi);
			}
		}
		return step;
	}

	private boolean exists(Product p) {
		Product found = productService.findByName(p.getName());
		return found != null;
	}

	public DualListModel<Input> createListModel() {
		List<Input> source = inputService.findWhitoutBottles();
		return new DualListModel<Input>(source, new ArrayList<Input>());
	}
	
	public String onFlowProcessEdit(FlowEvent event) {
		String step = event.getNewStep();
		if (step.equals("cdInputs")) {
			if (product.getSalePrice() <= product.getProductionCost()) {
					step = event.getOldStep();
					showMessage("Cuidado", "El precio de venta debe ser mayor al costo de produccón", FacesMessage.SEVERITY_WARN);
			} else {
				List<Input> source = inputService.findWhitoutBottles();
				List<Input> target = new ArrayList<Input>();
				for(ProductInput pi: product.getProductInputs())
					target.add(pi.getInput());
				dualListInput = new DualListModel<Input>(source, target);
			}
		} else if (step.equals("cdQuantity")) {
			productInputWrapper = new ArrayList<ProductInput>();
			for (Input in : dualListInput.getTarget()) {
				boolean flag = false;
				for(ProductInput pi : product.getProductInputs()) {
					if(in.equals(pi.getInput())) {
						productInputWrapper.add(pi);
						flag = true;
						break;
					}
				}
				if (!flag ) {
					ProductInput pi = new ProductInput();
					pi.setProduct(product);
					pi.setInput(in);
					pi.setQuantity(1);
					productInputWrapper.add(pi);
				}
			}
		}
		return step;
	}

	public String confirm() {
		String page = "";
		try {
			product.setProductInputs(productInputWrapper);
			productService.save(product);
			addMessage("Operación exitosa", "Producto guardado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/products.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde.", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public String confirmEdit() {
		String page = "";
		try {
			productService.update(product);

			// Se decide cuales ProductInput guardar, cuales actualizar y cuales eliminar
			List<ProductInput> aux = product.getProductInputs();
			for (ProductInput pi : productInputWrapper) {
				if (pi.getId() == null)
					productInputService.save(pi); // Insert
				else
					productInputService.update(pi);	// Update
					for(ProductInput pIn : aux)
						if (pi.getId() == pIn.getId())
							aux.remove(pIn);
			}
			
			for (ProductInput pi : aux)
				productInputService.delete(pi);	// Remove
			
			addMessage("Operación exitosa", "Producto editado exitosamente", FacesMessage.SEVERITY_INFO);
			page = "/protected/packing/products.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde.", FacesMessage.SEVERITY_ERROR);
		}
		return page;
	}

	public void enabledDisabledProduct() {
		try {
			String operation = product.getStatus() == EntityStatus.ACTIVE ? "deshabilitado" : "habilitado";
			product = productService.blockUnblockProduct(product.getId());
			showMessage("Operación exitosa", "El producto ha sido " + operation + " exitosamente", FacesMessage.SEVERITY_INFO);
			refreshTable();
			PrimeFaces.current().ajax().update(":form:dt-products");
		} catch (Exception ex) {
			showMessage("Error", "Ha ocurrido un error. Intente mas tarde.", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void refreshTable() {
		if (getStatus() == EntityStatus.ACTIVE)
			products = productService.findAllActive();
		else if (getStatus() == EntityStatus.INACTIVE)
			products = productService.findAllInactive();
		else
			products = productService.findAll();
	}

	public EntityStatus[] getEntityStatus() {
		return EntityStatus.values();
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public DualListModel<Input> getDualListInput() {
		return dualListInput;
	}

	public void setDualListInput(DualListModel<Input> dualListInput) {
		this.dualListInput = dualListInput;
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<Input> getInputs() {
		if (inputs == null)
			inputs = inputService.findAllActive();
		return inputs;
	}

	public List<ProductCategory> getCategories() {
		if (categories == null)
			categories = categoryService.findAllActive();
		return categories;
	}

	public List<ProductInput> getProductInputWrapper() {
		return productInputWrapper;
	}

	public void setProductInputWrapper(List<ProductInput> productInputWrapper) {
		this.productInputWrapper = productInputWrapper;
	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public MezcalCategory[] getMezcalCategories() {
		return MezcalCategory.values();
	}

	public MezcalClass[] getMezcalClasses() {
		return MezcalClass.values();
	}
}