package com.books.wishlist.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Consola {

	private Consola() {
		super();
	}
	
	private static Logger logger = LoggerFactory.getLogger(Consola.class);

	public static void debug(String mensaje) {
		logger.debug(mensaje);
	}

	public static void error(String mensaje) {
		logger.error(mensaje);
	}

	public static void warn(String mensaje) {
		logger.warn(mensaje);
	}

	public static void info(String mensaje) {
		logger.info(mensaje);
	}

}
