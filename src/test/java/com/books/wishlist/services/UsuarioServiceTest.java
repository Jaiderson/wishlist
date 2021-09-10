package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.books.wishlist.security.entities.Usuario;
import com.books.wishlist.security.repositories.IUsuarioRep;
import com.books.wishlist.security.services.IUsuarioService;
import com.books.wishlist.security.services.implementations.UsuarioServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UsuarioServiceTest {

	@Autowired
	private IUsuarioRep usuarioRep;

	private IUsuarioService usuarioService;
	private Usuario usuario;

	@BeforeEach
	public void init() {
		usuarioService = new UsuarioServiceImpl(usuarioRep);
		this.usuario = ProveedorObjetos.getUsuario();
	}

	@Test
	void buscarUsuarioTest() {
		usuarioService.crearUsuario(this.usuario);
		List<Usuario> usuarios = usuarioService.consultarUsuarios("USER_TEST");
		assertThat(usuarios).isNotNull().isNotEmpty();
	}

	@Test
	void crearUsuarioTest() {
		usuarioService.crearUsuario(this.usuario);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getIdUsuario()).isPositive();
	}

	@Test
	void modificarUsuarioTest() {
		usuarioService.crearUsuario(this.usuario);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getIdUsuario()).isPositive();
		
		this.usuario.setEmail("new_mail_test@gmail.com");
		this.usuario.setNombre("NUEVO USUARIO PRUEBAS");
		usuarioService.modificarUsuario(this.usuario);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getEmail()).isEqualTo("new_mail_test@gmail.com");
		assertThat(this.usuario.getNombre()).isEqualTo("NUEVO USUARIO PRUEBAS");
	}

	@Test
	void actualizarClaveUsuarioTest() {
		usuarioService.crearUsuario(this.usuario);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getIdUsuario()).isPositive();
		
		String nuevaClave = "M@L561HysT58*9!78";
		usuarioService.modificarClaveUsuario(this.usuario.getIdUsuario(), nuevaClave);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getClave()).isEqualTo(nuevaClave);
	}

	@Test
	void eliminarUsuarioTest() {
		usuarioService.crearUsuario(this.usuario);
		assertThat(this.usuario).isNotNull();
		assertThat(this.usuario.getIdUsuario()).isPositive();

		usuarioService.eliminarUsuario(this.usuario.getIdUsuario());
		this.usuario = usuarioService.buscarUsuarioPorId(this.usuario.getIdUsuario());
		assertThat(this.usuario).isNull();
	}

}
