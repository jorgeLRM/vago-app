package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.User;

@Local
public interface UserDao extends GenericDao<User, Long>{
	
	/**
     * Returns the user that matches the passed in email.
     *
     * @param email user's email address
     * @return user with the given {@code email} or {@literal null} if none found
     */
	User findByEmail(String email);
	
}
