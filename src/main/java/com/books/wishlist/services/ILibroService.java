package com.books.wishlist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public interface ILibroService {

	/**
	 * Busca libros dado el nombre o palabra clave del libro <b>nombreLibro</b>.
	 * 
	 * @param nombreLibro Nombre del titulo del libro o palabra clave para su busqueda.
	 * @return Listado de libros o lista vacia si no encuentra nada.
	 */
	public List<Libro> buscarLibrosPorNombre(String nombreLibro);

	/**
	 * Busca un libro dado su identificador unico <b>idLibro</b>.
	 * 
	 * @param idLibro Identificador unico del libro.
	 * @return Libro o <b>null</b> si no encuentra nada.
	 */
	public Libro buscarLibroPorIdLibro(Long idLibro);

	/**
	 * Busca un libro dado su identificador unico del API de Google <b>idLibroApi</b>.
	 * 
	 * @param idLibroApi Identificador unico del API de Google.
	 * @return Libro o <b>null</b> si no encuentra nada.
	 */
	public Libro buscarLibroPorIdApiGoogle(String idLibroApi);

	/**
	 * Crea un nuevo libro
	 * 
	 * @param nuevoLibro
	 * @return
	 */
	public MensajeRespuesta crearLibro(Libro nuevoLibro);

	/**
	 * 
	 * @param libro
	 * @return
	 */
	public MensajeRespuesta modificarLibro(Libro libro);

	/**
	 * 
	 * @param idLibro
	 * @return
	 */
	public MensajeRespuesta eliminarLibro(Long idLibro);

}
