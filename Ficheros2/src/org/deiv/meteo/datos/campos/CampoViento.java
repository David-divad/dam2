package org.deiv.meteo.datos.campos;

import java.io.Serializable;

public class CampoViento implements Serializable {

	private static final long serialVersionUID = 2573851117856836938L;
	
	/* m/s */
	protected double velocidad = 0.0d;
	protected double direccion = 0.0d;
	
	public double getVelocidad()
	{
		return velocidad;
	}
	
	public void setVelocidad(double velocidad)
	{
		this.velocidad = velocidad;
	}
	
	public double getDireccion() 
	{
		return direccion;
	}
	
	public void setDireccion(double direccion) 
	{
		this.direccion = direccion;
	}
	
	public String toString()
	{
		return String.format("CampoViento [velocidad=%s, direccion=%s]",
				velocidad, direccion);
	}
	
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(direccion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		
		CampoViento other = (CampoViento) obj;
		if (Double.doubleToLongBits(direccion) != Double
				.doubleToLongBits(other.direccion))
			return false;
		if (Double.doubleToLongBits(velocidad) != Double
				.doubleToLongBits(other.velocidad))
			return false;
		
		return true;
	}
}