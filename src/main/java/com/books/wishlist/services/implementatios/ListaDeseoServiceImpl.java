package com.books.wishlist.services.implementatios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.wishlist.dto.LibroExistenteDto;
import com.books.wishlist.dto.LibroNuevoDto;
import com.books.wishlist.entities.ItemListaLibro;
import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.repositories.IListaDeseoRep;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.services.ILibroService;
import com.books.wishlist.services.IListaDeseoService;
import com.books.wishlist.services.ItemListaLibroService;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
@Transactional
public class ListaDeseoServiceImpl implements IListaDeseoService {

	public ListaDeseoServiceImpl(ILibroService libroService, 
			                     IListaDeseoRep listaDeseoRep,
			                     ItemListaLibroService itemListaLibroService) {
		super();
		this.listaDeseoRep = listaDeseoRep;
		this.libroService  = libroService;
		this.itemListaLibroService = itemListaLibroService;
	}

	@Autowired
	private IListaDeseoRep listaDeseoRep;

	@Autowired
	private ItemListaLibroService itemListaLibroService;

	@Autowired
	private ILibroService libroService;

	@Override
	public ListaDeseo buscarListaDeseo(Long idListaDeseo) {
		return listaDeseoRep.findByIdLista(idListaDeseo);
	}

	@Override
	public ListaDeseo buscarListaDeseo(ListaDeseo listaDeseo) {
		return listaDeseoRep.buscarListaLibro(listaDeseo.getPosicionLista(), 
				                              listaDeseo.getNomListaDeseos(), 
				                              listaDeseo.getUsuario().getIdUsuario());
	}

	@Override
	public MensajeRespuesta crearListaDeseo(ListaDeseo nuevaLista) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ListaDeseo listaDeseoDb = buscarListaDeseo(nuevaLista);
		if(null == listaDeseoDb) {
			if(this.guardarListaDeseos(msnRespuesta, nuevaLista)) {
			    msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
			}
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.YA_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.YA_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta modificarListaDeseo(ListaDeseo listaDeseos) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ListaDeseo listaDeseoDb = buscarListaDeseo(listaDeseos.getIdLista());
		if(null != listaDeseoDb) {
			listaDeseos.setFecCreacion(listaDeseoDb.getFecCreacion());
			if(this.guardarListaDeseos(msnRespuesta, listaDeseos)) {
			    msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
			}
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta eliminarListaDeseo(Long idLista) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ListaDeseo listaDeseoDb = buscarListaDeseo(idLista);
		if(null != listaDeseoDb) {
			msnRespuesta = eliminarItemsListaLibroEliminarListaDeseo(listaDeseoDb);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	/**
	 * Elimina los registros asociados a una lista de deseos de la entidad lista de libros y 
	 * luego elimina la lista de deseos.
	 * 
	 * @param listaDeseo Lista de deseos a eliminar.
	 * @return Mensaje de respuesta con el estado http.
	 */
	private MensajeRespuesta eliminarItemsListaLibroEliminarListaDeseo(ListaDeseo listaDeseo) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		try {
			msnRespuesta = itemListaLibroService.eliminarItemsListaDeseos(listaDeseo.getIdLista());
			if(msnRespuesta.getEstado().equals(MensajeRespuesta.PROCESO_OK)) {
				listaDeseoRep.delete(listaDeseo);
				msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
			}
		}
		catch (Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR);
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
		}
		return msnRespuesta;
	}

	/**
	 * Metodo usado para guardar la informacion de una lista de deseos, en caso de encontrar
	 * inconsistencias se guardaran en el objeto de entrada <b>msnRespuesta</b>.
	 * 
	 * @param msnRespuesta Objeto en el cual se guardara las inconsistencias en caso de encontrar alguna.
	 * @param listaDeseos Lista de deseos a guardar.
	 */
	private boolean guardarListaDeseos(MensajeRespuesta msnRespuesta, ListaDeseo listaDeseos) {
	    boolean isOk = false;
		try {
			listaDeseoRep.save(listaDeseos);
			isOk = true;
		}
		catch (Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR + " "+ e.getMessage());
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
			isOk = false;
		}
		return isOk;
	}

	@Override
	public List<ListaDeseo> buscarListasDeseoPorIdUsuario(Long idUsuario) {
		return listaDeseoRep.findByUsuario(Usuario.builder().idUsuario(idUsuario).build());
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public MensajeRespuesta agregarLibroListaDeseos(LibroExistenteDto libro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ListaDeseo listaDeseoDb = buscarListaDeseo(libro.getIdListaDeseo());

		if(null != listaDeseoDb) {
			ItemListaLibro itemListaLibro = ItemListaLibro.builder()
					                                      .idListaDeseo(libro.getIdListaDeseo())
					                                      .posicionLibro(libro.getPosicionLibro())
					                                      .idLibro(libro.getIdLibro())
					                                      .build();
			msnRespuesta = itemListaLibroService.crearItemListaLibro(itemListaLibro);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE + 
					                                   " Lista de deseos idListaDeseo = "+libro.getIdListaDeseo());
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta agregarLibroListaDeseosCreaLibro(LibroNuevoDto libroNuevo) {
		MensajeRespuesta msnRespuesta = null;
		LibroExistenteDto libroExistente = new LibroExistenteDto(libroNuevo.getIdLibro(), 
																 libroNuevo.getIdListaDeseo(), 
																 libroNuevo.getPosicionLibro());

		Libro libro = libroService.buscarLibroPorIdApiGoogle(libroNuevo.getIdLibroApi());
		if(null == libro) {
			libro = libroNuevo.getLibro();
			msnRespuesta = libroService.crearLibro(libro);
			if(msnRespuesta.getEstado().equals(MensajeRespuesta.CREACION_OK)) {
				libroExistente.setIdLibro(libro.getIdLibro());
				msnRespuesta = this.agregarLibroListaDeseos(libroExistente);
			}
		}
		else {
			libroExistente.setIdLibro(libro.getIdLibro());
			msnRespuesta = this.agregarLibroListaDeseos(libroExistente);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta eliminarLibroListaDeseo(Long idListaDeseo, Long idLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ItemListaLibro itemListaLibroDb = itemListaLibroService.buscarItemListaLibro(idLibro, idListaDeseo);
		if(null != itemListaLibroDb) {
			msnRespuesta = itemListaLibroService.eliminarItemListaLibro(itemListaLibroDb.getIdItemListaLibro());
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE+" idListaDeseo = "+idListaDeseo);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}		
		return msnRespuesta;
	}

}
