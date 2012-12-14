package org.deiv.meteo.colecciones;

import java.util.Collection;

import org.deiv.meteo.datos.DatoMeteorologico;

public abstract class Coleccion<T extends DatoMeteorologico> {

	public abstract void añadir(T dato);
	public abstract void eliminar(T dato);
	
	public abstract Collection<T> obtenTodos();
}
