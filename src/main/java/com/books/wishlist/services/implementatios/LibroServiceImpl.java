package com.books.wishlist.services.implementatios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaLibroPk;
import com.books.wishlist.repositories.ILibroRep;
import com.books.wishlist.services.ILibroService;

@Service
public class LibroServiceImpl implements ILibroService {

	public LibroServiceImpl(ILibroRep libroRep) {
		super();
		this.libroRep = libroRep;
	}

	@Autowired
	private ILibroRep libroRep;

	@Override
	public List<Libro> listarLibros(ListaLibroPk listaLibro) {
		return libroRep.buscarLibros(listaLibro.getUsuario().getIdUsuario(), 
				                     listaLibro.getPosicion(), 
				                     listaLibro.getNomListaDeseos());
	}

	@Override
	public Libro buscarLibroPorId(ListaLibroPk listaLibro, String idLibroApi) {
		return libroRep.buscarLibro(listaLibro.getUsuario().getIdUsuario(), 
				                    listaLibro.getPosicion(), 
				                    listaLibro.getNomListaDeseos(),
				                    idLibroApi);
	}

	@Override
	public Libro crearLibro(ListaLibroPk listaLibro, Libro nuevoLibro) {
		Libro libroDb = buscarLibroPorId(listaLibro, nuevoLibro.getIdLibroApi());
		if(null == libroDb) {
			libroDb = libroRep.save(nuevoLibro);
		}
		return libroDb;
	}

	@Override
	public Libro modificarLibro(ListaLibroPk listaLibro, Libro libro) {
		Libro libroDb = buscarLibroPorId(listaLibro, libro.getIdLibroApi());
		if(null != libroDb) {
			libroDb = libroRep.save(libro);
		}
		return libroDb;
	}

	@Override
	public Libro eliminarLibro(ListaLibroPk listaLibro, String idLibroApi) {
		Libro libroDb = buscarLibroPorId(listaLibro, idLibroApi);
		if(null != libroDb) {
			libroRep.delete(libroDb);
		}
		return libroDb;
	}

	@Override
	public Libro crearLibro(Libro nuevoLibro) {
		Libro libroDb = buscarLibroPorIdApiGoogle(nuevoLibro.getIdLibroApi());
		if(null == libroDb) {
			libroDb = libroRep.save(nuevoLibro);
		}
		return libroDb;
	}

	@Override
	public Libro modificarLibro(Libro libro) {
		Libro libroDb = buscarLibroPorIdApiGoogle(libro.getIdLibroApi());
		if(null != libroDb) {
			libroDb = libroRep.save(libro);
		}
		return libroDb;
	}

	@Override
	public Libro eliminarLibro(String idLibroApi) {
		Libro libroDb = buscarLibroPorIdApiGoogle(idLibroApi);
		if(null != libroDb) {
			libroRep.delete(libroDb);
		}
		return libroDb;
	}

	@Override
	public Libro buscarLibroPorIdApiGoogle(String idLibroApi) {
		return libroRep.findByIdLibroApi(idLibroApi);
	}

}
