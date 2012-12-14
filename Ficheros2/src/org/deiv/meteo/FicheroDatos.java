package org.deiv.meteo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.deiv.meteo.colecciones.Coleccion;
import org.deiv.meteo.datos.DatoMeteorologico;
import org.deiv.meteo.discriminadores.Discriminador;
import org.deiv.meteo.util.Reflexion;

/**
 * 
 * @param <T> Tipo de dato
 * @param <C> Tipo de colección que almacena los datos
 */
public class FicheroDatos
  <T extends DatoMeteorologico, C extends Coleccion<T>> 
  implements Serializable {

	private static final long serialVersionUID = -1915370548748319647L;
	
	protected Class<T> claseDato;
	protected Class<? extends Coleccion> claseAlmacenamiento;
	protected C datos;
	
	public FicheroDatos(Class<T> claseDato, Class<? extends Coleccion> claseAlmacenamiento)
	{
		this.claseDato = claseDato;
		this.claseAlmacenamiento = claseAlmacenamiento;
		
		datos = (C) Reflexion.nuevaInstancia(claseAlmacenamiento);
	}

	public void leeDesdeFicheroVSC(String fichero, boolean tieneEncabezado) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		BufferedReader flujo = new BufferedReader(new FileReader(fichero));

		try {
			leeDesdeFicheroVSC(flujo, tieneEncabezado);

		} finally {
			flujo.close();
		}
	}
	
	public void leeDesdeFicheroVSC(LectorVSC<T> lector, boolean tieneEncabezado) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		try {
			/* saltamos el encabezado */
			if (tieneEncabezado)
				lector.descartaLinea();
			
			T dato = lector.leeLinea();
			
			while (dato != null) {
				datos.añadir(dato);
				dato = lector.leeLinea();
			}

		} catch (NumberFormatException e) {
			throw new FileFormatException(e);
		}
	}
	
	public void leeDesdeFicheroVSC(BufferedReader flujo, boolean tieneEncabezado) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		LectorVSC<T> lector = new LectorVSC<T>(flujo, claseDato);

		leeDesdeFicheroVSC(lector, tieneEncabezado);
	}
	
	public int escribeAFicheroObj(String fichero) throws IOException
	{
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichero));
			
			for (T d : datos.obtenTodos()) {
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
			
			C datos = (C) Reflexion.nuevaInstancia(claseAlmacenamiento);
			
			T dato = null;

			while (istream.available() > 0) {
				dato = (T) ois.readObject();
				datos.añadir(dato);
			}
			
			this.datos = datos;
			
		} catch (ClassNotFoundException e) {
			throw new FileFormatException(e);
			
		} finally {
			if (ois != null) 
				ois.close(); 
		}
		
		return 0;
	}
	
	
	public void discrimina(Discriminador<T> discriminador) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		ArrayList<T> noDiscriminados = new ArrayList<T>();
		
		/* Creamos una lista con los datos a eliminar, ... */
		for (T dato : datos.obtenTodos()) {
			if (!discriminador.discrimina(dato))
				noDiscriminados.add(dato);
		}
		
		/* ... para hacerlo despues de recorrer la coleccion, evitando problemas de concurrencia */
		for (T dato : noDiscriminados) {
			datos.eliminar(dato);
		}
	}
	
	public Collection<T> getDatos()
	{
		return datos.obtenTodos();
	}
	
	public C obtenAlmacen()
	{
		return datos;
	}
	
	public String toString()
	{
		String r = new String();
		
		for (T d : datos.obtenTodos()) {
			r += d.toString() + "\n";
		}
		
		return r;
	}
}
