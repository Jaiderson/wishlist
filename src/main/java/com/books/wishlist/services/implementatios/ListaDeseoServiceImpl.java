package com.books.wishlist.services.implementatios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ListaDeseoServiceImpl implements IListaDeseoService {

	public ListaDeseoServiceImpl(IListaDeseoRep listaDeseoRep) {
		super();
		this.listaDeseoRep = listaDeseoRep;
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
			this.guardarListaDeseos(msnRespuesta, nuevaLista);
			msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
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
			this.guardarListaDeseos(msnRespuesta, listaDeseos);
			msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
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
			listaDeseoRep.delete(listaDeseoDb);
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
	 * @param listaDeseos Lista de deseos a guardar.
	 */
	private void guardarListaDeseos(MensajeRespuesta msnRespuesta, ListaDeseo listaDeseos) {
		try {
			listaDeseoRep.save(listaDeseos);
		}
		catch (Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR + " "+ e.getMessage());
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
		}
	}

	@Override
	public List<ListaDeseo> buscarListasDeseoPorIdUsuario(Long idUsuario) {
		return listaDeseoRep.findByUsuario(Usuario.builder().idUsuario(idUsuario).build());
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public MensajeRespuesta agregarLibro(Long idListaDeseo, Libro libro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ListaDeseo listaDeseoDb = buscarListaDeseo(idListaDeseo);

		if(null != listaDeseoDb) {
			ItemListaLibro itemListaLibro = ItemListaLibro.builder()
					                                      .idListaDeseo(idListaDeseo)
					                                      .posicionLibro(libro.getPosicion())
					                                      .idLibro(libro.getIdLibro())
					                                      .build();
			msnRespuesta = itemListaLibroService.crearItemListaLibro(itemListaLibro);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE + " Lista de deseos idListaDeseo = "+idListaDeseo);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	
	
}
