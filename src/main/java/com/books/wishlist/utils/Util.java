package com.books.wishlist.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {

	private Util() {
		super();
	}

	/**
	 * Metodo que permite validar el formato de una fecha.
	 * 
	 * @param fecha Fecha a comprobar formato (yyyy-mm-dd)
	 * @return Dato con el contenido del resultado de la validacion.
	 */
	public static Dato stringToDate(String fecha) {
		Dato dato = new Dato();
		try {
			LocalDate localDate = LocalDate.parse(fecha);
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
			dato.setValor(Date.from(zonedDateTime.toInstant()));
			dato.setOk(true);
		} catch (Exception e) {
			dato.setValor(null);
			dato.setValor(false);
		}
		return dato;
	}

	/**
	 * Dada una fecha de tipo util.Date retorna un String con la fecha en formato <b>yyyy-MM-dd HH:mm:ss</b>
	 * 
	 * @param fecha Fecha a formatear.
	 * @return Fecha en formato yyyy-MM-dd HH:mm:ss.
	 */
	public static String dateToString(Date fecha){
		if(fecha == null){
			return "";
		}
		LocalDateTime fec = LocalDateTime.ofInstant(new Date(fecha.getTime()).toInstant(), ZoneId.systemDefault());
		return fec.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
