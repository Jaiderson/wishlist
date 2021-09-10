package com.books.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.books.wishlist.entities.ERol;
import com.books.wishlist.entities.Rol;
import com.books.wishlist.repositories.IRolRep;
import com.books.wishlist.services.implementatios.RolServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class RolServiceTest {

	@Autowired
	private IRolRep rolRep;
	
	private IRolService rolService;

	@BeforeEach
	public void init() {
		rolService = new RolServiceImpl(rolRep);
	}

	@Test
	@Order(3)
	void buscarRolTest() {
		Rol rol = new Rol(ERol.OTHER_TEST, null);
		rolService.crearRol(rol);
		List<Rol> roles = rolService.consultarRoles();
		assertThat(roles).isNotNull().isNotEmpty();
	}

	@Test
	@Order(1)
	void crearRolTest() {
		Rol rol = new Rol(ERol.OTHER_TEST, null);
		rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
	}

	@Test
	@Order(2)
	void modificarRolTest() {
		Rol rol = new Rol(ERol.USER_TEST, null);
		rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
		
		rol.setDescripcion("Nueva descripcion del usuario");
		rolService.modificarRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getDescripcion()).isEqualTo("Nueva descripcion del usuario");
	}

	@Test
	@Order(4)
	void eliminarRolTest() {
		Rol rol = new Rol(ERol.ADMIN_TEST, null);
		rolService.crearRol(rol);
		assertThat(rol).isNotNull();
		assertThat(rol.getIdRol()).isPositive();
		
		rolService.eliminarRol(rol.getIdRol());
		rol = rolService.buscarRol(rol.getIdRol());
		assertThat(rol).isNull();
	}

}
