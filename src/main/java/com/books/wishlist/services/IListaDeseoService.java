package com.books.wishlist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.entities.ListaLibroPk;
import com.books.wishlist.security.controllers.RequestMensaje;

@Service
public interface IListaDeseoService {

	public List<ListaDeseo> listarTodas();
	
	public ListaDeseo buscarListaDeseo(ListaLibroPk listaLibroPk);

	public ListaDeseo crearLista(ListaDeseo nuevaLista);

	/**
	 * Permite modificar la informacion de la lista de deseos pero no sus listas de libros.
	 * Para ello utilce los metodos agregarLibros o eliminarLibros
	 * 
	 * @param nuevaLista
	 * @return
	 */
	public ListaDeseo modificarLista(ListaDeseo nuevaLista);

	public ListaDeseo eliminarLista(ListaLibroPk listaLibroPk);


	
	
	
	
	public ListaDeseo buscarListaDeseo(Long id);
	
	public RequestMensaje agregarLibro(ListaLibroPk listaLibroPk, Libro libro);
	
	public ListaDeseo agregarLibros(List<Libro> libros, Long id);

	public ListaDeseo eliminarLibro(Libro libro, Long id);
	
	public ListaDeseo eliminarLibros(List<Libro> libros, Long id);

	public ListaDeseo eliminarLibros();

}
