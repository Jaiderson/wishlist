package com.books.wishlist.security.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioAutorizado  implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String nomCompleto;
    private String nombreUsuario;
    private String email;
    private String clave;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioAutorizado(String nomUsuario, String nombre, String email, String clave, 
    		                 Collection<? extends GrantedAuthority> authorities) {
    	this.nombreUsuario = nomUsuario;
        this.nomCompleto = nombre;
        this.email = email;
        this.clave = clave;
        this.authorities = authorities;
    }

    public static UsuarioAutorizado build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getNombre().name())).collect(Collectors.toList());

        return new UsuarioAutorizado(usuario.getNomUsuario(), usuario.getNombre(), usuario.getEmail(), usuario.getClave(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nomCompleto;
    }

    public String getEmail() {
        return email;
    }

}
