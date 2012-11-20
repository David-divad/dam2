package org.deiv.meteo.datos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.deiv.meteo.Util;
import org.deiv.meteo.datos.campos.CampoViento;

public class DatoPrevision4Km extends DatoMeteorologico implements Serializable {
	
	private static final long serialVersionUID = -8491872952268123213L;
	
	protected double latitud = 0.0d;
	protected double longitud = 0.0d;
	protected double cft = 0.0d;
	protected CampoViento viento = new CampoViento();
	protected double precipitacion = 0.0d;	
	protected double humedad = 0.0d;
	protected double temperatura2Metros = 0.0d;
	protected double u = 0.0d;
	protected double v = 0.0d;
	
	public DatoPrevision4Km()
	{	

	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		mapeaFechaHora(campos.get(Encabezados.FECHAHORA));
		
		latitud            = Double.parseDouble(campos.get(Encabezados.LATITUD));
		longitud           = Double.parseDouble(campos.get(Encabezados.LONGITUD));
		cft                = Double.parseDouble(campos.get(Encabezados.CFT));
		precipitacion      = Double.parseDouble(campos.get(Encabezados.PRECIPITACION));
		humedad            = Double.parseDouble(campos.get(Encabezados.HUMEDAD));
		u                  = Double.parseDouble(campos.get(Encabezados.U));
		v                  = Double.parseDouble(campos.get(Encabezados.V));
		
		temperatura2Metros = Util.kelvinACelsius(Double.parseDouble(campos.get(Encabezados.TEMPERATURA_2METROS)));

		viento.setDireccion(Double.parseDouble(campos.get(Encabezados.DIRECCION_VIENTO)));
		viento.setVelocidad(Double.parseDouble(campos.get(Encabezados.VELOCIDAD_VIENTO)));
	}
	
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
	
	public class Encabezados {
		/*
		 * 4KM
		 * 
		 * 0 date
		 * 1 lat[unit="degrees_north"]
		 * 2 lon[unit="degrees_east"]
		 * 3 cft[unit="1"]
		 * 4 dir[unit="degree"]
		 * 5 mod[unit="m s-1"]
		 * 6 prec[unit="kg m-2"]
		 * 7 rh[unit="1"]
		 * 8 t2m[unit="K"]
		 * 9 u[unit="m s-1"]
		 * 10 v[unit="m s-1"]
		 */
		public static final int FECHAHORA = 0;
		public static final int LATITUD = 1;
		public static final int LONGITUD = 2;
		public static final int CFT = 3;
		public static final int DIRECCION_VIENTO = 4;
		public static final int VELOCIDAD_VIENTO = 5;
		public static final int PRECIPITACION = 6;
		public static final int HUMEDAD = 7;
		public static final int TEMPERATURA_2METROS = 8;
		public static final int U = 9;
		public static final int V = 10;
	}

	@Override
	public String toString() {
		return String.format(
				"DatoPrevision4Km [latitud=%s, longitud=%s, cft=%s, viento=%s,"+
				"precipitacion=%s, humedad=%s, temperatura2Metros=%s, u=%s, v=%s, %s]",
				latitud, longitud, cft, viento, precipitacion, humedad,
				temperatura2Metros, u, v, super.toString());
	}
	

}
