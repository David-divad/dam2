package org.deiv.meteo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.deiv.meteo.datos.DatoMeteorologico;

public class FicheroDatos<T extends DatoMeteorologico> implements Serializable {
	
	private static final long serialVersionUID = -1915370548748319647L;
	
	protected Class<T> claseDato;
	Map<Date, T> datos = new TreeMap<Date, T>();
	
	public FicheroDatos(Class<T> claseDato)
	{
		this.claseDato = claseDato;
	}
	
	public Map<Date, T> getDatos()
	{
		return datos;
	}

	public void leeDesdeFicheroVSC(String fichero, boolean tieneEncabezado) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		LectorVSC<T> lector = null;
		
		lector = new LectorVSC<T>(fichero, claseDato);

		try {
			/* saltamos el encabezado */
			if (tieneEncabezado)
				lector.descartaLinea();
			
			T dato = lector.leeLinea();
			
			while (dato != null) {
				datos.put(dato.getFechaHora(), dato);
				dato = lector.leeLinea();
			}

		} catch (IOException e) {
			System.err.format("error: imposible leer el fichero %s, posiblemente este dañado\n", fichero);
			throw e;
				
		} catch (NumberFormatException e) {
			System.err.format("error: imposible leer el fichero %s, formato incorrecto\n", fichero);
			throw new FileFormatException();
			
		} finally {
			try {
				lector.close();
				
			} catch (IOException e) {
				System.err.format("fatal: imposible cerrar el fichero %s\n", fichero);
                throw e;
			} 
		}
	}
	
	public int escribeAFicheroObj(String fichero) throws IOException
	{
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichero));
			
			for (T d : datos.values()) {
				oos.writeObject(d);
			}
			
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", fichero);
			return -1;
			
		} catch (IOException e) {
			System.err.format("error: imposible leer el fichero %s, posiblemente este dañado\n", fichero);
			return -2;
			
		} finally {
			if (oos != null) {
				try {
					oos.close();

				} catch (IOException e) {
					System.err.format("fatal: imposible cerrar el fichero %s\n", fichero);
	                throw e;
				} 
			}
		}
		
		return 0;
	}

	public int leeDesdeFicheroObj(String fichero) throws IOException
	{
		FileInputStream istream = null;
		
		try {
			istream = new FileInputStream(fichero);
			
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", fichero);
			return -1;
			
		}
		
		ObjectInputStream ois = null;
		
		try {

			ois = new ObjectInputStream(istream);
			
			TreeMap<Date, T> datos = new TreeMap<Date, T>();
			T dato = null;

			while (istream.available() > 0) {
				dato = (T) ois.readObject();
				datos.put(dato.getFechaHora(), dato);
			}
			
			this.datos = datos;
			
		} catch (IOException e) {
			System.err.format("error: imposible leer el fichero %s, posiblemente este dañado\n", fichero);
			return -2;
			
		} catch (ClassNotFoundException e) {
			System.err.format("error: imposible leer el fichero %s, datos inesperados\n", fichero);
			return -2;
			
		} finally {
			try {
				istream.close();
			} catch (IOException e) {
				System.err.format("fatal: imposible cerrar el fichero %s\n", fichero);
                throw e;
			} 

			
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					System.err.format("fatal: imposible cerrar el fichero %s\n", fichero);
	                throw e;
				} 
			}
		}
		
		return 0;
	}
	
	public String toString()
	{
		String r = new String();
		
		for (T d : datos.values()) {
			r += d.toString() + "\n";
		}
		
		return r;
	}
	
}

class FileFormatException extends java.io.IOException {

	private static final long serialVersionUID = 4550277946412885951L;	
}