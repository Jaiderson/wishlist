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

import com.books.wishlist.dto.LibroExistenteDto;
import com.books.wishlist.dto.LibroNuevoDto;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.services.IListaDeseoService;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;

@Controller
@RequestMapping(value = "/listaDeseos")
public class ListaDeseoController {

	@Autowired
	private IListaDeseoService listaDeseoService;

    @GetMapping(value = "/{idUsuario}")
    public ResponseEntity<List<ListaDeseo>> buscarListasDeseoPorIdUsuario(
    		@PathVariable(name="idUsuario", required = true) Long idUsuario){
        List<ListaDeseo> listasDeseos = listaDeseoService.buscarListasDeseoPorIdUsuario(idUsuario);
        if(listasDeseos.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen listas de deseos registradas para el usuario id = "+idUsuario);
        }
        return ResponseEntity.ok(listasDeseos); 
    }

    @PostMapping
    public ResponseEntity<MensajeRespuesta> crearListaDeseo(@Valid @RequestBody ListaDeseo nuevaLista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.crearListaDeseo(nuevaLista);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PutMapping
    public ResponseEntity<MensajeRespuesta> modificarListaDeseo(
           @Valid @RequestBody ListaDeseo lista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.modificarListaDeseo(lista);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/{idListaDeseo}")
    public ResponseEntity<MensajeRespuesta> eliminarListaDeseo(
           @PathVariable("idListaDeseo") Long idListaDeseo){

        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarListaDeseo(idListaDeseo);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    //********************   METODOS PRIMORDIALES   ********************\\

    @PostMapping("/add-libro")
    public ResponseEntity<MensajeRespuesta> agregarLibroListaDeseo(
           @Valid @RequestBody LibroExistenteDto libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.agregarLibroListaDeseos(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PostMapping("/add-libro-nuevo")
    public ResponseEntity<MensajeRespuesta> agregarLibroListaDeseoCreaLibro(
           @Valid @RequestBody LibroNuevoDto libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.agregarLibroListaDeseos(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/ild/{idListaDeseo}/ili/{idLibro}")
    public ResponseEntity<MensajeRespuesta> eliminarLibroListaDeseo(
           @PathVariable("idListaDeseo") Long idListaDeseo,
           @PathVariable("idLibro") Long idLibro){

        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarLibroListaDeseo(idListaDeseo, idLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

}
