package org.deiv.javanet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 7 - Programa en Java que recibe como argumento la URL de un objeto (grafico, imagen, audio)
 *     y lo guarda en un fichero.
 * 
 *     David Suárez Rodríguez
 */
public class Javanet_E07 {
	
	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.err.println("error: no ha indicado ninguna URL");
			System.out.println("uso: atributos_url URL");
			return;
		}
		
		URL url;	
	    BufferedReader lector = null;
	    
		try {
			url = new URL(args[0]);
			
			URLConnection conn = url.openConnection();
			lector = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String fichero = obtenFicheroSalvar(url);
			
			escribeFichero(lector, fichero);
			
		} catch (MalformedURLException e) {
			System.err.format("La Url %s, esta mal formada\n", args[0]);
			
		} catch (IOException e) {
			System.err.format("Error leyendo la url %s\n", args[0]);
		
		} finally {
			try {
				if (lector != null) lector.close();
				
			} catch (IOException e) {
				System.err.println("Error de entrada/salida");
			}
		}
	}
	
	public static void escribeFichero(BufferedReader lector, String archivo) throws IOException
	{
		OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(archivo));
		char[] bufer = new char[128];
	     
		try {
			while ( lector.read(bufer, 0, 128) > 0) {
				ow.write(bufer);
			}
			
		} finally {
			
			ow.close();
		}
	}
	
	public static String obtenFicheroSalvar(URL url)
	{
		String fichero = url.getFile();
		
		if (fichero == null) {
			System.err.println("La url no presenta ningun fichero");
			
		} else {
			fichero = extraeFicheroDesdePath(fichero);
			
			if (fichero != null) {
				return String.format("%s/%s", System.getProperty("user.dir"), fichero);
			}
		}
		
		return null;
	}
	
	public static String extraeFicheroDesdePath(String path)
	{
		int i = path.lastIndexOf(File.separator);
		
		if (i < 0) {
			return null;
			
		} else {
			return path.substring(i+1);
		}
 	}
}
