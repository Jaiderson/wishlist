package com.books.wishlist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.books.wishlist.dto.LibroExistenteDto;
import com.books.wishlist.dto.LibroNuevoDto;
import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public interface IListaDeseoService {

	/**
	 * Busca un listado libros asociado a una lista de deseos dado su identificador unico <b>idListaDeseo</b>
	 * 
	 * @param idListaDeseo Identificador unico de la lista de deseos.
	 * @return Lista de deseos o <b>null</b> si no la encuentra.
	 */
	public List<Libro> buscarLibrosListaDeseo(Long idListaDeseo);

	/**
	 * Busca una lista de deseos dado su identificador unico de usuario <b>idUsuario</b>.
	 * 
	 * @param idUsuario Identificador unico del usuario.
	 * @return Todas las listas de deseos del usuario o una lista vacia si no tiene registradas.
	 */
	public List<ListaDeseo> buscarListasDeseoPorIdUsuario(Long idUsuario);

	/**
	 * Busca una lista de deseos dado su identificador unico <b>idLista</b>
	 * 
	 * @param idLista Identificador unico de la lista de deseos.
	 * @return Lista de deseos o <b>null</b> si no la encuentra.
	 */
	public ListaDeseo buscarListaDeseo(Long idLista);

	/**
	 * Busca una lista de deseos por si clave unica, es decir busca por posicion de la lista,
	 * nombre de la lista y id de su usuario por lo que estos atributos son obligatorios.
	 * 
	 * @param listaDeseo Lista de deseo a buscar.
	 * @return Lista de deseos o <b>null</b> si no la encuentra.
	 */
	public ListaDeseo buscarListaDeseo(ListaDeseo listaDeseo);

	/**
	 * Crea una lista de deseos asociada a un usuario. 
	 * Para el nodo de usuario solo es requerido el <b>idUsuario</b>. 
	 * 
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es <b>OK</b> este listado sera vacio.
	 * 
	 * @param nuevaLista Lista de deseos a registrar.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta crearListaDeseo(ListaDeseo nuevaLista);

	/**
	 * Modifica una lista de deseos nueva asociada a un usuario. 
	 * Para el nodo de usuario solo es requerido el <b>idUsuario</b>.
	 *  
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * Esta opcion permite actualizar el nombre de la lista y la posicion de esta.
	 * 
	 * @param listaDeseos Lista de deseos a modificar.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta modificarListaDeseo(ListaDeseo listaDeseos);

	/**
	 * Elimina una lista de deseos dado su id <b>idLista</b> junto con los libros asociados a esta. 
	 * 
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * @param idLista Id unico de la lista a eliminar
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta eliminarListaDeseo(Long idLista);

	/**
	 * Metodo que permite agregar un libro a una lista de deseos existente. Este metodo no valida
	 * la pre existencia del libro por lo que la insercion puede fallar si el idLibro no existe
	 * en la entidad <b>Libro</b>.
	 * 
	 * @param libro Dto con la informacion necesaria para el registro.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta agregarLibroListaDeseos(LibroExistenteDto libro);

	/**
	 * Metodo que permite agregar un libro a una lista de deseos existente. Este metodo valida
	 * la pre existencia del libro, si este no existe intenta registrarlo en la entidad <b>Libro</b>.
	 * 
	 * @param libro Dto con la informacion necesaria para el registro.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta agregarLibroListaDeseosCreaLibro(LibroNuevoDto libro);

	/**
	 * Elimina de una lista de deseos un libro dado el identificador unico de la lista de deseos 
	 * y el identificador unico del libro perteneciente a la lista. 
	 * 
	 * Retorna un mensaje respuesta el cual contiene el HttpStatus de la solicitud y 
	 * un listado de inconsistencias, si todo es OK este listado sera vacio.
	 * 
	 * @param idListaDeseo Id unico de la lista donde se eliminara el libro.
	 * @param idLibro Id unico del libro a quitar de la lista.
	 * @return Mensaje de respuesta con el estado http.
	 */
	public MensajeRespuesta eliminarLibroListaDeseo(Long idListaDeseo, Long idLibro);	

	/**
	 * Permite validar si un usuario que solicita acceso a un recurso sea el propietario de dichos recursos.
	 * 
	 * @param idUsuario Id de usuario a validar.
	 * @param nombreUsuario Nombre del usuario que solicita acceso.
	 * @return True si es el usuario propietario.
	 */
	public boolean isUsuarioPropietario(Long idUsuario, String nombreUsuario);
}
