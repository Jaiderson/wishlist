package com.books.wishlist.security.jwtokens;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.books.wishlist.utils.Consola;

@Component
public class PuntoEntrada implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			             AuthenticationException authException) throws IOException, ServletException {
		Consola.error("PuntoEntrada fallo metodo commence.");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Solicitud no autorizada.");
	}

}
