package com.books.wishlist.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CodificarClave {

	@Autowired
	private static BCryptPasswordEncoder passwordEncoder;

	public static String codificarClave(String clave) {
		return passwordEncoder.encode(clave);
	}

}
