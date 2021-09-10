package com.books.wishlist.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class BusquedaParametricaDto {

	@NotNull(message = "Parametro personalizado no puede ser vacio. [AUTOR, TITULO, EDITOR, CATEGORIA]")
	@ApiModelProperty(position=1, dataType="String ", value="Valor que representa el tipo de busqueda "+
	                                                        "a realizar en el API books de Google. "+
			                                                "[AUTOR, TITULO, EDITOR, CATEGORIA] <br>", 
			                                          example="AUTOR", required=true)
	private String paramPersonalizadoBusqueda;

	@NotNull
	@ApiModelProperty(position=2, dataType="String", value="Valor a buscar el cual esta relacionado "+
	                                                       "con el tipo de busqueda a realizar.<br>", 
	                                                 example="Gabriel Garcia Marquez", required=true)
	private String contenidoBusqueda;

}
