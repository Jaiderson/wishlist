package com.books.wishlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.books.wishlist.entities.ItemListaLibro;

public interface ItemListaLibroRep extends JpaRepository<ItemListaLibro, Long> {

	@Query(value="Select * From book_list Where listid = :idLista and " +
	             "bookid = :idLibro and book_item = :position ", nativeQuery = true)
	public ItemListaLibro buscarItemListaLibro(@Param("position") Integer position,
			                         @Param("idLibro") Long idLibro,
			                         @Param("idLista") Long idLista);

	public ItemListaLibro findByIdItemListaLibro(Long idItemListaLibro);

}
