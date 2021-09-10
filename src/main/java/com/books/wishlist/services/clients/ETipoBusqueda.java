package com.books.wishlist.services.clients;

import lombok.Getter;

@Getter
public enum ETipoBusqueda {

    AUTOR("inauthor:"),
    TITULO("intitle:"),
    EDITOR("inpublisher:"),
    CATEGORIA("subject:");

    private String tipoBusqueda;

    private ETipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public static ETipoBusqueda getTipoBusqueda(String tipo) {
    	ETipoBusqueda result = null;
    	
    	switch (tipo) {
		case "AUTOR":
			result = ETipoBusqueda.AUTOR;
			break;
		case "TITULO":
			result = ETipoBusqueda.TITULO;
			break;
		case "EDITOR":
			result = ETipoBusqueda.EDITOR;
			break;
		case "CATEGORIA":
			result = ETipoBusqueda.CATEGORIA;
			break;
		default:
			break;
		}
    	return result;
    }
}
