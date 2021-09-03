package com.books.wishlist.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenDto {

    private String token;
    private String nombreUsuario;

    public TokenDto(String token, String nombreUsuario) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
    }

}
