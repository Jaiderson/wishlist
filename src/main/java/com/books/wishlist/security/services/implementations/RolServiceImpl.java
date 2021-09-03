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

@Service
@Transactional
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolRep rolRep;

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
	public Rol crearRol(Rol rol) {
		Rol nuevoRol = rolRep.findByNombre(rol.getNombre().name());
		if(null == nuevoRol) {
			return rolRep.save(rol);
		}
		return null;
	}

	@Override
	public Rol modificarRol(Rol rol) {
		Rol nuevoRol = rolRep.findByIdRol(rol.getIdRol());
		if(null != nuevoRol) {
			nuevoRol = rolRep.save(rol);
		}
		return nuevoRol;
	}

	@Override
	public Rol eliminarRol(Long idRol) {
		Rol rol = rolRep.findByIdRol(idRol);
		if(null != rol) {
			rolRep.delete(rol);
		}
		return rol;
	}

	@Override
	public List<Rol> consultarRoles() {
		return rolRep.findAll();
	}

}
