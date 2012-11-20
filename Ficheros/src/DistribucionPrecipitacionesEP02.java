import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class DistribucionPrecipitacionesEP02 {
	
	public static void main(String[] args) throws IOException
	{	
		if (args.length < 1) {
			System.err.println("error: no ha indicado ningun fichero de entrada");
			System.out.println("uso: distribucionprecipitaciones archivo [, archivo, ...]");
		}
		
		DistribucionPrecipitacionesEP02 lector = new DistribucionPrecipitacionesEP02();
		
		for (String fichero : args ) {
			/* En caso de 'fallo tolerable' pasamos al proximo fichero */
			lector.leePrecipitaciones(fichero);
		}
	}
	
	
	public int leePrecipitaciones(String fichero) throws IOException
	{
		LectorVSC<DatoPrecipitacion> lector = null;
		Map<String, MediaAcumulada> precipitaciones = new TreeMap<String, MediaAcumulada>();
		
		try {
			lector = new LectorVSC<DatoPrecipitacion>(fichero, DatoPrecipitacion.class);
			 
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", fichero);
			return -1;
		} 
		
		System.out.format("analizando el fichero: %s\n", fichero);
		
		try {
			/* saltamos el encabezado */
			lector.descartaLinea();
			
			MediaAcumulada media = null;
			DatoPrecipitacion dato = lector.leeLinea();
			
			while (dato != null) {	
				
				if (precipitaciones.containsKey(dato.getHora())) {
					media = precipitaciones.get(dato.getHora());
					media.addDato(dato.precipitacion);
					
				} else {
					media = new MediaAcumulada();
					media.addDato(dato.precipitacion);
					precipitaciones.put(dato.getHora(), media);
				}		
				
				dato = lector.leeLinea();	
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
		
		for (Map.Entry<String, MediaAcumulada> p : precipitaciones.entrySet()) {
			System.out.format("A la hora %s hay una media de %.1f\n", p.getKey(), p.getValue().getMedia());
		}
		
		return 0;
	}
	
	/** 
	 * Clase para ayudarnos a calcular la media
	 */
	protected class MediaAcumulada {
		protected double sum = 0;
		protected int total = 0;
		
		void addDato(float dato)
		{
			//return media = (total * media + dato) / (total + 1);
			sum += dato;
			total++;
		}
		
		double getMedia()
		{
			return sum / total;
		}
	}
}

class DatoPrecipitacion extends DatoMeteorologico {
	
	float precipitacion;
	
	public DatoPrecipitacion()
	{	
		precipitacion = 0.0f;
	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		super.mapeaCampos(campos);
		precipitacion = Float.parseFloat(campos.get(Encabezados.PRECIPITACIONES));	
	}
	
	public String toString()
	{
		return String.format("%s %.1f", super.toString(), precipitacion);
	}
	
	public void copia(DatoPrecipitacion t)
	{
		super.copia(t);
		precipitacion = t.precipitacion;
	}
}
