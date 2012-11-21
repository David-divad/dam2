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

		} catch (NumberFormatException e) {
			throw new FileFormatException();
			
		} finally {
			lector.close();
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
			
		} finally {
			if (oos != null) 
				oos.close();
		}
		
		return 0;
	}

	public int leeDesdeFicheroObj(String fichero) throws IOException
	{
		FileInputStream istream = new FileInputStream(fichero);
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
			
		} catch (ClassNotFoundException e) {
			throw new FileFormatException();
			
		} finally {
			if (ois != null) 
				ois.close(); 
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