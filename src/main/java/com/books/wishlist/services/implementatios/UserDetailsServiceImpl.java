package com.books.wishlist.services.implementatios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Usuario;
import com.books.wishlist.security.jwtokens.UsuarioAutorizado;
import com.books.wishlist.services.IUsuarioService;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    	UsuarioAutorizado usuarioAutorizado = null;

    	Optional<Usuario> usuario = usuarioService.buscarUsuarioPorNombre(nombreUsuario);
    	if(usuario.isPresent()) {
    		usuarioAutorizado = UsuarioAutorizado.build(usuario.get());
    	}    	
        return usuarioAutorizado;
    }

}
