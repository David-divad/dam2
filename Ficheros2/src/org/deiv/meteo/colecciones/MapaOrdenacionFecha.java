package org.deiv.meteo.colecciones;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.deiv.meteo.datos.DatoMeteorologicoConFecha;

public class MapaOrdenacionFecha<T extends DatoMeteorologicoConFecha> extends Coleccion<T> {

	protected Map<Date, T> mapa = new TreeMap<Date, T>();
	
	@Override
    public void añadir(T dato) 
	{
		mapa.put(dato.getFechaHora(), dato);
	}

	@Override
	public void eliminar(T dato) 
	{
		mapa.remove(dato.getFechaHora());
	}

	@Override
	public Collection<T> obtenTodos() 
	{
		return mapa.values();
	}
	
	public Map<Date, T> obtenModelo()
	{
		return mapa;
	}

}
