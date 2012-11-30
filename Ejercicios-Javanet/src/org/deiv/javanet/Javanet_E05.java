package org.deiv.javanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 5 - Programa en Java que recibe como argumento una URL y escribe en la salida estandar el
 *     contenido de dicha URL.
 */
public class Javanet_E05 {
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ninguna URL");
			System.out.println("uso: atributos_url URL");
			return;
		}
		
		URL url;	
		InputStreamReader  inStream = null;
	    BufferedReader buff = null;
		String linea;
		
		try {
			url = new URL(args[0]);
			
			URLConnection conn = url.openConnection();
			String contentType = conn.getContentType();
			
			if(contentType.contains("text/html")) {
				inStream = new InputStreamReader(conn.getInputStream());
				buff = new BufferedReader(inStream);
			     
				while (true) {
					linea = buff.readLine();  
					
					if (linea != null){
						System.out.println(linea); 
						
					} else {
						break;
					} 
			    }
				
			} else {
				System.err.format("El contenido de la url %s, no es html\n", args[0]);
			} 
			
		} catch (MalformedURLException e) {
			System.err.format("La Url %s, esta mal formada\n", args[0]);
			
		} catch (IOException e) {
			System.err.format("Error leyendo la url %s\n", args[0]);
		
		} finally {
			try {
				if (buff != null) buff.close();
				
			} catch (IOException e) {
				System.err.println("Error de entrada/salida");
			}
		}
	}
}
