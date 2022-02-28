package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.exception.BadCredentialsException;
import com.dosvales.vagoapp.exception.UserNotReadyException;
import com.dosvales.vagoapp.model.User;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface UserService extends GenericService<User, Long>{
	
	User findByEmail(String email);
	
	User signIn(String email, String password) throws BadCredentialsException, UserNotReadyException;
	
}
