package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Customer;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface CustomerService extends GenericService<Customer, Long>{
	
	Customer findByName(String name);
	List<Customer> findAllActive();
	List<Customer> findAllInactive();
}
