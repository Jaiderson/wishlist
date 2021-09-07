package com.books.wishlist.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.security.controllers.RequestMensaje;
import com.books.wishlist.services.IListaDeseoService;
import com.books.wishlist.utils.MensajeError;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/listaDeseos")
public class ListaDeseoController {

	@Autowired
	private IListaDeseoService listaDeseoService;

    @GetMapping
    public ResponseEntity<List<ListaDeseo>> listarTodo(){
        List<ListaDeseo> listasDeseos = listaDeseoService.listarTodas();
        if(listasDeseos.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen listas de deseos registradas.");
        }
        return ResponseEntity.ok(listasDeseos); 
    }

    @PostMapping
    public ResponseEntity<ListaDeseo> crearListaDeseo(@Valid @RequestBody ListaDeseo nuevaLista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        ListaDeseo nuevaListaDeseo = listaDeseoService.crearLista(nuevaLista);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaListaDeseo);
    }

    @PutMapping
    public ResponseEntity<ListaDeseo> modificarListaDeseo(
    		@Valid @RequestBody ListaDeseo lista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        ListaDeseo listaDeseo = listaDeseoService.modificarLista(lista);
        if(null == listaDeseo) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista a modificar no encontrada.");
		}
        return ResponseEntity.status(HttpStatus.OK).body(listaDeseo);
    }

    
    @PutMapping(value = "/agregarLibro")
    public ResponseEntity<ListaDeseo> agregraLibro(
    		@Valid @RequestBody ListaDeseo listaDeseo, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        List<Libro> libros = Lists.newArrayList(listaDeseo.getLibros());
        Libro libro = libros.get(0);
        RequestMensaje msnRequest = listaDeseoService.agregarLibro(listaDeseo.getListaPk(), libro);

        if(!msnRequest.isOk()) {
        	throw new ResponseStatusException(msnRequest.getStatus(), msnRequest.getValor().toString());
		}
        return ResponseEntity.status(HttpStatus.OK).body((ListaDeseo) msnRequest.getValor());
    }
}
