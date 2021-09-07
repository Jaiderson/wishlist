package com.books.wishlist.security.entities;

import lombok.Getter;

@Getter
public enum ERol {

	ADMIN("ADMIN"),
	USER("USER"),
	ADMIN_TEST("ADMIN_TEST"),
	USER_TEST("USER_TEST"),
	OTHER_TEST("OTHER_USER_TEST");

	private String nombreRol;

	private ERol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

}
