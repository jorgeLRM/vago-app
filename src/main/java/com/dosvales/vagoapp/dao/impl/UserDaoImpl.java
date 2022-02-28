package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.UserDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.User;

@Stateless
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public User findByEmail(String email) {
		User user = null;
		try {
			user = em.createQuery("FROM User u WHERE u.email = :email",
					User.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (Exception ex) {}
		return user;
	}

}
