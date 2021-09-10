package com.books.wishlist.services.implementatios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.books.wishlist.entities.Usuario;
import com.books.wishlist.repositories.IUsuarioRep;
import com.books.wishlist.services.IUsuarioService;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

	public UsuarioServiceImpl(IUsuarioRep usuarioRep) {
		super();
		this.usuarioRep = usuarioRep;
	}
	
	@Autowired
	private IUsuarioRep usuarioRep;

	@Override
	public Optional<Usuario> buscarUsuarioPorNombre(String nombreUsuario) {
		return usuarioRep.findByNomUsuario(nombreUsuario);
	}
	
	@Override
	public boolean existeUsuarioNomUsuario(String nomUsuario) {
		return usuarioRep.existsByNomUsuario(nomUsuario);
	}

	@Override
	public boolean existeUsuarioEmail(String email) {
		return usuarioRep.existsByEmail(email);
	}

	@Override
	public Usuario buscarUsuarioPorId(Long idUsuario) {
		return usuarioRep.findByIdUsuario(idUsuario);
	}

	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		return usuarioRep.findByEmail(email);
	}

	@Override
	public MensajeRespuesta crearUsuario(Usuario usuario) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		boolean existe = usuarioRep.existsByNomUsuario(usuario.getNombre());
		if(!existe) {
		    if(this.guardarUsuario(msnRespuesta, usuario)) {
		        msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		    }
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.YA_EXISTE + usuario.getNombre());
			msnRespuesta.setEstado(MensajeRespuesta.YA_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta modificarUsuario(Usuario usuario) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		boolean existe = usuarioRep.existsByNomUsuario(usuario.getNombre());
		if(existe) {
		    if(this.guardarUsuario(msnRespuesta, usuario)) {
		        msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		    }
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE + usuario.getNombre());
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta modificarClaveUsuario(Long idUsuario, String nuevaClave) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Usuario actUsuario = usuarioRep.findByIdUsuario(idUsuario);
		if(null != actUsuario) {
			actUsuario.setClave(nuevaClave);
		    if(this.guardarUsuario(msnRespuesta, actUsuario)) {
		        msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		    }
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE +"idUsuario = "+idUsuario);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public MensajeRespuesta eliminarUsuario(Long idUsuario) {
		MensajeRespuesta msnRespuesta = new MensajeRespuesta();
		Usuario eliUsuario = usuarioRep.findByIdUsuario(idUsuario);
		if(null != eliUsuario) {
			usuarioRep.delete(eliUsuario);
		    msnRespuesta.setEstado(MensajeRespuesta.CREACION_OK);
		}
		else {
			msnRespuesta.getListaInconsistencias().add(MensajeRespuesta.NO_EXISTE +"idUsuario = "+idUsuario);
			msnRespuesta.setEstado(MensajeRespuesta.NO_EXISTE);
		}
		return msnRespuesta;
	}

	@Override
	public List<Usuario> consultarUsuarios(String nombre) {
		return usuarioRep.consultarUsuarios(nombre);
	}

	/**
	 * Metodo usado para guardar la informacion de un nuevo usuario, en caso de encontrar
	 * inconsistencias se guardaran en el objeto de entrada <b>msnRespuesta</b>.
	 * 
	 * @param msnRespuesta Objeto en el cual se guardara las inconsistencias en caso de encontrar alguna.
	 * @param usuario Usuario a guardar.
	 */
	private boolean guardarUsuario(MensajeRespuesta msnRespuesta, Usuario usuario) {
	    boolean isOk = false;
		try {
			usuarioRep.save(usuario);
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
