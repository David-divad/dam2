package org.deiv.javanet;

import java.net.MalformedURLException;
import java.net.URL;

/*
 * 4 - Programa en Java que recibe como argumento una URL y escribe en la salida estandar los
 *     atributos de dicha URL.
 */
public class Javanet_E04 {
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ninguna URL");
			System.out.println("uso: atributos_url URL");
			return;
		}
		
		URL url;
		
		try {
			url = new URL(args[0]);
			
			System.out.format("Atributos de la URL: %s\n", args[0]);
			System.out.format("protocolo: %s\n", url.getProtocol());
			System.out.format("host: %s\n", url.getHost());
			System.out.format("puerto: %s\n", url.getPort());
			System.out.format("ruta: %s\n", url.getPath());
			System.out.format("attr: %s\n", url.getQuery());
			
		} catch (MalformedURLException e) {
			System.err.format("La Url %s, esta mal formada\n", args[0]);
		}
	}
}
