import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ErrorPrevisionEP03 {

	static public void main(String[] args) throws IOException
	{
		if (args.length < 2) {
			System.err.println("error: no ha indicado ningun fichero de entrada");
			System.out.println("uso: ErrorPrevision observaciones previsiones [, previsiones, ...]");
		}
		
		String ficheroObservaciones = args[0];
		String[] ficheros = new String[args.length-1];
		
		for (int c = 1; c < args.length; c++ ) {
			ficheros[c-1] = args[c];
		}
		
		ErrorPrevisionEP03 lectorPrevisiones = new ErrorPrevisionEP03();
			
		for (String prevision : ficheros) {
			lectorPrevisiones.calculaError(prevision, ficheroObservaciones);
		}
	}
	
	public int calculaError(String fichero, String ficheroObservaciones) throws IOException
	{
		LectorVSC<DatoPrevisionTemperatura> lector = null;
		LectorVSC<DatoTemperatura> lectorObservaciones = null;
		
		try {
			lector = new LectorVSC<DatoPrevisionTemperatura>(fichero, DatoPrevisionTemperatura.class);
			 
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", fichero);
			return -1;
		}
		
		try {
			lectorObservaciones = new LectorVSC<DatoTemperatura>(ficheroObservaciones, DatoTemperatura.class);
			 
		} catch (FileNotFoundException e) {
			System.err.format("error: el fichero %s no existe\n", ficheroObservaciones);
			return -1;
		}
		
		System.out.format("analizando el fichero: %s\n", fichero);
		
		ErrorPrevision error = new ErrorPrevision();
		
		try {
			/* saltamos el encabezado */
			lector.descartaLinea();
			lectorObservaciones.descartaLinea();
			
			DatoPrevisionTemperatura dato = lector.leeLinea();
			DatoTemperatura datoObservacion = lectorObservaciones.leeLinea();
			
			while (dato != null) {	
		
				while (! dato.fecha.equals(datoObservacion.getFecha())) {
					datoObservacion = lectorObservaciones.leeLinea();
					
					if (datoObservacion == null) {
						/* se espera que el rango entre fechas de observaciones sea mayor */
						return -1;
					}
				}
				
				error.addMedida(dato.temperatura, datoObservacion.temperatura);

				dato = lector.leeLinea();	
			}

		} catch (IOException e) {
			System.err.format("error: imposible leer el fichero %s, posiblemente este dañado\n", fichero);
			return -2;
				
		} catch (NumberFormatException e) {
			System.err.format("error: imposible leer el fichero %s, formato incorrecto0\n", fichero);
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
		
		System.out.format("El error medio es: %.10f, el error medio cuadratico es: %.10f\n", 
				error.calcularErrorMedio(), error.calcularErrorCuadraticoMedio());
		
		return 0;
		
	}
	
	class ErrorPrevision {
		
		protected double sum = 0.0d;
		protected int total = 0;
		
		void addMedida(float dato, float datoObservado)
		{
			sum += dato - datoObservado;
			total++;
		}
		
		/*
		 * Error medio: sumatorio(observado - previsto) / numero observaciones
		 */
		double calcularErrorMedio()
		{
			return sum / total;
		}
		
		/*
		 * Error cuadratico medio: sqrt(sumatorio(observado - previsto)^2 / numero observaciones)
		 */
		double calcularErrorCuadraticoMedio()
		{
			return Math.sqrt((sum*sum) / total);
		}
	}
}

class DatoPrevisionTemperatura extends DatoMeteorologico {

	float temperatura;
	
	/*  Cº = K - 273,15 */
	private final float KELVIN_TO_CELSIUS = 273.15f;
		
	public DatoPrevisionTemperatura()
	{	
		temperatura = -1000.0f;
	}
	
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		super.mapeaCampos(campos); /* || mapeaFechaHora() */
		
		temperatura = Float.parseFloat(campos.get(Encabezados.TEMPERATURA)) - KELVIN_TO_CELSIUS;
	}
	
	/*
	 * Convierte el formato de fecha-hora del fichero de entrada. 
	 */
	@Override
	protected void mapeaFechaHora(String fechahora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		/* formato de entrada: '2012-03-07T13:00:00Z' */
		dt.applyPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		try {
			fecha = dt.parse(fechahora);
			
		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	public String toString()
	{
		return String.format("%S %.1f", super.toString(), temperatura);
	}
	
	protected class Encabezados {
		/* 0 date,
		 * 1 lat[unit="degrees_north"],
		 * 2 lon[unit="degrees_east"],
		 * 3 cft[unit="1"],
		 * 4 dir[unit="degree"],
		 * 5 mod[unit="m s-1"],
		 * 6 prec[unit="kg m-2"],
		 * 7 rh[unit="1"],
		 * 8 t2m[unit="K"],
		 * 9 temp[unit="K"],
		 * 10 u[unit="m s-1"],
		 * 11 [unit="m s-1"]*/
		public static final int FECHAHORA = 0;
		public static final int TEMPERATURA = 8;
	}
}

