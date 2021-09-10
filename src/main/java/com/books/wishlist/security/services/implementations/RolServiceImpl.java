package com.books.wishlist.security.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.repositories.IRolRep;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
@Transactional
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolRep rolRep;

	public RolServiceImpl(IRolRep rolRep) {
		super();
		this.rolRep = rolRep;
	}

	@Override
	public List<Rol> consultarRoles() {
		return rolRep.findAll();
	}

	@Override
	public Optional<Rol> buscarRol(ERol rol) {
		return rolRep.findByNombre(rol);
	}

	@Override
	public Rol buscarRol(String rol) {
		return rolRep.findByNombre(rol);
	}

	@Override
	public List<Rol> buscarRoles(Set<String> roles) {
		return rolRep.buscarRoles(roles);
	}

	@Override
	public Rol buscarRol(Long idRol) {
		return rolRep.findByIdRol(idRol);
	}


	@Override
	public MensajeRespuesta crearRol(Rol rol) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Rol nuevoRol = rolRep.findByNombre(rol.getNombre().name());
		if(null == nuevoRol) {
		    if(this.guardarRol(msnRespuesta, rol)) {
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
	public MensajeRespuesta modificarRol(Rol rol) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Rol nuevoRol = rolRep.findByNombre(rol.getNombre().name());
		if(null != nuevoRol) {
		    if(this.guardarRol(msnRespuesta, rol)) {
		        msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		    }
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta eliminarRol(Long idRol) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Rol rol = rolRep.findByIdRol(idRol);

		if(null != rol) {
			rolRep.delete(rol);
			msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	/**
	 * Metodo usado para guardar la informacion de un nuevo rol, en caso de encontrar
	 * inconsistencias se guardaran en el objeto de entrada <b>msnRespuesta</b>.
	 * 
	 * @param msnRespuesta Objeto en el cual se guardara las inconsistencias en caso de encontrar alguna.
	 * @param rol Rol a guardar.
	 */
	private boolean guardarRol(MensajeRespuesta msnRespuesta, Rol rol) {
	    boolean isOk = false;
		try {
			rolRep.save(rol);
			isOk = true;
		}
		catch (Exception e) {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.SQL_ERROR + " "+ e.getMessage());
			msnRespuesta.setEstado(MensajeRespuesta.SQL_ERROR);
			isOk = false;
		}
		return isOk;
	}

}
