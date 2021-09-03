package com.books.wishlist.security.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.books.wishlist.security.dto.MensajeError;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.services.IRolService;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/roles")
public class RolController {

	@Autowired
	private IRolService rolService;

	@GetMapping
	public ResponseEntity<List<Rol>> listarRoles(){
		List<Rol> roles = rolService.consultarRoles();
		if(roles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen roles registrados.");
		}
		return ResponseEntity.ok(roles); 
	}

	@GetMapping(value = "/{idRol}")
	public ResponseEntity<Rol> listarRol(@ApiParam(name="idRol", value="Valor opcional para traer los roles de un candidato.")
		   @PathVariable(name="idRol", required = true) Long idRol){

		Rol rol = rolService.buscarRol(idRol);
		if(null == rol) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con id "+idRol+" no encontrado.");
		}
		return ResponseEntity.ok(rol);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Rol> crearRol(@ApiParam(name="rol", value="Rol a crear", required = true)
			@Valid @RequestBody Rol rol, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		Rol nuevoRol = rolService.crearRol(rol);
		
		HttpStatus status = (null == nuevoRol) ? HttpStatus.OK : HttpStatus.CREATED; 

		return ResponseEntity.status(status).body(nuevoRol);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/{idRol}")
	public ResponseEntity<Rol> modificarRol(@ApiParam(name="idRol", value="Id obligatorio del rol.", required = true) 
	        @PathVariable("idRol") Long idRol, 
			@ApiParam(name="Rol", value="Rol a actualizar.") 
			@Valid @RequestBody Rol rol, BindingResult result){
		if(result.hasErrors()) {
			MensajeError msnError = new MensajeError(MensajeError.MODIFICAR_REGISTRO);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
		}
		rol.setIdRol(idRol);
		Rol nuevoRol = rolService.modificarRol(rol);
        if(null == nuevoRol) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol a modificar no encontrada.");
		}
		return ResponseEntity.ok(nuevoRol); 
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value="/{idRol}")
	public ResponseEntity<Rol> eliminarProduto(@ApiParam(name="idRol", required = true, value="Id del rol a eliminar.") 
			@PathVariable("idRol") Long idRol){

		Rol rol = rolService.eliminarRol(idRol);
		if(null == rol) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con id = "+idRol+" no encontrado.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(rol);
	}

}