package com.books.wishlist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.entities.ListaLibroPk;

@Repository
public interface IListaDeseoRep extends JpaRepository<ListaDeseo, Long> {

	@Query(value="select w.* from wish_list w where w.book_item = :position and "
			   + "w.listname = :listName and w.userid = :idUsuario ", nativeQuery = true)
	public ListaDeseo buscarListaLibroPk(@Param("position") Integer position,
			                             @Param("listName") String listName,
			                             @Param("idUsuario") Long idUsuario);

	@Query(value="SELECT nextval('sec_listadeseos') ", nativeQuery = true)
	public Long secuenciaListaLibro();

	public List<ListaDeseo> findByListaPk(ListaLibroPk listaPk);

	public ListaDeseo findByIdLista(Long idLista);

}
