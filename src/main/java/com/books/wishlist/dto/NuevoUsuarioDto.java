package com.books.wishlist.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.collect.Sets;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class NuevoUsuarioDto {

    @NotBlank(message = "Nombre de usuario no debe ser vacio.")
    @Size(min=4, max=50, message="Cantidad de carateres del nombre de usuario minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=1, dataType="String", value="Nombre de usuario el cual debe ser unico. <br>", example="JAIDER_2103", required=true)
    private String nomUsuario;

    @NotBlank(message = "Contraseña no puede ser vacia.")
    @Size(min=10, max=30, message="Cantidad de carateres de la contraseña minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=2, dataType="String", value="Contraseña del usuario. <br>", example="**********", required=true)
    private String password;

    @Email(message = "Formato de email errado.")
    @ApiModelProperty(position=3, dataType="String", value="Correo electronico del usuario el cual debe ser unico. <br>", example="jaider.serranox@hotmail.com", required=true)
    private String email;

    @ApiModelProperty(position=4, dataType="String", value="Nombre completo del usuario. <br>", example="JAIDER_2103", required=true)
    @NotBlank(message = "Nombre completo del usuario no debe ser vacio.")
    private String nomCompleto;

    @ApiModelProperty(position=5, value="Listado de roles del usuario. <br>", example="falta documentar", required=true)
    private Set<String> roles = Sets.newHashSet();

}
