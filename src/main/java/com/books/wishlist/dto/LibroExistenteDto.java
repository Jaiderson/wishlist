package com.books.wishlist.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class LibroExistenteDto {

	@NotNull(message = "Id libro no puede ser vacio.")
	private Long idLibro;

	@NotNull(message = "Id lista deseo no puede ser vacio.")
	private Long idListaDeseo;

	@NotNull(message = "Posicion del libro no puede ser vacio.")
	@Positive(message = "Posicion del libro debe ser un valor entero positivo.")
	private Integer posicionLibro;

}
