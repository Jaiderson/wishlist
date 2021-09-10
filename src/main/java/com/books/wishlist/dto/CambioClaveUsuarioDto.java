package com.books.wishlist.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CambioClaveUsuarioDto {

	@ApiModelProperty(position=1, dataType="Long", value="Id de usuario el cual debe ser unico. <br>", example="1501")
	private Long idUsuario;

    @ApiModelProperty(position=2, dataType="String", value="Nombre de usuario el cual sele cambiara "+
                                                           "la clave debe ser unico. <br>",
                                                     example="USER_2103", required=false)
    private String nomUsuario;

    @NotNull(message = "Contraseña no puede ser vacia.")
    @Size(min=10, max=30, message="Cantidad de carateres de la contraseña minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=3, dataType="String", value="Contraseña del usuario. <br>", 
                                                     example="M@r*20Thj21Pre$%&09", required=true)
    private String password;

}
