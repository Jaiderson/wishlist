package com.books.wishlist.security.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="users")
@NoArgsConstructor @Getter @Setter @ToString
@Data
public class Usuario {

	@Id
    @Column(name="userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position=1, dataType="Long", value="Identificador unico del usuario. <br>", example="10", required=true)
    private Long idUsuario;

    @Column(name="username", nullable = false, unique = true)
    @NotNull(message = "Nombre de usuario no puede ser vacio.")
    @Size(min=4, max=50, message="Cantidad de carateres para el nombre de uaurio minimo es de 4 y de maxima de 50.")
    @ApiModelProperty(position=2, dataType="String", value="Nombre de usuario el cual debe ser unico. <br>", example="JAIDER_2103", required=true)
    private String nomUsuario;

    @Column(name="password", nullable = false)
    @NotNull(message = "Contraseña de usuario no puede ser vacia.")
    @Size(min=10, max=300, message="Cantidad de carateres de la contraseña minimo debe ser de 10 y de maxima de 50.")
    @ApiModelProperty(position=3, dataType="String", value="Contraseña del usuario. <br>", example="**********", required=true)
    private String clave;

    @Column(name="email", nullable = false)
    @NotNull(message = "Email del usuario no puede ser vacio.")
    @ApiModelProperty(position=4, dataType="String", value="Correo electronico del usuario el cual debe ser unico. <br>", example="jaider.serranox@hotmail.com", required=true)
    @Email(message = "Formato de email no valido.")
    private String email;

    @ManyToMany
    @NotNull(message = "Rol no puede ser vacio.")
    @JoinTable(name="user_rol", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "rolid"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @ApiModelProperty(position=5, dataType="Rol", required=true)
    private Set<Rol> roles = Sets.newHashSet();

    @Column(name="fullname", nullable = false)
    @NotNull(message = "Nombre completo de usuario no puede ser vacio.")
    @Size(min=10, max=100, message = "Cantidad de carateres del nombre minimo es de 10 y de maximo de 100")
    @ApiModelProperty(position=6, dataType="String", value="<br>Nombres y apellidos del usuario. <br>", example="JAIDER JESUS VILLA CONTRERAS", required=true)
    private String nombre;

    @Column(name="create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(position=7, dataType = "Date", value = "Fecha creacion del usuario. (yyyy-MM-dd HH:mm:ss)", example = "2021-08-28 14:50:38")
    private Date fecCreacion;

    @PrePersist
    public void prePersist() {
    	this.email = this.email.toLowerCase().trim();
    	this.nombre = this.nombre.toUpperCase().trim();
    	this.fecCreacion = new Date();
    }

	public Usuario(
			@NotNull(message = "Nombre de usuario no puede ser vacio.") 
			@Size(min = 4, max = 50, message = "Cantidad de carateres del DNI minimo es de 4 y de maxima de 50.") 
			String nomUsuario,
			@NotNull(message = "Contraseña de usuario no puede ser vacia.") 
			@Size(min = 10, max = 300, message = "Cantidad de carateres de la contraseña minimo debe ser de 10 y de maxima de 50.") 
			String clave,
			@NotNull(message = "Email del usuario no puede ser vacio.") 
			@Email(message = "Formato de email no valido.") 
			String email,
			@NotNull(message = "Nombre completo de usuario no puede ser vacio.") 
			@Size(min = 10, max = 100, message = "Cantidad de carateres del nombre minimo es de 10 y de maximo de 100") 
			String nombre) {
		super();
		this.nomUsuario = nomUsuario;
		this.clave = clave;
		this.email = email;
		this.nombre = nombre;
	}

}
