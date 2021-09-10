package com.books.wishlist.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.books.wishlist.entities.Libro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class LibroNuevoDto {

	private Long idLibro;

	@NotNull(message = "Identificacdor <b>unico</b> el cual conicide con el id del libro en el API Books de Google.")
	@Size(min = 5, max = 100, message = "Cantidad de caracteres minima para el id del libro es minimo 5 y maximo de 100.")
	private String idLibroApi;

	@NotNull(message = "Id lista deseo no puede ser vacio.")
	private Long idListaDeseo;

	@NotNull(message = "Posicion del libro no puede ser vacio.")
	@Positive(message = "Posicion del libro debe ser un valor entero positivo.")
	private Integer posicionLibro;

	@NotNull(message = "Nombre del titulo del libro no puede ser vacio.")
	@Size(min = 3, max = 200, message = "Nombre del libro debe ser minimo de 3 y maximo de 200 caracteres.")
	private String titulo;

	private String subTitulo;

	@NotNull(message = "Nombre de la editorial asociada al libro no puede ser vacia.")
	@Size(min = 3, max = 200, message = "Nombre de la editorial del libro debe ser minimo de 3 y maximo de 200 caracteres.")
	private String editorial;

	@NotNull(message = "Nombre de los autores del libro no debe ser vacio.")
	private String autores;

	public Libro getLibro() {
		return Libro.builder().idLibro(idLibro)
				              .idLibroApi(idLibroApi)
				              .titulo(titulo)
				              .subTitulo(subTitulo)
				              .autores(autores)
				              .editorial(editorial)
				              .posicion(posicionLibro)
				              .build();
	}
}
