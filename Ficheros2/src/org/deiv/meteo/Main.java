package org.deiv.meteo;

import java.io.IOException;
import java.util.Iterator;

import org.deiv.meteo.datos.DatoMeteorologico;
import org.deiv.meteo.datos.DatoObservacion;
import org.deiv.meteo.datos.DatoPrevision12Km;
import org.deiv.meteo.datos.DatoPrevision4Km;

public class Main {

	public static void main(String[] args)
	{
		try {
			testEscribirFicheroObjetos(DatoObservacion.class, "/home/deiv/clase/workspace/Ficheros/validacioneswfroviedo - OVDMar2012.txt");
			testEscribirFicheroObjetos(DatoPrevision4Km.class, "/home/deiv/WRF_04km_OVD_RUN_2012-03-07T00 00 00Z.txt");
			testEscribirFicheroObjetos(DatoPrevision4Km.class, "/home/deiv/WRF_04km_OVD_RUN_2012-03-07T12 00 00Z.txt");
			testEscribirFicheroObjetos(DatoPrevision12Km.class, "/home/deiv/WRF_12km_OVD_RUN_2012-03-07T12 00 00Z.txt");	
			
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
	
	public static <T extends DatoMeteorologico>
	boolean testEscribirFicheroObjetos(Class<T> clase, String fichero) throws IOException
	{
		String ficheroObj = "/home/deiv/observaciones." + clase.getSimpleName();
		FicheroDatos<T> datos = new FicheroDatos<T>(clase);
		FicheroDatos<T> datosObj = new FicheroDatos<T>(clase);
		
		System.out.format("analizando el fichero %s que debe contener %s\n", 
				fichero, clase.getSimpleName());
		
		/* Leemos los datos del fichero VSC... */
		datos.leeDesdeFicheroVSC(fichero, true);

		/* los escribimos a un fichero de objetos... */
		datos.escribeAFicheroObj(ficheroObj);
		
		/* los leemos desde ese nuevo fichero... */
		datosObj.leeDesdeFicheroObj(ficheroObj);
		
		//System.out.println(datosObj.toString());
		
		/* y comparamos que tengan los mismos elementos. */
		return testContienenMismosElementos(datos, datosObj);
	}
	
	public static <T extends DatoMeteorologico>
	FicheroDatos<T> leeFichero(Class<T> clase, String fichero)
	{
		FicheroDatos<T> datos = new FicheroDatos<T>(clase);
		
		try {
			datos.leeDesdeFicheroVSC(fichero, true);
			
		} catch (IOException e) {
			System.err.format("Error: no se puede leer el archivo %s\n", fichero);
		}
		
		return datos;
	}
	
	public static <T extends DatoMeteorologico>
	boolean testContienenMismosElementos(FicheroDatos<T> f1, FicheroDatos<T> f2)
	{
		Iterator<T> datos1 = f1.getDatos().values().iterator();
		Iterator<T> datos2 = f2.getDatos().values().iterator();
		
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

