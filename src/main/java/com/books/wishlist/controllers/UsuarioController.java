package com.books.wishlist.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.books.wishlist.dto.CambioClaveUsuarioDto;
import com.books.wishlist.dto.LoginUsuarioDto;
import com.books.wishlist.dto.NuevoUsuarioDto;
import com.books.wishlist.dto.TokenDto;
import com.books.wishlist.entities.Usuario;
import com.books.wishlist.security.jwtokens.ProveedorToken;
import com.books.wishlist.services.IUsuarioService;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
    @ApiOperation(value = "Permite crear un usuario nuevo.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuario ya existe."),
        @ApiResponse(code = 201, message = "Usuario creado correctamente."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 400, message = "Informacion del usuario incompleta.")
    })
	public ResponseEntity<MensajeRespuesta> crearUsuario(
			@ApiParam(name="nuevoUsuario", value="Usuario a registrar.", required = true)
			@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult result){

		Usuario usuario = new Usuario(nuevoUsuario.getNomUsuario(), nuevoUsuario.getPassword(), 
				                      nuevoUsuario.getEmail(), nuevoUsuario.getNomCompleto());        
		usuario.setClave(passwordEncoder.encode(nuevoUsuario.getPassword()));

		MensajeRespuesta msnRespuesta = usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

	@PutMapping
    @ApiOperation(value = "Permite modificar un la informacion de un usuario existente.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuario actualizado correctamente."),
        @ApiResponse(code = 204, message = "Usuario actualizado correctamente."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 400, message = "Informacion del usuario incompleta."),
        @ApiResponse(code = 404, message = "Usuario a modificar no existe.")        
    })
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
    @ApiOperation(value = "Permite modificar la contraseña de un usuario existente.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuario actualizado correctamente."),
        @ApiResponse(code = 204, message = "Usuario actualizado correctamente."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 400, message = "Informacion del usuario incompleta."),
        @ApiResponse(code = 404, message = "Usuario a modificar no existe.")        
    })
	public ResponseEntity<MensajeRespuesta> cambiarClaveUsuario(
			@ApiParam(name="idUsuario", value="Id del usuario a cambiar clave.", required = true)
			@PathVariable(name="idUsuario", required = true) Long idUsuario,
			@ApiParam(name="usuario", value="Usuario a cambiar clave.", required = true)
			@Valid @RequestBody CambioClaveUsuarioDto usuario, BindingResult result){

		String nuevaClave = passwordEncoder.encode(usuario.getPassword());
		MensajeRespuesta msnRespuesta = usuarioService.modificarClaveUsuario(idUsuario, nuevaClave);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{idUsuario}")
    @ApiOperation(value = "Permite eliminar un usuario.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuario eliminado correctamente."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
	public ResponseEntity<MensajeRespuesta> eliminarUsuario(
			@ApiParam(name="idUsuario", value="Id del usuario a eliminar.", required = true)
			@PathVariable(name="idUsuario", required = true) Long idUsuario){

		MensajeRespuesta msnRespuesta = usuarioService.eliminarUsuario(idUsuario);
		return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
	}

    @PostMapping("/login")
    @ApiOperation(value = "Permite generar un token de autenticacion valido por una hora.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Token generado."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 400, message = "Informacion del usuario incompleta o incorrecta.")
    })
    public ResponseEntity<TokenDto> login(
    	   @ApiParam(name="loginUsuario", value="Credenciales de autenticacion para obtener token.", required = true)
    	   @Valid @RequestBody LoginUsuarioDto loginUsuario, BindingResult result){
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
