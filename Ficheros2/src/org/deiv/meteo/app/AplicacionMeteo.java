package org.deiv.meteo.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.deiv.meteo.FileFormatException;
import org.deiv.meteo.discriminadores.DiscriminadorEntreFechas;


/*
 * Realizar una aplicación en Java que muestre en pantalla el estado actual del tiempo
 * y la previsión meteorológica para las próximas 24 horas en la zona de Oviedo.
 * 
 * Para obtener el estado actual del tiempo, se parte de los datos sumistrados horariamente
 * por AEMET en formato csv.
 * 
 * http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_principado-de-asturias_yyyymmddhh.csv?k=ast&datos=det&w=0&f=temperatura&x=h24
 * 
 * yyyy año  cuatro dígitos
 * mm   mes  (01-12)
 * dd   día  (01-31)
 * hh   hora (00-23)
 * 
 * Para la previsión, se utilizará la última ejecución del modelo numérico GFS con resolución de 25 Km.
 * disponible para su descarga en la dirección URL siguiente:
 * 
 * Url de la ultima Run disponible del GFS 2.5
 * ID: gfs_25_yyyymmdd_hh00.grib2
 * yyyy año  cuatro dígitos
 * mm   mes  (01-12)
 * dd   día  (01-31)
 * hh   hora (00|12)
 * La ejecución de las 00 no está disponible hasta las 7 a.m. y la de las 12 hasta las 7 p.m.
 * 
 * http://mandeo.meteogalicia.es/thredds/ncss/grid/gfs_25/fmrc/files/gfs_25_yyyymmdd_hh00.grib2?
 * var=Pressure_reduced_to_MSL&var=Temperature_surface&var=Total_cloud_cover&var=
 * Total_precipitation&latitude=43.353333333333333&longitude=-5.874166666666667&temporal=
 * all&vertCoord=&accept=csv&point=true
 * 
 * Debe tenerse en cuenta que en los datos de AEMET, las horas están en horario civil, mientras que
 * en los del GFS lo están en UTC (hora civil=hora UTC +1)
 */
public class AplicacionMeteo {
	
	protected static final String URL_DATOS_TEMPERATURA = 
			"http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_principado-de-asturias_yyyymmddhh.csv?k=ast&datos=det&w=0&f=temperatura&x=h24";
	protected static final String URL_PREVISIONES = 
			"http://mandeo.meteogalicia.es/thredds/ncss/grid/gfs_25/fmrc/files/%s/gfs_05_%s.grib2?var=Pressure_reduced_to_MSL&var=Temperature_surface&var=Total_cloud_cover&var=Total_precipitation&latitude=43.353333333333333&longitude=-5.874166666666667&temporal=all&vertCoord=&accept=csv&point=true";
	
	public static void main(String args[])
	{
		AplicacionMeteo app = new AplicacionMeteo();
	}
	
