package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.repositories.IListaDeseoRep;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.repositories.IRolRep;
import com.books.wishlist.security.repositories.IUsuarioRep;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.security.services.implementations.RolServiceImpl;
import com.books.wishlist.security.services.implementations.UsuarioServiceImpl;
import com.books.wishlist.services.implementatios.ListaDeseoServiceImpl;
import com.books.wishlist.utils.MensajeRespuesta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ListaDeseoServiceTest {

	@Autowired
	private IListaDeseoRep listaDeseoRep;

	private IListaDeseoService listaDeseoService;

	private ListaDeseo listaDeseo;
	private Usuario usuario;
	private Rol rol;

	@Autowired
	private IRolRep rolRep;

	private IRolService rolService;

    @Autowired
    private IUsuarioRep usuarioRep;
    
    private IUsuarioService usuarioService;

	@BeforeEach
	public void init() {
	    rolService = new RolServiceImpl(rolRep);
	    usuarioService = new UsuarioServiceImpl(usuarioRep);
        listaDeseoService = new ListaDeseoServiceImpl(listaDeseoRep);


        this.rol = ProveedorObjetos.getRol();
        this.usuario = ProveedorObjetos.getUsuario();
		this.listaDeseo = ListaDeseo.builder().usuario(usuario)
				                              .posicionLista(1)
				                              .nomListaDeseos("Lista libros literatura colombiana")
				                              .build();

		this.rol = this.rolService.crearRol(this.rol);
		this.usuario.getRoles().add(this.rol);
		this.usuario = this.usuarioService.crearUsuario(this.usuario);
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

}
