package com.books.wishlist.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.books.wishlist.security.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class ListaLibroPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@NotNull(message = "Usuario no puede ser vacio.")
	@ApiModelProperty(position = 1, dataType = "Usuario", value = "Usuario dueño de la lista de deseos.")
    private Usuario usuario;

	@Column(name="book_item")
	@NotBlank(message = "Enumerado de la posicion de la lista de libros no debe ser vacia.")
	@ApiModelProperty(position = 2, dataType = "Integer", value = "Enumerdo del libro dentro de la lista, no se debe repetir en una lista.<br>", required = true, example="10")
	private Integer posicion;
	
	@Column(name="listname", unique = true)
	@NotBlank(message = "Lista de lisbros no puede ser vacia.")
	@Size(min = 5, max = 50, message = "Cantidad de caracteres minima para el nombre de lista es minimo 5 y maximo de 50.")
	@ApiModelProperty(position = 3, dataType = "String", value = "Nombre unico de una lista de deseos.<br>", required = true, example="Lista libros de amor.")
	private String nomListaDeseos;

}
