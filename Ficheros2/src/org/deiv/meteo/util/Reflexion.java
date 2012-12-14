package org.deiv.meteo.util;

import java.lang.reflect.Constructor;

public final class Reflexion {

	static public final <T> T nuevaInstancia(Class<T> clase)
	{
		T datos;
		
		try {
			Constructor<T> ctor = clase.getConstructor();
			datos = (T) ctor.newInstance();
			 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return datos;
	}
}
