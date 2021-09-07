package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.books.wishlist.security.entities.ERol;
import com.books.wishlist.security.entities.Rol;
import com.books.wishlist.security.repositories.IRolRep;
import com.books.wishlist.security.services.IRolService;
import com.books.wishlist.security.services.implementations.RolServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RolServiceTest {

	@Autowired
	private IRolRep rolRep;
	
	private IRolService rolService;

	@BeforeEach
	public void init() {
		rolService = new RolServiceImpl(rolRep);
	}

	@Test
	void buscarRolTest() {
		List<Rol> roles = rolService.consultarRoles();
		assertThat(roles).isNotNull().isNotEmpty();
	}

	@Test
	void crearRolTest() {
		Rol rol = new Rol(ERol.OTHER_TEST, null);
		rol = rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
	}

	@Test
	void modificarRolTest() {
		Rol rol = new Rol(ERol.USER_TEST, null);
		rol = rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
		
		rol.setDescripcion("Nueva descripcion del usuario");
		rol = rolService.modificarRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getDescripcion()).isEqualTo("Nueva descripcion del usuario");
	}

	@Test
	void eliminarRolTest() {
		Rol rol = new Rol(ERol.ADMIN_TEST, null);
		rol = rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
		
		rolService.eliminarRol(rol.getIdRol());
		rol = rolService.buscarRol(rol.getIdRol());
		assertThat(rol).isNull();
	}

}
