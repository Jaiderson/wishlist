package com.books.wishlist.security.controllers;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class RequestMensaje {

	public static final String NO_ENCONTRADO = "NO_ENCONTRADO";
	public static final String YA_EXISTE = "YA_EXISTE";
	
		private Object valor;
		private String estado;
		private boolean ok;

		public HttpStatus getStatus() {
			if(estado.equals(NO_ENCONTRADO)) {
				return HttpStatus.NOT_FOUND;
			}
			else if(estado.equals(YA_EXISTE)) {
				return HttpStatus.CONFLICT;
			}
			return null;
		}
}
