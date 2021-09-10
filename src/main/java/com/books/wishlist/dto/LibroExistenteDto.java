package com.books.wishlist.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class LibroExistenteDto {

	@NotNull(message = "Id libro no puede ser vacio.")
	@ApiModelProperty(position=1, dataType="Long", value="Identificador unico del libro. <br>" , example="101", required=true)
	private Long idLibro;

	@NotNull(message = "Id lista deseo no puede ser vacio.")
	@ApiModelProperty(position=2, dataType="Long", value="Id de la lista de deseos. <br>" , example="8", required=true)
	private Long idListaDeseo;

	@NotNull(message = "Posicion del libro no puede ser vacio.")
	@Positive(message = "Posicion del libro debe ser un valor entero positivo.")
	@ApiModelProperty(position=3, dataType="Integer", value="Valor de la posicion del "+
	                                                        "libro en la lista de deseos.<br>", 
	                                                  example="2", required=true)
	private Integer posicionLibro;

}
