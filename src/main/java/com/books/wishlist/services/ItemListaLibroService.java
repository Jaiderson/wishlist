package com.books.wishlist.services;

import org.springframework.stereotype.Service;

import com.books.wishlist.entities.ItemListaLibro;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public interface ItemListaLibroService {

	/**
	 * Busca un registro de asociacion de una lista de deseos con un libro en una posicion indiada.
	 * La busqueda se realiza en la tabla lista de libros buscando por los siguientes 3 campos: 
	 * id de la lista de deseos, id del libro y posicion del libro. [<b>idListaDeseo, idLibro y posicionLibro</b>]
	 * 
	 * @param listaLibro Lista libro a buscar.
	 * @return Registro asociando a una lista de deseos un libro o <b>null</b> si no se encuantra registro.
	 */
	public ItemListaLibro buscarItemListaLibro(ItemListaLibro listaLibro);

	/**
	 * Busca un registro de asociacion de una lista de deseos con un libro.
	 * La busqueda se realiza en la tabla lista de libros buscando por los siguientes 2 campos: 
	 * id de la lista de deseos y id del libro. [<b>idListaDeseo y idLibro</b>]
	 * 
	 * @param idLibro Id libro.
	 * @param idListaDeseo Id lista deseos.
	 * @return Registro asociando a una lista de deseos un libro o <b>null</b> si no se encuantra registro.
	 */
	public ItemListaLibro buscarItemListaLibro(Long idLibro, Long idListaDeseo);

	/**
	 * Crea un registro asociando a una lista de deseos un libro en una posicion indiada. 
	 * La posicion del libro debe ser unica dentro de la lista de deseos.
	 *  
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es <b>OK</b> este listado sera vacio.
	 * 
	 * @param nuevaLista Lista de libros a registrar.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta crearItemListaLibro(ItemListaLibro nuevaLista);

	/**
	 * Modifica el registro de asociando a una lista de deseos un libro en una posicion indiada, 
	 * pude utilizar este metodo cara cambiar la posicion de un libro.
	 * 
	 * La posicion del libro debe ser unica dentro de la lista de deseos.
	 *  
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * Esta opcion permite actualizar el nombre de la lista y la posicion de esta.
	 * 
	 * @param listaLibro Lista de libros a modificar.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta modificarItemListaLibro(ItemListaLibro listaLibro);

	/**
	 * Elimina el registro de asociando a una lista de deseos un libro en una posicion indiada. 
	 * 
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * @param idListaLibro Id unico de la lista a eliminar
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta eliminarItemListaLibro(Long idListaLibro);

	/**
	 * Elimina los registros asociandos a una lista de deseos. 
	 * 
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * @param idListaDeseo Id unico de la lista a eliminar
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta eliminarItemsListaDeseos(Long idListaDeseo);

}
