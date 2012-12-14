package org.deiv.meteo.app;

import java.io.Serializable;
import java.util.ArrayList;

import org.deiv.meteo.datos.DatoMeteorologico;
import org.deiv.meteo.datos.campos.CampoRacha;
import org.deiv.meteo.datos.campos.CampoViento;

public class DatoObservacionAemet extends DatoMeteorologico implements Serializable {

	private static final long serialVersionUID = -4806422220867294082L;
	
	protected String estacion;
	protected String provincia;
	protected double temperatura = 0.0d;
	protected CampoViento viento = new CampoViento();
	protected CampoRacha racha = new CampoRacha();
	protected double precipitacion = 0.0d;
	protected double presion = 0.0d;
	protected double tendencia = 0.0d;
	protected double humedad = 0.0d;
	
	public DatoObservacionAemet()
	{
		
	}
	
	@Override
	public void mapeaCampos(ArrayList<String> campos)
	{
		
		estacion = campos.get(Encabezados.ESTACION);
		provincia = campos.get(Encabezados.PROVINCIA);
		
		/* si los campos estan vacios, no los leemos */
		if (campos.get(Encabezados.TEMPERATURA).isEmpty()) return;
		
		temperatura   = Float.parseFloat(campos.get(Encabezados.TEMPERATURA));
		
		/*precipitacion = Float.parseFloat(campos.get(Encabezados.PRECIPITACION));
		presion       = Float.parseFloat(campos.get(Encabezados.PRESION));
		tendencia     = Float.parseFloat(campos.get(Encabezados.TENDENCIA));
		humedad       = Util.tantox100Atantox1(Float.parseFloat(campos.get(Encabezados.HUMEDAD)));
		
		viento.setDireccion(Util.calculaDireccion(campos.get(Encabezados.DIRECCION_VIENTO)));
		viento.setVelocidad(Util.kmhAms(Float.parseFloat(campos.get(Encabezados.VELOCIDAD_VIENTO))));
		
		racha.setDireccion(Util.calculaDireccion(campos.get(Encabezados.DIRECCION_RACHA)));
		racha.setVelocidad(Util.kmhAms(Float.parseFloat(campos.get(Encabezados.RACHA))));*/
	}
	
	@Override
	public String toString() {
		return String
				.format("DatoObservacionAemet [estacion=%s, provincia=%s, temperatura=%s, viento=%s, racha=%s, precipitacion=%s, presion=%s, tendencia=%s, humedad=%s, toString()=%s]",
						estacion, provincia, temperatura, viento, racha,
						precipitacion, presion, tendencia, humedad,
						super.toString());
	}
	
	public class Encabezados {
		/*
		 * "Estación",
		 * "Provincia",
		 * "Temperatura (ºC)",
		 * "Velocidad del viento (km/h)",
		 * "Dirección del viento",
		 * "Racha (km/h)",
		 * "Dirección de racha",
		 * "Precipitación (mm)",
		 * "Presión (hPa)",
		 * "Tendencia (hPa)",
		 * "Humedad (%)	
		 */
		public static final int ESTACION = 0;
		public static final int PROVINCIA = 1;
		public static final int TEMPERATURA = 2;
		public static final int VELOCIDAD_VIENTO = 3;
		public static final int DIRECCION_VIENTO = 4;
		public static final int RACHA = 5;
		public static final int DIRECCION_RACHA = 6;
		public static final int PRECIPITACION = 7;
		public static final int PRESION = 8;
		public static final int TENDENCIA = 9;
		public static final int HUMEDAD = 10;

	}

}
