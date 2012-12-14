package org.deiv.meteo.discriminadores;

import java.util.Date;

import org.deiv.meteo.datos.DatoMeteorologicoConFecha;

public class DiscriminadorMismoDia<T extends DatoMeteorologicoConFecha> implements Discriminador<T> {

	protected Date fecha;
	
	public DiscriminadorMismoDia(Date fecha)
	{
		this.fecha = fecha;
	}

	@Override
	public boolean discrimina(T dato) 
	{
		if (dato.esMismoDia(fecha))
			return true;
		
		return false;
	}
}
