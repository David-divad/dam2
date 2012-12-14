package org.deiv.meteo.datos;

import java.io.Serializable;
import java.util.ArrayList;

import org.deiv.meteo.LectorVSC;

/**
 * Encapsula el comportamiento comun para todos los datos metereologicos
 * que estamos manejando.
 */
public abstract class DatoMeteorologico implements LectorVSC.DatoVSC, Serializable {

	private static final long serialVersionUID = 708145103844764374L;

	
	public abstract void mapeaCampos(ArrayList<String> campos);

	public String toString()
	{
		return "";
	}
}
