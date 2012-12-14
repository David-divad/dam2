package org.deiv.meteo.app;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.deiv.meteo.datos.DatoMeteorologicoConFecha;
import org.deiv.meteo.datos.Util;

public class DatoPrevisionGfs5Km extends DatoMeteorologicoConFecha implements Serializable {
	
	private static final long serialVersionUID = -8491872952268123213L;
	
	protected double latitud = 0.0d;
	protected double longitud = 0.0d;
	protected double presion = 0.0d;
	protected double temperatura = 0.0d;	
	protected double nubes = 0.0d;
	protected double precipitacion = 0.0d;
	
	public DatoPrevisionGfs5Km()
	{	

	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		mapeaFechaHora(campos.get(Encabezados.FECHAHORA));
		
		latitud            = Double.parseDouble(campos.get(Encabezados.LATITUD));
		longitud           = Double.parseDouble(campos.get(Encabezados.LONGITUD));
		temperatura = Util.kelvinACelsius(Double.parseDouble(campos.get(Encabezados.TEMPERATURA)));
		presion                = Double.parseDouble(campos.get(Encabezados.PRESION));
		nubes            = Double.parseDouble(campos.get(Encabezados.NUBES));
		precipitacion      = Double.parseDouble(campos.get(Encabezados.PRECIPITACION));
	}
	
	protected void mapeaFechaHora(String fechahora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();

		/* formato de entrada: '2012-03-07T13:00:00Z' */
		dt.applyPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		/* pasamos de UTC a hora local */
		dt.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			fecha = dt.parse(fechahora);

		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	public class Encabezados {
		/*
		 * 5KM
		 * 
		 * date,
		 * lat[unit="degrees_north"],
		 * lon[unit="degrees_east"],
		 * Pressure_reducedto_MSL[unit="Pa"],
		 * Temperature_surface[unit="K"],
		 * Total_cloud_cover[unit="percent"],
		 * Total_precipitation[unit="kg m-2"]
		 */
		public static final int FECHAHORA = 0;
		public static final int LATITUD = 1;
		public static final int LONGITUD = 2;
		public static final int PRESION = 3;
		public static final int TEMPERATURA = 4;
		public static final int NUBES = 5;
		public static final int PRECIPITACION = 6;
	}

	public String toString() 
	{
		return String.format("DatoPrevisionGfs5Km [latitud=%s, longitud=%s, presion=%s, " +
				"temperatura=%s, nubes=%s, precipitacion=%s, toString()=%s]",
				latitud, longitud, presion, temperatura, nubes,
				precipitacion, super.toString());
	}

	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		
		temp = Double.doubleToLongBits(latitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(nubes);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(precipitacion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(presion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(temperatura);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		return result;
	}

	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		DatoPrevisionGfs5Km other = (DatoPrevisionGfs5Km) obj;
		
		if (Double.doubleToLongBits(latitud) != Double
				.doubleToLongBits(other.latitud))
			return false;
		if (Double.doubleToLongBits(longitud) != Double
				.doubleToLongBits(other.longitud))
			return false;
		if (Double.doubleToLongBits(nubes) != Double
				.doubleToLongBits(other.nubes))
			return false;
		if (Double.doubleToLongBits(precipitacion) != Double
				.doubleToLongBits(other.precipitacion))
			return false;
		if (Double.doubleToLongBits(presion) != Double
				.doubleToLongBits(other.presion))
			return false;
		if (Double.doubleToLongBits(temperatura) != Double
				.doubleToLongBits(other.temperatura))
			return false;
		
		return true;
	}


	

}
