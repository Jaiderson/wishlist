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

import com.books.wishlist.security.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private Long idLista;


	@Column(name="list_item")
	@NotNull(message = "Enumerado de la posicion de la lista de libros no debe ser vacia.")
	private Integer posicionLista;
	
	@Column(name="listname")
	@NotBlank(message = "Lista de lisbros no puede ser vacia.")
	@Size(min = 5, max = 50, message = "Cantidad de caracteres minima para el nombre de lista es minimo 5 y maximo de 50.")
	private String nomListaDeseos;

	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
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
