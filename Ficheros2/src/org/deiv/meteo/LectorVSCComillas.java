package org.deiv.meteo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 
 * Lector CSV, especializado en archivos CSV delimitados por comillas.
 *
 * @param <T> Tipo de dato que manejamos
 */
public class LectorVSCComillas<T extends LectorVSC.DatoVSC> extends LectorVSC<T> {
	
	static protected final String COMILLAS = "\"";  
	
	public LectorVSCComillas(BufferedReader lector, Class<T> tipoDato)
	{
		super(lector, tipoDato);
	}
	
	public LectorVSCComillas(FileReader lector, Class<T> tipoDato)
	{
		super(lector, tipoDato);
	}
	
	public LectorVSCComillas(String fichero, Class<T> tipoDato) throws FileNotFoundException
	{
		super(fichero, tipoDato);
	}
	
	protected ArrayList<String> troceaLinea(String linea)
	{
		StringTokenizer st = new StringTokenizer(linea, COMILLAS);
		ArrayList<String> campos = new ArrayList<String>();
		
		String token;
		boolean ultimoEsDelimitador = false;
		
		while (st.hasMoreTokens()) {
			token = st.nextToken().trim();
			
			if (token.equals(DELIMITADORES)) {
				if (ultimoEsDelimitador) {
					campos.add("");
					
				} else {
					ultimoEsDelimitador = true;
				}
				
				continue;
			}
			ultimoEsDelimitador = false;
			
			if (token.startsWith(COMILLAS) && token.endsWith(COMILLAS)) {
				token = token.substring(1, token.length()-1);
			}
			
			campos.add(token);
		}
				
		return campos;
	}
}
