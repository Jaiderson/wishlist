package com.books.wishlist.services.implementatios;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.entities.ListaLibroPk;
import com.books.wishlist.repositories.IListaDeseoRep;
import com.books.wishlist.security.controllers.RequestMensaje;
import com.books.wishlist.services.IListaDeseoService;
import com.google.common.collect.Sets;

@Service
public class ListaDeseoServiceImpl implements IListaDeseoService {

	public ListaDeseoServiceImpl(IListaDeseoRep listaDeseoRep) {
		super();
		this.listaDeseoRep = listaDeseoRep;
	}
	
	@Autowired
	private IListaDeseoRep listaDeseoRep;
	
	@Override
	public List<ListaDeseo> listarTodas() {
		return listaDeseoRep.findAll();
	}

	@Override
	public ListaDeseo buscarListaDeseo(ListaLibroPk listaLibroPk) {
		ListaDeseo result = null;
		List<ListaDeseo> listaDeseos = listaDeseoRep.findByListaPk(listaLibroPk);
		if(!listaDeseos.isEmpty()) {
			result = listaDeseos.get(0);
		}
		return result;
	}

	@Override
	public ListaDeseo crearLista(ListaDeseo nuevaLista) {
		ListaDeseo listaDeseoDb = buscarListaDeseo(nuevaLista.getListaPk());
		
		if(null == listaDeseoDb) {
			nuevaLista.setIdLista(listaDeseoRep.secuenciaListaLibro());
			listaDeseoDb = listaDeseoRep.save(nuevaLista);
		}
		return listaDeseoDb;
	}

	@Override
	public ListaDeseo modificarLista(ListaDeseo nuevaLista) {
		//ListaDeseo listaDeseoDb = buscarListaDeseo(nuevaLista.getListaPk());
		ListaDeseo listaDeseoDb = buscarListaDeseo(nuevaLista.getIdLista());
		if(null != listaDeseoDb) {
			listaDeseoDb.setLibros(Sets.newHashSet());
			listaDeseoDb = listaDeseoRep.save(nuevaLista);
		}
		return listaDeseoDb;
	}

	@Override
	public ListaDeseo eliminarLista(ListaLibroPk listaLibroPk) {
		ListaDeseo listaDeseoDb = buscarListaDeseo(listaLibroPk);
		if(null != listaDeseoDb) {
			listaDeseoRep.delete(listaDeseoDb);
		}
		return listaDeseoDb;
	}

	@Override
	public RequestMensaje agregarLibro(ListaLibroPk listaLibroPk, Libro libro) {
		RequestMensaje msnRequest = new RequestMensaje();
		msnRequest.setOk(true);

		ListaDeseo listaDeseo = this.buscarListaDeseo(listaLibroPk);
		if(null != listaDeseo) {
			Predicate<Libro> libroPredicate =  (lib) -> lib.getIdLibroApi().equals(libro.getIdLibroApi());
			List<Libro> libros = listaDeseo.getLibros().stream().filter(libroPredicate).collect(Collectors.toList());

			if(libros.isEmpty()) {
				listaDeseo.getLibros().add(libro);
				listaDeseo = listaDeseoRep.save(listaDeseo);
				listaDeseo.limpiarDatos();
				msnRequest.setValor(listaDeseo);
				msnRequest.setOk(true);
			}
			else {
				msnRequest.setValor("Libro con id = "+libro.getIdLibroApi()+" ya esta registrado.");
				msnRequest.setEstado(RequestMensaje.YA_EXISTE);
				msnRequest.setOk(false);
			}
		}
		else {
			msnRequest.setValor("Lista con PK = "+listaLibroPk+" no encontrada.");
			msnRequest.setEstado(RequestMensaje.NO_ENCONTRADO);
			msnRequest.setOk(false);
		}
		return msnRequest;
	}

	@Override
	public ListaDeseo agregarLibros(List<Libro> libros, Long id) {
		ListaDeseo listaDeseo = null;
		Optional<ListaDeseo> optListaDeseo = listaDeseoRep.findById(id);
		if(optListaDeseo.isPresent()) {
			listaDeseo = optListaDeseo.get();
			
		}
		return listaDeseo;
	}

	
	
	
	
	@Override
	public ListaDeseo eliminarLibros(List<Libro> libros, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaDeseo eliminarLibros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaDeseo eliminarLibro(Libro libro, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaDeseo buscarListaDeseo(Long idLista) {
		return listaDeseoRep.findByIdLista(idLista);
	}

}
