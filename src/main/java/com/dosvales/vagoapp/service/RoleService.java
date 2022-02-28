package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.model.Role;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface RoleService extends GenericService<Role, Long>{
	
	Role findByName(String name);
	
}