	public AplicacionMeteo()
	{
		FicheroDatosAemet ficheroAemet;
		FicheroPrevisionGfs ficheroPrevision;
	
		Date fechaActual = new Date();
		Date fecha24Hposterior = sumaUnDia(fechaActual);
		
		try {
			ficheroAemet = obtenFicheroAemet();
			ficheroAemet.discrimina(new DiscriminadorEstacionAemet("oviedo"));
			
			ficheroPrevision = obtenFicheroPrevision(fechaActual);
			ficheroPrevision.discrimina(
					new DiscriminadorEntreFechas<DatoPrevisionGfs5Km>(fechaActual, fecha24Hposterior));
			
			imprimeDatos(fechaActual, ficheroAemet, ficheroPrevision);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (NetworkException e) {
			e.printStackTrace();
		}	
	}
	
	public FicheroDatosAemet obtenFicheroAemet() 
	throws MalformedURLException, FileFormatException, IOException, NetworkException
	{
		InputStreamReader flujoEntrada = obtenFlujoDeRed(URL_DATOS_TEMPERATURA);
		
		BufferedReader lector = new BufferedReader(flujoEntrada);
		//BufferedReader lector = new BufferedReader(new FileReader("/home/deiv/ultimosdatos_principado-de-asturias_yyyymmddhh.csv")/*flujoEntrada*/);
		FicheroDatosAemet datos = new FicheroDatosAemet();
		
		datos.leeDesdeFicheroVSC(lector);
		
		return datos;
	}
	
	public FicheroPrevisionGfs obtenFicheroPrevision(Date fecha) 
	throws MalformedURLException, FileFormatException, IOException, NetworkException
	{
		String url = construyeUrlPrevision(fecha);

		InputStreamReader flujoEntrada = obtenFlujoDeRed(url);
		
		BufferedReader lector = new BufferedReader(flujoEntrada);
		//BufferedReader lector = new BufferedReader(new FileReader("/home/deiv/gfs_05_20121213_0000.grib2")/*flujoEntrada*/);
		
		FicheroPrevisionGfs datos = new FicheroPrevisionGfs();
		
		datos.leeDesdeFicheroVSC(lector, true);
		
		return datos;
	}
	
	protected String construyeUrlPrevision(Date fecha)
	{
		SimpleDateFormat df = new SimpleDateFormat();
		
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(fecha);

		int hora = calendario.get(Calendar.HOUR_OF_DAY);
		
		if( hora < 7 || hora > 19 )
			hora = 12;
		else
			hora = 0;
		
		calendario.set(Calendar.HOUR_OF_DAY, hora);
		
		df.applyPattern("yyyyMMdd'_'HH'00'");
		String fechaHoraFormateada = df.format(calendario.getTime());
		
		df.applyPattern("yyyyMMdd");
		String fechaFormateada = df.format(calendario.getTime());
		
		return String.format(URL_PREVISIONES, fechaFormateada, fechaHoraFormateada);
	}

	InputStreamReader obtenFlujoDeRed(String urlFichero)
	throws MalformedURLException, FileFormatException, IOException, NetworkException
	{
		URL url = new URL(urlFichero);
		URLConnection conn = url.openConnection();
	    
		String contentType = conn.getContentType();

		if (contentType == null)
			throw new NetworkException();
			
		/* solo aceptamos contenido con texto */
		if(contentType.contains("text/")) {
			
			String conjuntoCaracteres = obtenConjuntoCaracteres(contentType);
			
			if (!conjuntoCaracteres.isEmpty()) {
				/* informamos al flujo de cual es su conjunto de caracteres */
				return new InputStreamReader(conn.getInputStream(), conjuntoCaracteres);
				
			} else {
				/* conjunto de caracteres desconocido */
				return new InputStreamReader(conn.getInputStream());
			}
			
		} else {
			throw new FileFormatException();
		}
	}
	
	protected String obtenConjuntoCaracteres(String contentType)
	{
		final String CHARSET_CONTENIDO_HTTP = "charset=";
		
		String conjunto = "";
		String[] valores = contentType.split(";");

		for (String valor : valores) {
			valor = valor.trim();

		    if (valor.toLowerCase().startsWith(CHARSET_CONTENIDO_HTTP)) {
		    	conjunto = valor.substring(CHARSET_CONTENIDO_HTTP.length());
		        break;
		    }
		}
		
		return conjunto;
	}
	
	protected Date sumaUnDia(Date fecha)
	{
		GregorianCalendar calendario = new GregorianCalendar();
		
		calendario.setTime(fecha);
		calendario.add(Calendar.DAY_OF_MONTH, 1);
		
		return calendario.getTime();
	}
	
	protected void imprimeDatos(Date fechaActual, 
			FicheroDatosAemet ficheroAemet, FicheroPrevisionGfs ficheroPrevision)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		dt.applyPattern("HH:mm' del 'd/M/yyyy");
		
		System.out.format("Son las %s\n", dt.format(fechaActual));
		System.out.format("La temperatura actual en Oviedo es de: %.1fº centígrados (última actualización: %s)\n", 
				ficheroAemet.obtenAlmacen().obtenDato(0).temperatura, 
				dt.format(ficheroAemet.fechaUltimaActualizacion));
		
		System.out.println("La previsión para las próximas 24 horas es:");
		/*for (DatoPrevisionGfs5Km dato : ficheroPrevision.getDatos()) {
			System.out.format("\t- A las %s del %s habra una temperatura de %.1fº centigrados\n", 
					dato.getHora(), dato.getFecha(), dato.temperatura);
		}*/
		
		imprimeGrafico(ficheroPrevision);
	}
	
	protected void imprimeGrafico(FicheroPrevisionGfs ficheroPrevision)
	{
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for (DatoPrevisionGfs5Km dato : ficheroPrevision.getDatos()) {
			
			if (dato.temperatura > max)
				max = dato.temperatura;
			
			if (dato.temperatura < min)
				min = dato.temperatura;
		}
		
		final double TAMAÑO_PASO = 0.20d;
		final char SEPARADOR_VERTICAL = '-';
		
		min -= TAMAÑO_PASO*2;
		max += TAMAÑO_PASO;
		
		int pasos = (int) ( (max - min) / TAMAÑO_PASO);
		
		/* margen */
		System.out.format("\n");
		
		/* barras temperatura */
		for (int c=pasos; c > 0; c--) {

			System.out.format("\t% 5.1fºC |", max);
			
			for (DatoPrevisionGfs5Km dato : ficheroPrevision.getDatos()) {
				char caracter = ' ';
				if (dato.temperatura >= (max-0.001d))
					caracter = 'x';
				
				System.out.format(" %1$s%1$s%1$s%1$s%1$s |", caracter);
			}
			
			System.out.format("\n");
			max -= TAMAÑO_PASO;
		}
		
		/* separador horas */
		System.out.format("\t%1$c%1$c%1$c%1$c%1$c%1$c%1$c%1$c|", SEPARADOR_VERTICAL);
		for (int c = 0; c < ficheroPrevision.getDatos().size(); c++) {
			System.out.format("%1$c%1$c%1$c%1$c%1$c%1$c%1$c|", SEPARADOR_VERTICAL);
		}
		System.out.format("\n");
		
		/* horas */
		System.out.format("\t        |");
		for (DatoPrevisionGfs5Km dato : ficheroPrevision.getDatos()) {
			System.out.format(" %s |", dato.getHora());
		}
		System.out.format("\n");
		
		/* margen */
		System.out.format("\n");
	}
}

class NetworkException extends Exception {

	private static final long serialVersionUID = -1835376312084336316L;
}
