package com.books.wishlist.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class VolumeInfoDto {

    private String title;
    private String subtitle;
    private String publisher;
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
