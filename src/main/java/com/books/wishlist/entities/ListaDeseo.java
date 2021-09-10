package com.books.wishlist.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="wish_list")
@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"userid","list_item","listname"})
                          }) 
@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter @ToString @EqualsAndHashCode
@Data
public class ListaDeseo {

	@Id
    @Column(name="listid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position=1, dataType="Long", value="Identificador unico de la lista de deseos. <br>", 
	                                               example="101", required=true)
	private Long idLista;


	@Column(name="list_item")
	@NotNull(message = "Enumerado de la posicion de la lista de libros no debe ser vacia.")
	@ApiModelProperty(position=2, dataType="Long", value="Posicioun unica de la lista usada para indear "+
	                                                     "las listas de deseos de los usuarios. <br>", 
	                                               example="3", required=true)
	private Integer posicionLista;
	
	@Column(name="listname")
	@NotBlank(message = "Lista de lisbros no puede ser vacia.")
	@Size(min = 5, max = 50, message = "Cantidad de caracteres minima para el nombre de lista es minimo 5 y maximo de 50.")
	@ApiModelProperty(position=3, dataType="String", value="Nombre unico de la lista de deseos el cual debe ser unico por"+
	                                                       " usuario. Es decir un usuario no puede tener dos litas de "+
			                                               "libros con el mismo nombre. <br>", 
			                                         example="10", required=true)
	private String nomListaDeseos;

	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(position=4, dataType="Date", value="Fecha de creacion de la lista de deseos la cual la genera el "+
	                                                     "api wishlist. <br>", 
	                                               example="2021-08-31 15:45:12", required=false)
	private Date fecCreacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@NotNull(message = "Usuario no puede ser vacio.")
	private Usuario usuario;

    @PrePersist
    public void prePersist() {
    	this.nomListaDeseos = this.nomListaDeseos.trim().toUpperCase();
    	this.fecCreacion = new Date();
    }

}
