package com.books.wishlist.services.implementatios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.repositories.ILibroRep;
import com.books.wishlist.services.ILibroService;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public class LibroServiceImpl implements ILibroService {

	public LibroServiceImpl(ILibroRep libroRep) {
		super();
		this.libroRep = libroRep;
	}

	@Autowired
	private ILibroRep libroRep;

	@Override
	public Libro buscarLibroPorIdLibro(Long idLibro) {
		return libroRep.findByIdLibro(idLibro);
	}

	@Override
	public Libro buscarLibroPorIdApiGoogle(String idLibroApi) {
		return libroRep.findByIdLibroApi(idLibroApi);
	}

	@Override
	public List<Libro> buscarLibrosPorNombre(String nombreLibro) {
		return libroRep.buscarLibrosPorNombre(nombreLibro);
	}

	@Override
	public MensajeRespuesta crearLibro(Libro nuevoLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Libro libroBd = buscarLibroPorIdApiGoogle(nuevoLibro.getIdLibroApi());
		if(null == libroBd) {
			 libroRep.save(nuevoLibro);
			 msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.YA_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.YA_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta modificarLibro(Libro libro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Libro libroBd = buscarLibroPorIdApiGoogle(libro.getIdLibroApi());
		if(null != libroBd) {
			 libro.setIdLibro(libroBd.getIdLibro());
			 libro.setFecCreacion(libroBd.getFecCreacion());
			 this.guardarLiro(msnRespuesta, libro);
			 msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta eliminarLibro(Long idLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Libro libroBd = buscarLibroPorIdLibro(idLibro);
		if(null != libroBd) {
			libroRep.delete(libroBd);
			msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	/**
	 * Metodo usado para guardar la informacion de una lista de deseos, en caso de encontrar
	 * inconsistencias se guardaran en el objeto de entrada <b>msnRespuesta</b>.
	 * 
	 * @param msnRespuesta Objeto en el cual se guardara las inconsistencias en caso de encontrar alguna.
	 * @param libro Libro a guardar.
	 */
	private void guardarLiro(MensajeRespuesta msnRespuesta, Libro libro) {
		try {
			libroRep.save(libro);
		}
		catch (Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR + " "+ e.getMessage());
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
		}
	}

}
