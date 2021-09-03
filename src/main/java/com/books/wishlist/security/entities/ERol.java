package com.books.wishlist.security.entities;

import lombok.Getter;

@Getter
public enum ERol {

	ADMIN("ADMIN"),
	USER("USER");

	private String nombreRol;

	private ERol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

}
