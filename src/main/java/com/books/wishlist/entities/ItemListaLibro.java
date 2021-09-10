package com.books.wishlist.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "book_list")
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"listid","bookid"}),
                           @UniqueConstraint(columnNames = {"listid","book_item"})
                           })
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
@Data
public class ItemListaLibro {

	@Id
	@Column(name="book_listid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position=1, dataType="Long", value="Identificador unico de la relacion lista deseos - libro "
			                                           + "la cual representa un libro de una lista de deseo. <br>", 
			                                       example="100015")
	private Long idItemListaLibro;

	@Column(name = "book_item")
	@NotNull(message = "Posicion del libro no puede ser vacia.")
	@ApiModelProperty(position=2, dataType="Integer", value="Valor que representa la posicion del libro dentro de "+
	                                                        "la lista de deseos el cual debe ser unico el cual es "+
			                                                "usado para indexar los libros de la lista de deseos a"+
	                                                        " la que pertenecen. <br>", 
	                                                  example="1", required=true)
    private Integer posicionLibro;

	@Column(name = "bookid", nullable = false)
	@NotNull(message = "Id del libro no puede ser vacio.")
	@ApiModelProperty(position=3, dataType="Long", value="Identificador del libro asociado a la lista de deseos.", 
	                                               example="101", required=true)
	private Long idLibro;

	@Column(name = "listid", nullable = false)
	@NotNull(message = "Id Lista deseo no puede ser vacia.")
	@ApiModelProperty(position=4, dataType="Long", value="Identificador de la lista de deseos a la cual se le "+
	                                                     "esta asociando un libro por medio del <b>idLibro</b>.",
			                                       example="10", required=true)
	private Long idListaDeseo;

}
