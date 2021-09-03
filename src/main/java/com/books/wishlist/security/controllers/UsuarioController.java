package com.books.wishlist.security.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.books.wishlist.security.dto.LoginUsuario;
import com.books.wishlist.security.dto.MensajeError;
import com.books.wishlist.security.dto.NuevoUsuario;
import com.books.wishlist.security.dto.TokenDto;
import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.jwtokens.ProveedorToken;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.security.services.IUsuarioService;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ProveedorToken proveedorToken;

	@PostMapping
	public ResponseEntity<Usuario> crearUsuario(
			@ApiParam(name="usuario", value="Usuario a registrar.", required = true)
			@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		String nomUsuario = nuevoUsuario.getNomUsuario().toUpperCase();
		String email = nuevoUsuario.getEmail().toLowerCase();
		if(usuarioService.existeUsuarioNomUsuario(nomUsuario)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MensajeError.getMensageNomUsuario(nomUsuario));
		}
		else if(usuarioService.existeUsuarioNomUsuario(email)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MensajeError.getMensageNomUsuario(email));
		}
		String clave = passwordEncoder.encode(nuevoUsuario.getPassword());
		Usuario usuario = new Usuario(nomUsuario, clave, email, nuevoUsuario.getNomCompleto());

		HashSet<Rol> roles = Sets.newHashSet();
		if(nuevoUsuario.getRoles().isEmpty()) {
			Optional<Rol> rol = rolService.buscarRol(ERol.USER);
			if(!rol.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MensajeError.SIN_ROLES);
			}
			roles.add(rol.get());
		}
		else {
			List<Rol> rolesEncontrados = rolService.buscarRoles(nuevoUsuario.getRoles());
			if(rolesEncontrados.size() != nuevoUsuario.getRoles().size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MensajeError.ROL_NO_EXISTE);
			}
			roles.addAll(rolesEncontrados);
			usuario.setRoles(roles);	
		}
		usuario = usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult result){
        if(result.hasErrors()) {
			MensajeError msnError = new MensajeError(MensajeError.LOGIN);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(loginUsuario.getNomUsuario(), loginUsuario.getPassword());
        Authentication authentication =authManager.authenticate(userAuth);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = proveedorToken.generarToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        TokenDto tokenDto = new TokenDto(token, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

}

