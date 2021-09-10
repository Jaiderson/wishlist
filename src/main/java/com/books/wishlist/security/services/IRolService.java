package com.books.wishlist.security.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public interface IRolService {

	/**
	 * Busca un rol de usuario dado su nombre <b>nombreRol</b>.
	 * 
	 * @param nombreRol Identificador unico del rol a buscar. 
	 * @return Optional<Rol> asociado al <b>nombreRol</b>.
	 */
	public Optional<Rol> buscarRol(ERol rol);

	/**
	 * Dada una lista de roles <b>roles</b> realiza una busqueda de cada uno de 
	 * ellos y retorna una lista con los roles encontrados.
	 * 
	 * @param roles Listado de roles a buscar. 
	 * @return Lista de roles o lista vacia en caso de no encontrar ninguno.
	 */
	public List<Rol> buscarRoles(Set<String> roles);

	/**
	 * Busca un rol dado su nombre.
	 * 
	 * @param nombreRol Nombre del rol a buscar.
	 * @return Rol encontrado o null sin no existe.
	 */
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
	public MensajeRespuesta crearRol(Rol rol);

	/***
	 * Modifica el registro de un rol de usuario existente.
	 * 
	 * @param Rol a modificar. 
	 * @return Rol actualizado correctamente o <b>null</b> si el rol no existe.
	 */
	public MensajeRespuesta modificarRol(Rol rol);

	/***
	 * Elimina el registro de un rol de usuario.
	 * 
	 * @param idRol Identificador unico del rol a eliminar. 
	 * @return Rol eliminado o <b>null</b> si el rol no existe.
	 */
	public MensajeRespuesta eliminarRol(Long idRol);

	/***
	 * Consulta todos los roles registrados.
	 * 
	 * @return Listado de roles existentes o una lista vacia si no existen roles registrados.
	 */
	public List<Rol> consultarRoles();

}
