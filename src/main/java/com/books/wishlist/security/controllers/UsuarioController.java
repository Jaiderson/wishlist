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

import com.books.wishlist.security.dto.LoginUsuarioDto;
import com.books.wishlist.security.dto.NuevoUsuarioDto;
import com.books.wishlist.security.dto.TokenDto;
import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.jwtokens.ProveedorToken;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.utils.Dato;
import com.books.wishlist.utils.MensajeError;
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
			@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult result){

		Dato dato = this.validarExistenciaUsuario(nuevoUsuario, result);
		if(!dato.isOk()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, dato.getValor().toString());
		}
		
		Usuario usuario = new Usuario(nuevoUsuario.getNomUsuario(), nuevoUsuario.getPassword(), nuevoUsuario.getEmail(), nuevoUsuario.getNomCompleto());        
		dato = this.validarRoles(nuevoUsuario, usuario);
		if(!dato.isOk()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, dato.getValor().toString());
		}
		String clave = passwordEncoder.encode(nuevoUsuario.getPassword());
		usuario.setClave(clave);

		usuario = usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	/**
	 * Metodo que aplica todas la validaciones de los datos de entrada y la existencia 
	 * de un nombre de usario o email asociado a otro usuario.
	 * 
	 * @param nuevoUsuario Usuario a validar.
	 * @param result Objeto usado para aplicar las validaciones anotadas en la clase <b>NuevoUsuario</b>. 
	 * @return Dato con el valor de la inconsistencia si la encontro.
	 */
	private Dato validarExistenciaUsuario(NuevoUsuarioDto nuevoUsuario, BindingResult result) {
		Dato dato = new Dato();
		dato.setOk(false);
		
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
			dato.setValor(msnError.getMensaje(result));
			return dato;
		}
		String nomUsuario = nuevoUsuario.getNomUsuario().toUpperCase();
		String email = nuevoUsuario.getEmail().toLowerCase();
		if(usuarioService.existeUsuarioNomUsuario(nomUsuario)) {
			dato.setValor(MensajeError.getMensageNomUsuario(nomUsuario));
			return dato;
		}
		else if(usuarioService.existeUsuarioNomUsuario(email)) {
			dato.setValor(MensajeError.getMensageNomUsuario(email));
			return dato;
		}
		nuevoUsuario.setNomUsuario(nomUsuario);
		nuevoUsuario.setEmail(email);
		dato.setOk(true);
		return dato;
	}

	/**
	 * Meto que permite validar la existencia de los roles asociados 
	 * al usuario ya sea que vengan o no en la peticion.
	 * 
	 * @param nuevoUsuario Usuario de entrada a validar los roles.
	 * @param usuario Usuario construido en el proceso de creacion.
	 * @return Dato con la informacion si existen los roles.
	 */
	private Dato validarRoles(NuevoUsuarioDto nuevoUsuario, Usuario usuario) {
		Dato dato = new Dato();
		dato.setOk(false);
		
		HashSet<Rol> roles = Sets.newHashSet();
		if(nuevoUsuario.getRoles().isEmpty()) {
			Optional<Rol> rol = rolService.buscarRol(ERol.USER);
			if(!rol.isPresent()) {
				dato.setValor(MensajeError.SIN_ROLES);
				return dato;
			}
			roles.add(rol.get());
		}
		else {
			List<Rol> rolesEncontrados = rolService.buscarRoles(nuevoUsuario.getRoles());
			if(rolesEncontrados.size() != nuevoUsuario.getRoles().size()) {
				dato.setValor(MensajeError.ROL_NO_EXISTE);
				return dato;
			}
			roles.addAll(rolesEncontrados);
			usuario.setRoles(roles);	
		}
		dato.setOk(true);
		return dato;
	}

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginUsuarioDto loginUsuario, BindingResult result){
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

