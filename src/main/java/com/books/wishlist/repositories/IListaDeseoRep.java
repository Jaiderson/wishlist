package com.books.wishlist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.security.entities.Usuario;

@Repository
public interface IListaDeseoRep extends JpaRepository<ListaDeseo, Long> {

	/**
	 * Busca una lista de deseos por si clave unica, es decir busca por posicion de la lista,
	 * nombre de la lista y id de su usuario por lo que estos atributos son obligatorios.
	 * 
	 * @param position Posicion de la lista de deseos.
	 * @param listName Nombre de la lista de deseos.
	 * @param idUsuario Id unico del usuario dueño de la lista de deseos.
	 * @return Lista de deseos o <b>null</b> si no la encuentra.
	 */
	@Query(value="select w.* from wish_list w where w.list_item = :position and "
			   + "w.listname = :listName and w.userid = :idUsuario ", nativeQuery = true)
	public ListaDeseo buscarListaLibro(@Param("position") Integer position,
			                           @Param("listName") String listName,
			                           @Param("idUsuario") Long idUsuario);

	/**
	 * Busca una lista de deseos dado su identificador unico <b>idLista</b>
	 * 
	 * @param idLista Identificador unico de la lista de deseos.
	 * @return Lista de deseos o <b>null</b> si no la encuentra.
	 */
	public ListaDeseo findByIdLista(Long idLista);

	/**
	 * Busca una lista de deseos dado su usuario <b>usuario</b>.
	 * El usuario debe contener su idUsuario.
	 * 
	 * @param usuario Identificador unico de la lista de deseos.
	 * @return Todas las listas de deseos del usuario o una lista vacia si no tiene registradas.
	 */
	public List<ListaDeseo> findByUsuario(Usuario usuario);

}
