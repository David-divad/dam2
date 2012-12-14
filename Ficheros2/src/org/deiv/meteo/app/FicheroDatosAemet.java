package org.deiv.meteo.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.deiv.meteo.FicheroDatos;
import org.deiv.meteo.FileFormatException;
import org.deiv.meteo.LectorVSCComillas;
import org.deiv.meteo.colecciones.Lista;

public class FicheroDatosAemet 
extends FicheroDatos<DatoObservacionAemet, Lista<DatoObservacionAemet>> implements Serializable {
	
	private static final long serialVersionUID = -1915370548748319678L;
	
	protected String region;
	protected Date fecha;
	protected Date fechaUltimaActualizacion;

	public FicheroDatosAemet()
	{
		super(DatoObservacionAemet.class, Lista.class);
	}

	public void leeDesdeFicheroVSC(BufferedReader flujo) 
	throws FileFormatException, FileNotFoundException, IOException
	{
		/* 
		 * "Principado de Asturias"
		 * Actualizado: lunes, 10 diciembre 2012 a las 18:32 hora oficial
		 * Fecha y hora: lunes, 10 diciembre 2012 a las 18:00
		 */
		region = flujo.readLine();
		mapeaFechaActualizacion(flujo.readLine());
		mapeaFechaHora(flujo.readLine());
		flujo.readLine();
		
		LectorVSCComillas<DatoObservacionAemet> lector = new LectorVSCComillas<DatoObservacionAemet>(flujo, DatoObservacionAemet.class);
		
		super.leeDesdeFicheroVSC(lector, true);
	}
	
	protected void mapeaFechaHora(String fechaHora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		// Fecha y hora: lunes, 10 diciembre 2012 a las 16:00
		dt.applyPattern("'Fecha y hora:' E',' d MMMM yyyy 'a las' HH:mm");
		
		try {
			fecha =  dt.parse(fechaHora);
			
		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	protected void mapeaFechaActualizacion(String fechaHora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		// Actualizado: lunes, 10 diciembre 2012 a las 18:32 hora oficial
		dt.applyPattern("'Actualizado:' E',' d MMMM yyyy 'a las' HH:mm 'hora oficial'");
		
		try {
			fechaUltimaActualizacion =  dt.parse(fechaHora);
			
		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
}
