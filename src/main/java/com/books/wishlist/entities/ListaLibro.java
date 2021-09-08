package com.books.wishlist.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "book_list")
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"listid","bookid","book_item"}) })
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
@Data
public class ListaLibro {

	@Id
	@Column(name="book_listid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idListaLibro;

	@Column(name = "book_item")
	@NotNull(message = "Posicion del libro no puede ser vacia.")
    private Integer posicionLibro;

	@Column(name = "bookid", nullable = false)
	@NotNull(message = "Id del libro no puede ser vacio.")
	private Long idLibro;
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "bookid", nullable = false)
//	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//	private Libro libro;

	@Column(name = "listid", nullable = false)
	@NotNull(message = "Id Lista deseo no puede ser vacia.")
	private Long idListaDeseo;
//	@JsonIgnore
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "listid", nullable = false)
//	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//	private ListaDeseo listaDeseo;

}
