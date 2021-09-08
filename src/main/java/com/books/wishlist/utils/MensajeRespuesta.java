package com.books.wishlist.utils;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MensajeRespuesta {

	public static final String YA_EXISTE = "Registro ya existe. ";
	public static final String NO_EXISTE = "Registro no existe. ";
	public static final String SQL_ERROR = "Error en recursos de base de datos. ";
	public static final String CREACION_OK = "Registrado ingresado correctamente. ";
	public static final String PROCESO_OK = "Proceso finalizado correctamente. ";

	private List<String> listaInconsistencias = Lists.newArrayList();

	private String estado;


	public HttpStatus generarEstadoHttp() {
		HttpStatus result = null; 
		
		if(this.estado.equals(YA_EXISTE) || estado.equals(YA_EXISTE)) {
			result = HttpStatus.CONFLICT;
		}
		else if(this.estado.equals(SQL_ERROR)) {
			result = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		else if(this.estado.equals(NO_EXISTE)) {
			result = HttpStatus.NOT_FOUND;
		}
		else if(this.estado.equals(CREACION_OK)) {
			result = HttpStatus.CREATED;
		}
		else if(this.estado.equals(PROCESO_OK)) {
			result = HttpStatus.OK;
		}
		return result;
	}

}
