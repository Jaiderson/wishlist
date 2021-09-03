package com.books.wishlist.security.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="rols")
@NoArgsConstructor @Getter @Setter @ToString
@Data
@ApiModel(description = "Roles o perfiles de usuarios del sistema wishlist.")
public class Rol {

    @Id
    @Column(name="rolid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1, dataType = "Long", value = "Identificador unico del rol del usuario.<br>", example = "100", required = true)
    private Long idRol;
    
    @Column(name="rolname", unique = true)
    @NotNull(message = "El rol de usuario no puede ser vacio.")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(position = 2, dataType = "String", value = "Nombre del rol de usuario. <br>", example = "USER", required = true)
    private ERol nombre;

    @Column(name="description")
    @Size(max=100, message = "Cantidad de carateres de la descripcion del rol de usuario es maxima de 50.")
    @ApiModelProperty(position = 3, dataType = "String", value = "Descripcion de los permisos de acceso del rol de usuario. <br>", 
                      example = "Usuario solo tiene permisos de crear listas de libros.", required = false)
    private String descripcion;

	public Rol(ERol nombre,
               @Size(max = 100, message = "Cantidad de carateres de la descripcion del rol de usuario es maxima de 50.") 
               String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

}
