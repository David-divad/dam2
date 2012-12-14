package org.deiv.meteo.datos;

import java.io.Serializable;
import java.util.ArrayList;


public class DatoPrevision12Km extends DatoPrevision4Km implements Serializable {

	private static final long serialVersionUID = 3841895491967693988L;
	
	protected double temperatura = 0.0d;
	
	public DatoPrevision12Km()
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
		
		temperatura        = Util.kelvinACelsius(Double.parseDouble(campos.get(Encabezados.TEMPERATURA)));
	}
	
	public class Encabezados {
		/* 
		 * 12KM
		 * 
		 * 0 date,
		 * 1 lat[unit="degrees_north"],
		 * 2 lon[unit="degrees_east"],
		 * 3 cft[unit="1"],  
		 * 4 dir[unit="degree"], direccion viento
		 * 5 mod[unit="m s-1"],  velocidad viento
		 * 6 prec[unit="kg m-2"],  precipitacion
		 * 7 rh[unit="1"], humedad
		 * 8 t2m[unit="K"], <--- temp a 2 metros
		 * 9 temp[unit="K"],   temperatura
		 * 10 u[unit="m s-1"],
		 * 11 v[unit="m s-1"]
		 */
		public static final int FECHAHORA = 0;
		public static final int LATITUD = 1;
		public static final int LONGITUD = 2;
		public static final int CFT = 3;
		public static final int DIRECCION_VIENTO = 4;
		public static final int VELOCIDAD_VIENTO = 5;
		public static final int PRECIPITACION = 6;
		public static final int HUMEDAD = 7;
		public static final int TEMPERATURA = 8;
		public static final int TEMPERATURA_2METROS = 9;
		public static final int U = 10;
		public static final int V = 11;
	}

	public String toString()
	{
		return String.format( "DatoPrevision12Km [temperatura=%s, %s]",
				temperatura, super.toString());
	}
	
	
}
