package com.books.wishlist.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class VolumeInfoDto {

	@ApiModelProperty(position=1, dataType="String", value="Nombre del libro.", 
			                      example="Cien años de soledad.<br>", required=true)
    private String title;

	@ApiModelProperty(position=2, dataType="String", value="Sun titulo del libro.<br>", 
			                      example="Resumen literario de la obra cien años de...", required=true)
    private String subtitle;

	@ApiModelProperty(position=3, dataType="String", value="Nombre de la editorial.<br>", 
			                      example="EDITORIAL NORMA", required=true)
    private String publisher;

	@ApiModelProperty(position=4, dataType="String", value="Listado de autores del libro." , example="10", required=true)
    private List<String> authors;

    /**
     * Permite concatenar los autores de un libro de tal manera que se pueda 
     * trabajar como un String y no como una lista.
     * 
     * @return Cadena con los autores concatenados y separados por <b>|</b> 
     */
    public String formatAuthors() {
    	StringBuilder formatAuthors = new StringBuilder("");
    	if(null != this.authors && !this.authors.isEmpty()) {
    		this.authors.stream().forEach(author -> formatAuthors.append(author).append("|"));
    	}
    	return formatAuthors.toString();
    }
}
