package com.books.wishlist.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="books")
@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter @ToString
@Data
public class Libro {

	@Id
    @Column(name="bookid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position=1, dataType="Long", value="Identificador unico del libro. <br>", example="2001", required=true)
	private Long idLibro;

	@Column(name="bookid_api", unique = true)
	@NotNull(message = "Identificacdor <b>unico</b> el cual conicide con el id del libro en el API Books de Google.")
	@Size(min = 5, max = 100, message = "Cantidad de caracteres minima para el id del libro es minimo 5 y maximo de 100.")
	@ApiModelProperty(position = 2, dataType = "String", value = "Identificador unico del libro en el API Books de Google.<br>", required = true, example="zug36aj0JWIC")
	private String idLibroApi;

	@Column(name="title")
	@NotNull(message = "Nombre del titulo del libro no puede ser vacio.")
	@Size(min = 3, max = 200, message = "Nombre del libro debe ser minimo de 3 y maximo de 200 caracteres.")
	@ApiModelProperty(position = 3, dataType = "String", value = "Nombre del titulo del libro .<br>", required = true, example="Amor en los tiempos del colera.")
	private String titulo;

	@Column(name="subtitle")
	@ApiModelProperty(position = 4, dataType = "String", value = "Nombre del titulo del libro .<br>", example="y epilogo adicional con acotación sobre 'El general en su laberinto'.")
	private String subTitulo;

	@Column(name="publisher")
	@NotNull(message = "Nombre de la editorial asociada al libro no puede ser vacia.")
	@Size(min = 3, max = 200, message = "Nombre de la editorial del libro debe ser minimo de 3 y maximo de 200 caracteres.")
	@ApiModelProperty(position = 5, dataType = "String", value = "Nombre de la editorial que publica el libro.<br>", required = true, example="LITERATURA RANDOM HOUSE.")
	private String editorial;

	@Column(name="authors")
	@NotNull(message = "Nombre de los autores del libro no debe ser vacio.")
	@ApiModelProperty(position = 6, dataType = "String", value = "Nombre de los autores del libro.<br>", required = true, example="Gabriel García Márquez|Luisa Rivera")	
	private String autores;

	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(position = 7, dataType = "Date", value = "Fecha creacion del registro del libro.", example = "2021/08/25 15:45:33")
	private Date fecCreacion;

	@Transient
	private int posicion;

    @PrePersist
    public void prePersist() {
    	this.fecCreacion = new Date();
    	this.mayusculaAtributos();
    }

    @PreUpdate
    public void preUpdate() {
    	this.mayusculaAtributos();
    }

    /**
     * Da formato mayuscula a los atributos titulo, subtitulo, editorial y aotores.
     */
    private void mayusculaAtributos() {
    	this.titulo = this.titulo.toUpperCase().trim();
    	this.subTitulo = (null != this.subTitulo) ? this.subTitulo.toUpperCase().trim() : null;
    	this.editorial = this.editorial.toUpperCase().trim();
    	this.autores = this.autores.toUpperCase().trim();
    }
}
