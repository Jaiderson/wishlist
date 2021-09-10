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

import com.books.wishlist.dto.BusquedaParametricaDto;
import com.books.wishlist.dto.RespuestaLibroDto;
import com.books.wishlist.entities.Libro;
import com.books.wishlist.services.ILibroService;
import com.books.wishlist.services.clients.ClienteLibro;
import com.books.wishlist.services.clients.ETipoBusqueda;
import com.books.wishlist.utils.MensajeError;
import com.books.wishlist.utils.MensajeRespuesta;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/libros")
public class LibroController {

	@Autowired
	private ILibroService listaDeseoService;

    @GetMapping(value = "/ag/{datoGeneral}")
    @ApiOperation(value = "Permite buscar libros en el API de Google ingresando el nombre "+
                          "del libro o el nombre del autor o cualquier parametror que el "+
    		              "usuario considere que identifica al libro.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Proceso de busqueda en API Google finaliza correctamente."),
        @ApiResponse(code = 204, message = "Sin contenido."),
        @ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
        @ApiResponse(code = 404, message = "No se encontraron libros.")
    })
    public ResponseEntity<RespuestaLibroDto> buscarLibroApiGoogle(
    		@ApiParam(name="datoGeneral", value="Valor del contenido a buscar. Puede ser el nombre del libro o autor, etc.")
    		@PathVariable(name="datoGeneral", required = true) String datoGeneral){

    	ClienteLibro clienteLibro = new ClienteLibro();
    	RespuestaLibroDto rtaLibroDto = clienteLibro.buscarLibros(datoGeneral);
        return ResponseEntity.ok(rtaLibroDto); 
    }

    @GetMapping(value = "/agp/{datoGeneral}")
    @ApiOperation(value = "Permite buscar libros en el API de Google de manera personalizada. "+
                          "El usuario debe especificar el tipo de busqueda y el valor "+
    		              "correspondiente a este tipo de busqueda. Por ejemplo AUTOR y Gabriel Garcia Marquez." )
	@ApiResponses({
	@ApiResponse(code = 200, message = "Proceso de busqueda en API Google finaliza correctamente."),
	@ApiResponse(code = 204, message = "Sin contenido."),
	@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
	@ApiResponse(code = 404, message = "No se encontraron libros.")
	})
    public ResponseEntity<RespuestaLibroDto> buscarLibroApiGoogleProParametro(
    		@ApiParam(name="datoGeneral", value="Valor del contenido a buscar. Puede ser el nombre del libro o autor, etc.")
    		@PathVariable(name="datoGeneral", required = true) String datoGeneral,
    		@ApiParam(name="busqueda", value="Objeto con los valores personalizados de busqueda. "+
    		                                 "Por ejemplo AUTOR y nombre del autor.")
    		@RequestBody BusquedaParametricaDto busqueda){

    	String parametro = busqueda.getParamPersonalizadoBusqueda().trim().toUpperCase();
    	List<String> parametrosPermitidos = Lists.newArrayList("AUTOR","TITULO","EDITOR","CATEGORIA");
    	ETipoBusqueda tipoBusqueda = null;

    	if(parametrosPermitidos.contains(parametro)) {
    		tipoBusqueda = ETipoBusqueda.getTipoBusqueda(parametro);
    	}
    	
    	ClienteLibro clienteLibro = new ClienteLibro();
    	RespuestaLibroDto rtaLibroDto = (null == tipoBusqueda) ? clienteLibro.buscarLibros(datoGeneral)
                                         : clienteLibro.buscarLibros(datoGeneral, tipoBusqueda, busqueda.getContenidoBusqueda());
        return ResponseEntity.ok(rtaLibroDto); 
    }	

    @GetMapping(value = "/il/{idLibro}")
    @ApiOperation(value = "Permite buscar un libro en nuestro modelo dado su id.")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Proceso de busqueda finaliza correctamente."),
    	@ApiResponse(code = 204, message = "Sin contenido."),
    	@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
    	@ApiResponse(code = 404, message = "Libro no encontrado.")
    })
    public ResponseEntity<Libro> buscarLibroPorIdLibro(
    	   @ApiParam(name="idLibro", value="Id del libro a buscar")
    	   @PathVariable(name="idLibro", required = true) Long idLibro){
        Libro libro = listaDeseoService.buscarLibroPorIdLibro(idLibro);
        if(null == libro) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe libro para el id = "+idLibro);
        }
        return ResponseEntity.ok(libro); 
    }

    @GetMapping(value = "/iag/{idLibroApi}")
    @ApiOperation(value = "Permite buscar un libro en nuestro modelo dado su id correspondiente al id del API book de Google.")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Proceso de busqueda finaliza correctamente."),
    	@ApiResponse(code = 204, message = "Sin contenido."),
    	@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
    	@ApiResponse(code = 404, message = "Libro no encontrado.")
    })
    public ResponseEntity<Libro> buscarLibroPorIdApiGoogle(
    	   @ApiParam(name="idLibroApi", value="Id del libro a buscar el cual debe corresponder con el id del API book de Google.")
    	   @PathVariable(name="idLibroApi", required = true) String idLibroApi){
        Libro libro = listaDeseoService.buscarLibroPorIdApiGoogle(idLibroApi);
        if(null == libro) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe libro para el idApi = "+idLibroApi);
        }
        return ResponseEntity.ok(libro); 
    }

    @GetMapping(value = "/{nombreLibro}")
    @ApiOperation(value = "Permite buscar un libro en nuestro modelo dado su nombre. "+
                          "Se buscaran todos lo libros donde el nombre este contenido "+
    		              "el prarametro de ingreso nombreLibro.")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Proceso de busqueda finaliza correctamente."),
    	@ApiResponse(code = 204, message = "Sin contenido."),
    	@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
    	@ApiResponse(code = 404, message = "Libros no encontrados.")
    })
    public ResponseEntity<List<Libro>> buscarLibrosPorNombre(
    	   @ApiParam(name="nombreLibro", value="Nombre del libro a buscar.")
    	   @PathVariable(name="nombreLibro", required = true) String nombreLibro){
    	List<Libro> libros = listaDeseoService.buscarLibrosPorNombre(nombreLibro.trim().toUpperCase());
        if(libros.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen libros para el titulo = "+nombreLibro);
        }
        return ResponseEntity.ok(libros); 
    }

    @PostMapping
    @ApiOperation(value = "Permite registrar un libro nuevo en nuestro modelo.")
		@ApiResponses({
		@ApiResponse(code = 201, message = "Libro creado correctamente."),
		@ApiResponse(code = 204, message = "Sin contenido."),
		@ApiResponse(code = 400, message = "Informacion incompleta o errada."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado.")
		})
    public ResponseEntity<MensajeRespuesta> crearLibro(
    	   @ApiParam(name="nombreLibro", value="Libro a registrar.")
    	   @Valid @RequestBody Libro nuevoLibro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.crearLibro(nuevoLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @PutMapping
    @ApiOperation(value = "Permite modificar los datos de un libro registrado en nuestro modelo.")
		@ApiResponses({
		@ApiResponse(code = 200, message = "Libro modificado correctamente."),
		@ApiResponse(code = 204, message = "Sin contenido."),
		@ApiResponse(code = 400, message = "Informacion incompleta o errada."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Libro a modificar no existe.")
		})
    public ResponseEntity<MensajeRespuesta> modificarLibro(
    	   @ApiParam(name="libro", value="Libro a actualizar.")
    	   @Valid @RequestBody Libro libro, BindingResult result){
        if(result.hasErrors()) {
            MensajeError msnError = new MensajeError(MensajeError.CREAR_REGISTRO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MensajeRespuesta msnRespuesta = listaDeseoService.modificarLibro(libro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

    @DeleteMapping(value="/{idLibro}")
    @ApiOperation(value = "Permite eliminar un libro registrado en nuestro modelo.")
		@ApiResponses({
		@ApiResponse(code = 200, message = "Libro eliminado correctamente."),
		@ApiResponse(code = 204, message = "Sin contenido."),
		@ApiResponse(code = 400, message = "Informacion incompleta o errada."),
		@ApiResponse(code = 401, message = "Acceso al recurso no autorizado."),
		@ApiResponse(code = 404, message = "Libro a eliminar no existe.")
		})
    public ResponseEntity<MensajeRespuesta> eliminarLibro(
   		   @ApiParam(name="idLibro", value="Identificador unico del ibro a eliminar.")
    	   @PathVariable("idLibro") Long idLibro){

        MensajeRespuesta msnRespuesta = listaDeseoService.eliminarLibro(idLibro);
        return ResponseEntity.status(msnRespuesta.generarEstadoHttp()).body(msnRespuesta);
    }

}
