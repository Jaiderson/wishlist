package com.books.wishlist.services.implementatios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.wishlist.entities.ItemListaLibro;
import com.books.wishlist.repositories.ItemListaLibroRep;
import com.books.wishlist.services.ItemListaLibroService;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
@Transactional
public class ItemListaLibroServiceImpl implements ItemListaLibroService {

	@Autowired
	private ItemListaLibroRep itemListaLibroRep;

	public ItemListaLibroServiceImpl(ItemListaLibroRep itemListaLibroRep) {
		super();
		this.itemListaLibroRep = itemListaLibroRep;
	}

	@Override
	public ItemListaLibro buscarItemListaLibro(ItemListaLibro listaLibro) {
		return itemListaLibroRep.buscarItemListaLibro(listaLibro.getPosicionLibro(),
				                                      listaLibro.getIdLibro(),
				                                      listaLibro.getIdListaDeseo());
	}

	@Override
	public MensajeRespuesta crearItemListaLibro(ItemListaLibro itemListaLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ItemListaLibro itemBd = buscarItemListaLibro(itemListaLibro);
		if(null == itemBd) {
			if(guardarItemListaLiro(msnRespuesta, itemListaLibro)) {
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
	public MensajeRespuesta modificarItemListaLibro(ItemListaLibro itemListaLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ItemListaLibro itemBd = buscarItemListaLibro(itemListaLibro);
		if(null != itemBd) {
			itemListaLibro.setIdItemListaLibro(itemBd.getIdItemListaLibro());
			if(guardarItemListaLiro(msnRespuesta, itemListaLibro)) {
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
	public MensajeRespuesta eliminarItemListaLibro(Long idItemListaLibro) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		ItemListaLibro itemBd = itemListaLibroRep.findByIdItemListaLibro(idItemListaLibro);
		if(null != itemBd) {
			itemListaLibroRep.delete(itemBd);
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
	private boolean guardarItemListaLiro(MensajeRespuesta msnRespuesta, ItemListaLibro itemListaLibro) {
	    boolean isOk = false;
		try {
			itemListaLibroRep.save(itemListaLibro);
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
	public MensajeRespuesta eliminarItemsListaDeseos(Long idListaDeseo) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		try {
			itemListaLibroRep.eliminarItemsListaDeseo(idListaDeseo);
			msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
		}
		catch(Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR+e.getMessage());
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
		}
		return msnRespuesta;
	}

	@Override
	public ItemListaLibro buscarItemListaLibro(Long idLibro, Long idListaDeseo) {
		return itemListaLibroRep.buscarItemListaLibro(idLibro, idListaDeseo);
	}

}
