package org.deiv.meteo.discriminadores;

import java.util.Date;

import org.deiv.meteo.datos.DatoMeteorologicoConFecha;

public class DiscriminadorEntreFechas<T extends DatoMeteorologicoConFecha> implements Discriminador<T> {

	protected Date fechaPosterior;
	protected Date fechaAnterior;
	
	public DiscriminadorEntreFechas(Date posterior, Date anterior)
	{
		fechaPosterior = posterior;
		fechaAnterior = anterior;
	}

	@Override
	public boolean discrimina(T dato) 
	{
		if (dato.getFechaHora().after(fechaPosterior)) {
			if (dato.getFechaHora().before(fechaAnterior)) {
				return true;
			}
		}
		
		return false;
	}
}
