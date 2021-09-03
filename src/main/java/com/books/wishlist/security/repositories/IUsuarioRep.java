package com.books.wishlist.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.books.wishlist.security.entities.Usuario;

@Repository
public interface IUsuarioRep  extends JpaRepository<Usuario, Long> {

	/**
	 * Busca un usuario dado su <b>idUsuario</b> Identificador unico.
	 * 
	 * @param idUsuario Identificador unico del usuario a buscar.
	 * @return Usuario asociado al <b>idUsuario</b> o <b>null</b> en caso que no exista.
	 */
	public Usuario findByIdUsuario(Long idUsuario);


	
	
	
	/**
	 * Busca un usuario dado nombre de usuario <b>nomUsuario</b>.
	 * 
	 * @param nomUsuario Nombre del usuario a buscar.
	 * @return Usuario asociado al <b>nomUsuario</b>.
	 */
	public Optional<Usuario> findByNomUsuario(String nomUsuario);

	public boolean existsByNomUsuario(String nomUsuario);
	
	public boolean existsByEmail(String email);


	
	
	
	/**
	 * Busca un usuario dado el email de usuario <b>email</b>.
	 * 
	 * @param email Nombre del usuario a buscar.
	 * @return Usuario asociado al <b>email</b> o <b>null</b> en caso de no existir.
	 */
	public Usuario findByEmail(String email);

	/***
	 * Consulta todos los usuarios registrados cuyo nombre contenga el <b>nombre</b>.
	 * 
	 * @param nombre Subcadena a buscar en el nombre de usuarios.
	 * @return Listado de usuario existentes o una lista vacia si no se encontro nada.
	 */
	@Query(value="select * from users where username like %:nombre% ", nativeQuery = true)
	public List<Usuario> consultarUsuarios(@Param("nombre") String nombre);

}
