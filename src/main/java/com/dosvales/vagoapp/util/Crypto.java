package com.dosvales.vagoapp.util;

import org.mindrot.jbcrypt.BCrypt;

public class Crypto {
	
	public static String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public static boolean isValid(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}
}
