package com.books.wishlist.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.utils.MensajeRespuesta;

@Service
public interface IUsuarioService {

	/***
	 * Busca un usuario dado su nombre unico <b>nombreUsuario</b>.
	 * 
	 * @param nombreUsuario Identificador unico del usuario. 
	 * @return Usuario asociado al <b>nombreUsuario</b> o null si no se encuentra.
	 */
	public Optional<Usuario> buscarUsuarioPorNombre(String nombreUsuario);

	/**
	 * Busca si el nombre de un usuario <b>nomUsuario</b> ya existe.
	 * 
	 * @param nomUsuario Nombre de usuario a validar si existe.
	 * @return True si el usuario con nombre <b>nomUsuario</b> ya existe de lo contrario false.
	 */
	public boolean existeUsuarioNomUsuario(String nomUsuario);

	/**
	 * Busca si el mail ingresado <b>email</b> ya esta asociado a un usuario existente.
	 * 
	 * @param email Email a validar si existe.
	 * @return True si el <b>email</b> ya existe de lo contrario false.
	 */
	public boolean existeUsuarioEmail(String email);

	/***
	 * Busca un usuario de usuario dado su identificador unico <b>idUsuario</b>.
	 * 
	 * @param idUsuario Identificador unico del usuario. 
	 * @return Usuario asociado al <b>idUsuario</b> o null si no se encuentra.
	 */
	public Usuario buscarUsuarioPorId(Long idUsuario);

	/***
	 * Busca un usuario dado su email unico <b>email</b>.
	 * 
	 * @param email Email asosiado al usuario. 
	 * @return Usuario asociado al <b>email</b> o null si no se encuentra.
	 */
	public Usuario buscarUsuarioPorEmail(String email);

	/***
	 * Consulta todos los usuarios registrados cuyo nombre contenga el <b>nombre</n>.
	 * 
	 * @param nombre Subcadena a buscar en el nombre de usuarios.
	 * @return Listado de usuario existentes o una lista vacia si no se encontro nada.
	 */
	public List<Usuario> consultarUsuarios(String nombre);

	/***
	 * Crea un nuevo usuario de usuario.
	 * 
	 * @param Usuario Nuevo usuario a registrar. 
	 * @return Usuario creado o existente o <b>null</b> si el usuario ya existe.
	 */
	public MensajeRespuesta crearUsuario(Usuario usuario);

	/***
	 * Modifica el registro de un usuario de usuario existente.
	 * 
	 * @param Usuario a modificar. 
	 * @return Usuario actualizado correctamente o <b>null</b> si el usuario no existe.
	 */
	public MensajeRespuesta modificarUsuario(Usuario usuario);

	/***
	 * Modifica la contraseña de un usuario por una nueva <b>nuevaClave</b>.
	 * 
	 * @param idUsuario Identificador unico del usuario. 
	 * @param nuevaClave Clave nueva.
	 * @return Usuario actualizado correctamente o <b>null</b> si el usuario no existe.
	 */
	public MensajeRespuesta modificarClaveUsuario(Long idUsuario, String nuevaClave);

	/***
	 * Elimina el registro de la cuenta de un usuario.
	 * 
	 * @param idUsuario Identificador unico del usuario a eliminar. 
	 * @return Usuario eliminado o <b>null</b> si el usuario no existe.
	 */
	public MensajeRespuesta eliminarUsuario(Long idUsuario);

}
