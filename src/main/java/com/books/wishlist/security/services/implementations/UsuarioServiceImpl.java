package com.books.wishlist.security.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.repositories.IUsuarioRep;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.utils.CodificarClave;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRep usuarioRep;

	@Override
	public Optional<Usuario> buscarUsuarioPorNombre(String nombreUsuario) {
		return usuarioRep.findByNomUsuario(nombreUsuario);
	}
	
	public boolean existeUsuarioNomUsuario(String nomUsuario) {
		return usuarioRep.existsByNomUsuario(nomUsuario);
	}

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
	public Usuario crearUsuario(Usuario usuario) {
		return usuarioRep.save(usuario);
	}

	@Override
	public Usuario modificarUsuario(Usuario usuario) {
		Optional<Usuario> actUsuario = usuarioRep.findByNomUsuario(usuario.getNombre());
		if(!actUsuario.isPresent()) {
			return null;
		}
		return usuarioRep.save(usuario);
	}

	@Override
	public Usuario modificarClaveUsuario(Long idUsuario, String nuevaClave) {
		Usuario actUsuario = usuarioRep.findByIdUsuario(idUsuario);
		if(null == actUsuario) {
			return actUsuario;
		}
		actUsuario.setClave(CodificarClave.codificarClave(nuevaClave));
		return usuarioRep.save(actUsuario);	
	}

	@Override
	public Usuario eliminarUsuario(Long idUsuario) {
		Usuario eliUsuario = buscarUsuarioPorId(idUsuario);
		if(null == eliUsuario) {
			return eliUsuario;
		}
		usuarioRep.delete(eliUsuario);
		return eliUsuario;
	}

	@Override
	public List<Usuario> consultarUsuarios(String nombre) {
		return usuarioRep.consultarUsuarios(nombre);
	}

}
