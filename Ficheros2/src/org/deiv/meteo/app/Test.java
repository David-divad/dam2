package org.deiv.meteo.app;

import java.io.IOException;
import java.util.Iterator;

import org.deiv.meteo.FicheroDatos;
import org.deiv.meteo.FileFormatException;
import org.deiv.meteo.colecciones.MapaOrdenacionFecha;
import org.deiv.meteo.colecciones.Coleccion;
import org.deiv.meteo.datos.DatoMeteorologico;
import org.deiv.meteo.datos.DatoObservacion;
import org.deiv.meteo.datos.DatoPrevision12Km;
import org.deiv.meteo.datos.DatoPrevision4Km;

public class Test {

	public static void main(String[] args)
	{
		String cwd =  System.getProperty("user.dir");
	
		try {
			testEscribirFicheroObjetos(DatoObservacion.class, MapaOrdenacionFecha.class, cwd + "/validacioneswfroviedo - OVDMar2012.txt");
			testEscribirFicheroObjetos(DatoPrevision4Km.class, MapaOrdenacionFecha.class, cwd + "/WRF_04km_OVD_RUN_2012-03-07T00 00 00Z.txt");
			testEscribirFicheroObjetos(DatoPrevision4Km.class, MapaOrdenacionFecha.class, cwd + "/WRF_04km_OVD_RUN_2012-03-07T12 00 00Z.txt");
			testEscribirFicheroObjetos(DatoPrevision12Km.class, MapaOrdenacionFecha.class, cwd + "/WRF_12km_OVD_RUN_2012-03-07T12 00 00Z.txt");	
			
			System.out.println("todos los tests OK");
			
		} catch (FileFormatException e) {
			System.err.format("Error: formato de archivo incorrecto");
			e.printStackTrace();
			
		} catch (IOException e) {
			System.err.format("Error: problemas al leer el archivo");
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T extends DatoMeteorologico, A extends Coleccion<T>>
	boolean testEscribirFicheroObjetos(Class<T> claseT, Class<A> claseA, String fichero)
	throws IOException
	{ 
		String ficheroObj = System.getProperty("user.dir") + "/" + claseT.getSimpleName();
		FicheroDatos<T, A> datos = new FicheroDatos<T, A>(claseT, claseA);
		FicheroDatos<T, A> datosObj = new FicheroDatos<T, A>(claseT, claseA);
		
		System.out.format("analizando el fichero %s que debe contener %s\n", 
				fichero, claseT.getSimpleName());
		
		/* Leemos los datos del fichero VSC... */
		datos.leeDesdeFicheroVSC(fichero, true);

		/* los escribimos a un fichero de objetos... */
		datos.escribeAFicheroObj(ficheroObj);
		
		/* los leemos desde ese nuevo fichero... */
		datosObj.leeDesdeFicheroObj(ficheroObj);
		
		System.out.println(datosObj.toString());
		
		/* y comparamos que tengan los mismos elementos. */
		return testContienenMismosElementos(datos, datosObj);
	}
	
	public static <T extends DatoMeteorologico, A extends Coleccion<T>>
	boolean testContienenMismosElementos(FicheroDatos<T, A> f1, FicheroDatos<T, A> f2)
	{
		Iterator<T> datos1 = f1.getDatos().iterator();
		Iterator<T> datos2 = f2.getDatos().iterator();
		
		while (datos1.hasNext()) {
			
			T d1 = datos1.next();
			
			/* Escasez ? */
			if (datos2.hasNext() == false) {
				System.err.println("Test fallido: La cantidad de datos no coincide (escasez)");
				return false;
			}
			T d2 = datos2.next();
			
			if (!d1.equals(d2)) {
				System.err.println("Test fallido: Los datos no coinciden");
				return false;
			}
		}
		
		/* Exceso? */
		if (datos2.hasNext() == true) { 
			System.err.println("Test fallido: La cantidad de datos no coincide (exceso)");
			return false;
		}
		
		return true;
	}
}

