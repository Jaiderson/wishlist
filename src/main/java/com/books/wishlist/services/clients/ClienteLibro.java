package com.books.wishlist.services.clients;

import org.springframework.web.client.RestTemplate;

import com.books.wishlist.dto.RespuestaLibroDto;

public class ClienteLibro {

	private static final String URL_BOOK_API = "https://www.googleapis.com/books/v1/volumes?q=";

	/**
	 * Metodo de busqueda general el cual consulta en el API de google sin tipo de busqueda alguno.
	 * 
	 * @param libroBusqueda Nombre del libro a buscar
	 * @return Respuesta con listado de libros resultado de la busqueda.
	 */
	public RespuestaLibroDto buscarLibros(String libroBusqueda) {
		libroBusqueda = libroBusqueda.trim().replaceFirst(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URL_BOOK_API + libroBusqueda, RespuestaLibroDto.class);
	}

	/**
	 * Metodo personalizado para buscar libros en el cual el usuario define su tipo de busqueda <b>tipoBusqueda</b> 
	 * y el valor asociado a este <b>parametroBusqueda</b> para obtener resultados optimos en una busqueda.
	 * 
	 * @param nombreLibro
	 * @param tipoBusqueda Enumerdo de tipo de busqueda el cual personaliza la busqueda. <b>(AUTOR, TITULO, EDITOR o CATEGORIA)</b> 
	 * @param parametroBusqueda Valor a buscar asociado al tipo de busqueda.
	 *        Por ejemplo tipoBusqueda = <b>AUTOR</b> parametroBusqueda = <b>'GABRIEL GARCIA MARQUEZ'</b> 
	 * @return Respuesta con listado de libros resultado de la busqueda.
	 */
    public RespuestaLibroDto buscarLibros(String nombreLibro, ETipoBusqueda tipoBusqueda, String parametroBusqueda) {
       nombreLibro = nombreLibro.trim().replaceFirst(" ", "+");
       nombreLibro += nombreLibro+"+";
       parametroBusqueda = parametroBusqueda.trim().replaceFirst(" ", "+");

       RestTemplate restTemplate = new RestTemplate();
       String url = URL_BOOK_API + nombreLibro+tipoBusqueda.getTipoBusqueda()+parametroBusqueda;
       return restTemplate.getForObject(url, RespuestaLibroDto.class);
    }

}
