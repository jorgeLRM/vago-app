package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.RoleDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Role;

@Stateless
public class RoleDaoImpl extends GenericDaoImpl<Role,Long> implements RoleDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Role findByName(String name) {
		Role role = null;
		try {
			role = em.createQuery("FROM Role r WHERE r.name = :name", Role.class)
					.setParameter(name, name)
					.getSingleResult();
		} catch (Exception ex) {}
		return role;
	}

}
