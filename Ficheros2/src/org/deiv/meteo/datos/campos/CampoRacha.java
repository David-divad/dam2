package org.deiv.meteo.datos.campos;

import java.io.Serializable;

public class CampoRacha implements Serializable {

	private static final long serialVersionUID = -6854383880525708378L;
	
	protected double velocidad = 0.0d;
	protected String direccion = "";
	
	public double getVelocidad()
	{
		return velocidad;
	}
	
	public void setVelocidad(double velocidad)
	{
		this.velocidad = velocidad;
	}
	
	public String getDireccion()
	{
		return direccion;
	}
	
	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public String toString() 
	{
		return String.format("CampoRacha [velocidad=%s, direccion=%s]",
				velocidad, direccion);
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		long temp;
		temp = Double.doubleToLongBits(velocidad);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		CampoRacha other = (CampoRacha) obj;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (Double.doubleToLongBits(velocidad) != Double
				.doubleToLongBits(other.velocidad))
			return false;
		
		return true;
	}
}
