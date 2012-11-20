import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class LeeTemperaturasEP01 {
	
	public static void main(String[] args) throws IOException
	{	
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun fichero de entrada");
			System.out.println("uso: leetemperaturas archivo [, archivo, ...]");
		}
		
		LeeTemperaturasEP01 lector = new LeeTemperaturasEP01();

		for (String fichero : args ) {
			/* En caso de 'fallo tolerable' pasamos al proximo fichero */
			lector.leeTemperaturas(fichero);
		}
	}
	
	public int leeTemperaturas(String fichero) throws IOException
	{
		LectorVSC<DatoTemperatura> lector = null;
		
		try {
			lector = new LectorVSC<DatoTemperatura>(fichero, DatoTemperatura.class);
			 
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", fichero);
			return -1;
		} 
		
		System.out.format("analizando el fichero: %s\n", fichero);
		
		try {
			/* saltamos el encabezado */
			lector.descartaLinea();
			
			DatoTemperatura dato = lector.leeLinea();
			
			while (dato != null) {
				
				DatoTemperatura temperaturaMaxima = new DatoTemperatura();
						
				do {					
					if (dato.temperatura > temperaturaMaxima.temperatura)
						temperaturaMaxima.copia(dato);
					
					dato = lector.leeLinea();
					
				} while ( dato != null && dato.esMismoDia(temperaturaMaxima) );
				
				System.out.format("La temperatura más alta el día %s fue: %.1fCº a las %s horas\n", 
						temperaturaMaxima.geFecha(), temperaturaMaxima.temperatura, temperaturaMaxima.getHora());	
			}

		} catch (IOException e) {
			System.err.format("error: imposible leer el fichero %s, posiblemente este dañado\n", fichero);
			return -2;
				
		} catch (NumberFormatException e) {
			System.err.format("error: imposible leer el fichero %s, formato incorrecto\n", fichero);
			return -3;
			
		} catch (NoSuchElementException e) {
			System.err.format("error: imposible leer el fichero %s, formato incorrecto\n", fichero);
			return -3;
				 	
		} finally {
			try {
				lector.close();
				
			} catch (IOException e) {
				System.err.format("fatal: imposible cerrar el fichero %s\n", fichero);
                throw e;
			} 
		}
		
		return 0;
	}
}

class DatoTemperatura extends DatoMeteorologico {
	
	/* no definimos getters/setters */
	float temperatura;
	
	public DatoTemperatura()
	{	
		temperatura = -1000.0f;
	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		super.mapeaCampos(campos);
		temperatura = Float.parseFloat(campos.get(Encabezados.TEMPERATURA));
	}
	
	public void copia(DatoTemperatura t)
	{	
		super.copia(t);
		temperatura = t.temperatura;
	}
	
	public String toString()
	{
		return String.format("%s %.1f", super.toString(), temperatura);
	}
}
