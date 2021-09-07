package com.books.wishlist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.books.wishlist.entities.Libro;

@Repository
public interface ILibroRep  extends JpaRepository<Libro, Long> {

	@Query(value="Select B.* From books B inner join list_book LB on (B.bookid = LB.bookid) "
			   + "Where LB.userid = :idUsuario and LB.listname = :nomLista and LB.book_item = :posicion", nativeQuery = true)
	public List<Libro> buscarLibros(@Param("idUsuario") Long idUsuario, 
			                        @Param("posicion") Integer posicion, 
			                        @Param("nomLista") String nomLista);

	@Query(value="Select B.* From books B inner join list_book LB on (B.bookid = LB.bookid) "
			   + "Where LB.userid = :idUsuario and LB.listname = :nomLista and "
			   + "LB.book_item = :posicion and B.bookid_api = :idLibroApi", nativeQuery = true)
	public Libro buscarLibro(@Param("idUsuario") Long idUsuario, 
                             @Param("posicion") Integer posicion, 
                             @Param("nomLista") String nomLista,
                             @Param("idLibroApi") String idLibroApi);

	public Libro findByIdLibroApi(String idLibroApi);

}
