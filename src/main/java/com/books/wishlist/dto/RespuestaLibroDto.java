package com.books.wishlist.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class RespuestaLibroDto {

	private String kind;
	private int totalItems;
	private List<ItemDto> items;

}
