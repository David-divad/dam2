package org.deiv.javanet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 6 - Programa en Java que recibe como argumento una URL y escribe la informacion de
 *     cabecera de esta URL en su salida estandar.
 */
public class Javanet_E06 {
	
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
			
			URLConnection conn = url.openConnection();
			  
			for (int i=0; ; i++)  {
				
				String encabezado = conn.getHeaderFieldKey(i);
				String valor = conn.getHeaderField(i);
				
				if (encabezado == null && valor == null) {
					break; 
				}
				
				if (encabezado == null) {
					System.out.format("Codigo de respuesta = %s\n", valor);
					
				} else {
					System.out.format("%s = %s\n", encabezado, valor);
				}
			 }
			
		} catch (MalformedURLException e) {
			System.err.format("La Url %s, esta mal formada\n", args[0]);
			
		} catch (IOException e) {
			System.err.format("Error leyendo la url %s\n", args[0]);
		}
	}
}
