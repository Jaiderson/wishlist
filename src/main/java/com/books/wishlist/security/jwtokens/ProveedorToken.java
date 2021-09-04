package com.books.wishlist.security.jwtokens;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.books.wishlist.security.entities.UsuarioAutorizado;
import com.books.wishlist.utils.Consola;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class ProveedorToken {

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
        	Consola.error("Token mal formado. "+e.getMessage());
        }catch (UnsupportedJwtException e){
        	Consola.error("Token no soportado. "+e.getMessage());
        }catch (ExpiredJwtException e){
        	Consola.error("Token expirado. "+e.getMessage());
        }catch (IllegalArgumentException e){
        	Consola.error("Token no valido o vacio. "+e.getMessage());
        }catch (SignatureException e){
        	Consola.error("Firma del token errada. "+e.getMessage());
        }
        return esValido;
    }
}
