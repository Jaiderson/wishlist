package com.books.wishlist.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class LoginUsuarioDto {

    @NotBlank(message = "Nombre de usuario no debe ser vacio.")
    @Size(min=4, max=50, message="Cantidad de carateres del nombre de usuario minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=1, dataType="String", value="Nombre de usuario el cual debe ser unico. <br>", example="JAIDER_2103", required=true)
    private String nomUsuario;

    @NotBlank(message = "Contraseña no puede ser vacia.")
    @Size(min=10, max=30, message="Cantidad de carateres de la contraseña minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=2, dataType="String", value="Contraseña del usuario. <br>", example="**********", required=true)
    private String password;

}
