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

}
