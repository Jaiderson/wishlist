package com.books.wishlist.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MensajeError {

	public static final String CREAR_REGISTRO = "NUEVO-REGISTRO";
	public static final String MODIFICAR_REGISTRO = "ACTUALIZA-REGISTRO";
	public static final String LOGIN = "LOGIN-USUARIO";
	public static final String SIN_ROLES = "Rol USER no encontrado.";
	public static final String ROL_NO_EXISTE = "Uno o mas de los roles no existen.";

	private String codMensaje;
	private List<Map<String, String>> mensajes;

	public MensajeError(String codMensaje) {
		this.codMensaje = codMensaje;
		this.mensajes = new ArrayList<>();
	}

	public String getMensaje(BindingResult result) {
		String resultMsn = "";
		
		if(null != result) {
			this.mensajes = result.getFieldErrors().stream().map(err -> {
				Map<String, String> error = new HashMap<>();
				error.put(err.getField(), err.getDefaultMessage());
				return error;
			}).collect(Collectors.toList());
			resultMsn = formatoJson();
		}
		return resultMsn;
	}

	public String formatoJson() {
		String jsonString = "";
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			Consola.error("MensajeError.formatoJson: "+jsonString+" - "+e.getMessage());
		}
		return jsonString;
	}

	public static String getMensageNomUsuario(String nomUsuario) {
		return "Nombre de usuario: " + nomUsuario + " ya esta registrado.";
	}

	public static String getMensageEmail(String email) {
		return "Email ingresado: " + email + " ya esta asociado a otro usuario.";
	}

}
