package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Role;

@Local
public interface RoleDao extends GenericDao<Role, Long>{
	
	/**
     * Finds user's role by its name.
     *
     * @param name the name of a role
     * @return {@link Role} entity or {@literal null} if none found
     */
    Role findByName(String name);
	
}
