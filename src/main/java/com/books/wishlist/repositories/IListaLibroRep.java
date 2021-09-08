package com.books.wishlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.books.wishlist.entities.ListaLibro;

public interface IListaLibroRep extends JpaRepository<ListaLibro, Long> {

	@Query(value="select book_listid from book_list where listid = :idLista and " +
	             "bookid = :idLibro and book_item = :position ", nativeQuery = true)
	public Long buscarListaLibro(@Param("position") Integer position,
			                     @Param("idLibro") Long idLibro,
			                     @Param("idLista") Long idLista);

}
