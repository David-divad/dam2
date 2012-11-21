package org.deiv.meteo.datos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.deiv.meteo.Util;
import org.deiv.meteo.datos.campos.CampoRacha;
import org.deiv.meteo.datos.campos.CampoViento;


public class DatoObservacion extends DatoMeteorologico implements Serializable {
	
	private static final long serialVersionUID = 612981911538235809L;
	
	protected double temperatura = 0.0d;
	protected CampoViento viento = new CampoViento();
	protected CampoRacha racha = new CampoRacha();
	protected double precipitacion = 0.0d;
	protected double presion = 0.0d;
	protected double tendencia = 0.0d;
	protected double humedad = 0.0d;
	protected double windchill = 0.0d;
	protected double confort = 0.0d;
	
	public DatoObservacion()
	{	

	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		mapeaFechaHora(campos.get(Encabezados.FECHAHORA));
		
		temperatura   = Float.parseFloat(campos.get(Encabezados.TEMPERATURA));
		precipitacion = Float.parseFloat(campos.get(Encabezados.PRECIPITACION));
		presion       = Float.parseFloat(campos.get(Encabezados.PRESION));
		tendencia     = Float.parseFloat(campos.get(Encabezados.TENDENCIA));
		humedad       = Util.tantox100Atantox1(Float.parseFloat(campos.get(Encabezados.HUMEDAD)));
		windchill     = Float.parseFloat(campos.get(Encabezados.WINDCHILL));
		confort       = Float.parseFloat(campos.get(Encabezados.CONFORT));
		
		viento.setDireccion(Util.calculaDireccion(campos.get(Encabezados.DIRECCION_VIENTO)));
		viento.setVelocidad(Util.kmhAms(Float.parseFloat(campos.get(Encabezados.VELOCIDAD_VIENTO))));
		
		racha.setDireccion(Util.calculaDireccion(campos.get(Encabezados.DIRECCION_RACHA)));
		racha.setVelocidad(Util.kmhAms(Float.parseFloat(campos.get(Encabezados.RACHA))));
	}
	
	
	/*
	 * Convierte el formato de fecha-hora del fichero de entrada.
	 */
	protected void mapeaFechaHora(String fechahora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		/* formato de entrada: '07/03/2012 00:00' */
		dt.applyPattern("dd/MM/yyyy HH:mm");
		
		try {
			fecha = dt.parse(fechahora);
			
		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	public class Encabezados {
		/*
		 * 01/03/2012 02:00,7.4,2,Norte,5,Nordeste,0,982.3,-0.4,100,,573.822152444588,7.4
		 * 
		 * 0 Fecha Hora oficial,
		 * 1 Temperatura (ºC),
		 * 2 Velocidad del viento (km/h),
		 * 3 Dirección del viento,
		 * 4 Racha (km/h),
		 * 5 Dirección de racha,
		 * 6 Precipitación (mm),
		 * 7 Presión (hPa),
		 * 8 Tendencia (hPa),
		 * 9 Humedad (%),
		 * 10 WindChill,
		 * 11 I.Confort (ºC)
		 */	
		public static final int FECHAHORA = 0;
		public static final int TEMPERATURA = 1;
		public static final int VELOCIDAD_VIENTO = 2;
		public static final int DIRECCION_VIENTO = 3;
		public static final int RACHA = 4;
		public static final int DIRECCION_RACHA = 5;
		public static final int PRECIPITACION = 6;
		public static final int PRESION = 7;
		public static final int TENDENCIA = 8;
		public static final int HUMEDAD = 9;
		//public static final int VACIO = 10; /* StringTokenizer no consume espacios vacios */
		public static final int WINDCHILL = 10;
		public static final int CONFORT = 11;
	}

	public String toString() 
	{
		return String.format(
				"DatoObservacion [temperatura=%s, viento=%s, racha=%s, precipitacion=%s, " +
				"presion=%s, tendencia=%s, humedad=%s, windchill=%s, confort=%s, %s]",
						temperatura, viento, racha, precipitacion, presion,
						tendencia, humedad, windchill, confort, super.toString());
	}
	
	/* 
	 * Implementamos los metodos hashCode() y equals() necesarios para una correcta 
	 * comparacion.
	 */
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(confort);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(humedad);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(precipitacion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(presion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((racha == null) ? 0 : racha.hashCode());
		temp = Double.doubleToLongBits(temperatura);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tendencia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((viento == null) ? 0 : viento.hashCode());
		temp = Double.doubleToLongBits(windchill);
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
		
		DatoObservacion other = (DatoObservacion) obj;
		if (Double.doubleToLongBits(confort) != Double
				.doubleToLongBits(other.confort))
			return false;
		if (Double.doubleToLongBits(humedad) != Double
				.doubleToLongBits(other.humedad))
			return false;
		if (Double.doubleToLongBits(precipitacion) != Double
				.doubleToLongBits(other.precipitacion))
			return false;
		if (Double.doubleToLongBits(presion) != Double
				.doubleToLongBits(other.presion))
			return false;
		if (racha == null) {
			if (other.racha != null)
				return false;
		} else if (!racha.equals(other.racha))
			return false;
		if (Double.doubleToLongBits(temperatura) != Double
				.doubleToLongBits(other.temperatura))
			return false;
		if (Double.doubleToLongBits(tendencia) != Double
				.doubleToLongBits(other.tendencia))
			return false;
		if (viento == null) {
			if (other.viento != null)
				return false;
		} else if (!viento.equals(other.viento))
			return false;
		if (Double.doubleToLongBits(windchill) != Double
				.doubleToLongBits(other.windchill))
			return false;
		
		return true;
	}
}
