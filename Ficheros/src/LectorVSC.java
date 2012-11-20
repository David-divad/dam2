import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LectorVSC<T extends LectorVSC.DatoVSC> {
	
	private final String DELIMITADORES = ",";
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
		
		StringTokenizer st = new StringTokenizer(linea, DELIMITADORES);
		ArrayList<String> campos = new ArrayList<String>();
		
		while (st.hasMoreTokens()) {
			campos.add(st.nextToken());
		}
				
		return mapeaLinea(campos);
	}
	
	protected T mapeaLinea(ArrayList<String> campos)
	{
		T dato;

		try {
			Constructor<T> ctor = tipoDato.getConstructor();
			dato = ctor.newInstance();
			 
		} catch (Exception e) {
			/* 
             * Imposible recuperarse.
             * La encapsulamos como RuntimeException para evitar manejarla. 
             */
			throw new RuntimeException(e);
		}
		
		dato.mapeaCampos(campos);
		
		return dato;
	}
	
	public interface DatoVSC {
		public void mapeaCampos(ArrayList<String> campos);
	}
}
