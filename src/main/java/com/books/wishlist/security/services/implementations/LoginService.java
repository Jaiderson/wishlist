package com.books.wishlist.security.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.books.wishlist.security.entities.Usuario;

@Service
public class LoginService {//TODO Validar si se elimina o no.

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	public boolean isAutorizado(Usuario usuario) {
		boolean isAutorizado = false;
		UserDetails  userDetails = userDetailsService.loadUserByUsername(usuario.getNombre());
		
		if(null != userDetails) {
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, usuario.getClave(), userDetails.getAuthorities());
			try {
				authenticationManager.authenticate(userToken);
				if(userToken.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(userToken);
					isAutorizado = true;
				}
			}
			catch (Exception e) {
				isAutorizado = false;
			}
		}
		return isAutorizado;
	}

}
