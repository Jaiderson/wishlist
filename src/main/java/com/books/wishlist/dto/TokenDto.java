package com.books.wishlist.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenDto {

	@ApiModelProperty(position=1, dataType="String", value="Token de autenticacion.<br>", 
			                      example="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKQUlERVJTT04iLCJp.", required=true)
    private String token;

	@ApiModelProperty(position=2, dataType="String", value="Nombre de usuario.<br>", 
			                      example="MARADONA_2021", required=true)
	private String nombreUsuario;

    public TokenDto(String token, String nombreUsuario) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
    }

}
