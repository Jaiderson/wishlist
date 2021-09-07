package com.books.wishlist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaLibroPk;

@Service
public interface ILibroService {

	public List<Libro> listarLibros(ListaLibroPk listaLibro);

	public Libro buscarLibroPorId(ListaLibroPk listaLibro, String idLibroApi);

	public Libro crearLibro(ListaLibroPk listaLibro, Libro nuevoLibro);

	public Libro modificarLibro(ListaLibroPk listaLibro, Libro libro);

	public Libro eliminarLibro(ListaLibroPk listaLibro, String idLibroApi);
	

	
	
	public Libro buscarLibroPorIdApiGoogle(String idLibroApi);
	
	public Libro crearLibro(Libro nuevoLibro);
	
	public Libro modificarLibro(Libro libro);

	public Libro eliminarLibro(String idLibroApi);
	

}
