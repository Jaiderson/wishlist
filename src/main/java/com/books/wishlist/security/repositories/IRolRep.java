package com.books.wishlist.security.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;

@Repository
public interface IRolRep extends JpaRepository<Rol, Long> {

	/**
	 * Busca un Rol dado su enumerado <b>enumRol</b>.
	 * 
	 * @param enumRol Enumerado del rol.
	 * @return Rol asociado al <b>enumRol</b> o <b>null</b> en caso de no existir.
	 */
	public Optional<Rol> findByNombre(ERol enumRol);

	@Query(value="select * from rols where rolname IN :roles ", nativeQuery = true)
	public List<Rol> buscarRoles(@Param("roles") Set<String> roles);

	/**
	 * Busca un Rol dado su identificador unico <b>idRol</b>.
	 * 
	 * @param idRol Identificador unico del rol.
	 * @return Rol asociado al <b>idRol</b> o <b>null</b> en caso de no existir.
	 */
	public Rol findByIdRol(Long idRol);

	/**
	 * Busca un Rol dado su enumerado <b>nombreRol</b>.
	 * 
	 * @param nombreRol Enumerado del rol.
	 * @return Rol asociado al <b>nombreRol</b> o <b>null</b> en caso de no existir.
	 */
	@Query(value="select * from rols where rolname = :nombreRol ", nativeQuery = true)
	public Rol findByNombre(@Param("nombreRol") String nombreRol);

}
