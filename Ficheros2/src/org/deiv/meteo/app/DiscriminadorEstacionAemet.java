package org.deiv.meteo.app;

import org.deiv.meteo.discriminadores.Discriminador;

public class DiscriminadorEstacionAemet implements Discriminador<DatoObservacionAemet> {

	protected String estacion;
	
	public DiscriminadorEstacionAemet(String estacion)
	{
		this.estacion = estacion;
	}
	
	@Override
	public boolean discrimina(DatoObservacionAemet dato) 
	{
		if (dato.estacion.equalsIgnoreCase(this.estacion))
			return true;
		
		return false;
	}
}
