package org.deiv.meteo;

public class Util {

	private static final double KELVIN_TO_CELSIUS = 273.15d;
	
	/*  Cº = K - 273,15 */
	public static final double kelvinACelsius(double kelvin)
	{
		return kelvin - KELVIN_TO_CELSIUS;
	}
	
	public static final double calculaDireccion(String direccion)
	{
		if (direccion.equalsIgnoreCase("Norte")) {
			return 0.0d;
		}
		if (direccion.equalsIgnoreCase("Sur")) {
			return 180.0d;
		}
		if (direccion.equalsIgnoreCase("Este")) {
			return 90.0d;
		}
		if (direccion.equalsIgnoreCase("Oeste")) {
			return 270.0d;
		}
		if (direccion.equalsIgnoreCase("Nordeste")) {
			return 45.0d;
		}
		if (direccion.equalsIgnoreCase("Noroeste")) {
			return 315.0d;
		}
		if (direccion.equalsIgnoreCase("Sudeste")) {
			return 135.0d;
		}
		if (direccion.equalsIgnoreCase("Sudoeste")) {
			return 225.0d;
		}
		if (direccion.equalsIgnoreCase("Calma")) {
			return -1.0d;
		}
		
		return -360.0d;
	}
}
