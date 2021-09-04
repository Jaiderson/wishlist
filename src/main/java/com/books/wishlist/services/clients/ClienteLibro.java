package com.books.wishlist.services.clients;

import org.springframework.web.client.RestTemplate;

import com.books.wishlist.dto.RespuestaLibroDto;

public class ClienteLibro {

	private static final String URL_BOOK_API = "https://www.googleapis.com/books/v1/volumes?q=";
	
	public RespuestaLibroDto buscarLibros(String libroBusqueda) {

		libroBusqueda = libroBusqueda.trim().replaceFirst(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URL_BOOK_API + libroBusqueda, RespuestaLibroDto.class);
	}

}
