package com.books.wishlist.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.books.wishlist.dto.ListasUsuario;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.services.IListaDeseoService;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/listaDeseos")
public class ListaDeseoController {

	public static final String NO_ES_PROPIETARIO = "Solicitud rechazada. Acceso al recurso no es propiedad de ";
	public static final Long ID_INEXISTENTE = -150L;

	@Autowired
	private IListaDeseoService listaDeseoService;

    @GetMapping(value = "/{idUsuario}")
    @ApiOperation(value = "Permite buscar las listas de deseos regitradas de un usuario dado su id.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Listas no encontradas."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 404, message = "Sin listas registradas.")
    })
    public ResponseEntity<List<ListasUsuario>> buscarListasDeseoPorIdUsuario(
    		@ApiParam(name="idUsuario", value="Id de usuario.")
    		@PathVariable(name="idUsuario", required = true) Long idUsuario){
        
        if(!listaDeseoService.isUsuarioPropietario(idUsuario, getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }

    	List<ListaDeseo> listasDeseos = listaDeseoService.buscarListasDeseoPorIdUsuario(idUsuario);
        if(listasDeseos.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existen listas de deseos registradas para el usuario id = "+idUsuario);
        }
        List<ListasUsuario> listasUsuario = Lists.newArrayList(); 
        listasDeseos.stream().forEach(lista -> {
        	listasUsuario.add(ListasUsuario.convertirListaDeseo(lista));
        });
        
        listasUsuario.stream().forEach(listaDeseo ->{
        	listaDeseo.setLibros(listaDeseoService.buscarLibrosListaDeseo(listaDeseo.getIdLista()));
        });

        return ResponseEntity.ok(listasUsuario); 
    }

    @PostMapping
    @ApiOperation(value = "Permite crear una lista de deseos nueva, Recuerde que el nombre "+
                          "de la lista y la posiscion deben ser unicos.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Lista creada."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada.")
    })
    public ResponseEntity<MensajeRespuesta> crearListaDeseo(
    		@ApiParam(name="nuevaLista", value="Lista de deseos a crear", required = true)
    		@Valid @RequestBody ListaDeseo nuevaLista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        if(!listaDeseoService.isUsuarioPropietario(nuevaLista.getUsuario().getIdUsuario(), getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.crearListaDeseo(nuevaLista);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PutMapping
    @ApiOperation(value = "Permite modificar los datos de una lista de deseos, Recuerde que el nombre "+
                          "de la lista y la posiscion deben ser unicos.")
		@ApiResponses({
		@ApiResponse(code = 200, message = "Lista actualizada correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada."),
		@ApiResponse(code = 404, message = "Lista a modificar no existe.")
		})
    public ResponseEntity<MensajeRespuesta> modificarListaDeseo(
    	   @ApiParam(name="lista", value="Lista a actualizar.")
           @Valid @RequestBody ListaDeseo lista, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        if(!listaDeseoService.isUsuarioPropietario(lista.getUsuario().getIdUsuario(), getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.modificarListaDeseo(lista);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/{idListaDeseo}")
    @ApiOperation(value = "Permite eliminar una lista de deseos dado su id. "+
                          "Si la lista contiene libros asociados elina tambien dicha asociacion.")
		@ApiResponses({
		@ApiResponse(code = 200, message = "Lista eliminada correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada."),
		@ApiResponse(code = 404, message = "Lista a eliminar no existe.")
		})    
    public ResponseEntity<MensajeRespuesta> eliminarListaDeseo(
    	   @ApiParam(name="idListaDeseo", value="Id de la ista a eliminar.")
           @PathVariable("idListaDeseo") Long idListaDeseo){

    	ListaDeseo listaDeseo = listaDeseoService.buscarListaDeseo(idListaDeseo);
    	Long idUsuario = (null == listaDeseo) ? ID_INEXISTENTE : listaDeseo.getUsuario().getIdUsuario();

    	if(idUsuario.equals(ID_INEXISTENTE) || !listaDeseoService.isUsuarioPropietario(idUsuario, getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarListaDeseo(idListaDeseo);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }


    @PostMapping("/al")
    @ApiOperation(value = "Permite agreagar un libro preexistente a una lista registrada. "+
                          "Este servicio no valida si existe el libro.")
		@ApiResponses({
		@ApiResponse(code = 201, message = "Libro agredado a la lista correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada."),
		@ApiResponse(code = 404, message = "Lista o libro no existe.")
		})
    public ResponseEntity<MensajeRespuesta> agregarLibroListaDeseo(
    	   @ApiParam(name="libro", value="Libro a asosiar a la lista.")
           @Valid @RequestBody LibroExistenteDto libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
    	ListaDeseo listaDeseo = listaDeseoService.buscarListaDeseo(libro.getIdListaDeseo());
    	Long idUsuario = (null == listaDeseo) ? ID_INEXISTENTE : listaDeseo.getUsuario().getIdUsuario();

    	if(idUsuario.equals(ID_INEXISTENTE) || !listaDeseoService.isUsuarioPropietario(idUsuario, getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.agregarLibroListaDeseos(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PostMapping("/adln")
    @ApiOperation(value = "Permite agreagar un libro a una lista registrada. "+
                          "Este servicio no valida existe el libro, si no existe lo registra.")
		@ApiResponses({
		@ApiResponse(code = 201, message = "Libro agredado a la lista correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada."),
		@ApiResponse(code = 404, message = "Lista o libro no existe.")
		})
    public ResponseEntity<MensajeRespuesta> agregarLibroListaDeseoCreaLibro(
           @Valid @RequestBody LibroNuevoDto libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
    	ListaDeseo listaDeseo = listaDeseoService.buscarListaDeseo(libro.getIdListaDeseo());
    	Long idUsuario = (null == listaDeseo) ? ID_INEXISTENTE : listaDeseo.getUsuario().getIdUsuario();

    	if(idUsuario.equals(ID_INEXISTENTE) || !listaDeseoService.isUsuarioPropietario(idUsuario, getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.agregarLibroListaDeseosCreaLibro(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/ild/{idListaDeseo}/ili/{idLibro}")
    @ApiOperation(value = "Permite eliminar un libro a una lista registrada. Es decir la asocioacion lista - libro.")
		@ApiResponses({
		@ApiResponse(code = 201, message = "Libro eliminado de la lista correctamente."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 400, message = "Informacion de la lista incompleta o errada."),
		@ApiResponse(code = 404, message = "Lista o libro no existe.")
		})
    public ResponseEntity<MensajeRespuesta> eliminarLibroListaDeseo(
           @PathVariable("idListaDeseo") Long idListaDeseo,
           @PathVariable("idLibro") Long idLibro){

    	ListaDeseo listaDeseo = listaDeseoService.buscarListaDeseo(idListaDeseo);
    	Long idUsuario = (null == listaDeseo) ? ID_INEXISTENTE : listaDeseo.getUsuario().getIdUsuario();

    	if(idUsuario.equals(ID_INEXISTENTE) || !listaDeseoService.isUsuarioPropietario(idUsuario, getUsuario())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_ES_PROPIETARIO + getUsuario());
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarLibroListaDeseo(idListaDeseo, idLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    private String getUsuario() {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userDetails.getUsername();
    }

}
