package com.books.wishlist.security.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;

@Service
public interface IRolService {

	/***
	 * Busca un rol de usuario dado su nombre unico <b>nombreRol</b>.
	 * 
	 * @param nombreRol Identificador unico del rol. 
	 * @return Rol asociado al <b>nombreRol</b> o null si no se encuentra.
	 */
	public Optional<Rol> buscarRol(ERol rol);

	public List<Rol> buscarRoles(Set<String> roles);
	
	public Rol buscarRol(String nombreRol);
	
	/***
	 * Busca un rol de usuario dado su identificador unico <b>idRol</b>.
	 * 
	 * @param idRol Identificador unico del rol. 
	 * @return Rol asociado al <b>idRol</b> o null si no se encuentra.
	 */
	public Rol buscarRol(Long idRol);

	/***
	 * Crea un nuevo rol de usuario.
	 * 
	 * @param Rol Nuevo rol a registrar. 
	 * @return Rol creado o existente o <b>null</b> si el rol ya existe.
	 */
	public Rol crearRol(Rol rol);

	/***
	 * Modifica el registro de un rol de usuario existente.
	 * 
	 * @param Rol a modificar. 
	 * @return Rol actualizado correctamente o <b>null</b> si el rol no existe.
	 */
	public Rol modificarRol(Rol rol);

	/***
	 * Elimina el registro de un rol de usuario.
	 * 
	 * @param idRol Identificador unico del rol a eliminar. 
	 * @return Rol eliminado o <b>null</b> si el rol no existe.
	 */
	public Rol eliminarRol(Long idRol);

	/***
	 * Consulta todos los roles registrados.
	 * 
	 * @return Listado de roles existentes o una lista vacia si no existen roles registrados.
	 */
	public List<Rol> consultarRoles();

}
