package com.books.wishlist.security.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.books.wishlist.security.dto.CambioClaveUsuarioDto;
import com.books.wishlist.security.dto.LoginUsuarioDto;
import com.books.wishlist.security.dto.NuevoUsuarioDto;
import com.books.wishlist.security.dto.TokenDto;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.jwtokens.ProveedorToken;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ProveedorToken proveedorToken;

	@PostMapping
	public ResponseEntity<MensajeRespuesta> crearUsuario(
			@ApiParam(name="usuario", value="Usuario a registrar.", required = true)
			@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult result){

		Usuario usuario = new Usuario(nuevoUsuario.getNomUsuario(), nuevoUsuario.getPassword(), 
				                      nuevoUsuario.getEmail(), nuevoUsuario.getNomCompleto());        
		usuario.setClave(passwordEncoder.encode(nuevoUsuario.getPassword()));

		MensajeRespuesta msnRespuesta = usuarioService.modificarUsuario(usuario);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

	@PutMapping
	public ResponseEntity<MensajeRespuesta> modificarUsuario(
			@ApiParam(name="usuario", value="Usuario a registrar.", required = true)
			@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult result){

		Usuario usuario = new Usuario(nuevoUsuario.getNomUsuario(), nuevoUsuario.getPassword(), 
				                      nuevoUsuario.getEmail(), nuevoUsuario.getNomCompleto());        
		usuario.setClave(passwordEncoder.encode(nuevoUsuario.getPassword()));

		MensajeRespuesta msnRespuesta = usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

	@PutMapping(value = "/{idUsuario}")
	public ResponseEntity<MensajeRespuesta> cambiarClaveUsuario(
			@PathVariable(name="idUsuario", required = true) Long idUsuario,
			@ApiParam(name="usuario", value="Usuario a registrar.", required = true)
			@Valid @RequestBody CambioClaveUsuarioDto usuario, BindingResult result){

		String nuevaClave = passwordEncoder.encode(usuario.getPassword());
		MensajeRespuesta msnRespuesta = usuarioService.modificarClaveUsuario(idUsuario, nuevaClave);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

	@DeleteMapping(value = "/{idUsuario}")
	public ResponseEntity<MensajeRespuesta> eliminarUsuario(
			@PathVariable(name="idUsuario", required = true) Long idUsuario){

		MensajeRespuesta msnRespuesta = usuarioService.eliminarUsuario(idUsuario);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
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
