package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.books.wishlist.dto.LibroExistenteDto;
import com.books.wishlist.dto.LibroNuevoDto;
import com.books.wishlist.entities.ItemListaLibro;
import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.repositories.ILibroRep;
import com.books.wishlist.repositories.IListaDeseoRep;
import com.books.wishlist.repositories.ItemListaLibroRep;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.repositories.IRolRep;
import com.books.wishlist.security.repositories.IUsuarioRep;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.security.services.implementations.RolServiceImpl;
import com.books.wishlist.security.services.implementations.UsuarioServiceImpl;
import com.books.wishlist.services.implementatios.ItemListaLibroServiceImpl;
import com.books.wishlist.services.implementatios.LibroServiceImpl;
import com.books.wishlist.services.implementatios.ListaDeseoServiceImpl;
import com.books.wishlist.utils.MensajeRespuesta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ListaDeseoServiceTest {

	@Autowired
	private IRolRep rolRep;
	@Autowired
	private ILibroRep libroRep;
	@Autowired
	private IUsuarioRep usuarioRep;
	@Autowired
	private IListaDeseoRep listaDeseoRep;
	@Autowired
	private ItemListaLibroRep itemListaLibroRep;

	private IRolService rolService;
	private ILibroService libroService;
	private IUsuarioService usuarioService;
	private IListaDeseoService listaDeseoService;
	private ItemListaLibroService itemListaLibroService;

	private ListaDeseo listaDeseo;
	private Usuario usuario;
	private Rol rol;
	private int pos = 1;

	@BeforeEach
	public void init() {
	    rolService = new RolServiceImpl(rolRep);
	    libroService = new LibroServiceImpl(libroRep);
	    usuarioService = new UsuarioServiceImpl(usuarioRep);
        itemListaLibroService = new ItemListaLibroServiceImpl(itemListaLibroRep);
        listaDeseoService = new ListaDeseoServiceImpl(libroService, listaDeseoRep, itemListaLibroService);

        this.rol = ProveedorObjetos.getRol();
        this.usuario = ProveedorObjetos.getUsuario();
		this.listaDeseo = ProveedorObjetos.getListaDeseos(this.usuario);

		this.rolService.crearRol(this.rol);
		this.usuario.getRoles().add(this.rol);
		this.usuarioService.crearUsuario(this.usuario);
		this.pos = 1;
	}

	@Test
	void buscarListaDeseoTest() {
		MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
		assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
		assertThat(this.listaDeseo.getIdLista()).isPositive();

		this.listaDeseo = listaDeseoService.buscarListaDeseo(listaDeseo.getIdLista());
		assertThat(this.listaDeseo).isNotNull();
	}

	@Test
	void crearListaDeseoTest() {
        MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
        assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
        assertThat(this.listaDeseo.getIdLista()).isPositive();
    }

	@Test
	void modificarListaDeseoTest() {
        MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
        assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
        assertThat(this.listaDeseo.getIdLista()).isPositive();

		this.listaDeseo.setNomListaDeseos("Libros de PHP");
		listaDeseoService.modificarListaDeseo(listaDeseo);
		assertThat(this.listaDeseo).isNotNull();
		assertThat(this.listaDeseo.getNomListaDeseos()).isEqualTo("Libros de PHP");
	}

	@Test
	void eliminarListaDeseoTest() {
        MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
        assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
        assertThat(this.listaDeseo.getIdLista()).isPositive();

		listaDeseoService.eliminarListaDeseo(this.listaDeseo.getIdLista());
		this.listaDeseo = listaDeseoService.buscarListaDeseo(this.listaDeseo.getIdLista());
		assertThat(this.listaDeseo).isNull();
	}

	@Test
	void agregarLibroExistenteListaDeseoTest() {
        MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
        assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
        assertThat(this.listaDeseo.getIdLista()).isPositive();

        List<Libro> libros = ProveedorObjetos.getLibros();
        libros.stream().forEach(libro -> {
        	this.libroService.crearLibro(libro);
        	LibroExistenteDto libroExistente = new LibroExistenteDto(libro.getIdLibro(), listaDeseo.getIdLista(), pos++);
        	
        	listaDeseoService.agregarLibroListaDeseos(libroExistente);
        	ItemListaLibro item = itemListaLibroService.buscarItemListaLibro(libro.getIdLibro(), listaDeseo.getIdLista());
        	assertThat(item).isNotNull();
        });
	}
	
	@Test
	void agregarLibroListaDeseoCreaLibroTest() {
        MensajeRespuesta msn = listaDeseoService.crearListaDeseo(this.listaDeseo);
        assertThat(msn.generarEstadoHttp()).isEqualTo(HttpStatus.CREATED);
        assertThat(this.listaDeseo.getIdLista()).isPositive();

        List<Libro> libros = ProveedorObjetos.getLibros();
        libros.stream().forEach(libro -> {
        	LibroNuevoDto libroNuevo = LibroNuevoDto.builder()
        			                                .idLibro(libro.getIdLibro())
        			                                .idLibroApi(libro.getIdLibroApi())
        			                                .titulo(libro.getTitulo())
        			                                .subTitulo(libro.getSubTitulo())
        			                                .autores(libro.getAutores())
        			                                .editorial(libro.getEditorial())
        			                                .posicionLibro(pos++)
        			                                .idListaDeseo(this.listaDeseo.getIdLista())
        			                                .build();

        	listaDeseoService.agregarLibroListaDeseosCreaLibro(libroNuevo);
        	Libro libroBd = libroService.buscarLibroPorIdApiGoogle(libro.getIdLibroApi());
        	assertThat(libroBd).isNotNull();
        	ItemListaLibro item = itemListaLibroService.buscarItemListaLibro(libroBd.getIdLibro(), listaDeseo.getIdLista());
        	assertThat(item).isNotNull();
        });
	}

}
