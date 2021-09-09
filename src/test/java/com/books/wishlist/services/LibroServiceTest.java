package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.repositories.ILibroRep;
import com.books.wishlist.services.implementatios.LibroServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LibroServiceTest {

	@Autowired
	private ILibroRep libroRep;

	private ILibroService libroService;

	private Libro libro;

	@BeforeEach
	public void init() {
		libroService = new LibroServiceImpl(libroRep);
		this.libro = ProveedorObjetos.getLibroUno();
	}

	@Test
	void buscarLibroTest() {
		libroService.crearLibro(this.libro);
		assertThat(this.libro).isNotNull();
		assertThat(this.libro.getIdLibro()).isPositive();
		
		this.libro = libroService.buscarLibroPorIdApiGoogle(this.libro.getIdLibroApi());
		assertThat(this.libro).isNotNull();
	}

	@Test
	void crearLibroTest() {
		libroService.crearLibro(this.libro);
		assertThat(this.libro).isNotNull();
		assertThat(this.libro.getIdLibro()).isPositive();
	}

	@Test
	void modificarLibroTest() {
		libroService.crearLibro(this.libro);
		assertThat(this.libro).isNotNull();
		assertThat(this.libro.getIdLibro()).isPositive();
		
		this.libro.setTitulo("EL AMOR EN LOS TIEMPOS DEL COLERA");
		libroService.modificarLibro(this.libro);
		assertThat(this.libro).isNotNull();
		assertThat(this.libro.getTitulo()).isEqualTo("EL AMOR EN LOS TIEMPOS DEL COLERA");
	}

	@Test
	void eliminarLibroTest() {
		libroService.crearLibro(this.libro);
		assertThat(this.libro).isNotNull();
		assertThat(this.libro.getIdLibro()).isPositive();

		libroService.eliminarLibro(this.libro.getIdLibro());
		this.libro = libroService.buscarLibroPorIdApiGoogle(this.libro.getIdLibroApi());
		assertThat(this.libro).isNull();
	}

}
