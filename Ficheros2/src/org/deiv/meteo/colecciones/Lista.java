package org.deiv.meteo.colecciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.deiv.meteo.datos.DatoMeteorologico;

public class Lista<T extends DatoMeteorologico> extends Coleccion<T> {

	protected List<T> lista = new ArrayList<T>();
	
	@Override
	public void añadir(T dato) 
	{
		lista.add(dato);
	}

	@Override
	public void eliminar(T dato) 
	{
		lista.remove(dato);
	}
	
	public T obtenDato(int indice)
	{
		return lista.get(indice);
	}
	
	public List<T> obtenModelo()
	{
		return lista;
	}

	@Override
	public Collection<T> obtenTodos() 
	{
		return lista;
	}

}
