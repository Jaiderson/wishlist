package com.books.wishlist.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="wish_list")
@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter @ToString 
@Data
public class ListaDeseo {

	@Column(name = "listid", nullable = false, insertable = true)
	@ApiModelProperty(position=1, dataType="Long", value="Identificador unico de la lista de deseos. <br>", example="101", required=true)
	private Long idLista;

	@EmbeddedId
	@ApiModelProperty(position=2, dataType="ListaLibroPk", required = true)
	private ListaLibroPk listaPk;

	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(position = 3, dataType = "Date", value = "Fecha creacion de la lista.", example = "2021/09/05 15:45:33")
	private Date fecCreacion;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="list_book", 
    joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "userid"), 
                   @JoinColumn(name = "book_item", referencedColumnName = "book_item"),
                   @JoinColumn(name = "listname", referencedColumnName = "listname")
                   },
    inverseJoinColumns = @JoinColumn(name = "bookid_api"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @ApiModelProperty(position=4, dataType="Set<Libro>")
	private Set<Libro> libros = Sets.newHashSet();

    @PrePersist
    public void prePersist() {
    	this.fecCreacion = new Date();
    }

    public void limpiarDatos() {
    	this.listaPk.getUsuario().setClave(null);
    	this.listaPk.getUsuario().setRoles(null);
    	this.listaPk.getUsuario().setEmail(null);
    	this.listaPk.getUsuario().setNombre(null);
    }

}

