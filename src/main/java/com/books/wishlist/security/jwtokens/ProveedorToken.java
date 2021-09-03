package com.books.wishlist.security.jwtokens;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import com.books.wishlist.security.entities.UsuarioAutorizado;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class ProveedorToken {

	private final static Logger logger = LoggerFactory.getLogger(PuntoEntrada.class);
	
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generarToken(Authentication authentication){
        UsuarioAutorizado usuarioAutorizado = (UsuarioAutorizado) authentication.getPrincipal();
        
        return Jwts.builder().setSubject(usuarioAutorizado.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
    	boolean esValido = false;
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            esValido = true;
        }catch (MalformedJwtException e){
            logger.error("Token mal formado.");
        }catch (UnsupportedJwtException e){
            logger.error("Token no soportado.");
        }catch (ExpiredJwtException e){
            logger.error("Token expirado.");
        }catch (IllegalArgumentException e){
            logger.error("Token no valido o vacio.");
        }catch (SignatureException e){
            logger.error("Firma del token errada.");
        }
        return esValido;
    }
}
