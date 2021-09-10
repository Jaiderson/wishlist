package com.books.wishlist.services;

import java.util.List;

import com.books.wishlist.entities.ERol;
import com.books.wishlist.entities.Libro;
import com.books.wishlist.entities.ListaDeseo;
import com.books.wishlist.entities.Rol;
import com.books.wishlist.entities.Usuario;
import com.books.wishlist.utils.MensajeRespuesta;
import com.google.common.collect.Lists;

public class ProveedorObjetos {

    public static Rol getRolUno() {
        return new Rol(ERol.USER_TEST, "Rol de usuario de pruebas unitarias.");
    }

    public static Rol getRolDos() {
        return new Rol(ERol.ADMIN_TEST, "Rol de usuario administrador pruebas unitarias.");
    }

    public static Usuario getUsuario() {
        return new Usuario("USER_TEST", "Med@ll*321+-*02", "test@hotmail.com.co", "USUARIO DE PRUEBAS UNITARIAS");
    }

    public static Libro getLibroUno() {
        return Libro.builder().idLibroApi("zug36@j0JWIAb*123")
                              .autores("Gabriel Garcia Marquez")
                              .editorial("LITERATURA RANDOM HOUSE")
                              .titulo("EL CORONEL NO TIENE QUIEN LE ESCRIBA")
                              .subTitulo("estudio literario")
                              .build();
    }

    public static Libro getLibroDos() {
        return Libro.builder().idLibroApi("zug36@j0JWIAb*321")
                              .autores("Pablo Monteria Garcia")
                              .editorial("EDITORIAL NORMA")
                              .titulo("LA CULPA ES DE LA VACA")
                              .subTitulo("Aventuras del campo colombiano")
                              .build();
    }

    public static List<Libro> getLibros(){
        List<Libro> libros = Lists.newArrayList();
        libros.add(getLibroUno());
        libros.add(getLibroDos());
        return libros;
    }

    public static List<Rol> getRoles(){
        List<Rol> roles = Lists.newArrayList();
        roles.add(getRolUno());
        roles.add(getRolDos());
        return roles;
    }

    public static ListaDeseo getListaDeseos(Usuario usuario) {
    	return ListaDeseo.builder().usuario(usuario)
                .posicionLista(1)
                .nomListaDeseos("Lista libros literatura colombiana")
                .build();
    }

    public static MensajeRespuesta getMensajeRespuestaOk() {
    	MensajeRespuesta msnRespuesta = new MensajeRespuesta();
    	msnRespuesta.setEstado(MensajeRespuesta.PROCESO_OK);
    	return msnRespuesta;
    }
}
