package org.deiv.meteo.datos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.deiv.meteo.LectorVSC;

/**
 * Encapsula el comportamiento comun para todos los datos metereologicos
 * que tienen una fecha.
 */
public abstract class DatoMeteorologicoConFecha extends DatoMeteorologico implements LectorVSC.DatoVSC, Serializable {

	private static final long serialVersionUID = 708145103844764374L;
	
	protected Date fecha;
	
	/**
	 * Mapea el campo indicado como fecha-hora.
	 * 
	 * No asumimos, ni que conocemos la posicion de su campo, ni su formato.
	 * Lo delegamos a quien derive.
	 */
	protected abstract void mapeaFechaHora(String fechaHora);
	
	public void copia(DatoMeteorologicoConFecha t)
	{	
		fecha = (Date) t.fecha.clone();
	}
	
	public String toString()
	{
		return fecha.toString();
	}
	
	public Date getFechaHora()
	{
		return fecha;
	}
	
	public boolean esMismoDia(Date fecha)
	{
		GregorianCalendar calendario1 = new GregorianCalendar();
		GregorianCalendar calendario2 = new GregorianCalendar();
		
		calendario1.setTime(this.fecha);
		calendario2.setTime(fecha);

		if (calendario1.get(Calendar.YEAR) == calendario2.get(Calendar.YEAR)) {
			if (calendario1.get(Calendar.DAY_OF_YEAR) == calendario2.get(Calendar.DAY_OF_YEAR)) {
				return true;	
			}
		}
		
		return false;
	}
	
	public boolean esMismoDia(DatoMeteorologicoConFecha d)
	{
		return esMismoDia(fecha);
	}
	
	public String getFecha()
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		dt.applyPattern("dd/MM/yyyy");
		
		return dt.format(fecha);
	}
	
	public String getHora()
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		dt.applyPattern("HH:mm");
		
		return dt.format(fecha);
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
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
		
		DatoMeteorologicoConFecha other = (DatoMeteorologicoConFecha) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		
		return true;
	}
}
