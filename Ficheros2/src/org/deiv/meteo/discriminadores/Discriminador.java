package org.deiv.meteo.discriminadores;

import org.deiv.meteo.datos.DatoMeteorologico;

public interface Discriminador <T extends DatoMeteorologico>{

	public boolean discrimina(T dato);
}
