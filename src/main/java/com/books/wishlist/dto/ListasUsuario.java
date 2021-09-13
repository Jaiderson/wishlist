package com.books.wishlist.dto;

import java.util.Date;
import java.util.List;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.entities.Usuario;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class ListasUsuario {

	@ApiModelProperty(position=1, dataType="Long", value="Identificador unico de la lista de deseos. <br>", 
            example="101", required=true)
	private Long idLista;

	@ApiModelProperty(position=2, dataType="Long", value="Posicioun unica de la lista usada para indear "+
            "las listas de deseos de los usuarios. <br>", 
      example="3", required=true)
	private Integer posicionLista;

	@ApiModelProperty(position=3, dataType="String", value="Nombre unico de la lista de deseos el cual debe ser unico por"+
            " usuario. Es decir un usuario no puede tener dos litas de "+
            "libros con el mismo nombre. <br>", 
      example="10", required=true)
	private String nomListaDeseos;

	@ApiModelProperty(position=4, dataType="Date", value="Fecha de creacion de la lista de deseos la cual la genera el "+
            "api wishlist. <br>", 
      example="2021-08-31 15:45:12", required=false)
	private Date fecCreacion;

	private Usuario usuario;

	private List<Libro> libros;

	public static ListasUsuario convertirListaDeseo(ListaDeseo listaDeseo) {
		return ListasUsuario.builder().idLista(listaDeseo.getIdLista())
				                      .posicionLista(listaDeseo.getPosicionLista())
				                      .nomListaDeseos(listaDeseo.getNomListaDeseos())
				                      .fecCreacion(listaDeseo.getFecCreacion())
				                      .usuario(listaDeseo.getUsuario())
				                      .libros(Lists.newArrayList())
				                      .build();
	}

}
