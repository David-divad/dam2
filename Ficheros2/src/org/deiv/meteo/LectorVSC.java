package org.deiv.meteo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.deiv.meteo.util.Reflexion;

/**
 *
 * Lector de archivos CSV.
 *
 * @param <T> Tipo de dato que manejamos
 */
public class LectorVSC<T extends LectorVSC.DatoVSC> {
	
	static protected final String DELIMITADORES = ",";
	protected BufferedReader lector;
	Class<T> tipoDato;
	
	public LectorVSC(BufferedReader lector, Class<T> tipoDato)
	{
		this.lector = lector;
		this.tipoDato = tipoDato;
	}
	
	public LectorVSC(FileReader lector, Class<T> tipoDato)
	{
		this.lector = new BufferedReader(lector);
		this.tipoDato = tipoDato;
	}
	
	public LectorVSC(String fichero, Class<T> tipoDato) throws FileNotFoundException
	{
		this.lector = new BufferedReader(new FileReader(fichero));
		this.tipoDato = tipoDato;
	}
	
	public void close() throws IOException
	{
		lector.close();
	}
	
	public void descartaLinea() throws IOException
	{
		lector.readLine();
	}
	
	public T leeLinea() throws IOException
	{
		String linea = lector.readLine();
		
		if (linea == null)
			return null;
		
		ArrayList<String> campos = troceaLinea(linea);
		
		return mapeaLinea(campos);
	}
	
	protected ArrayList<String> troceaLinea(String linea)
	{
		StringTokenizer st = new StringTokenizer(linea, DELIMITADORES);
		ArrayList<String> campos = new ArrayList<String>();
		
		while (st.hasMoreTokens()) {
			campos.add(st.nextToken().trim());
		}
				
		return campos;
	}
	
	protected T mapeaLinea(ArrayList<String> campos)
	{
		T dato = Reflexion.nuevaInstancia(tipoDato);
		
		dato.mapeaCampos(campos);
		
		return dato;
	}
	
	/**
	 * @note Para que se pueda instanciar, mediante reflexion, la clase que derive de DatoVSC,
	 *        esta ha de implementar un constructor por defecto.
	 */
	public interface DatoVSC {
		public void mapeaCampos(ArrayList<String> campos);
	}
}
