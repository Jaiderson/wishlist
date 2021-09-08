package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.repositories.IListaDeseoRep;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.services.implementatios.ListaDeseoServiceImpl;
import com.books.wishlist.utils.MensajeRespuesta;
import com.google.common.collect.Sets;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ListaDeseoServiceTest {

	@Autowired
	private IListaDeseoRep listaDeseoRep;
	
	private IListaDeseoService listaDeseoService;
	
	private ListaDeseo listaDeseo;

	@BeforeEach
	public void init() {
		listaDeseoService = new ListaDeseoServiceImpl(listaDeseoRep);

		Usuario usuario = Usuario.builder().idUsuario(1L)
				                           .nomUsuario("USER-TEST")
				                           .build();
		Libro libro1 = Libro.builder().idLibroApi("zug36@j0JWIAb*123")
                .autores("Gabriel Garcia Marquez")
                .editorial("LITERATURA RANDOM HOUSE")
                .titulo("EL CORONEL NO TIENE QUIEN LE ESCRIBA")
                .subTitulo("estudio literario")
                .build();
		Libro libro2 = Libro.builder().idLibroApi("zug36@j0JWIAb*321")
                .autores("Pablo Monteria Garcia")
                .editorial("EDITORIAL NORMA")
                .titulo("LA CULA ES DE LA VACA")
                .subTitulo("Aventuras del campo colombiano")
                .build();

		Set<Libro> libros = Sets.newHashSet();
		libros.add(libro1);
		libros.add(libro2);

		this.listaDeseo = ListaDeseo.builder().usuario(usuario)
				                              .posicionLista(1)
				                              .nomListaDeseos("Lista libros Java")
				                              .build();
	}

	@Test
	void buscarListaDeseoTest() {
		MensajeRespuesta msn = listaDeseoService.crearListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getIdLista()).isPositive();

		this.listaDeseo = listaDeseoService.buscarListaDeseo(listaDeseo.getIdLista());
		assertThat(this.listaDeseo).isNotNull();
	}

	@Test
	void crearListaDeseoTest() {
		//this.listaDeseo = listaDeseoService.crearListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getIdLista()).isPositive();
	}

	@Test
	void modificarListaDeseoTest() {
		//this.listaDeseo = listaDeseoService.crearListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getIdLista()).isPositive();
		
		this.listaDeseo.setNomListaDeseos("Libros de PHP");
		//this.listaDeseo = listaDeseoService.modificarListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getNomListaDeseos()).isEqualTo("Libros de PHP");
	}

	@Test
	void eliminarListaDeseoTest() {
		//this.listaDeseo = listaDeseoService.crearListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getIdLista()).isPositive();
		
		//this.listaDeseo = listaDeseoService.eliminarListaDeseo(this.listaDeseo.getIdLista());
		this.listaDeseo = listaDeseoService.buscarListaDeseo(this.listaDeseo.getIdLista());
		assertThat(this.listaDeseo).isNull();
	}

}
