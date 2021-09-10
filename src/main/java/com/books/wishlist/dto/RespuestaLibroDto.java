package com.books.wishlist.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class RespuestaLibroDto {

	@ApiModelProperty(position=1, dataType="String", value="Tipo de volumenes encontrados.<br>",
			                                         example="books#volumes", required=true)
	private String kind;

	@ApiModelProperty(position=2, dataType="Integer", value="Cantidad de libros encontrados.<br>", 
			                                          example="19", required=true)
	private int totalItems;

	@ApiModelProperty(position=3, dataType="ItemDto", value="Lista de libros encontrados en el API books Google.<br>", 
			                                          example="10", required=true)
	private List<ItemDto> items;

}
