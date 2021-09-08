package com.books.wishlist.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.books.wishlist.entities.Libro;
import com.books.wishlist.services.ILibroService;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;

@Controller
@RequestMapping(value = "/libros")
public class LibroController {

	@Autowired
	private ILibroService listaDeseoService;

    @GetMapping(value = "/id/{idLibro}")
    public ResponseEntity<Libro> buscarLibroPorIdLibro(
    		@PathVariable(name="idLibro", required = true) Long idLibro){
        Libro libro = listaDeseoService.buscarLibroPorIdLibro(idLibro);
        if(null == libro) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe libro para el id = "+idLibro);
        }
        return ResponseEntity.ok(libro); 
    }

    @GetMapping(value = "/api/{idLibroApi}")
    public ResponseEntity<Libro> buscarLibroPorIdApiGoogle(
    		@PathVariable(name="idLibroApi", required = true) String idLibroApi){
        Libro libro = listaDeseoService.buscarLibroPorIdApiGoogle(idLibroApi);
        if(null == libro) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe libro para el idApi = "+idLibroApi);
        }
        return ResponseEntity.ok(libro); 
    }

    @GetMapping(value = "/{nombreLibro}")
    public ResponseEntity<List<Libro>> buscarLibrosPorNombre(
    		@PathVariable(name="nombreLibro", required = true) String nombreLibro){
    	List<Libro> libros = listaDeseoService.buscarLibrosPorNombre(nombreLibro.trim().toUpperCase());
        if(libros.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen libros para el titulo = "+nombreLibro);
        }
        return ResponseEntity.ok(libros); 
    }

    @PostMapping
    public ResponseEntity<MensajeRespuesta> crearLibro(@Valid @RequestBody Libro nuevoLibro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.crearLibro(nuevoLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PutMapping
    public ResponseEntity<MensajeRespuesta> modificarLibro(
    		@Valid @RequestBody Libro libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.modificarLibro(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/{idLibro}")
    public ResponseEntity<MensajeRespuesta> eliminarLibro(
    		@PathVariable("idLibro") Long idLibro){

        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarLibro(idLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

}
